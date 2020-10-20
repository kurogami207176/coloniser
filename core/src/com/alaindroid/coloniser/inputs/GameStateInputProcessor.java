package com.alaindroid.coloniser.inputs;

import com.alaindroid.coloniser.service.DecisionService;
import com.alaindroid.coloniser.state.GameSave;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameStateInputProcessor implements InputProcessor {
    private OrthographicCamera camera;
    private GameSave gameSave;
    private DecisionService decisionService;

    @Override
    public boolean keyDown (int keycode) {
//        System.out.println("keyDown (" + keycode + ")");
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
//        System.out.println("keyUp (" + keycode + ")");
        return false;
    }

    @Override
    public boolean keyTyped (char character) {
//        System.out.println("keyTyped (" + character + ")");
        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        System.out.println("touchDown (" + x + "," + y + ") p=" + pointer + ", b=" + button);
        unproject(x, y);
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        System.out.println("touchUp (" + x + "," + y + ") p=" + pointer + ", b=" + button);
        unproject(x, y);
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        System.out.println("touchDragged (" + x + "," + y + ") p=" + pointer);
        unproject(x, y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
//        System.out.println("mouseMoved (" + screenX + "," + screenY + ")");
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
//        System.out.println("scrolled (" + amount + ")");
        return false;
    }

    private void unproject(float screenX, float screenY) {
        Vector3 gameWorldVector = camera.unproject(new Vector3(screenX, screenY, 0));
        System.out.println("> gameWorldVector: " + gameWorldVector);
    }
}
