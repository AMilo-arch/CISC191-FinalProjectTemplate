package edu.sdccd.cisc191.template.entities;

import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.Vector2;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Any object on a tile will inherit from this class.
 */

public abstract class Entity {


    // coords is the position of the tile within the 2D array map.
    public Vector2 coords;

    protected Image _image;
    public Image getImage(){return _image;}


    /**
     * Constructor
     *
     * @param coords
     * @param imagePath
     */
    public Entity(Vector2 coords, String imagePath){

        if(imagePath != null && imagePath.isEmpty() == false){
           _image = new Image(imagePath);
        }
        this.coords = coords;
    }

    /**
     *  Returns the color of the entity.
     * @return
     */
    public abstract Color getColor();

    /**
     * Returns the type of collision that happens when this entity is hit.
     * @param entity
     * @return
     */

    public abstract CollisionType onCollision(Entity entity);



}
