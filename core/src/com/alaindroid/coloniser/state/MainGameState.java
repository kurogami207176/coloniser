package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.draw.HexGridDrawer;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.CellGeneratorService;
import com.alaindroid.coloniser.service.GridGeneratorService;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MainGameState implements GameState {
    final HexGridDrawer hexGridDrawer;
    final GridGeneratorService gridGeneratorService;
    final CellGeneratorService cellGeneratorService;

    ShapeRenderer shapeRenderer;
    Grid grid;

    @Override
    public void onCreate() {
        shapeRenderer = new ShapeRenderer();
        grid = gridGeneratorService.generateGrid(4, cellGeneratorService);
    }

    @Override
    public void onRender(float deltaTime) {
        hexGridDrawer.draw(shapeRenderer, 100, 50, 400, 400, grid,20);
    }

    @Override
    public void onDispose() {

    }
}
