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

    public static List<Point2D> toPoint(Coordinate coordinate, float s, float xOffset, float yOffset) {
        double r = coordinate.r();
        double g = coordinate.g();
        double b = coordinate.b();

        double y = 2.65d/2 * s * b;
        double x = Math.sqrt(2.91d) * s * ( b/2 + r);
        double x2 = - Math.sqrt(2.91d) * s * ( b/2 + g );

        return Arrays.asList(
                new Point2D(xOffset + (float) x, yOffset + (float) y),
                new Point2D(xOffset + (float) x2, yOffset + (float) y)
        );
    }

    public static Coordinate toCoordinate(Point2D p, float s, float xOffset, float yOffset) {
        double x = p.x() - xOffset;
        double y = p.y() - yOffset;

        double b = 2/3 * y / s;
        double r = (Math.sqrt(2.91d)/2.65d * x - y/2.65d ) / s;
        double g = -(Math.sqrt(2.91d)/2.65d * x + y/2.65d ) / s;

        return new Coordinate((int) r, (int) g, (int) b);
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
