package ru.codemonkeystudio.old53.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.codemonkeystudio.old53.controls.PlayerController;

import java.util.ArrayList;

public class Player {

    PlayerState playerState = PlayerState.carStand;

    // Bullets
    ArrayList<Bullet> bullets;
    Texture bulletTexture;

    // Car
    Vector2 carPos = new Vector2();
    Vector2 carSpeed = new Vector2();
    int hp = 3;
    float engine = 0f;
    float speed = 0f;
    float dir = 0f;

    Texture carTexture;
    Sprite carSprite;

    float animTimer = 0f;
    Texture fire;
    Animation<TextureRegion> fireAnimation;
    Texture smoke;
    Animation<TextureRegion> smokeAnimation;

    // Man
    Vector2 manPos = new Vector2();
    Vector2 manSpeed = new Vector2();
    boolean manAlive = true;
    boolean manOnGround = false;
    boolean parachuteOpen = false;
    boolean parachuteAlive = true;
    Texture parachuteTexture;

    Texture manTexture;
    Sprite manSprite;


    PlayerController playerController;
    public int score;

    public Player(PlayerController playerController, String car, int player, ArrayList<Bullet> bullets) {
        this.bullets = bullets;

        carTexture = new Texture(car + ".png");
        carSprite = new Sprite(carTexture);

        manTexture = new Texture("cheliki.png");
        manSprite = new Sprite(new TextureRegion(manTexture, 56 * player, 0, 56, 82));

        bulletTexture = new Texture("bullet.png");

        fire = new Texture("fire_0.png");
        TextureRegion[][] tmp = TextureRegion.split(fire, 16, 16);
        TextureRegion[] fireFrames = new TextureRegion[32];
        int index = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 1; j++) {
                fireFrames[index++] = tmp[i][j];
            }
        }
        fireAnimation = new Animation<>(0.03f, fireFrames);

        smoke = new Texture("smoke.png");
        tmp = TextureRegion.split(smoke, 32, 32);
        TextureRegion[] smokeFrames = new TextureRegion[8];
        index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 1; j++) {
                smokeFrames[index++] = tmp[i][j];
            }
        }
        smokeAnimation = new Animation<>(0.1f, smokeFrames);

        parachuteTexture = new Texture("parachute.png");

        this.playerController = playerController;
    }

    public void update(Float delta) {
        animTimer += delta;

        if (playerState == PlayerState.carStand) {
            if (playerController.throttle() > 0.1f) {
                playerState = PlayerState.carFly;
                engine = 0.5f;
            }
        } else if (playerState == PlayerState.carFly) {
            // Руление
            dir += playerController.steering() * 180f * delta;
            // Газ
            engine += playerController.throttle() * delta;
            engine = MathUtils.clamp(engine, 0f, 1f);
            // Движение
            carSpeed.set(new Vector2(engine * 500f * delta, 0f).setAngleDeg(dir));
            carPos.add(carSpeed);
            if (carPos.x > Gdx.graphics.getWidth()) carPos.x = carPos.x % Gdx.graphics.getWidth();
            if (carPos.x < 0) carPos.x = Gdx.graphics.getWidth();
            if (carPos.y > Gdx.graphics.getHeight()) carPos.y = Gdx.graphics.getHeight();
            if (carPos.y < 0) {
                hp = 0;
                manAlive = false;
            }
            // TODO: 30.04.2023 Физика
            // TODO: 30.04.2023 EXPLOSION

            if (playerController.fire()) {
                int c = 0;
                for (Bullet b: bullets) {
                    if (b.whoShoot == this) c++; // I love C language
                }
                if (c < 1) bullets.add(new Bullet(carPos.cpy(), bulletTexture, this, dir));
            }
            if (playerController.escape()) {
                manPos = carPos.cpy();
                manSpeed.set(carSpeed).add(new Vector2(5, 0).setAngleDeg(dir + 90));
                playerState = PlayerState.manEscape;
            }
        } else if (playerState == PlayerState.manEscape) {
            if (!manOnGround) {
                if (!parachuteOpen) {
                    if (parachuteAlive && playerController.useParachute()) {
                        parachuteOpen = true;
                    }
                    manSpeed.add(0, -15 * delta);
                } else {
                    if (manSpeed.y < -2) {
                        manSpeed.y += 7 * delta;
                    } else {
                        manSpeed.y -= 7 * delta;
                    }

                    if (manSpeed.x < 0) {
                        manSpeed.x += 10 * delta;
                    } else {
                        manSpeed.x -= 10 * delta;
                    }
                    manSpeed.x += playerController.humanMove() * 11 * delta;
                }
                manPos.add(manSpeed);
            }
            if (manPos.x > Gdx.graphics.getWidth()) manPos.x = manPos.x % Gdx.graphics.getWidth();
            if (manPos.x < 0) manPos.x = Gdx.graphics.getWidth();

            if (manPos.y < 10) {
                if (manSpeed.y < -4)
                    manAlive = false;
                else {
                    manOnGround = true;
                    parachuteOpen = false;
                    parachuteAlive = false;
                }
            }
            if (manOnGround) {
                manPos.y = 0f;
                manPos.x += playerController.humanMove() * delta * 200f;
                if (Math.abs(manPos.x - Gdx.graphics.getWidth() / 2f) < 128f) {
                    hp = 3;
                    carPos.set(0, 0);
                    playerState = PlayerState.carStand;
                    dir = 0f;
                    parachuteOpen = false;
                    parachuteAlive = true;
                    manOnGround = false;
                }
            }
        }

        if (!manAlive) {
            if (playerController.start()) {
                score = 0;
                hp = 3;
                carPos.set(0, 0);
                playerState = PlayerState.carStand;
                dir = 0f;
                parachuteOpen = false;
                parachuteAlive = true;
                manOnGround = false;
                manAlive = true;
            }
        }
    }

    void draw(SpriteBatch batch) {
        // Car render
        if (hp > 0) {
            carSprite.setPosition(carPos.x - carSprite.getWidth() / 2f, carPos.y - carSprite.getHeight() / 2f);
            carSprite.setOrigin(carSprite.getWidth() / 2, carSprite.getHeight() / 2);
            carSprite.setRotation(dir);
            carSprite.draw(batch);

            if (hp == 2) {
                Vector2 t = carPos.cpy().sub(carSprite.getHeight() / 2f, carSprite.getHeight() / 2f);
                batch.draw(smokeAnimation.getKeyFrame(animTimer, true), t.x, t.y, carSprite.getHeight(), carSprite.getHeight());
                t = carPos.cpy().sub(carSprite.getHeight() / 2f, carSprite.getHeight() / 2f).add(new Vector2(carSprite.getHeight(), 0f).setAngleDeg(dir));
                batch.draw(smokeAnimation.getKeyFrame(animTimer, true), t.x, t.y, carSprite.getHeight(), carSprite.getHeight());
                t = carPos.cpy().sub(carSprite.getHeight() / 2f, carSprite.getHeight() / 2f).add(new Vector2(carSprite.getHeight(), 0f).setAngleDeg(dir + 180));
                batch.draw(smokeAnimation.getKeyFrame(animTimer, true), t.x, t.y, carSprite.getHeight(), carSprite.getHeight());
            }
            if (hp <= 1) {
                Vector2 t = carPos.cpy().sub(carSprite.getHeight() / 2f, carSprite.getHeight() / 2f);
                batch.draw(fireAnimation.getKeyFrame(animTimer, true), t.x, t.y, carSprite.getHeight(), carSprite.getHeight());
                t = carPos.cpy().sub(carSprite.getHeight() / 2f, carSprite.getHeight() / 2f).add(new Vector2(carSprite.getHeight(), 0f).setAngleDeg(dir));
                batch.draw(fireAnimation.getKeyFrame(animTimer, true), t.x, t.y, carSprite.getHeight(), carSprite.getHeight());
                t = carPos.cpy().sub(carSprite.getHeight() / 2f, carSprite.getHeight() / 2f).add(new Vector2(carSprite.getHeight(), 0f).setAngleDeg(dir + 180));
                batch.draw(fireAnimation.getKeyFrame(animTimer, true), t.x, t.y, carSprite.getHeight(), carSprite.getHeight());
            }
        }


        if (playerState == PlayerState.manEscape && manAlive) {
            manSprite.setPosition(manPos.x - manSprite.getWidth() / 2f, manPos.y);
            if (parachuteOpen) batch.draw(parachuteTexture, manPos.x - parachuteTexture.getWidth() / 2f, manPos.y + parachuteTexture.getHeight() / 2f);
            manSprite.draw(batch);
        }
    }

}
