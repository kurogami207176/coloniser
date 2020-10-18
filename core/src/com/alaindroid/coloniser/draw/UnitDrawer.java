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
        units.forEach( unit -> renderShape(shapeRenderer, unit, tickerPercent) );

    }

    private void renderTexture(SpriteBatch spriteBatch, Unit unit,
                        float tickerPercent) {
        Point2D p = derivePoint(unit, tickerPercent);
        int level = unit.healthLevel();
        List<Texture> textures = unitTypeTextureMap.get(unit.unitType());
        if (textures == null || textures.isEmpty()) {
            return;
        }
        Texture texture = textures.get(level);
        Sprite sprite = new Sprite(texture);
        float scale = 0.4f;
        sprite.setScale(scale);
        sprite.setX(p.x() - texture.getWidth() / 2);
        sprite.setY(p.y() - texture.getHeight() / 2);
        sprite.draw(spriteBatch);
    }

    private void renderShape(ShapeRenderer shapeRenderer, Unit unit,
                        float tickerPercent) {
        float s = 38;
        if (unitTypeTextureMap.keySet().contains(unit.unitType())) {
            // Dont render already rendered
            shapeRenderer.end();
            return;
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Point2D p = derivePoint(unit, tickerPercent);
        switch (unit.unitType()){
            case LAND:
                shapeRenderer.setColor(Color.BROWN);
                shapeRenderer.rect(p.x()-s/2, p.y()-s/2, s, s);
                break;
            case SHIP_CROSS:
            case SHIP_NEUTRAL:
            case SHIP_PIRATE:
            case SHIP_GOLD:
            case SHIP_SPEED:
            case SHIP_SWORD:
                shapeRenderer.setColor(Color.DARK_GRAY);
                shapeRenderer.triangle(p.x(), p.y() + s/3,
                        p.x() - s/3, p.y() - s/2,
                        p.x() + s/3, p.y() - s/2);
                break;
        }
        shapeRenderer.end();
    }

    private Point2D derivePoint(Unit unit, float tickerPercent) {
        if (unit.previousPoint() == null) {
            return unit.point();
        }
        float midX = tickerPercent * (unit.point().x() - unit.previousPoint().x()) + unit.previousPoint().x();
        float midY = tickerPercent * (unit.point().y() - unit.previousPoint().y()) + unit.previousPoint().y();
        return new Point2D(midX, midY);
    }

}
