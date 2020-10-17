package com.alaindroid.coloniser.draw;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

public class HexGridDrawer {

    public static final double SQRT_THREE = Math.sqrt(3);

    public void draw(ShapeRenderer shapeRenderer,
                     float offsetX, float offsetY,
                     float drawWidth, float drawHeight,
                     Grid grid, float s) {
        final float xStart = offsetX + drawWidth / 2;
        final float yStart = offsetY + drawHeight / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        grid.cells().keySet().forEach(coordinate -> {
                List<Point> points = toPoint(coordinate, s, xStart, yStart);
                    renderHexagon(shapeRenderer, points.get(0), s);
                    renderHexagon(shapeRenderer, points.get(1), s);
            }
        );
        shapeRenderer.end();
    }

    private void renderHexagon(ShapeRenderer shapeRenderer, Point p, float s) {
        shapeRenderer.polygon( new float[] {
            p.x(),            p.y() + s * 4/5,     // top
            p.x() - s * 2/3,  p.y() + s * 3/10,    // upper left
            p.x() - s * 2/3,  p.y() - s * 3/10,    // bottom left
            p.x(),            p.y() - s * 4/5,     // bottom
            p.x() + s * 2/3,  p.y() - s * 3/10,    // bottom right
            p.x() + s * 2/3,  p.y() + s * 3/10     // upper right
        });
    }

    private List<Point> toPoint(Coordinate coordinate, float s, float xOffset, float yOffset) {
        double x;
        double x2;
        double y;
        double r = coordinate.r();
        double g = coordinate.g();
        double b = coordinate.b();

        y = 3/2 * s * b;
        x = SQRT_THREE * s * ( b/2 + r);
        x2 = - SQRT_THREE * s * ( b/2 + g );

        return Arrays.asList(
            new Point(xOffset + (float) x, yOffset + (float) y),
            new Point(xOffset + (float) x2, yOffset + (float) y)
        );
    }

    private Coordinate toCoordinate(Point p, float s, float xOffset, float yOffset) {
        double x = p.x() - xOffset;
        double y = p.y() - yOffset;
        double r;
        double g;
        double b;

        b = 2/3 * y / s;
        r = (SQRT_THREE/3 * x - y/3 ) / s;
        g = -(SQRT_THREE/3 * x + y/3 ) / s;

        return new Coordinate((int) r, (int) g, (int) b);
    }

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    public static class Point {
        private float x, y;
    }
}
