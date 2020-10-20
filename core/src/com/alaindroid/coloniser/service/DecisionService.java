package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.state.GameSave;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.util.CoordinateUtil;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class DecisionService {
    private Random random = new Random();

    private DecisionState decisionState = DecisionState.SELECTION;

    public Unit select(GameSave gameSave) {
        List<Unit> unitList = gameSave.units();
        Unit selected =  unitList.get(random.nextInt(unitList.size()));
        popPossibles(selected, gameSave.grid());
        decisionState = DecisionState.DECISION;
        return selected;
    }

    public Unit decide(Unit unit, Grid grid, Set<Coordinate> navigable) {
        Coordinate nextCoordinate = random(unit, navigable);

        unit.setNextDestination(nextCoordinate, grid.point(nextCoordinate).get(0));
        decisionState = DecisionState.SELECTION;
        return unit;
    }

    private Set<Coordinate> findNavigableCoordinates(Unit unit, Grid grid) {
        Set<Coordinate> neighbors = grid.neighbors(unit.coordinate());
        if (neighbors == null) {
            throw new InvalidParameterException("No neighbors for unit coords: " + unit.coordinate());
        }
        Set<Coordinate> navigable = neighbors.stream()
                .filter(CoordinateUtil.navigable(unit, grid))
                .collect(Collectors.toSet());
        return navigable;
    }

    private void popPossibles(Unit unit, Grid grid) {
        findNavigableCoordinates(unit, grid).stream()
                .map(grid::cell)
                .forEach(h -> h.popped(true));
    }

    private Coordinate random(Unit unit, Set<Coordinate> navigable) {
        if (navigable.size() <= 0) {
            System.out.println(unit.unitType() + " no change");
            return unit.coordinate();
        }
        Coordinate newCoord = navigable.toArray(new Coordinate[0])[random.nextInt(navigable.size())];
        return newCoord;
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
