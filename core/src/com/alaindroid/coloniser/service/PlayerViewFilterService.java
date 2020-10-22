package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.grid.TileType;
import com.alaindroid.coloniser.state.GameSave;
import com.alaindroid.coloniser.state.Player;
import com.alaindroid.coloniser.units.Unit;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PlayerViewFilterService {
    private final NavigationService navigationService;

    public GameSave filterGameSave(GameSave fullGameSave) {
        Player player = fullGameSave.currentPlayer();
        Set<Coordinate> playerVisibleCoords = fullGameSave.units().stream()
                .filter(u -> u.player().equals(player))
                .map(u -> navigationService.visible(u.coordinate(), fullGameSave.grid(), u.unitType().range()))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        return new GameSave(
                filterGrid(player, playerVisibleCoords, fullGameSave.grid()),
                filterUnits(player, playerVisibleCoords, fullGameSave.units()),
                fullGameSave.players()
        );
    }

    public Grid filterGrid(Player player, Set<Coordinate> playerVisibleCoords, Grid fullGrid) {
        Grid grid = new Grid();

        fullGrid.cells().keySet().forEach( k -> grid.cells().put(k, new HexCell(TileType.UNKNOWN)) );
        fullGrid.cells().keySet()
                .stream()
                .filter(c -> player.seenCoordinates().contains(c) || playerVisibleCoords.contains(c))
                .forEach(c -> grid.cells().put(c, fullGrid.cell(c)));
        return grid;
    }

    public List<Unit> filterUnits(Player player, Set<Coordinate> playerVisibleCoords, List<Unit> allUnits) {
        return allUnits.stream()
                .filter(u -> u.player().equals(player) || playerVisibleCoords.contains(u.coordinate()))
                .collect(Collectors.toList());
    }
}
