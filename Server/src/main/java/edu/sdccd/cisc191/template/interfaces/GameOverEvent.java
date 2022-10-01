package edu.sdccd.cisc191.template.interfaces;

/**
 * This event is triggered any time a game over collision happens. such as the player running into a guard.
 */

public interface GameOverEvent {
    void onGameOver(boolean isGameFailed);
}
