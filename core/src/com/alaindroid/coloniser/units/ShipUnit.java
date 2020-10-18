package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.TileType;

public class ShipUnit extends Unit{
    public ShipUnit() {
        super(UnitType.SHIP);
        traversalSpeed().put(TileType.WATER, 2f);
    }
}
