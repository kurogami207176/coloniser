package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.state.GameSave;
import com.alaindroid.coloniser.units.Unit;
import lombok.RequiredArgsConstructor;

import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
public class DecisionService {
    private Random random = new Random();
    private final NavigationService navigationService;
    private final PathFinderService pathFinderService;

    private DecisionState decisionState = DecisionState.SELECTION;

    public void reset() {
        decisionState = DecisionState.SELECTION;
    }

    public Unit select(GameSave gameSave, Unit selected) {
        selected.wobble(true);
        popPossibles(selected, gameSave.grid());
        decisionState = DecisionState.DECISION;
        return selected;
    }

    public boolean decide(Unit unit, Grid grid, Coordinate nextCoordinate) {
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
            return true;
        } else {
            System.err.println("Couldn't find path");
            decisionState = DecisionState.SELECTION;
            return false;
        }
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
