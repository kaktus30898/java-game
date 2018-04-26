package td.Entities;

import com.almasb.fxgl.entity.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Game;

public class Home {
    public interface HomeCom {
        void onDead();
    }

    private SimpleIntegerProperty hitPoints = new SimpleIntegerProperty(10);
    private boolean dead = false;
    private HomeCom com;
    private Entity entity;

    public Home(Entity entity, HomeCom com) {
        this.com = com;
        this.entity = entity;
    }

    public SimpleIntegerProperty hitPoints() {
        return hitPoints;
    }

    public void hit(int decrement) {
        if (dead) return;
        final int points = hitPoints.getValue() - decrement;
        hitPoints.setValue(points);
        if (points <= 0) {
            dead = true;
            com.onDead();
        }
    }

    public static Entity SpawnHome(GameWorld world, int x, int y) {
        return Entities.builder()
                .type(Game.EntityType.Home)
                .at(x - 25, y - 25)
                .viewFromNode(new Circle(25, Color.rgb(0x7c, 0xb3, 0x42)))
                .buildAndAttach(world);
    }

    public Point2D getCenter() {
        return entity.getPosition()
                .add(25, 25);
    }
}
