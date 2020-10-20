package com.alaindroid.coloniser.draw;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.grid.TileType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
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

    public List<SpriteDraw> draw(SpriteBatch spriteBatch,
                     Grid grid) {
        List<HexDraw> hexDraws = grid.cells().keySet().stream()
                .map(coordinate -> {
                    List<Point2D> points = coordinate.point();
                    HexCell cell = grid.cell(coordinate);
                    HexDraw hexDrawA = new HexDraw(points.get(0), coordinate, cell);
                    HexDraw hexDrawB = new HexDraw(points.get(1), coordinate, cell);
                    return Arrays.asList(hexDrawA, hexDrawB);
                })
                .flatMap(List::stream)
                .sorted((a, b) -> (int) (b.p().y() - a.p().y()))
                .collect(Collectors.toList());
        return hexDraws.stream()
                .map(d -> renderHexTile(spriteBatch, d))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Data
    @Accessors(fluent = true)
    @AllArgsConstructor
    private class HexDraw {
        private Point2D p;
        private Coordinate coordinate;
        private HexCell cell;
    }

    private SpriteDraw renderHexTile(SpriteBatch spriteBatch, HexDraw hexDraw) {
        Point2D p = hexDraw.p();
        HexCell cell = hexDraw.cell();
        Texture texture = tileTypeTextureMap.get(cell.tileType());
        Sprite sprite = new Sprite(texture);
        sprite.setX(p.x() - texture.getWidth()/2);
        sprite.setY(p.y() - texture.getHeight()/2 - texture.getHeight()/7 + cell.currentPopHeight());
        sprite.draw(spriteBatch);
        return new SpriteDraw(sprite, (int) p.y());
    }
}
