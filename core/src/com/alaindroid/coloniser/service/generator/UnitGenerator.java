package com.alaindroid.coloniser.service.generator;

import com.alaindroid.coloniser.units.UnitType;
import com.alaindroid.coloniser.util.CoordinateUtil;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.units.ShipUnit;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.units.LandUnit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UnitGenerator {
    private Map<UnitType, Integer> genConfig = new HashMap<>();

    public UnitGenerator() {
        genConfig.put(UnitType.LAND, 5);
        genConfig.put(UnitType.SHIP, 5);
    }

    public List<Unit> generate(Grid grid) {
        List<Unit> gens = genConfig.entrySet().stream()
            .map(kv -> {
                List<Unit> units;
                switch (kv.getKey()) {
                    case SHIP:
                        units = IntStream.range(0, kv.getValue())
                                .mapToObj(i -> ShipUnit.random())
                                .collect(Collectors.toList());
                        break;
                    case LAND:
                        units = IntStream.range(0, kv.getValue())
                                .mapToObj(i -> LandUnit.random())
                                .collect(Collectors.toList());
                        break;
                    default:
                        units = Arrays.asList();
                }
                return units;
            })
            .flatMap(List::stream)
            .collect(Collectors.toList());
        for(Unit unit:  gens) {
            setCoordinate(unit, grid, gens);
        }
        System.out.println("Generated units: ");
        gens.forEach(System.out::println);
        return gens;
    }

    private void setCoordinate(Unit unit, Grid grid, List<Unit> unitsSoFar) {
        grid.cells().keySet().stream()
                .filter(CoordinateUtil.navigable(unit, grid))
                .filter(coordinate -> unitsSoFar.stream().map(Unit::coordinate).filter(Objects::nonNull).noneMatch(coordinate::equals))
                .findAny()
                .ifPresent(c -> {
                    unit.setNextDestination(c, grid.point(c).get(0));
                });
    }


}
