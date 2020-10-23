package com.alaindroid.coloniser.service.generator;

import com.alaindroid.coloniser.bldg.Settlement;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.state.Player;
import com.alaindroid.coloniser.units.LandUnit;
import com.alaindroid.coloniser.util.RandomUtil;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.alaindroid.coloniser.bldg.SettlementType.CASTLE;

@RequiredArgsConstructor
public class BuildingGeneratorService {

    private final NavigationService navigationService;

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
        Set<Coordinate> rands = new HashSet<>();
        List<Coordinate> avail = grid.cells().keySet().stream()
                .filter(c -> !coordinatesSoFar.contains(c) && LandUnit.isLandType(grid.cell(c).tileType()) )
                .collect(Collectors.toList());
        while (rands.size() < count) {
            Coordinate c = avail.get(RandomUtil.nextInt(avail.size()));
            rands.add(c);
            coordinatesSoFar.add(c);
            avail.remove(c);
        }
        return rands;

    }

    private List<Settlement> generate(Player player, Set<Coordinate> coordinates) {
        boolean hasCastle = false;
        List<Settlement> settlements = new ArrayList<>();
        for (Coordinate coordinate: coordinates) {
            int age = hasCastle? RandomUtil.nextInt(10): CASTLE.minAge();
            settlements.add(new Settlement(age, coordinate, player));
            hasCastle = true;
        }
        return settlements;
    }

}
