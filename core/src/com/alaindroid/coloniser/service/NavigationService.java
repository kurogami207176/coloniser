package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.CoordinateUtil;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.units.Unit;

import java.security.InvalidParameterException;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class NavigationService {
    public Unit navigate(Unit unit, Grid grid) {
        Set<Coordinate> neighbors = grid.neighbors(unit.coordinate());
        if (neighbors == null) {
            throw new InvalidParameterException("No neighbors for unit coords: " + unit.coordinate());
        }
        Set<Coordinate> navigable = neighbors.stream()
                .filter(CoordinateUtil.navigable(unit, grid))
                .collect(Collectors.toSet());

        Coordinate nextCoordinate = decide(unit, navigable,grid);
        unit.coordinate(nextCoordinate);
        return unit;
    }

    private Coordinate decide(Unit unit, Set<Coordinate> navigable, Grid grid) {
        System.out.println("Deciding: ");
        System.out.println(unit.unitType() + " on " + unit.coordinate() + " hex " + grid.cell(unit.coordinate()));
        if (navigable.size() <= 0) {
            System.out.println(unit.unitType() + " no change");
            return unit.coordinate();
        }
        Coordinate newCoord = navigable.toArray(new Coordinate[0])[new Random().nextInt(navigable.size())];
        System.out.println(unit.unitType() + " moving to " + newCoord + " hex " + grid.cell(newCoord));
        return newCoord;
    }

}
