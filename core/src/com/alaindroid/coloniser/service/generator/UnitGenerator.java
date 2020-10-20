package com.alaindroid.coloniser.service.generator;

import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.units.LandUnit;
import com.alaindroid.coloniser.units.ShipUnit;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.util.CoordinateUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UnitGenerator {
    public List<Unit> generate(Grid grid, int ship, int land) {
        List<Unit> gens =  Stream.of(
                IntStream.range(0, ship)
                        .mapToObj(i -> ShipUnit.random()),
                IntStream.range(0, land)
                        .mapToObj(i -> LandUnit.random()))
                .flatMap(Function.identity())
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
                    unit.setNextDestination(c);
                });
    }


}
