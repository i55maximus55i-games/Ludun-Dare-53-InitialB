package ru.codemonkeystudio.old53;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import ru.codemonkeystudio.old53.controls.ControlManager;
import ru.codemonkeystudio.old53.screens.GameScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Application extends Game {

    public static ControlManager controlManager;

    @Override
    public void create() {
        controlManager = new ControlManager();
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.4f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        super.render();
        controlManager.update();
    }

}
