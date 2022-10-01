package edu.sdccd.cisc191.template;
import edu.sdccd.cisc191.template.enums.MovementDirection;
import edu.sdccd.cisc191.template.interfaces.GameOverEvent;
import edu.sdccd.cisc191.template.interfaces.PlayerMovementEvent;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Implements both of the interfaces, in order to update visuals when certain events happen.
 * Server will start the game.
 */

public class Server extends Application implements PlayerMovementEvent, GameOverEvent {



    private Pane _root = new Pane();
    private Vector2 _tileSize = new Vector2(100,100);
    private GameManager _gameManager;

    public static void main(String[] args){
        launch();
    }

    // the start method
    @Override
    public void start(Stage stage) throws IOException {
        _gameManager = new GameManager();
        _gameManager.registerPlayerMovementListener(this);
        _gameManager.registerGameOverEvent(this);
        _gameManager.loadMap();

        // sets the title and icon
        stage.setTitle("Press F to Restart the Game");
        stage.getIcons().add(new Image("/moneybag.png"));
        Vector2 size = getWindowSize(_gameManager.getMapSize());
        stage.setMinWidth(size.x);
        stage.setMinHeight(size.y);
        stage.setScene(new Scene(createContent(size)));
        stage.setResizable(false);
        stage.show();
        // This handles any input from the player
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.W) {

                _gameManager.movePlayer(MovementDirection.Up);
                System.out.println("Moving player up!");
            }
            else if(key.getCode()== KeyCode.A){
                _gameManager.movePlayer(MovementDirection.Left);
                System.out.println("Moving player left!");
            }
            else if(key.getCode()== KeyCode.S){

                _gameManager.movePlayer(MovementDirection.Down);
                System.out.println("Moving player down!");
            }
            else if(key.getCode()== KeyCode.D){
                _gameManager.movePlayer(MovementDirection.Right);
                System.out.println("Moving player right!");

            }
            if(key.getCode() == KeyCode.F){

                _root.getChildren().clear();
                _gameManager.loadMap();
                createContent(size);

            }
        });

    }

    /**
     * Draws every tile on the map, as a square, sets the size and the position of each. 100x100
     * @param windowSize
     * @return
     */
    private Parent createContent(Vector2 windowSize){

        _root.setMinSize(windowSize.x,windowSize.y);
        Vector2 mapSize = _gameManager.getMapSize();
        Tile[][] map = _gameManager.getMap();

        for (int x = 0; x < mapSize.x; x++) {
            for (int y = mapSize.y - 1; y >= 0; y--){
                Tile tile = map[x][y];
                tile.setupSprite((_tileSize.x * y), (_tileSize.y * x), _tileSize);
                updateSpriteColor(tile);
                _root.getChildren().add(tile.sprite.getStackPane());

            }
        }
        return _root;
    }

    // Getting the size of the entire map window, based on the amount of tiles and how big the tiles are.
    private Vector2 getWindowSize(Vector2 mapSize){

        int xSize = (mapSize.x * _tileSize.x);
        int ySize = (mapSize.y * _tileSize.y);
        return new Vector2(xSize, ySize);

    }

    // Triggers when the player move event is called
    @Override
    public void onPlayerMove(Tile currentTile, Tile previousTile) {

        updateSpriteColor(previousTile);
        updateSpriteColor(currentTile);

    }

    // Triggered when a gameoverevent is called. Passes whether it is a win or lose
    @Override
    public void onGameOver(boolean isGameFailed){
        Vector2 windowSize = getWindowSize(_gameManager.getMapSize());
        Vector2 center = new Vector2(windowSize.x / 2, windowSize.y / 2);
        String msg = " ";
        Color color = null;
        if(isGameFailed == true){

            msg = "Game Over!";
            color = Color.RED;
        }
        else{

            msg = "You Won!";
            color = Color.LIMEGREEN;
        }

        Label label = new Label(msg);
        label.setTextFill(color);
        label.setFont(new Font("Arial", 50));

        label.relocate(center.x - (msg.length() * 12.5), center.y);
        _root.getChildren().add(label);

    }

    // Updates the tile fill color based on the entity currently on it
    private void updateSpriteColor(Tile tile){
        // black denotes an empty tile.
        Color color = Color.BLACK;
        if(tile.currentEntity != null){

            color = tile.currentEntity.getColor();

        }
        tile.sprite.setFill(color);

    }



}
