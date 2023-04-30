package ru.codemonkeystudio.old53.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class House {

    Texture texture;
    Rectangle collider;

    public House() {
        this.texture = new Texture("domic.png");
        collider = new Rectangle(Gdx.graphics.getWidth() / 2f - texture.getWidth() / 2f, 0f, texture.getWidth(), texture.getHeight());
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, Gdx.graphics.getWidth() / 2f - texture.getWidth() / 2f, 0f);
    }

    public void resize() {
        collider.x = (Gdx.graphics.getWidth() - texture.getWidth()) / 2f;
    }

}
