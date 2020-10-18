package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.grid.TileType;

import java.util.Random;

public class CellGeneratorService {
    public HexCell generate(Coordinate coordinate) {
        return new HexCell(TileType.WATER);
//        return new HexCell(randomType());
    }


    private static TileType randomType() {
        return TileType.values()[new Random().nextInt(TileType.values().length)];
//        return new TileType[] {TileType.WATER, TileType.GRASS}[new Random().nextInt(2)];
    }

    private static TileType

}
