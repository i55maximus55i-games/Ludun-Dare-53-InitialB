package ru.codemonkeystudio.old53.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    // Man
    Vector2 manPos = new Vector2();
    Vector2 manSpeed = new Vector2();
    boolean manAlive = true;

    Texture manTexture;
    Sprite manSprite;


    PlayerController playerController;

    public Player(PlayerController playerController, String car, int player, ArrayList<Bullet> bullets) {
        this.bullets = bullets;

        carTexture = new Texture(car + ".png");
        carSprite = new Sprite(carTexture);

        manTexture = new Texture("cheliki.png");
        manSprite = new Sprite(new TextureRegion(manTexture, 56 * player, 0, 56, 82));

        bulletTexture = new Texture("bullet.png");

        this.playerController = playerController;
    }

    public void update(Float delta) {
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
            carPos.add(new Vector2(engine * 500f * delta, 0f).setAngleDeg(dir));
            if (carPos.x > Gdx.graphics.getWidth()) carPos.x = carPos.x % Gdx.graphics.getWidth();
            if (carPos.x < 0) carPos.x = Gdx.graphics.getWidth();
            if (carPos.y > Gdx.graphics.getHeight()) carPos.y = Gdx.graphics.getHeight();
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
                // TODO: 30.04.2023 Дать импульс на выброс из кабины
                playerState = PlayerState.manEscape;
            }
        } else if (playerState == PlayerState.manEscape) {
            // TODO: 29.04.2023 Мужик летит, может открыть парашют, может ноги в жопу вбить
            // TODO: 29.04.2023 Можно на парашюте двигаться влево/вправо
            // TODO: 30.04.2023 Смэрть
        } else if (playerState == PlayerState.manMove) {
            // TODO: 29.04.2023 Мужик на земле, можно ходить, можно зайти в ангар и сесть в машину
        }
    }

    void draw(SpriteBatch batch) {
        if (playerState == PlayerState.carStand || playerState == PlayerState.carFly) {
            carSprite.setPosition(carPos.x - carSprite.getWidth() / 2f, carPos.y);
            carSprite.setOrigin(carSprite.getWidth() / 2, carSprite.getHeight() / 2);
            carSprite.setRotation(dir);
            carSprite.draw(batch);
            // TODO: 30.04.2023 Отображение урона
        }
        if (playerState == PlayerState.manEscape) {
            manSprite.setPosition(manPos.x, manPos.y);
            manSprite.draw(batch);
            // TODO: 30.04.2023 Парашют
        }
    }

}
