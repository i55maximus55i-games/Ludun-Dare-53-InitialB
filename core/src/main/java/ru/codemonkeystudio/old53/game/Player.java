package ru.codemonkeystudio.old53.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.codemonkeystudio.old53.controls.PlayerController;

public class Player {

    // Car
    Vector2 carPos = new Vector2();
    Vector2 carSpeed = new Vector2();
    float engine = 0f;
    float speed = 0f;
    float dir = 0f;
    PlayerState playerState = PlayerState.carStand;

    Texture carTexture;
    Sprite carSprite;

    // Man
    Vector2 manPos = new Vector2();
    Vector2 manSpeed = new Vector2();

    Texture manTexture;
    Sprite manSprite;


    PlayerController playerController;

    public Player(PlayerController playerController, String car, int player) {
        carTexture = new Texture(car + ".png");
        carSprite = new Sprite(carTexture);

        manTexture = new Texture("cheliki.png");
        manSprite = new Sprite(new TextureRegion(manTexture, 56 * player, 0, 56, 82));

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
            // TODO: 30.04.2023 Физика
            // TODO: 29.04.2023 Стрельба
            // TODO: 29.04.2023 Урон в лицо
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
        }
        if (playerState == PlayerState.manEscape) {
            manSprite.setPosition(manPos.x, manPos.y);
            manSprite.draw(batch);
        }
        // TODO: 29.04.2023 Отображение игроков
    }

}
