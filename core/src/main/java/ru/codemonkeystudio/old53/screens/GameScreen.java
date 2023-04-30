package ru.codemonkeystudio.old53.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.codemonkeystudio.old53.Application;
import ru.codemonkeystudio.old53.game.*;

import java.util.ArrayList;

public class GameScreen implements Screen {


    private final Label label;
    GameRender gameRender;
    public ArrayList<Player> players;
    public ArrayList<Bullet> bullets;
    public House house;
    public Sun sun;


    Stage stage = new Stage();

    public GameScreen() {
        players = new ArrayList<>();
        bullets = new ArrayList<>();
        players.add(new Player(Application.controlManager.playerControllers.get(0), "Ae68", 0, bullets, new Vector2(0, 60)));
        players.add(new Player(Application.controlManager.playerControllers.get(1), "r32", 1, bullets, new Vector2(200, 60)));
        players.add(new Player(Application.controlManager.playerControllers.get(2), "rx7fd", 2, bullets, new Vector2(1200, 60)));
        players.add(new Player(Application.controlManager.playerControllers.get(3), "Wrxsti", 3, bullets, new Vector2(1400, 60)));

        house = new House();
        gameRender = new GameRender(this);

        sun = new Sun();

        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("a.fnt"));
        labelStyle.fontColor = Color.BLACK;
        label = new Label("", labelStyle);
        table.add(label);
        table.pad(40f);
        stage.addActor(table);
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

        stage.act();
        stage.draw();
        label.setText("AE68: " + players.get(0).score + "\n"+
        "R32: " + players.get(1).score + "\n"+
        "RX7: " + players.get(2).score + "\n"+
        "NEXIA: " + players.get(3).score + "\n");
    }

    @Override
    public void resize(int width, int height) {
        gameRender.resize(width, height);
        house.resize();
        stage.getViewport().update(width, height, true);
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
