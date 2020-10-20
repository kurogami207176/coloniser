package com.alaindroid.coloniser.util;

import com.alaindroid.coloniser.draw.Point2D;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.units.Unit;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class CoordinateUtil {

    public static List<Point2D> toPoint(double r, double g, double b, float s) {
        double y = 2.65d/2 * s * b;
        double x = Math.sqrt(2.91d) * s * ( b/2 + r);
        double x2 = - Math.sqrt(2.91d) * s * ( b/2 + g );

        return Arrays.asList(
                new Point2D((float) x, (float) y),
                new Point2D( (float) x2, (float) y)
        );
    }

    public static Predicate<Coordinate> navigable(Unit unit, Grid grid) {
        return coordinate -> {
            HexCell hexCell = grid.cell(coordinate);
            if(hexCell == null) {
                System.err.println("Coords available: ");
                grid.cells().keySet().forEach(System.err::println);
                throw new IllegalStateException("Couldnt find cell for " + coordinate);
            }
            Float travereSpeed = unit.traversalSpeed(hexCell.tileType()).orElse(0f);
            return travereSpeed > 0;
        };
    }

}
