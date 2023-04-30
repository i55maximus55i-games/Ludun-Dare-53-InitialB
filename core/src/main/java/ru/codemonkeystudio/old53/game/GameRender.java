package ru.codemonkeystudio.old53.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.codemonkeystudio.old53.screens.GameScreen;


public class GameRender {

    /** Viewport */
    ScreenViewport viewport;
    OrthographicCamera camera;

    /** Batch */
    SpriteBatch batch;

    GameScreen gameScreen;

    Texture backTexture;

    public GameRender(GameScreen gameScreen) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new ScreenViewport(camera);

        batch = new SpriteBatch();

        backTexture = new Texture("bghd2.png");

        this.gameScreen = gameScreen;
    }

    public void draw() {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        {
            batch.draw(backTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            gameScreen.sun.draw(batch);
            for (Bullet bullet: gameScreen.bullets) {
                bullet.draw(batch);
            }
            for (Player player : gameScreen.players) {
                player.draw(batch);
            }
            gameScreen.house.draw(batch);

        }
        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
