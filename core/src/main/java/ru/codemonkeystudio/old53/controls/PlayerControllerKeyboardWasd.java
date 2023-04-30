package ru.codemonkeystudio.old53.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

public class PlayerControllerKeyboardWasd extends PlayerController {
    @Override
    public boolean start() {
        return Gdx.input.isKeyJustPressed(Input.Keys.E);
    }

    @Override
    public float throttle() {
        float t = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) t += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) t -= 1f;
        return MathUtils.clamp(t, -1f, 1f);
    }

    @Override
    public float steering() {
        float t = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) t += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) t -= 1f;
        return MathUtils.clamp(t, -1f, 1f);
    }

    @Override
    public boolean fire() {
        return false;
    }

    @Override
    public boolean escape() {
        return Gdx.input.isKeyJustPressed(Input.Keys.Q);
    }

    @Override
    public boolean useParachute() {
        return false;
    }

    @Override
    public float humanMove() {
        return 0;
    }
}
