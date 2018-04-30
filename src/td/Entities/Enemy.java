package td.Entities;

import com.almasb.fxgl.entity.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Components.Hittable;
import td.Components.Movement;
import td.Components.Target;
import td.Game;

public class Enemy extends Entity {
    public static Entity SpawnEnemy(GameWorld world, int x, int y, Home target) {
        Entity entity = Entities.builder()
                .type(Game.EntityType.Enemy)
                .at(x - 10, y - 10)
                .with(new Target(10, 10))
                .viewFromNode(new Circle(10, Color.rgb(0xe5, 0x73, 0x73)))
                .build();
        entity.addComponent(new Movement(
                target.getCenter().subtract(10, 10),
                50,
                () -> {
                    target.hit(1);
                    entity.removeFromWorld();
                }
        ));
        entity.addComponent(new Hittable(entity::removeFromWorld));
        world.addEntity(entity);
        return entity;
    }
}
