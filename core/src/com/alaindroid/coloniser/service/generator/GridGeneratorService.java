package com.alaindroid.coloniser.service.generator;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.service.grid.CellGeneratorService;
import com.alaindroid.coloniser.util.CoordinateUtil;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GridGeneratorService {
    @SneakyThrows
    public Grid generateGrid(int size, CellGeneratorService cellGeneratorService, float s) {
        Coordinate curr = new Coordinate(0,0,0, CoordinateUtil.toPoint(0, 0, 0, s));

        Grid grid = new Grid();
        Set<Coordinate> coordinates = new HashSet<>();
        coordinates.add(curr);
        for(int i =0 ; i < size; i++) {
            coordinates.addAll(grow(coordinates, s));
        }
        coordinates.forEach(c -> grid.cell(c, cellGeneratorService.generate(c, grid.cells())));
        gridNeighbors(grid, s);
//        gridPoint(grid, s);
        return grid;
    }

    private Set<Coordinate> grow(Set<Coordinate> coordinates, float s) {
        return coordinates.stream().map(c -> generateNeighbors(c, s))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private void gridNeighbors(Grid grid, float s) {
        grid.cells().keySet().stream()
                .forEach( coordinate -> {
                    Set<Coordinate> coordinates = generateNeighbors(coordinate, s).stream()
                            .filter(grid.cells().keySet()::contains)
                            .collect(Collectors.toSet());
                    grid.neighbors(coordinate, coordinates);
                } );
    }

    public Set<Coordinate> generateNeighbors(Coordinate origin, float s) {
        System.out.println("generateNeighbors: " + origin);
        Set<Coordinate> coordinates = new HashSet<>();
        coordinates.add(coordOffset(origin, 1, 0, -1, s));
        coordinates.add(coordOffset(origin, 1, -1, 0, s));
        coordinates.add(coordOffset(origin, 0, 1, -1, s));
        coordinates.add(coordOffset(origin, -1, 1, 0, s));
        coordinates.add(coordOffset(origin, 0, -1, 1, s));
        coordinates.add(coordOffset(origin, -1, 0, 1, s));
        return coordinates;
    }

    private Coordinate coordOffset(Coordinate origin, int r, int g, int b, float s) {
        return new Coordinate(origin.r() + r,
                origin.g() + g,
                origin.b() + b,
                CoordinateUtil.toPoint(origin.r() + r, origin.g() + g, origin.b() + b, s));
    }

}
