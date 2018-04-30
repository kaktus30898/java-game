package td.Entities;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Game;

public class Tower {
    private GameWorld world;
    private Entity entity;

    public Tower(GameWorld world, int x, int y) {
        this.world = world;
        this.entity = Entities.builder()
                .type(Game.EntityType.Fabric)
                .at(x - 15, y - 15)
                .viewFromNode(new Circle(15, Color.rgb(0x78, 0x90, 0x9c)))
                .buildAndAttach(world);
    }

    public Point2D getCenter() {
        return entity.getPosition()
                .add(15, 15);
    }

    public Bullet shoot(Entity target) {
        final Point2D initialPosition = getCenter();
        return new Bullet(
                world,
                (int) initialPosition.getX(),
                (int) initialPosition.getY(),
                target
        );
    }
}
