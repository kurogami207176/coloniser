package com.alaindroid.coloniser.draw;

import com.alaindroid.coloniser.CoordinateUtil;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.TileType;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.units.UnitType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitDrawer {
    Map<UnitType, Texture> unitTypeTextureMap = new HashMap<>();

    public void create() {
        for (UnitType type: UnitType.values()) {
//            Texture texture = new Texture("unit/" + type.name().toLowerCase() + ".png");
//            unitTypeTextureMap.put(type,texture);
        }
    }

    public void dispose() {
        unitTypeTextureMap.values().forEach(Texture::dispose);
    }

    public void draw(ShapeRenderer shapeRenderer,
                     SpriteBatch spriteBatch,
                     float offsetX, float offsetY,
                     float drawWidth, float drawHeight,
                     List<Unit> units) {
        final float xStart = offsetX + drawWidth / 2;
        final float yStart = offsetY + drawHeight / 2;
        float s = 38;
        units.forEach(unit -> {
            List<Point2D> points = CoordinateUtil.toPoint(unit.coordinate(), s, xStart, yStart);
            points.forEach(point2D -> {
                renderHexagon(shapeRenderer, unit, point2D, s);
            });
        });

    }
    private void renderHexagon(ShapeRenderer shapeRenderer, Unit unit, Point2D p, float s) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        switch (unit.unitType()){
            case WAGON:
                shapeRenderer.setColor(Color.BROWN);
                shapeRenderer.rect(p.x()-s/2, p.y()-s/2, s, s);
                break;
            case SHIP:
                shapeRenderer.setColor(Color.DARK_GRAY);
                shapeRenderer.triangle(p.x(), p.y() + s/3,
                        p.x() - s/3, p.y() - s/2,
                        p.x() + s/3, p.y() - s/2);
                break;
        }
        shapeRenderer.end();
    }

}
