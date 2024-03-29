package com.alaindroid.coloniser.service.generator;

import com.alaindroid.coloniser.bldg.Settlement;
import com.alaindroid.coloniser.bldg.SettlementType;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.state.Player;
import com.alaindroid.coloniser.units.LandUnit;
import com.alaindroid.coloniser.units.ShipUnit;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.util.CoordinateUtil;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class UnitGenerator {

    private final NavigationService navigationService;

    public List<Unit> generateUnitsForPlayers(Collection<Player> players, Map<Player, Set<Coordinate>> settlementRange,
                                              int ship, int land, Grid grid) {
        List<Unit> units = players.stream()
                .map(player -> generate(player, ship, land))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("Generated units: " + units.size());
        List<Unit> removable = new ArrayList<>();
        for(Unit unit: units) {
            randomCoordinate(unit, grid, settlementRange.get(unit.player()), units);
            if (unit.coordinate() == null) {
                removable.add(unit);
                continue;
            }
            Set<Coordinate> visible = navigationService.visible(unit.coordinate(), grid, unit.unitType().range());
            visible.forEach(unit.player().seenCoordinates()::add);
        }
        units.removeAll(removable);
        return units;
    }

    public List<Unit> generate(Player player, int ship, int land) {
        List<Unit> gens =  Stream.of(
                IntStream.range(0, ship)
                        .mapToObj(i -> ShipUnit.random()),
                IntStream.range(0, land)
                        .mapToObj(i -> LandUnit.random()))
                .flatMap(Function.identity())
                .collect(Collectors.toList());
        for(Unit unit: gens) {
            unit.player(player);
        }
        return gens;
    }

    private void randomCoordinate(Unit unit, Grid grid, Set<Coordinate> settlementRange, List<Unit> unitsSoFar) {
        settlementRange.stream()
                .filter(CoordinateUtil.navigable(unit, grid))
                .filter(coordinate -> unitsSoFar.stream().map(Unit::coordinate).filter(Objects::nonNull).noneMatch(coordinate::equals))
                .findAny()
                .ifPresent(c -> unit.setNextDestination(c) );
    }


}
