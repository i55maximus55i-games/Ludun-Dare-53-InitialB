package ru.codemonkeystudio.old53;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import ru.codemonkeystudio.old53.screens.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Application extends ManagedGame<ManagedScreen, ScreenTransition> {

    @Override
    public void create() {
        super.create();

        screenManager.addScreen("Game", new GameScreen());

        screenManager.pushScreen("Game", null);
    }

}
