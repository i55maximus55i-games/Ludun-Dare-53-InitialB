package ru.codemonkeystudio.old53.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sun {

    Texture texture;

    public Sun() {
        texture = new Texture("sun.png");
    }

    void draw(SpriteBatch batch) {
        batch.draw(texture, Gdx.graphics.getWidth() * 2 / 3f, Gdx.graphics.getHeight() * 3 / 4f);
    }
}
