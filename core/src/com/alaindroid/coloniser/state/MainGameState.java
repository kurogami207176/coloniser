package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.draw.BackgroundDrawer;
import com.alaindroid.coloniser.draw.SpriteDrawer;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.inputs.GameControllerListener;
import com.alaindroid.coloniser.service.DecisionService;
import com.alaindroid.coloniser.service.GamespeedService;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.service.PlayerViewFilterService;
import com.alaindroid.coloniser.service.animation.AnimationProcessorService;
import com.alaindroid.coloniser.service.generator.GridGeneratorService;
import com.alaindroid.coloniser.service.generator.UnitGenerator;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.util.Constants;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class MainGameState implements GameState {
    final SpriteDrawer spriteDrawer;
    final BackgroundDrawer backgroundDrawer;
    final GridGeneratorService gridGeneratorService;
    final UnitGenerator unitGenerator;
    final NavigationService navigationService;
    final DecisionService decisionService;
    final GamespeedService gamespeedService;
    final AnimationProcessorService animationProcessorService;
    final PlayerViewFilterService playerViewFilterService;

    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    SpriteBatch bgSpriteBatch;
    SpriteBatch spriteBatch;
    GameSave gameSave;
    float width;
    float height;
    Player player1;
    Player player2;

    @Override
    public void onCreate() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        bgSpriteBatch = new SpriteBatch();
        width = isAndroid()
                ? Gdx.graphics.getWidth() / Gdx.graphics.getDensity()
                : 1280;
        height = isAndroid()
                ? Gdx.graphics.getHeight() / Gdx.graphics.getDensity()
                : 960;
        camera = new OrthographicCamera(width, height);
        Coordinate minRGB = new Coordinate(-50, -50, -50, Constants.HEX_SIDE_LENGTH);
        Coordinate maxRGB = new Coordinate(50, 50, 50, Constants.HEX_SIDE_LENGTH);
        Grid grid = gridGeneratorService.initGrid(10, minRGB, maxRGB, Constants.HEX_SIDE_LENGTH);

        Set<Player> players = new HashSet<>();
        player1 = new Player(Player.Color.RED);
        player2 = new Player(Player.Color.GREEN);
        players.add(player1);
        players.add(player2);

        List<Unit> units1 = unitGenerator.generate(player1, 3, 3);
        List<Unit> units2 = unitGenerator.generate(player2, 3, 3);

        List<Unit> units = new ArrayList<>();
        units.addAll(units1);
        units.addAll(units2);
        for(Unit unit: units) {
            unitGenerator.randomCoordinate(unit, grid, units);
            Set<Coordinate> visible = navigationService.visible(unit.coordinate(), grid, unit.unitType().range());
            visible.forEach(unit.player().seenCoordinates()::add);
        }

        gameSave = new GameSave(grid, units, players);
        gameSave.currentPlayer(player1);
        backgroundDrawer.create();
        spriteDrawer.create();

//        Gdx.input.setInputProcessor(new GameStateInputProcessor(camera, gameSave, decisionService, navigationService));
        Gdx.input.setInputProcessor(new GestureDetector(new GameControllerListener(camera, gameSave, decisionService, navigationService)));
    }

    @Override
    public void onRender(float deltaTime) {
        if (gamespeedService.tick(deltaTime)) {
            // TODO: Do something?
        }
        animationProcessorService.processAnimation(gameSave, deltaTime);
        bgSpriteBatch.begin();
        backgroundDrawer.draw(bgSpriteBatch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgSpriteBatch.end();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        System.out.println("Rendering grid hex: " + gameSave.grid().cells().keySet().size());

//        spriteDrawer.draw(spriteBatch, gameSave);
        spriteDrawer.draw(spriteBatch, playerViewFilterService.filterGameSave(gameSave));

        spriteBatch.end();
    }

    @Override
    public void onDispose() {
        spriteDrawer.dispose();
        backgroundDrawer.dispose();
    }

    private boolean isAndroid() {
        return Gdx.app.getType().equals(Application.ApplicationType.Android);
    }

}
