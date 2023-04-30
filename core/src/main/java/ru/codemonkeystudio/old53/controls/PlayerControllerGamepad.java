package ru.codemonkeystudio.old53.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;

public class PlayerControllerGamepad extends PlayerController implements ControllerListener {

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public float throttle() {
        return 0;
    }

    @Override
    public float steering() {
        return 0;
    }

    @Override
    public boolean fire() {
        return false;
    }

    @Override
    public boolean escape() {
        return false;
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
        System.out.println("prop_govno");
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }
}
