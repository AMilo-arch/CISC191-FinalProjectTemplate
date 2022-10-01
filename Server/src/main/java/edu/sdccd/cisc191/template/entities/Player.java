package edu.sdccd.cisc191.template.entities;

import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.Vector2;
import javafx.scene.paint.Color;

/**
 * Entity that sets up the player.
 */

public class Player extends Entity {

    // constructor
    public Player(Vector2 coords) {
        super(coords, null);
    }

    @Override
    public Color getColor() {
        return Color.DODGERBLUE;
    }

    @Override
    public CollisionType onCollision(Entity entity) {
        return CollisionType.None;
    }

}
