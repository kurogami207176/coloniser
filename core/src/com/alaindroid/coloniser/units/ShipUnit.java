package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.TileType;
import com.alaindroid.coloniser.util.RandomUtil;

import java.util.Random;

public abstract class ShipUnit extends Unit{

    public static UnitType[] SHIP_UNITS = new UnitType[]{
            UnitType.SHIP_SPEED,
            UnitType.SHIP_ATTACK,
            UnitType.SHIP_LARGE
    };

    public ShipUnit(UnitType unitType) {
        super(unitType);
        traversable().add(TileType.WATER);
    }

    public static ShipUnit random() {
        return new ShipUnit(randomType()){};
    }

    private static UnitType randomType() {
        return SHIP_UNITS[RandomUtil.nextInt(SHIP_UNITS.length)];
    }

}
