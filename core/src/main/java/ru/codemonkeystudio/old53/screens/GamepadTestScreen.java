package ru.codemonkeystudio.old53.screens;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.eskalon.commons.screen.ManagedScreen;

public class GamepadTestScreen extends ManagedScreen {

    private Stage stage;

    @Override
    protected void create() {
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        for (Controller c : Controllers.getControllers()) {
            float t = Math.max(c.getAxis(4), c.getAxis(5));
            c.startVibration(100, MathUtils.clamp(MathUtils.norm(0f, 1f, t), 0, 1f));
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
