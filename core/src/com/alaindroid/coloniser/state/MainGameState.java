package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.draw.HexGridDrawer;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.GridImpl;
import com.alaindroid.coloniser.grid.hex.HexCell;
import com.alaindroid.coloniser.service.GridGeneratorService;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.inject.Inject;
import java.util.Set;

public class MainGameState implements GameState{
    @Inject
    HexGridDrawer hexGridDrawer;

    @Inject
    GridGeneratorService gridGeneratorService;

    ShapeRenderer shapeRenderer;
    Grid grid;

    @Override
    public void onCreate() {
        shapeRenderer = new ShapeRenderer();
        grid = new GridImpl(false);
        Coordinate origin = new Coordinate(new int[] {0, 0, 0});
        Set<Coordinate> coordinates = gridGeneratorService.generateNeighbors(origin);
        grid.cell(origin, new HexCell());
        coordinates.forEach(coordinate -> grid.cell(origin, new HexCell()));
    }

    @Override
    public void onRender(float deltaTime) {
        hexGridDrawer.draw(shapeRenderer, 0, 0, 40, 400, grid,10);
    }

    @Override
    public void onDispose() {

    }
}
