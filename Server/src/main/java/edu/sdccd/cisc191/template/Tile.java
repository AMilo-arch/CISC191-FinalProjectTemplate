package edu.sdccd.cisc191.template;

import edu.sdccd.cisc191.template.entities.Entity;
import javafx.scene.image.Image;

/**
 * Tile denotes an area within the map. The map is made up of a 2D array of tiles. Every position on the map is
 * a tile.
 */

public class Tile {

    public Vector2 coords;
    public Sprite sprite;
    public Entity currentEntity;
    public Entity cachedEntity;

    // constructor
    public Tile(Vector2 coords, Entity currentEntity){
        this.coords = coords;
        this.currentEntity = currentEntity;
    }

    // Sets the image on a tile.
    public void setupSprite( int x, int y, Vector2 tileSize){
        Image image = null;
        if(currentEntity != null){
            image = currentEntity.getImage();
        }
        sprite = new Sprite(image, tileSize.x * 0.25, tileSize.y * 0.25, x, y, tileSize.x, tileSize.y);
    }

    // Replaces which entity is the current entity on this tile.
    public void replaceEntity(Entity entity){

        if(entity == null){
            currentEntity = cachedEntity;
            cachedEntity = null;
            return;
        }
        cachedEntity = currentEntity;
        currentEntity = entity;
    }

}
