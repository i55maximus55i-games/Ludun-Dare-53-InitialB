package ru.codemonkeystudio.old53.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import ru.codemonkeystudio.old53.Application;

public class ControlListener implements ControllerListener {
    @Override
    public void connected(Controller controller) {
        PlayerControllerGamepad pc = new PlayerControllerGamepad(controller);
        Application.controlManager.playerControllers.add(pc);
        controller.addListener(pc);
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
