package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.util.Constants;
import com.alaindroid.coloniser.util.CoordinateUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class NavigationService {
    public Set<Coordinate> navigable(Unit unit, Grid grid) {
        return navigable(unit, grid, unit.unitType().range());
    }

    public Set<Coordinate> navigable(Unit unit, Grid grid, int range) {
        return navigable(unit, unit.coordinate(), grid, range);
    }

    public Set<Coordinate> navigable(Unit unit, Coordinate coordinate, Grid grid, int range) {
        Set<Coordinate> navigable = new HashSet<>();
        navigable.add(coordinate);
        for (int i = 0; i < range; i++) {
            navigable.addAll(navigable.stream()
                    .map(Coordinate::generateNeighbors)
                    .flatMap(Set::stream)
                    .filter(CoordinateUtil.navigable(unit, grid))
                    .collect(Collectors.toSet())
            );
        }
        navigable.remove(coordinate);
        return navigable;
    }

    public Set<Coordinate> visible(Coordinate coordinate, Grid grid, int range) {
        Set<Coordinate> navigable = new HashSet<>();
        navigable.add(coordinate);
        for (int i = 0; i < range; i++) {
            navigable.addAll(navigable.stream()
                    .map(Coordinate::generateNeighbors)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet())
            );
        }
        return navigable;
    }
}
