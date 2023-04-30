package ru.codemonkeystudio.old53.controls;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

import java.util.ArrayList;

public class ControlManager {

    public ArrayList<PlayerController> playerControllers = new ArrayList<>();

    public ControlManager() {
//        playerControllers.add(new PlayerControllerKeyboardWasd());

        for (Controller controller : Controllers.getControllers()) {
            PlayerControllerGamepad pc = new PlayerControllerGamepad(controller);
            playerControllers.add(pc);
            controller.addListener(pc);
        }

        Controllers.addListener(new ControlListener());
    }

    public void update() {
        for (PlayerController pc : playerControllers) {
            pc.update();
        }
    }

}
