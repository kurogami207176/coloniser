package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.draw.BackgroundDrawer;
import com.alaindroid.coloniser.draw.SpriteDrawer;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.inputs.GameStateInputProcessor;
import com.alaindroid.coloniser.service.DecisionService;
import com.alaindroid.coloniser.service.GamespeedService;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.service.animation.AnimationProcessorService;
import com.alaindroid.coloniser.service.generator.GridGeneratorService;
import com.alaindroid.coloniser.service.generator.UnitGenerator;
import com.alaindroid.coloniser.service.grid.CellGeneratorService;
import com.alaindroid.coloniser.units.Unit;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class MainGameState implements GameState {
    final SpriteDrawer spriteDrawer;
    final BackgroundDrawer backgroundDrawer;
    final GridGeneratorService gridGeneratorService;
    final UnitGenerator unitGenerator;
    final CellGeneratorService cellGeneratorService;
    final NavigationService navigationService;
    final DecisionService decisionService;
    final GamespeedService gamespeedService;
    final AnimationProcessorService animationProcessorService;

    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    SpriteBatch bgSpriteBatch;
    SpriteBatch spriteBatch;
    GameSave gameSave;
    float width;
    float height;

    @Override
    public void onCreate() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        bgSpriteBatch = new SpriteBatch();
        width = isAndroid()
                ? Gdx.graphics.getWidth() / Gdx.graphics.getDensity()
                : 800;
        height = isAndroid()
                ? Gdx.graphics.getHeight() / Gdx.graphics.getDensity()
                : 600;
        camera = new OrthographicCamera(width, height);

        Grid grid = gridGeneratorService.generateGrid(10, cellGeneratorService, 38);
        List<Unit> units = unitGenerator.generate(grid, 3, 3);
        Set<Player> players = new HashSet<>();
        gameSave = new GameSave(grid, units, players);
        backgroundDrawer.create();
        spriteDrawer.create();

        Gdx.input.setInputProcessor(new GameStateInputProcessor(camera, gameSave, decisionService, navigationService));
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

        spriteDrawer.draw(spriteBatch, gameSave);

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
