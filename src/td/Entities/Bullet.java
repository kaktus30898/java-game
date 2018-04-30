package td.Entities;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Components.Hittable;
import td.Components.Movement;
import td.Components.MovementToTarget;
import td.Components.Target;
import td.Game;

public class Bullet {

    public Bullet(GameWorld world, int x, int y, Entity target) {
        Hittable hitTarget = target.getComponent(Hittable.class);
        int radius = 2;
        Entity result = Entities.builder()
                .type(Game.EntityType.Bullet)
                .at(x - radius, y - radius)
                .viewFromNode(new Circle(radius, Color.rgb(0x00, 0x73, 0x73)))
                .with(new Target(radius, radius))
                .build();
        result.addComponent(new MovementToTarget(
                target,
                250,
                () -> {
                    hitTarget.hit(1);
                    result.removeFromWorld();
                }
        ));
        world.addEntity(result);
    }
}
