package ru.codemonkeystudio.old53.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import ru.codemonkeystudio.old53.Application;
import ru.codemonkeystudio.old53.game.*;

import java.util.ArrayList;

public class GameScreen implements Screen {


    GameRender gameRender;
    public ArrayList<Player> players;
    public ArrayList<Bullet> bullets;
    public House house;
    public Sun sun;

    public GameScreen() {
        players = new ArrayList<>();
        bullets = new ArrayList<>();
        players.add(new Player(Application.controlManager.playerControllers.get(0), "Ae68", 0, bullets));
        players.add(new Player(Application.controlManager.playerControllers.get(1), "r32", 1, bullets));
        players.add(new Player(Application.controlManager.playerControllers.get(2), "rx7fd", 2, bullets));
        players.add(new Player(Application.controlManager.playerControllers.get(3), "Wrxsti", 3, bullets));

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
