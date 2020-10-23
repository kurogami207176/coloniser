package com.alaindroid.coloniser.draw;

import com.alaindroid.coloniser.bldg.Settlement;
import com.alaindroid.coloniser.state.Player;
import com.alaindroid.coloniser.units.Unit;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BuildingDrawer {
    final Map<String, Texture> unitTypeTextureMap = new HashMap<>();

    public void create() {
    }

    public void dispose() {
        unitTypeTextureMap.values().stream().forEach(Texture::dispose);
    }

    public List<SpriteDraw> draw(SpriteBatch spriteBatch,
                     List<Settlement> units) {
        return units.stream()
                .map( unit -> renderTexture(spriteBatch, unit) )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
//
//    public List<SpriteDraw> drawMemory(SpriteBatch spriteBatch,
//                     Set<Player.UnitMemory> units) {
//        return units.stream()
//                .map( unit -> renderTexture(spriteBatch, unit) )
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }

    private SpriteDraw renderTexture(SpriteBatch spriteBatch, Settlement unit) {
        Point2D p = unit.coordinate().point().get(0);

        String key = textureKey.apply(unit);
        Texture texture = unitTypeTextureMap.computeIfAbsent(key, k -> new Texture("bldg/" + k + ".png"));
        Sprite sprite = new Sprite(texture);
        float scale = 0.4f;
        sprite.setScale(scale);
        sprite.setX(p.x() - texture.getWidth() / 2);
        sprite.setY(p.y() - texture.getHeight() / 2);
        sprite.draw(spriteBatch);
        return new SpriteDraw(sprite, (int) p.y() - 2);
    }
//
//    private SpriteDraw renderTexture(SpriteBatch spriteBatch, Player.UnitMemory unit) {
//        Point2D p = unit.coordinate().point().get(0);
//        String key = textureUnitKey.apply(unit);
//        Texture texture = unitTypeTextureMap.computeIfAbsent(key, k -> new Texture("unit/" + k + ".png"));
//        Sprite sprite = new Sprite(texture);
//        float scale = 0.4f;
//        sprite.setScale(scale);
//        sprite.setX(p.x() - texture.getWidth() / 2);
//        sprite.setY(p.y() - texture.getHeight() / 2);
//        sprite.draw(spriteBatch);
//        return new SpriteDraw(sprite, (int) p.y());
//    }

    private Function<Settlement, String> textureKey = settlement -> (settlement.player().color() + "_" + settlement.type()).toLowerCase();
//    private Function<Player.UnitMemory, String> textureUnitKey = unit -> (unit.player().color() + "_" + unit.unitType()).toLowerCase();

}
