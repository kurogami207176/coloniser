package com.alaindroid.coloniser.service.generator;

import com.alaindroid.coloniser.util.CoordinateUtil;
import com.alaindroid.coloniser.draw.Point2D;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.grid.CellGeneratorService;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GridGeneratorService {
    @SneakyThrows
    public Grid generateGrid(int size, CellGeneratorService cellGeneratorService, float s) {
        Coordinate curr = new Coordinate(0,0,0);

        Grid grid = new Grid();
        Set<Coordinate> coordinates = new HashSet<>();
        coordinates.add(curr);
        for(int i =0 ; i < size; i++) {
            coordinates.addAll(grow(coordinates));
        }
        coordinates.forEach(c -> grid.cell(c, cellGeneratorService.generate(c, grid.cells())));
        gridNeighbors(grid);
        gridPoint(grid, s);
        return grid;
    }

    private Set<Coordinate> grow(Set<Coordinate> coordinates) {
        return coordinates.stream().map(this::generateNeighbors)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private void gridPoint(Grid grid, float s) {
        grid.cells().keySet().stream()
                .forEach( coordinate -> {
                    List<Point2D> points = CoordinateUtil.toPoint(coordinate, s, 0, 0);
                    grid.point(coordinate, points);
                } );
    }

    private void gridNeighbors(Grid grid) {
        grid.cells().keySet().stream()
                .forEach( coordinate -> {
                    Set<Coordinate> coordinates = generateNeighbors(coordinate).stream()
                            .filter(grid.cells().keySet()::contains)
                            .collect(Collectors.toSet());
                    grid.neighbors(coordinate, coordinates);
                } );
    }

    public Set<Coordinate> generateNeighbors(Coordinate origin) {
        System.out.println("generateNeighbors: " + origin);
        Set<Coordinate> coordinates = new HashSet<>();
        coordinates.add(coordOffset(origin, 1, 0, -1));
        coordinates.add(coordOffset(origin, 1, -1, 0));
        coordinates.add(coordOffset(origin, 0, 1, -1));
        coordinates.add(coordOffset(origin, -1, 1, 0));
        coordinates.add(coordOffset(origin, 0, -1, 1));
        coordinates.add(coordOffset(origin, -1, 0, 1));
        return coordinates;
    }

    private Coordinate coordOffset(Coordinate origin, int r, int g, int b) {
        return new Coordinate(origin.r() + r,
                origin.g() + g,
                origin.b() + b);
    }

}
