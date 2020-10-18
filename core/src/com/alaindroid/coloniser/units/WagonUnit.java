package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.TileType;

public class WagonUnit extends Unit {
    public WagonUnit() {
        super(UnitType.WAGON);
        traversalSpeed().put(TileType.GRASS, 1f);
        traversalSpeed().put(TileType.DIRT, 1f);
        traversalSpeed().put(TileType.SAND, 0.5f);
        traversalSpeed().put(TileType.SNOW, 0.2f);
        traversalSpeed().put(TileType.AUTUMN, 1f);
        traversalSpeed().put(TileType.ROCK, 0.1f);
        traversalSpeed().put(TileType.STONE, 0.2f);
    }
}
