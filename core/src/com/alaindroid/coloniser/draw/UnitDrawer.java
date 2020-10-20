package com.alaindroid.coloniser.draw;

import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.units.UnitType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitDrawer {
    Map<UnitType, List<Texture>> unitTypeTextureMap = new HashMap<>();

    public void create() {
        for (UnitType type: UnitType.values()) {
            try {
                List<Texture> sprites = new ArrayList<>();
                for(int i = 0; i < type.levels(); i++) {
                    Texture texture = new Texture("unit/" + type.name().toLowerCase() + "_" + i + ".png");
                    sprites.add(texture);
                }
                unitTypeTextureMap.put(type, sprites);
            }catch (Exception e) {
                // DO NOTHING
            }
        }
    }

    public void dispose() {
        unitTypeTextureMap.values().stream().flatMap(List::stream).forEach(Texture::dispose);
    }

    public void draw(ShapeRenderer shapeRenderer,
                     SpriteBatch spriteBatch,
                     List<Unit> units,
                     float tickerPercent) {
        units.forEach( unit -> renderTexture(spriteBatch, unit, tickerPercent) );
    }

    private void renderTexture(SpriteBatch spriteBatch, Unit unit,
                        float tickerPercent) {
        Point2D p = derivePoint(unit, tickerPercent);
        int level = unit.healthLevel();
        List<Texture> textures = unitTypeTextureMap.get(unit.unitType());
        if (textures == null || textures.isEmpty()) {
            System.out.println("No texture found for " + unit.unitType());
            return;
        }
        Texture texture = textures.get(level);
        Sprite sprite = new Sprite(texture);
        float scale = 0.4f;
        sprite.setScale(scale);
        sprite.setX(p.x() - texture.getWidth() / 2);
        sprite.setY(p.y() - texture.getHeight() / 2);
        sprite.setRotation(unit.currentWobbleAngle());
        sprite.draw(spriteBatch);
    }

    private Point2D derivePoint(Unit unit, float tickerPercent) {
        Point2D point = unit.coordinate().point().get(0);
        if (unit.previousCoordinate() == null) {
            return unit.coordinate().point().get(0);
        }

        Point2D prevPoint = unit.previousCoordinate().point().get(0);

        float midX = tickerPercent * (point.x() - prevPoint.x()) + prevPoint.x();
        float midY = tickerPercent * (point.y() - prevPoint.y()) + prevPoint.y();
        return new Point2D(midX, midY);
    }

}
