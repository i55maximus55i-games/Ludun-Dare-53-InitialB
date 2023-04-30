package ru.codemonkeystudio.old53.controls;

public abstract class PlayerController {

    public abstract boolean start();

    public abstract float throttle();

    public abstract float steering();

    public abstract boolean fire();

    public abstract boolean escape();

    public abstract boolean useParachute();

    public abstract float humanMove();

    public abstract void update();
}
