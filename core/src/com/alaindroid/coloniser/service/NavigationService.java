package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.util.CoordinateUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class NavigationService {
    public Set<Coordinate> navigable(Unit unit, Grid grid) {
        Set<Coordinate> navigable = new HashSet<>();
        navigable.add(unit.coordinate());
        for (int i = 0; i < unit.unitType().range(); i++) {
            navigable.addAll(navigable.stream()
                    .map(c -> grid.neighbors(c))
                    .flatMap(Set::stream)
                    .filter(CoordinateUtil.navigable(unit, grid))
                    .collect(Collectors.toSet())
            );
        }
        navigable.remove(unit.coordinate());
        return navigable;
    }
}
