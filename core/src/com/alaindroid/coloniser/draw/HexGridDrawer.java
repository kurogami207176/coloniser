package com.alaindroid.coloniser.draw;

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
import java.util.function.Function;
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
            tileTypeTextureMap.put(type, texture);
        }
    }

    public void dispose() {
        tileTypeTextureMap.values().stream().forEach(Texture::dispose);
    }

    public void draw(ShapeRenderer shapeRenderer,
                     SpriteBatch spriteBatch,
                     Grid grid) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        List<HexDraw> hexDraws = grid.cells().keySet().stream()
                .map(coordinate -> {
                    List<Point2D> points = grid.point(coordinate);
                    HexCell cell = grid.cell(coordinate);
                    HexDraw hexDrawA = new HexDraw(points.get(0), cell);
                    HexDraw hexDrawB = new HexDraw(points.get(1), cell);
                    return Arrays.asList(hexDrawA, hexDrawB);
                })
                .flatMap(List::stream)
                .sorted((a, b) -> (int) (b.p().y() - a.p().y()))
                .collect(Collectors.toList());
        hexDraws.forEach(d -> renderHexTile(spriteBatch, d.p(), d.cell()));

        shapeRenderer.end();
    }

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    private class HexDraw {
        private Point2D p;
        private HexCell cell;
    }

    private void renderHexTile(SpriteBatch spriteBatch, Point2D p, HexCell cell) {
        Texture texture = tileTypeTextureMap.get(cell.tileType());
        spriteBatch.draw(texture,
                p.x() - texture.getWidth()/2,
                p.y() - texture.getHeight()/2 - texture.getHeight()/7 + cell.currentPopHeight());
    }
}
