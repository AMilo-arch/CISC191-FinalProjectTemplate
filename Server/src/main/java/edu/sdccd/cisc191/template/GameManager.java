package edu.sdccd.cisc191.template;

import edu.sdccd.cisc191.template.entities.*;
import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.enums.MovementDirection;
import edu.sdccd.cisc191.template.interfaces.GameOverEvent;
import edu.sdccd.cisc191.template.interfaces.PlayerMovementEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Controls the 2D array tile map and actually playing the game. It will control the player movement,
 * the tile map, and the winning and losing conditions.
 */

public class GameManager {

    // The 2D Array which controls the map.
    private Tile[][] _map;
    private Player _player;

    // Listen to their event when triggered.
    private List<PlayerMovementEvent> _listeners = new ArrayList<PlayerMovementEvent>();
    private List<GameOverEvent> _gameOverListener = new ArrayList<GameOverEvent>();
    private Boolean _canMove;

    public Player getPlayer(){return _player;}
    public Tile[][] getMap(){return _map;}
    public Vector2 getMapSize(){return new Vector2(_map.length,_map[0].length);}

    /**
     * Listen for the player movement event.
     * @param evt
     */
    public void registerPlayerMovementListener(PlayerMovementEvent evt){
        if(_listeners == null){

            _listeners = new ArrayList<PlayerMovementEvent>();

        }
        _listeners.add(evt);

    }

    /**
     * Listen for the game over event.
     * @param evt
     */
    public void registerGameOverEvent(GameOverEvent evt){
        if(_gameOverListener == null){

            _gameOverListener = new ArrayList<GameOverEvent>();

        }
        _gameOverListener.add(evt);

    }

    /**
     * Get the tile in the map at the given coordinates.
     * @param coords
     * @return
     */
    public Tile getTile(Vector2 coords){

        if(coords.x > _map.length - 1 || coords.y > _map[0].length - 1 || coords.x < 0 || coords.y < 0){
            return null;
        }
        return _map[coords.x][coords.y];
    }


    /**
     * Loads the hardcoded version of the map into the tile map from an array of strings.
     */
    public void loadMap(){

        String[][] sMap = {

                /* x0 */{"-", "$", "-","-","-"},
                /* x1 */{"-", "_", "_","_","-"},
                /* x2 */{"G", "-", "-","-","-"},
                /* x3 */{"|", "-", "|","G","_"},
                /* x4 */{"^", "-", "|","-","-"}
                       /* y0, y1,  y2, y3, y4 */
        };
        _map = new Tile[sMap.length][sMap[0].length];

        /*
            Legend for array:
            ^ = player
            - = empty tile
            G = guard
            _ = horizontal wall
            | = vertical wall
            $ = goal
         */

        // Create entites on my tile map based on what is in the string map.
        for (int x = 0; x < sMap.length; x++) {
            for (int y = sMap[x].length - 1; y >= 0; y--) {
                Vector2 coords = new Vector2(x, y);

                Entity entity = entityFactory(sMap[x][y], coords);

                // is entity player
                if(entity != null && entity.getClass() == Player.class) {
                    _player = (Player) entity;

                }

                _map[x][y] = new Tile(coords, entity);

            }
        }
        _canMove = true;
    }

    /**
     * Takes a character from the string map and creates an entity based on the character given.
     * @param character
     * @param coords
     * @return
     */
    private Entity entityFactory(String character, Vector2 coords){

        switch(character){
            case "^": return new Player(coords);
            case "G": return new Guard(coords);
            case "_": return new Wall(coords);
            case "|": return new Wall(coords);
            case "$": return new Goal(coords);
            default : return null;

        }
    }

    /**
     * Go to move direction
     * @param direction
     */
    public void movePlayer(MovementDirection direction){
        if(_canMove == false){
            return;
        }
        // take in the direction and tell it which way it's moving inside of the map.
        Vector2 move = null;
        switch(direction){
            case Up:
                move = new Vector2(-1,0);
                break;
            case Down:
                move = new Vector2(1,0);
                break;
            case Left:
                move = new Vector2(0,-1);
                break;
            case Right:
                move = new Vector2(0,1);
                break;
        }
        // Add the move direction onto the players current position to get the next position
        move.x += _player.coords.x;
        move.y += _player.coords.y;

        Tile nextTile = getTile(move);
        Tile currentTile = getTile(_player.coords);

        if(nextTile == null || currentTile == null) return;

        if(nextTile.currentEntity != null){

            /**
             * collisionType needs to know what entity is on the next tile so that it can determine what happens
             * when the player collides with it. The if statements following this line determine what happens with each
             * collision type.
             */

            CollisionType collisionType = nextTile.currentEntity.onCollision(_player);
            if(collisionType == CollisionType.Wall){
                return;
            }
            if(collisionType == CollisionType.Guard){
                _canMove = false;
                _gameOverListener.forEach((x) -> x.onGameOver(true));
                return;
            }
            if(collisionType == CollisionType.Goal){
                _canMove = false;
                _gameOverListener.forEach((x) -> x.onGameOver(false));
                return;
            }

        }
        // Moves the players entity to the new position if possible.
        currentTile.replaceEntity(null);
        nextTile.replaceEntity(_player);
        _player.coords = move;
        _listeners.forEach((x) -> x.onPlayerMove(currentTile, nextTile));
        System.out.println("New player coords: "+ move.x + " " + move.y);

    }

    // A method used to send the player to any position on the map, as long as it's not blocked. Used this for test cases.
    public boolean teleportPlayer(Vector2 coords){

        Tile nextTile = getTile(coords);
        Tile currentTile = getTile(_player.coords);

        if(nextTile == null || nextTile.currentEntity != null){
            return false;
        }

        currentTile.replaceEntity(null);
        nextTile.replaceEntity(_player);
        _player.coords = nextTile.coords;
        _listeners.forEach((x) -> x.onPlayerMove(currentTile, nextTile));
        System.out.println("New player coords: "+ nextTile.coords.x + " " + nextTile.coords.y);

        return true;
    }
}