package edu.sdccd.cisc191.template.entities;
import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.Vector2;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public abstract class Entity {
/*

 */
    public Vector2 coords;
    protected Image _image;

    public Entity(Vector2 coords, String imagePath){

        if(imagePath != null && imagePath.isEmpty() == false){

           _image = new Image(imagePath);

        }
        this.coords = coords;
    }
    public abstract Color getColor();



    // Anyone who extends this class has to implement this method.
    public abstract CollisionType onCollision(Entity entity);
    public Image getImage(){
        return _image;

    }

}
