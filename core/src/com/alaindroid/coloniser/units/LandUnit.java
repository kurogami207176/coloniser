package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.TileType;
import com.alaindroid.coloniser.util.RandomUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class LandUnit extends Unit {

    public static UnitType[] LAND_UNITS = new UnitType[]{
            UnitType.LAND_ATTACK,
            UnitType.LAND_SPEED,
            UnitType.LAND_LARGE
    };
    private static List<TileType> landTypes = Arrays.asList(TileType.GRASS,TileType.DIRT,TileType.SAND,TileType.SNOW,
            TileType.GOLD,TileType.ROCK, TileType.STONE);
    public LandUnit(UnitType unitType) {
        super(unitType);
        traversable().addAll(landTypes);
    }

    public static LandUnit random() {
        return new LandUnit(randomType()){};
    }

    public static boolean isLandType(TileType tileType) {
        return landTypes.contains(tileType);
    }

    private static UnitType randomType() {
        return LAND_UNITS[RandomUtil.nextInt(LAND_UNITS.length)];
    }
}
