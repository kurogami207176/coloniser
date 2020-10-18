package com.alaindroid.coloniser.service.grid;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.grid.TileType;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class CellGeneratorService {
    public CellGeneratorService() {
        initWeightedTiles();
    }

    public HexCell generate(Coordinate coordinate, Map<Coordinate, HexCell> hexCellMap) {
        return new HexCell(weightedType());
    }

    private TreeMap<Integer, TileType> weightedTileTreeMap;
    private Integer totalWeight;

    private void initWeightedTiles() {
        totalWeight = 0;
        weightedTileTreeMap = new TreeMap<>();
        totalWeight = addWeightedTile(weightedTileTreeMap, 1, TileType.DIRT, totalWeight);
        totalWeight = addWeightedTile(weightedTileTreeMap, 6, TileType.GRASS, totalWeight);
        totalWeight = addWeightedTile(weightedTileTreeMap, 1, TileType.SAND, totalWeight);
        totalWeight = addWeightedTile(weightedTileTreeMap, 1, TileType.ROCK, totalWeight);
        totalWeight = addWeightedTile(weightedTileTreeMap, 9, TileType.WATER, totalWeight);
    }

    private int addWeightedTile(TreeMap<Integer, TileType> weightedTileTreeMap, int weight, TileType type, int totalWeight) {
        weightedTileTreeMap.put(totalWeight, type);
        return totalWeight + weight;
    }

    private TileType weightedType() {
        return weightedTileTreeMap.get(weightedTileTreeMap.floorKey(new Random().nextInt(totalWeight)));
    }


}
