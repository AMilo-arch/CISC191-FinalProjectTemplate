package edu.sdccd.cisc191.template.entities;

import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.Vector2;
import javafx.scene.paint.Color;

/**
 * The entity setting up the guard object which ends the game when the player collides with it.
 */

public class Guard extends Entity {


    // constructor
    public Guard(Vector2 coords) {
        super(coords, null);
    }

    @Override
    public Color getColor() {
        return Color.DARKRED;
    }

    @Override
    public CollisionType onCollision(Entity entity) {
        return CollisionType.Guard;
    }

}
