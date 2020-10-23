package com.alaindroid.coloniser.service.generator;

import com.alaindroid.coloniser.bldg.Settlement;
import com.alaindroid.coloniser.bldg.SettlementType;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.state.Player;
import com.alaindroid.coloniser.units.LandUnit;
import com.alaindroid.coloniser.util.CoordinateUtil;
import com.alaindroid.coloniser.util.RandomUtil;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BuildingGeneratorService {

    private final NavigationService navigationService;

    public List<Settlement> generateStart(Collection<Player> players, Grid grid) {
        List<Settlement> castles = generateTypeForPlayer(players, SettlementType.CASTLE, grid);
        List<Settlement> accessoryBldgs = generateForPlayers(players, 2,grid);
        List<Settlement> settlements = new ArrayList<>();
        settlements.addAll(castles);
        settlements.addAll(accessoryBldgs);
        return settlements;
    }

    public List<Settlement> generateTypeForPlayer(Collection<Player> players, SettlementType type, Grid grid) {
        Set<Coordinate> coordinates = new HashSet<>();
        List<Settlement> settlements = players.stream()
                .map(player -> generate(player, randomCoordinates(grid, 1, coordinates, type.range() * 2), type))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("Generated Settlement Type: " + type);
        settlements.forEach(System.out::println);
        for(Settlement settlement: settlements) {
            Set<Coordinate> visible = navigationService.visible(settlement.coordinate(), grid, settlement.type().range());
            visible.forEach(settlement.player().seenCoordinates()::add);
        }
        return settlements;
    }

    public List<Settlement> generateForPlayers(Collection<Player> players, int towns, Grid grid) {
        Set<Coordinate> coordinates = new HashSet<>();
        List<Settlement> settlements = players.stream()
                .map(player -> generate(player, randomCoordinates(grid, towns, coordinates)))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("Generated Settlement: ");
        settlements.forEach(System.out::println);
        for(Settlement settlement: settlements) {
            Set<Coordinate> visible = navigationService.visible(settlement.coordinate(), grid, settlement.type().range());
            visible.forEach(settlement.player().seenCoordinates()::add);
        }
        return settlements;
    }

    private Set<Coordinate> randomCoordinates(Grid grid, int count, Set<Coordinate> coordinatesSoFar) {
        return randomCoordinates(grid, count, coordinatesSoFar, 1);
    }
    private Set<Coordinate> randomCoordinates(Grid grid, int count, Set<Coordinate> coordinatesSoFar, float minimumDistance) {
        Set<Coordinate> rands = new HashSet<>();
        Set<Coordinate> toUse = new HashSet<>(grid.cells().keySet());
        while (rands.size() < count) {
            List<Coordinate> avail = toUse.stream()
                    .filter(c -> LandUnit.isLandType(grid.cell(c).tileType()) )
                    .filter(c -> coordinatesSoFar.stream()
                            .allMatch(a -> CoordinateUtil.distance(c, a) > minimumDistance))
                    .collect(Collectors.toList());
            Coordinate c = avail.get(RandomUtil.nextInt(avail.size()));
            rands.add(c);
            coordinatesSoFar.add(c);
            avail.remove(c);
        }
        return rands;

    }

    private List<Settlement> generate(Player player, Set<Coordinate> coordinates) {
        return generate(player, coordinates,
                SettlementType.getType(RandomUtil.nextInt(SettlementType.LARGE_CITY.minAge())));
    }

    private List<Settlement> generate(Player player, Set<Coordinate> coordinates, SettlementType settlementType) {
        List<Settlement> settlements = new ArrayList<>();
        for (Coordinate coordinate: coordinates) {
            settlements.add(new Settlement(settlementType.minAge(), coordinate, player));
        }
        return settlements;
    }

}
