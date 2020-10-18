package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.TileType;

import java.util.Random;

public abstract class ShipUnit extends Unit{

    private static UnitType[] SHIP_UNITS = new UnitType[]{
            UnitType.SHIP_CROSS,
            UnitType.SHIP_GOLD,
            UnitType.SHIP_NEUTRAL,
            UnitType.SHIP_PIRATE,
            UnitType.SHIP_SPEED,
            UnitType.SHIP_SWORD};

    public ShipUnit(UnitType unitType) {
        super(unitType);
        traversalSpeed().put(TileType.WATER, 2f);
    }

    public static ShipUnit random() {
        return new ShipUnit(randomType()){};
    }

    private static Random random = new Random();
    private static UnitType randomType() {
        return SHIP_UNITS[random.nextInt(SHIP_UNITS.length)];
    }

}
