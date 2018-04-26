package td.Entities;

import com.almasb.fxgl.entity.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Components.Movement;
import td.Game;

public class Enemy {
    public static Entity SpawnEnemy(GameWorld world, int x, int y, Home target) {
        final Entity result = Entities.builder()
                .type(Game.EntityType.Enemy)
                .at(x - 10, y - 10)
                .viewFromNode(new Circle(10, Color.rgb(0xe5, 0x73, 0x73)))
                .build();
        result.addComponent(new Movement(target.getCenter().subtract(10, 10), 75, () -> {
            target.hit(1);
            result.removeFromWorld();
        }));
        world.addEntity(result);
        return result;
    }
}
