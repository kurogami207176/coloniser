package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.util.CoordinateUtil;

import java.security.InvalidParameterException;
import java.util.Set;
import java.util.stream.Collectors;

public class NavigationService {
    public Set<Coordinate> navigable(Unit unit, Grid grid) {
        Set<Coordinate> neighbors = grid.neighbors(unit.coordinate());
        if (neighbors == null) {
            throw new InvalidParameterException("No neighbors for unit coords: " + unit.coordinate());
        }
        Set<Coordinate> navigable = neighbors.stream()
                .filter(CoordinateUtil.navigable(unit, grid))
                .collect(Collectors.toSet());
        return navigable;
    }
}
