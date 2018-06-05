package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.Group;
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
        final Group view = new Group();
        final Circle outerCircle = new Circle(5, Color.BLACK);
        outerCircle.setTranslateX(5);
        outerCircle.setTranslateY(5);
        view.getChildren().add(outerCircle);
        final Circle innerCircle = new Circle(3, Color.RED);
        innerCircle.setTranslateX(5);
        innerCircle.setTranslateY(5);
        view.getChildren().add(innerCircle);
        setView(view);
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
