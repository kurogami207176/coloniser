package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.draw.BackgroundDrawer;
import com.alaindroid.coloniser.draw.HexGridDrawer;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.CellGeneratorService;
import com.alaindroid.coloniser.service.GridGeneratorService;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MainGameState implements GameState {
    final HexGridDrawer hexGridDrawer;
    final BackgroundDrawer backgroundDrawer;
    final GridGeneratorService gridGeneratorService;
    final CellGeneratorService cellGeneratorService;

    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    Grid grid;

    @Override
    public void onCreate() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        grid = gridGeneratorService.generateGrid(4, cellGeneratorService);
        backgroundDrawer.create();
        hexGridDrawer.create();
    }

    @Override
    public void onRender(float deltaTime) {
        spriteBatch.begin();
        backgroundDrawer.draw(spriteBatch);
        hexGridDrawer.draw(shapeRenderer, spriteBatch, 100, 50, 400, 400, grid);
        spriteBatch.end();
    }

    @Override
    public void onDispose() {
        hexGridDrawer.dispose();
        backgroundDrawer.dispose();
    }
}
