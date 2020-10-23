package com.alaindroid.coloniser.service.grid;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.grid.TileType;
import com.alaindroid.coloniser.util.RandomUtil;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alaindroid.coloniser.util.Constants.WATER_PECENTAGE;

@RequiredArgsConstructor
public class CellGeneratorService {
    private final LandTileWeightService landTileWeightService;

    public HexCell generate(Coordinate coordinate, Grid grid) {
        Map<Coordinate, HexCell> hexCellMap = grid.cells();
        Map<TileType, Long> neighborTiles = coordinate
                .generateNeighbors()
                .stream()
                .map(hexCellMap::get)
                .filter(Objects::nonNull)
                .map(HexCell::tileType)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                ;
        return new HexCell(weightedType(coordinate, grid.minRGB(), grid.maxRGB(), neighborTiles));
    }

    private TreeMap<Integer, TileType> createWeighted(TreeMap<Integer, TileType> fullWeightedMap,
                                                      Coordinate coordinate,
                                                      Coordinate minRGB,
                                                      Coordinate maxRGB,
                                                      Map<TileType, Long> neighborTiles) {
        float polesDistance = maxRGB.b() - minRGB.b();
        float equatorToPoleDistance = polesDistance / 2;
        float equator = (maxRGB.b() - minRGB.b()) / 2;
        float distanceFromClosestPole = Math.min(Math.abs(maxRGB.b() - coordinate.b()), Math.abs(coordinate.b() - minRGB.b()));
        float distanceFromEquator = Math.abs(equatorToPoleDistance - coordinate.b());
        LandTileWeightService.TileWeightBundle.TileWeightBundleBuilder builderBundle = LandTileWeightService.TileWeightBundle.builder()
                .fullWeightedMap(fullWeightedMap)
                .coordinate(coordinate)
                .minRGB(minRGB)
                .maxRGB(maxRGB)
                .neighborTiles(neighborTiles)
                .polesDistance(polesDistance)
                .equator(equator)
                .equatorToPoleDistance(equatorToPoleDistance)
                .distanceFromClosestPole(distanceFromClosestPole)
                .distanceFromEquator(distanceFromEquator);
        Map<TileType, Integer> baseWeights = Stream.of(TileType.DIRT, TileType.GOLD, TileType.GRASS, TileType.LAVA,
                TileType.ROCK, TileType.SAND, TileType.SNOW, TileType.STONE)
                .collect(Collectors.toMap(
                        Function.identity(),
                        t -> landTileWeightService.getWeight(builderBundle.tileType(t).build()))
                );
        baseWeights = baseWeights.entrySet().stream()
                .filter(kv -> kv.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue));
        int totalWeight = 0;
        for(TileType tileType: baseWeights.keySet()) {
            totalWeight = addWeightedTile(fullWeightedMap, baseWeights.get(tileType), tileType, totalWeight);
        }
        return fullWeightedMap;
    }

    private TileType weightedType(Coordinate coordinate,
                                  Coordinate minRGB,
                                  Coordinate maxRGB,
                                  Map<TileType, Long> neighborTiles) {
        TreeMap<Integer, TileType> fullWeightedMap = createWeighted( new TreeMap<>(),
                coordinate,
                minRGB,
                maxRGB,
                neighborTiles);

        int totalWeight = addWaterTile(fullWeightedMap);
        int randWeight = RandomUtil.nextInt(totalWeight);
        TileType generated = fullWeightedMap.get(fullWeightedMap.ceilingKey(randWeight));
        return generated;
    }

    private int addWeightedTile(TreeMap<Integer, TileType> weightedTileTreeMap, int weight, TileType type, int totalWeight) {
        int nextWeight = totalWeight + weight;
        weightedTileTreeMap.put(nextWeight, type);
        return nextWeight;
    }

    private int addWaterTile(TreeMap<Integer, TileType> fullWeightedMap) {
        int maxWeight = 0;
        int totalIndividualWeight = 0;
        for(int w: fullWeightedMap.keySet()) {
            totalIndividualWeight += w - totalIndividualWeight;
            maxWeight = w > maxWeight? w : maxWeight;
        }
        int waterWeight = (int) ((float) totalIndividualWeight * WATER_PECENTAGE);

        int totalWeight = addWeightedTile(fullWeightedMap, waterWeight, TileType.WATER, maxWeight);
        return totalWeight;
    }
}
