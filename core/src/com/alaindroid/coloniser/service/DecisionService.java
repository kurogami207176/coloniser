package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.state.GameSave;
import com.alaindroid.coloniser.units.Unit;
import com.badlogic.gdx.math.Vector3;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DecisionService {
    private Random random = new Random();
    private final NavigationService navigationService;

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
        Coordinate[] nextCoords = findPath(unit, nextCoordinate, grid).toArray(new Coordinate[0]);
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

    private List<Coordinate> findPath(Unit unit, Coordinate end, Grid grid) {
        Coordinate current = unit.coordinate();
        List<Coordinate> pathTaken = new ArrayList<>();
        Set<Coordinate> blockSet = new HashSet<>();
        Set<Coordinate> considered = new HashSet<>();
        while (!current.equals(end)) {
            Set<Coordinate> possibles = navigationService
                    .navigable(unit, current, grid, 1)
                    .stream()
                    .filter(c -> !pathTaken.contains(c))
                    .filter(c -> !blockSet.contains(c))
                    .collect(Collectors.toSet());
            Optional<Coordinate> coords = possibles.stream()
                    .map(n -> new CoordinateDistance(n, end))
                    .sorted(Comparator.comparing(CoordinateDistance::distance))
                    .findFirst()
                    .map(CoordinateDistance::coordinate);
            if (coords.isPresent()) {
                current = coords.get();
                pathTaken.add(current);
                possibles.remove(current);
                considered.addAll(possibles);
            }
            else {
                Coordinate blockThis = pathTaken.remove(pathTaken.size()-1);
                blockSet.add(blockThis);
                current = pathTaken.get(pathTaken.size() - 1);
            }
        }
//        return Arrays.asList(end);
        System.out.println(pathTaken);
        return pathTaken;
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

    @Data
    @Accessors(fluent = true)
    private static class CoordinateDistance {
        private final Coordinate coordinate;
        private final float distance;
        public CoordinateDistance(Coordinate n, Coordinate end) {
            this.coordinate = n;
            this.distance = new Vector3(n.r(), n.g(), n.b()).dst(new Vector3(end.r(), end.g(), end.b()));
        }
    }
}
