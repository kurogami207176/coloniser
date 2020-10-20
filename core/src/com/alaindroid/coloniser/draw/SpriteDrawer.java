package com.alaindroid.coloniser.draw;

import com.alaindroid.coloniser.grid.TileType;
import com.alaindroid.coloniser.state.GameSave;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class SpriteDrawer {
    private HexGridDrawer hexGridDrawer;
    private UnitDrawer unitDrawer;

    public void create() {
        hexGridDrawer.create();
        unitDrawer.create();
    }

    public void dispose() {
        hexGridDrawer.dispose();
        unitDrawer.dispose();
    }
    public void draw(SpriteBatch spriteBatch, GameSave gameSave) {
        List<SpriteDraw> spriteDraws = new ArrayList<>();
        spriteDraws.addAll(hexGridDrawer.draw(spriteBatch, gameSave.grid()));
        spriteDraws.addAll(unitDrawer.draw(spriteBatch, gameSave.units()));

        draw(spriteBatch, spriteDraws);
    }

    private void draw(SpriteBatch spriteBatch, List<SpriteDraw> spriteList) {
        spriteList.stream()
                .sorted(Comparator.comparing(SpriteDraw::drawOrder).reversed())
                .map(SpriteDraw::sprite)
                .forEach(sprite -> sprite.draw(spriteBatch));
    }
}
