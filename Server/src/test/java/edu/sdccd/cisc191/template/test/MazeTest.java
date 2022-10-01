package edu.sdccd.cisc191.template.test;

import edu.sdccd.cisc191.template.GameManager;
import edu.sdccd.cisc191.template.Vector2;
import edu.sdccd.cisc191.template.enums.MovementDirection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Checks to see if the major functions are working properly.
 */


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class MazeTest {

   private GameManager _gameManager;


    @BeforeAll
    public void setUp(){
        _gameManager = new GameManager();

    }

    @Test
    public void checkLoadMap(){
        _gameManager.loadMap();
        assertTrue(_gameManager.getMap() != null && _gameManager.getMap()[0][0] != null);
    }

    @Test
    public void checkWallCollision(){
        _gameManager.loadMap();
        _gameManager.movePlayer(MovementDirection.Up);
        assertTrue(_gameManager.getTile(new Vector2(4, 0)).currentEntity != null);
    }

    @Test
    public void checkGuardCollision(){
        _gameManager.loadMap();
        AtomicBoolean gameOver = new AtomicBoolean(false);
        _gameManager.registerGameOverEvent((x) -> {

            gameOver.set(x);

        });
        _gameManager.teleportPlayer(new Vector2(2,1));
        _gameManager.movePlayer(MovementDirection.Left);
        assertTrue(gameOver.get() == true);
    }

    @Test
    public void checkGoalCollision(){
        _gameManager.loadMap();
        AtomicBoolean gameOver = new AtomicBoolean(true);
        _gameManager.registerGameOverEvent((x) -> {

            gameOver.set(x);

        });
        _gameManager.teleportPlayer(new Vector2(0,2));
        _gameManager.movePlayer(MovementDirection.Left);
        assertTrue(gameOver.get() == false);
    }

    @Test
    public void checkTeleport(){
        _gameManager.loadMap();
        _gameManager.teleportPlayer(new Vector2(0,2));
        assertTrue(_gameManager.getPlayer().coords.x == 0 && _gameManager.getPlayer().coords.y == 2);
    }
}
