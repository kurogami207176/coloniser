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
    public void draw(ShapeRenderer shapeRenderer,
                     float offsetX, float offsetY,
                     float drawWidth, float drawHeight,
                     Grid grid, float s) {
        final float xStart = grid.positiveCoordsOnly()
                ? offsetX
                : offsetX + drawWidth / 2;
        final float yStart = grid.positiveCoordsOnly()
                ? offsetY
                : offsetY + drawHeight / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.CHARTREUSE);
        grid.cells().keySet().forEach(coordinate ->
            toPoint(coordinate,  s).forEach(point -> shapeRenderer.circle(xStart + point.x(), yStart + point.y(), s/2))
        );
        shapeRenderer.end();
    }

    private List<Point> toPoint(Coordinate coordinate, float s) {
        double x;
        double x2;
        double y;
        double r = coordinate.dim(0);
        double g = coordinate.dim(1);
        double b = coordinate.dim(2);

        y = 3/2 * s * b;
//        b = 2/3 * y / s;
        x = Math.sqrt(3) * s * ( b/2 + r);
        x2 = - Math.sqrt(3) * s * ( b/2 + g );
//        r = (Math.sqrt(3)/3 * x - y/3 ) / s;
//        g = -(Math.sqrt(3)/3 * x + y/3 ) / s;

        return Arrays.asList(
            new Point((float) x, (float) y),
            new Point((float) x2, (float) y)
        );
    }

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    public static class Point {
        private float x, y;
    }
}
