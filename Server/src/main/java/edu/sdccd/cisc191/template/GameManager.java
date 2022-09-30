package edu.sdccd.cisc191.template;

import edu.sdccd.cisc191.template.entities.*;
import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.enums.MovementDirection;
import edu.sdccd.cisc191.template.interfaces.GameOverEvent;
import edu.sdccd.cisc191.template.interfaces.PlayerMovementEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager {

    /*

     */


    private Tile[][] _map;
    private Player _player;
    private List<PlayerMovementEvent> _listeners = new ArrayList<PlayerMovementEvent>();
    private List<GameOverEvent> _gameOverListener = new ArrayList<GameOverEvent>();
    private Boolean _canMove;

    public void registerPlayerMovementListener(PlayerMovementEvent evt){
        if(_listeners == null){

            _listeners = new ArrayList<PlayerMovementEvent>();

        }
        _listeners.add(evt);

    }

    public void registerGameOverEvent(GameOverEvent evt){
        if(_gameOverListener == null){

            _gameOverListener = new ArrayList<GameOverEvent>();

        }
        _gameOverListener.add(evt);

    }

    public Tile getTile(Vector2 coords){

        /* Guard clause allows you to do conditional checks without increasing the indentation of the entire block
           of code.
        */
        if(coords.x > _map.length - 1 || coords.y > _map[0].length - 1 || coords.x < 0 || coords.y < 0){
            return null;
        }
        return _map[coords.x][coords.y];

    }
    public Player getPlayer(){
        return _player;
    }

    public List<Tile> convertMapToList() {
        List<Tile> list = new ArrayList<Tile>();
        for (Tile[] array : _map) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }

    public Tile[][] getMap(){
        return _map;
    }
    public Vector2 getMapSize(){
        return new Vector2(_map.length,_map[0].length);
    }

    /**
     * asdfasdfasdfasdf
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
            ^ = player
            - = empty tile
            G = guard
            _ = horizontal wall
            | = vertical wall
            $ = goal
         */

        for (int x = 0; x < sMap.length; x++) {
            for (int y = sMap[x].length - 1; y >= 0; y--) {
                Vector2 coords = new Vector2(x, y);

                Entity entity = entityFactory(sMap[x][y], coords);

                if(entity != null && entity.getClass() == Player.class) {
                    _player = (Player) entity;

                }

                _map[x][y] = new Tile(coords, entity);

            }
        }
        _canMove = true;
    }


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

    public void movePlayer(MovementDirection direction){
        if(_canMove == false){
            return;
        }

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
        move.x += _player.coords.x;
        move.y += _player.coords.y;

        Tile nextTile = getTile(move);
        Tile currentTile = getTile(_player.coords);

        if(nextTile == null || currentTile == null) return;

        if(nextTile.currentEntity != null){

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
        currentTile.replaceEntity(null);
        nextTile.replaceEntity(_player);
        _player.coords = move;
        _listeners.forEach((x) -> x.onPlayerMove(currentTile, nextTile));
        System.out.println("New player coords: "+ move.x + " " + move.y);

    }

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