package com.alaindroid.coloniser.draw;

import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.units.UnitType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<SpriteDraw> draw(SpriteBatch spriteBatch,
                     List<Unit> units) {
        return units.stream()
                .map( unit -> renderTexture(spriteBatch, unit) )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private SpriteDraw renderTexture(SpriteBatch spriteBatch, Unit unit) {
        Point2D p = unit.currentPoint();
        int level = unit.healthLevel();
        List<Texture> textures = unitTypeTextureMap.get(unit.unitType());
        if (textures == null || textures.isEmpty()) {
            System.out.println("No texture found for " + unit.unitType());
            return null;
        }
        Texture texture = textures.get(level);
        Sprite sprite = new Sprite(texture);
        float scale = 0.4f;
        sprite.setScale(scale);
        sprite.setX(p.x() - texture.getWidth() / 2);
        sprite.setY(p.y() - texture.getHeight() / 2);
        sprite.setRotation(unit.currentWobbleAngle());
        sprite.draw(spriteBatch);
        return new SpriteDraw(sprite, (int) p.y() - (unit.moving()? 100 : 0));
    }

}
