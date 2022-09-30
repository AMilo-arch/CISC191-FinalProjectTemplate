package edu.sdccd.cisc191.template.entities;
import edu.sdccd.cisc191.template.enums.CollisionType;
import edu.sdccd.cisc191.template.Vector2;
import javafx.scene.paint.Color;

public class Goal extends Entity {

    /*

     */
    public Goal(Vector2 coords) {
        super(coords, null);
    }
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }
    @Override
    public CollisionType onCollision(Entity entity) {
        return CollisionType.Goal;
    }


}
