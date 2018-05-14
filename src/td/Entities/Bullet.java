package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Components.Hittable;
import td.Components.MovementToTarget;
import td.Components.Shift;
import td.Game;

public class Bullet extends Entity {
    public Bullet(final Entity target) {
        super();

        setType(Game.EntityType.Bullet);
        setView(new Circle(2, Color.rgb(0x00, 0x73, 0x73)));
        addComponent(new Shift(2));
        addComponent(new MovementToTarget(
                new MovementToTarget.MovementPathBuilder(target).build(),
                250,
                () -> {
                    if (target.isActive())
                        target.getComponent(Hittable.class).hit(1);
                    removeFromWorld();
                }
        ));
    }
}
