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
        traversable().add(TileType.GRASS);
        traversable().add(TileType.DIRT);
        traversable().add(TileType.SAND);
        traversable().add(TileType.SNOW);
        traversable().add(TileType.AUTUMN);
        traversable().add(TileType.ROCK);
        traversable().add(TileType.STONE);
    }

    public static LandUnit random() {
        return new LandUnit(randomType()){};
    }

    private static Random random = new Random();
    private static UnitType randomType() {
        return TANK_UNITS[random.nextInt(TANK_UNITS.length)];
    }
}
