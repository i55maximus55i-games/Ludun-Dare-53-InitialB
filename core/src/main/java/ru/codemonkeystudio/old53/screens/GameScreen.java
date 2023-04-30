package ru.codemonkeystudio.old53.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import de.eskalon.commons.screen.ManagedScreen;
import ru.codemonkeystudio.old53.controls.ControlManager;
import ru.codemonkeystudio.old53.controls.PlayerController;
import ru.codemonkeystudio.old53.controls.PlayerControllerKeyboardWasd;
import ru.codemonkeystudio.old53.game.*;

import java.util.ArrayList;

public class GameScreen implements Screen {

    ArrayList<PlayerController> playerControllers;

    GameRender gameRender;
    public ArrayList<Player> players;
    public ArrayList<Bullet> bullets;
    public House house;
    public Sun sun;

    public GameScreen() {
        playerControllers = new ArrayList<>();
        playerControllers.add(new PlayerControllerKeyboardWasd());

        players = new ArrayList<>();
        bullets = new ArrayList<>();
        players.add(new Player(playerControllers.get(0), "Ae68", 0, bullets));

        house = new House();
        gameRender = new GameRender(this);

        sun = new Sun();
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void hide() {
    }

    @Override
    public void render(float delta) {
        for (Player player : players) {
            player.update(delta);
        }
        for (Bullet bullet : bullets) {
            bullet.update(players, delta);
        }
        bullets.removeIf(bullet -> !bullet.alive);
        gameRender.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameRender.resize(width, height);
        house.resize();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
