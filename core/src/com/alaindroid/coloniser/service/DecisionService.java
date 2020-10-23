package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.generator.GridGeneratorService;
import com.alaindroid.coloniser.state.GameSave;
import com.alaindroid.coloniser.state.Player;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.util.Constants;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
public class DecisionService {
    private Random random = new Random();
    private final NavigationService navigationService;
    private final PathFinderService pathFinderService;
    private final GridGeneratorService gridGeneratorService;

    private DecisionState decisionState = DecisionState.SELECTION;

    public void reset() {
        decisionState = DecisionState.SELECTION;
    }

    public Unit select(GameSave gameSave, Unit selected) {
        Player player = gameSave.currentPlayer();
        if (!player.equals(selected.player())) {
            decisionState = DecisionState.SELECTION;
            return selected;
        }
        selected.wobble(true);
        popPossibles(selected, gameSave.grid());
        registerSeen(player, selected, selected.coordinate(), gameSave.grid(), gameSave.units());
        decisionState = DecisionState.DECISION;
        return selected;
    }

    public boolean decide(Player player, Unit unit, Grid grid, Coordinate nextCoordinate, List<Unit> allUnits) {
        Set<Coordinate> navigable = navigationService.navigable(unit, grid);
        if (!navigable.contains(nextCoordinate)) {
            reset();
            return false;
        }
        Coordinate[] nextCoords = pathFinderService.findPath(unit, nextCoordinate, grid).toArray(new Coordinate[0]);
        if (nextCoords.length > 0) {
            unit.moving(true);
            unit.setNextDestination(nextCoords);
            decisionState = DecisionState.SELECTION;
            for (Coordinate c: nextCoords) {
                registerSeen(player, unit, c, grid, allUnits);
            }
        } else {
            System.err.println("Couldn't find path");
            decisionState = DecisionState.SELECTION;
        }
        return true;
    }

    private void registerSeen(Player player, Unit unit, Coordinate coordinate, Grid grid, List<Unit> allUnits) {
        Set<Coordinate> visible = navigationService.visible(coordinate, grid, unit.unitType().range());
        gridGeneratorService.growGrid(grid, unit.coordinate(), Constants.HEX_SIDE_LENGTH, unit.unitType().range());
        visible.forEach(player.seenCoordinates()::add);
//        player.seenUnit().clear();
        allUnits.stream()
                .filter(u -> u.player().equals(player) && visible.contains(u))
                .map(Player.UnitMemory::new)
                .forEach(player.seenUnit()::add);

    }

    private void popPossibles(Unit unit, Grid grid) {
        navigationService.navigable(unit, grid)
                .stream()
                .map(grid::cell)
                .forEach(h -> h.popped(true));
    }

    public boolean isWaitingForSelection() {
        return decisionState == DecisionState.SELECTION;
    }
    public boolean isWaitingForDecision() {
        return decisionState == DecisionState.DECISION;
    }

    enum DecisionState {
        SELECTION, DECISION
    }
}
