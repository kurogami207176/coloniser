package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.TileType;

import java.util.Random;

public abstract class LandUnit extends Unit {

    private static UnitType[] LAND_UNITS = new UnitType[]{
            UnitType.LAND_ATTACK,
            UnitType.LAND_SPEED,
            UnitType.LAND_LARGE
    };
    public LandUnit(UnitType unitType) {
        super(unitType);
        traversable().add(TileType.GRASS);
        traversable().add(TileType.DIRT);
        traversable().add(TileType.SAND);
        traversable().add(TileType.SNOW);
        traversable().add(TileType.GOLD);
        traversable().add(TileType.ROCK);
        traversable().add(TileType.STONE);
    }

    public static LandUnit random() {
        return new LandUnit(randomType()){};
    }

    private static Random random = new Random();
    private static UnitType randomType() {
        return LAND_UNITS[random.nextInt(LAND_UNITS.length)];
    }
}
