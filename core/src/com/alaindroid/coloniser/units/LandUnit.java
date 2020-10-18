package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.TileType;

import java.util.Random;

public abstract class LandUnit extends Unit {

    private static UnitType[] TANK_UNITS = new UnitType[]{
            UnitType.TANKS_DESERT,
            UnitType.TANKS_GREEN,
            UnitType.TANKS_GREY,
            UnitType.TANKS_NAVY
    };
    public LandUnit(UnitType unitType) {
        super(unitType);
        traversalSpeed().put(TileType.GRASS, 1f);
        traversalSpeed().put(TileType.DIRT, 1f);
        traversalSpeed().put(TileType.SAND, 0.5f);
        traversalSpeed().put(TileType.SNOW, 0.2f);
        traversalSpeed().put(TileType.AUTUMN, 1f);
        traversalSpeed().put(TileType.ROCK, 0.1f);
        traversalSpeed().put(TileType.STONE, 0.2f);
    }

    public static LandUnit random() {
        return new LandUnit(randomType()){};
    }

    private static Random random = new Random();
    private static UnitType randomType() {
        return TANK_UNITS[random.nextInt(TANK_UNITS.length)];
    }
}
