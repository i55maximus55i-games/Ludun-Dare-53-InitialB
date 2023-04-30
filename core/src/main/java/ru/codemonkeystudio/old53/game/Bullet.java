package ru.codemonkeystudio.old53.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Bullet {

    Vector2 pos;
    Vector2 speed;
    Texture texture;
    public Player whoShoot;
    public boolean alive = true;

    public Bullet(Vector2 pos, Texture texture, Player whoShoot, float dir) {
        this.pos = pos;
        this.texture = texture;
        this.whoShoot = whoShoot;
        speed = new Vector2(1400f, 0f).setAngleDeg(dir);
    }

    public void update(ArrayList<Player> players, float delta) {
        pos.mulAdd(speed, delta);
        for (Player player : players) {
            if (player == whoShoot) continue;
            if (pos.dst(player.carPos) < player.carTexture.getHeight() / 2f) {
                player.hp--;
                if (player.hp == 0 && (player.playerState == PlayerState.carFly || player.playerState == PlayerState.carStand)) {
                    whoShoot.score++;
                }
                alive = false;
            }
            if (player.playerState == PlayerState.manEscape) {
                if (pos.dst(player.manPos.x, player.manPos.y + player.manTexture.getHeight() / 2f) < player.manTexture.getHeight() / 2f) {
                    whoShoot.score += 2;
                    alive = false;
                    player.manAlive = false;
                }
                if (pos.dst(player.manPos.x, player.manPos.y + player.manTexture.getHeight() / 2f + 60f) < player.manTexture.getHeight() / 2f) {
                    alive = false;
                    player.parachuteOpen = false;
                    player.parachuteAlive = false;
                }
            }
        }
        if (pos.x < 0f) alive = false;
        if (pos.y < 0f) alive = false;
        if (pos.x > Gdx.graphics.getWidth()) alive = false;
        if (pos.y > Gdx.graphics.getHeight()) alive = false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, pos.x - texture.getWidth() / 2f, pos.y - texture.getHeight() / 2f);
    }

}
