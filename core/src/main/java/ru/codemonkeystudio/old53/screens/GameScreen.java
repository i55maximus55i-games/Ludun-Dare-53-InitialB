package ru.codemonkeystudio.old53.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.eskalon.commons.screen.ManagedScreen;

public class GameScreen extends ManagedScreen {

    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;

    OrthographicCamera camera = new OrthographicCamera();
    ScreenViewport screenViewport = new ScreenViewport(camera);

    Texture texture = new Texture("Car.png");

    @Override
    protected void create() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        shapeRenderer.end();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(texture, 0, 0);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        screenViewport.update(width / 2, height / 2, true);
    }

    @Override
    public void dispose() {

    }
}
