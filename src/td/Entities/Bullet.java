package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Components.Hittable;
import td.Components.MovementToTarget;
import td.Components.Shift;
import td.Game;

/**
 * Сущность "Пуля" движется прямо к указаной цели.
 * Поведение зависит от аргумента конструктора type.
 * Красные пули двигаются медленно и наносят обычный урон.
 * Залёные пули двигаются быстрее красных, но наносят меньше урона.
 * Синие пули двигаются быстрее прочих и наносят больше всего урона.
 */
public class Bullet extends Entity {
    public enum BulletType {
        Red,
        Green,
        Blue,
    }

    public Bullet(final Entity target, final BulletType type) {
        super();

        final Color color;
        final double speed;
        final int damage;
        switch (type) {
            case Red:
                color = Color.RED;
                damage = 10;
                speed = 250;
                break;
            case Green:
                color = Color.GREEN;
                damage = 5;
                speed = 500;
                break;
            case Blue:
                color = Color.BLUE;
                damage = 100;
                speed = 1250;
                break;
            default:
                color = Color.RED;
                damage = 10;
                speed = 250;
                break;
        }
        setType(Game.EntityType.Bullet);
        final Group view = new Group();
        final Circle outerCircle = new Circle(5, Color.BLACK);
        outerCircle.setTranslateX(5);
        outerCircle.setTranslateY(5);
        view.getChildren().add(outerCircle);
        final Circle innerCircle = new Circle(3, color);
        innerCircle.setTranslateX(5);
        innerCircle.setTranslateY(5);
        view.getChildren().add(innerCircle);
        setView(view);
        addComponent(new Shift(5));
        addComponent(new MovementToTarget(
                new MovementToTarget.MovementPathBuilder(target).build(),
                speed,
                () -> {
                    if (target.isActive())
                        target.getComponent(Hittable.class).hit(damage);
                    removeFromWorld();
                }
        ));
    }
}
