package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.draw.BackgroundDrawer;
import com.alaindroid.coloniser.draw.HexGridDrawer;
import com.alaindroid.coloniser.draw.UnitDrawer;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.GamespeedService;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.service.generator.GridGeneratorService;
import com.alaindroid.coloniser.service.generator.UnitGenerator;
import com.alaindroid.coloniser.service.grid.CellGeneratorService;
import com.alaindroid.coloniser.units.Unit;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MainGameState implements GameState {
    final HexGridDrawer hexGridDrawer;
    final UnitDrawer unitDrawer;
    final BackgroundDrawer backgroundDrawer;
    final GridGeneratorService gridGeneratorService;
    final UnitGenerator unitGenerator;
    final CellGeneratorService cellGeneratorService;
    final NavigationService navigationService;
    final GamespeedService gamespeedService;

    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    SpriteBatch bgSpriteBatch;
    SpriteBatch spriteBatch;
    Grid grid;
    List<Unit> units;

    @Override
    public void onCreate() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        bgSpriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(800, 600);

        grid = gridGeneratorService.generateGrid(7, cellGeneratorService, 38);
        units = unitGenerator.generate(grid);
        backgroundDrawer.create();
        hexGridDrawer.create();
        unitDrawer.create();
    }

    @Override
    public void onRender(float deltaTime) {
        if (gamespeedService.tick(deltaTime)) {
            units.forEach( unit -> navigationService.navigate(unit, grid) );
        }
        bgSpriteBatch.begin();
        backgroundDrawer.draw(bgSpriteBatch);
        bgSpriteBatch.end();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        hexGridDrawer.draw(shapeRenderer, spriteBatch, grid);
        unitDrawer.draw(shapeRenderer, spriteBatch, units, gamespeedService.tickerPercent());

        spriteBatch.end();
    }

    @Override
    public void onDispose() {
        hexGridDrawer.dispose();
        backgroundDrawer.dispose();
        unitDrawer.dispose();
    }
}
