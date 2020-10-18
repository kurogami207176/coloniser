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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MainGameState implements GameState {
    public static final int OFFSET_X = 100;
    public static final int OFFSET_Y = 50;
    public static final int DRAW_WIDTH = 400;
    public static final int DRAW_HEIGHT = 400;
    final HexGridDrawer hexGridDrawer;
    final UnitDrawer unitDrawer;
    final BackgroundDrawer backgroundDrawer;
    final GridGeneratorService gridGeneratorService;
    final UnitGenerator unitGenerator;
    final CellGeneratorService cellGeneratorService;
    final NavigationService navigationService;
    final GamespeedService gamespeedService;

    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    Grid grid;
    List<Unit> units;

    @Override
    public void onCreate() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        grid = gridGeneratorService.generateGrid(4, cellGeneratorService);
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
        spriteBatch.begin();
        backgroundDrawer.draw(spriteBatch);
        hexGridDrawer.draw(shapeRenderer, spriteBatch, OFFSET_X, OFFSET_Y, DRAW_WIDTH, DRAW_HEIGHT, grid);
        spriteBatch.end();
        unitDrawer.draw(shapeRenderer, spriteBatch, OFFSET_X, OFFSET_Y, DRAW_WIDTH, DRAW_HEIGHT, units);
    }

    @Override
    public void onDispose() {
        hexGridDrawer.dispose();
        backgroundDrawer.dispose();
        unitDrawer.dispose();
    }
}
