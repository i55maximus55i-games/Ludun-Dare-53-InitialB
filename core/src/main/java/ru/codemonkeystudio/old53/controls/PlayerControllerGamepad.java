package ru.codemonkeystudio.old53.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class PlayerControllerGamepad extends PlayerController implements ControllerListener {

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public float throttle() {
        float t = 0f;
        t += controller.getAxis(5);
        t -= controller.getAxis(4);
        return MathUtils.clamp(t, -1f, 1f);
    }

    @Override
    public float steering() {
        float t = 0f;
        t -= controller.getAxis(controller.getMapping().axisLeftX);
        t += controller.getAxis(controller.getMapping().axisLeftY);
        return MathUtils.clamp(t, -1f, 1f);
    }

    @Override
    public boolean fire() {
        return justPressed(controller.getMapping().buttonA);
    }

    @Override
    public boolean escape() {
        return controller.getButton(controller.getMapping().buttonY);
    }

    @Override
    public boolean useParachute() {
        return false;
    }

    @Override
    public float humanMove() {
        return 0;
    }


    Controller controller;
    public PlayerControllerGamepad(Controller controller) {
        this.controller = controller;
        keyPressed = new ArrayList<>();
        keyJustPressed = new ArrayList<>();
        for (int i = 0; i <= controller.getMaxButtonIndex(); i++) {
            keyPressed.add(false);
            keyJustPressed.add(false);
        }
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    ArrayList<Boolean> keyPressed;
    ArrayList<Boolean> keyJustPressed;
    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        keyPressed.set(buttonCode, true);
        keyJustPressed.set(buttonCode, true);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        keyPressed.set(buttonCode, false);
        keyJustPressed.set(buttonCode, false);
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
//        System.out.println("Axis " + axisCode + " val " + value);
        return false;
    }

    @Override
    public void update() {
        for (int i = 0; i < keyJustPressed.size(); i++) {
            keyJustPressed.set(i, false);
        }
    }

    private boolean justPressed(int buttonCode) {
        boolean t = keyJustPressed.get(buttonCode) && keyPressed.get(buttonCode);
        keyJustPressed.set(buttonCode, false);
        return t;
    }

}
