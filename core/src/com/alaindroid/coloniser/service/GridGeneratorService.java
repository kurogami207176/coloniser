package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Cell;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.GridImpl;
import dagger.Module;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Module
public class GridGeneratorService {
    @SneakyThrows
    public static Grid generateGrid(Class<? extends Cell> clzCell,
                                    int dimCount,
                                    boolean positiveCoordsOnly,
                                    int... dimensions) {
        Coordinate curr = new Coordinate(dimCount);
        int[] dimsSoFar = new int[dimCount];
        for (int i = 0; i < dimCount; i++) {
            dimsSoFar[i] = 1;
        }

        Grid grid = new GridImpl(positiveCoordsOnly);
        for (int d = 0; d < dimCount; d++) {
            if (dimsSoFar[d] < dimensions[d]) {
                Coordinate newCoor = new Coordinate(dimCount);
            }
        }
        return grid;
    }

    private Set<Coordinate> enforcePositiveCoordsFlag(boolean positiveCoordsOnly, Set<Coordinate> coordinates) {
        if (!positiveCoordsOnly) {
            return coordinates;
        }
        return coordinates.stream().filter(Coordinate::positiveCoordsOnly).collect(Collectors.toSet());
    }

    public Set<Coordinate> generateNeighbors(Coordinate origin) {
        int[] numerical = origin.numerical();
        Set<Coordinate> coordinates = new HashSet<>();
        for (int i = 0; i < numerical.length; i++) {
            coordinates.add(
                    generateCoordinate(i, numerical)
            );
        }
        return coordinates;
    }

    private Coordinate generateCoordinate(int dimension, final int[] numerical) {
        int[] newNumerical = new int[numerical.length];
        System.arraycopy(numerical, 0, newNumerical, 0, numerical.length);
        newNumerical[dimension] += 1;
        return new Coordinate(newNumerical);
    }

}
