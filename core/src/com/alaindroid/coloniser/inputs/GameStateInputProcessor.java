package com.alaindroid.coloniser.inputs;

import com.alaindroid.coloniser.draw.Point2D;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.service.DecisionService;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.state.GameSave;
import com.alaindroid.coloniser.units.Unit;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GameStateInputProcessor implements InputProcessor {
    private final OrthographicCamera camera;
    private final GameSave gameSave;
    private final DecisionService decisionService;
    private final NavigationService navigationService;

    private static final float dragDrag = 0.5f;
    private boolean dragging = false;
    private float prevDragX = 0;
    private float prevDragY = 0;
    private float maxX = 0;
    private float maxY = 0;

    private float xOffset = 0;
    private float yOffset = 0;

    public GameStateInputProcessor(OrthographicCamera camera, GameSave gameSave,
                                   DecisionService decisionService, NavigationService navigationService) {
        this.camera = camera;
        this.gameSave = gameSave;
        this.decisionService = decisionService;
        this.navigationService = navigationService;
        setBounds(gameSave);
    }

    private void setBounds(GameSave gameSave) {
        List<Point2D> points = gameSave.grid().cells().keySet()
                .stream()
                .map(Coordinate::point)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Supplier thrower = () -> new RuntimeException("Failures!");
        maxX = points.stream().map(Point2D::x).max(Float::compareTo).orElseThrow(thrower);
        maxY = points.stream().map(Point2D::x).max(Float::compareTo).orElseThrow(thrower);

    }

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
//        handleXY(x, y);
        prevDragX = x;
        prevDragY = y;
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        System.out.println("touchUp (" + x + "," + y + ") p=" + pointer + ", b=" + button);
        if (!dragging) {
            handleXY(x, y);
        }
        dragging = false;
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        dragging = true;
        System.out.println("touchDragged (" + x + "," + y + ") p=" + pointer);

        float deltaX = dragDrag * (prevDragX - x);
        float deltaY = dragDrag * (y - prevDragY);
        xOffset = xOffset + deltaX;
        yOffset = yOffset + deltaY;
        float absXOffset = Math.abs(xOffset);
        float absYOffset = Math.abs(yOffset);
        if (absXOffset > maxX) {
            float deltaDelta = (absXOffset / xOffset) * (absXOffset - maxX);
            deltaX = deltaX - deltaDelta;
            xOffset = xOffset - deltaDelta;
            System.out.println("xOffset: " + xOffset);
        }
        if (absYOffset > maxY) {
            float deltaDelta = (absYOffset / yOffset) * (absYOffset - maxY);
            deltaY = deltaY - deltaDelta;
            yOffset = yOffset - deltaDelta;
            System.out.println("yOffset: " + yOffset);
        }

        camera.translate(deltaX, deltaY);
        camera.update();

        prevDragX = x;
        prevDragY = y;
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

    private Optional<Coordinate> findCoordinates(float screenX, float screenY) {
        Vector3 gameWorldVector = camera.unproject(new Vector3(screenX, screenY, 0));
        return gameSave.grid().cells().keySet().stream()
                .map(c -> new Distance(c, gameWorldVector))
                .sorted(Comparator.comparing(Distance::distance))
                .findFirst()
                .map(Distance::coordinate);
    }

    private Optional<Unit> findUnit(Coordinate coordinate) {
        return gameSave.units().stream().filter(u -> u.coordinate().equals(coordinate)).findFirst();
    }

    private boolean handleXY(float screenX, float screenY) {
        Optional<Coordinate> coordinateOptional = findCoordinates(screenX, screenY);
        if(!coordinateOptional.isPresent()) {
            gameSave.reset();
            return true;
        }
        Coordinate coordinate = coordinateOptional.get();
        if (decisionService.isWaitingForSelection()) {
            Optional<Unit> unitOptional = findUnit(coordinate);
            if(!unitOptional.isPresent()) {
                gameSave.reset();
                return true;
            }
            decisionService.select(gameSave, unitOptional.get());
        } else if (decisionService.isWaitingForDecision()) {
            Unit wobblingUnit = gameSave.findWobblingUnit();
            Set<Coordinate> navigable = navigationService.navigable(wobblingUnit, gameSave.grid());
            decisionService.decide(wobblingUnit, gameSave.grid(), navigable, coordinate);
            gameSave.postDecisionReset();
        }

        return false;
    }

    @Data
    @Accessors(fluent = true)
    private static class Distance {
        private final Coordinate coordinate;
        private final float distance;
        public Distance(Coordinate coordinate, Vector3 vector) {
            this.coordinate = coordinate;
            Point2D point2D = coordinate.point().get(0);
            this.distance = vector.dst(new Vector3(point2D.x(), point2D.y(), vector.z));
        }
    }
}
