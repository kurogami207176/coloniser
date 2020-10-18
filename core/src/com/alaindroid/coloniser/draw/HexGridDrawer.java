package com.alaindroid.coloniser.draw;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.grid.TileType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HexGridDrawer {

    Map<TileType, Texture> tileTypeTextureMap = new HashMap<>();

    float textureWidth;
    float textureHeight;

    public void create() {
        for (TileType type: TileType.values()) {
            Texture texture = new Texture("terrain/" + type.name().toLowerCase() + ".png");
            textureWidth = texture.getWidth();
            textureHeight = texture.getHeight();
            tileTypeTextureMap.put(type,texture);
        }
    }

    public void dispose() {
        tileTypeTextureMap.values().forEach(Texture::dispose);
    }

    public void draw(ShapeRenderer shapeRenderer,
                     SpriteBatch spriteBatch,
                     float offsetX, float offsetY,
                     float drawWidth, float drawHeight,
                     Grid grid) {
        final float xStart = offsetX + drawWidth / 2;
        final float yStart = offsetY + drawHeight / 2;

        float s = textureHeight * 0.426f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        List<HexDraw> hexDraws = grid.cells().keySet().stream()
                .map(coordinate -> {
                    List<Point> points = toPoint(coordinate, s, xStart, yStart);
                    HexCell cell = grid.cell(coordinate);
                    HexDraw hexDrawA = new HexDraw(points.get(0), cell);
                    HexDraw hexDrawB = new HexDraw(points.get(1), cell);
                    return Arrays.asList(hexDrawA, hexDrawB);
                })
                .flatMap(List::stream)
                .sorted((a, b) -> (int) (b.p().y() - a.p().y()))
                .collect(Collectors.toList());
        hexDraws.forEach(d -> renderHexTile(spriteBatch, d.p(), d.cell()));
        hexDraws.forEach(d -> renderHexagon(shapeRenderer, d.p(), s));

        shapeRenderer.end();
    }

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    private class HexDraw {
        private Point p;
        private HexCell cell;
    }

    private void renderHexagon(ShapeRenderer shapeRenderer, Point p, float s) {
//        shapeRenderer.polygon( new float[] {
//                p.x(),            p.y() + s * 4/5,     // top
//                p.x() - s * 2/3,  p.y() + s * 3/10,    // upper left
//                p.x() - s * 2/3,  p.y() - s * 3/10,    // bottom left
//                p.x(),            p.y() - s * 4/5,     // bottom
//                p.x() + s * 2/3,  p.y() - s * 3/10,    // bottom right
//                p.x() + s * 2/3,  p.y() + s * 3/10     // upper right
//        });
//        shapeRenderer.line(p.x() - s, p.y(),
//                p.x() + s, p.y());
//        shapeRenderer.line(p.x(), p.y() - s,
//                p.x(), p.y() + s);
//        shapeRenderer.circle(p.x(), p.y(), s/3);
    }

    private void renderHexTile(SpriteBatch spriteBatch, Point p, HexCell cell) {
        Texture texture = tileTypeTextureMap.get(cell.tileType());
        spriteBatch.draw(texture,
                p.x() - texture.getWidth()/2,
                p.y() - texture.getHeight()/2 - texture.getHeight()/7);
    }

    private List<Point> toPoint(Coordinate coordinate, float s, float xOffset, float yOffset) {
        double r = coordinate.r();
        double g = coordinate.g();
        double b = coordinate.b();

        double y = 2.65d/2 * s * b;
        double x = Math.sqrt(2.91d) * s * ( b/2 + r);
        double x2 = - Math.sqrt(2.91d) * s * ( b/2 + g );

        return Arrays.asList(
            new Point(xOffset + (float) x, yOffset + (float) y),
            new Point(xOffset + (float) x2, yOffset + (float) y)
        );
    }
//
//    private Coordinate toCoordinate(Point p, float s, float xOffset, float yOffset) {
//        double x = p.x() - xOffset;
//        double y = p.y() - yOffset;
//        double r;
//        double g;
//        double b;
//
//        b = 2/3 * y / s;
//        r = (Math.sqrt(2.91d)/2.65d * x - y/2.65d ) / s;
//        g = -(Math.sqrt(2.91d)/2.65d * x + y/2.65d ) / s;
//
//        return new Coordinate((int) r, (int) g, (int) b);
//    }

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    public static class Point {
        private float x, y;
    }
}
