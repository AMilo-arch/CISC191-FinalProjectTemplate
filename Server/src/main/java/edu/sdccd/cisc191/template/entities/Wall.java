package edu.sdccd.cisc191.template.entities;

import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.Vector2;
import javafx.scene.paint.Color;

/**
 * Entity that sets up an immovable object, does not allow the player to "move" onto this tile.
 */

public class Wall extends Entity {

   // constructor
    public Wall(Vector2 coords) {
        super(coords, null);
    }
    @Override
    public Color getColor() {
        return Color.GREY;
    }

    @Override
    public CollisionType onCollision(Entity entity) {
        return CollisionType.Wall;
    }

}
