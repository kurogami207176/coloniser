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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    @Override
    public void onCreate() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        bgSpriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(800, 600);

        Grid grid = gridGeneratorService.generateGrid(10, cellGeneratorService, 38);
        List<Unit> units = unitGenerator.generate(grid, 3, 3);
        gameSave = new GameSave(grid, units);
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
        backgroundDrawer.draw(bgSpriteBatch);
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

}
