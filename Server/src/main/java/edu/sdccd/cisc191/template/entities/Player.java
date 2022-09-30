package edu.sdccd.cisc191.template.entities;

import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.Vector2;
import javafx.scene.paint.Color;

public class Player extends Entity {

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
