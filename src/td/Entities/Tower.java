package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import td.Components.Shift;
import td.Game;
import td.GameContext;
import td.Helpers.List;

/**
 * Сущность "Башня". Периодически стреляет в случайных "врагов" "пулями".
 * Интенсивность стрельбы зависит от типа "башни".
 * Красные башни стречают часто по 1 красной пуле.
 * Зелёные башни стреляют реже красных, но по 15 зелёных пуль за раз.
 * Синие башни стреляют реже всех по 5 синих пуль за раз.
 */
public class Tower extends Entity {
    public enum TowerType {
        Red,
        Green,
        Blue,
    }

    private final GameContext context;
    private final Bullet.BulletType bulletType;
    private final int bulletCountAtShot;
    private final int radius;

    public Tower(final GameContext context, final TowerType type) {
        super();
        this.context = context;

        final Color color;
        final int duration;
        switch (type) {
            case Red:
                color = Color.RED;
                duration = 50;
                bulletType = Bullet.BulletType.Red;
                bulletCountAtShot = 1;
                radius = 125;
                break;
            case Green:
                color = Color.GREEN;
                duration = 500;
                bulletType = Bullet.BulletType.Green;
                bulletCountAtShot = 15;
                radius = 125;
                break;
            case Blue:
                color = Color.BLUE;
                duration = 1500;
                bulletType = Bullet.BulletType.Blue;
                bulletCountAtShot = 5;
                radius = 250;
                break;
            default:
                color = Color.RED;
                duration = 50;
                bulletType = Bullet.BulletType.Red;
                bulletCountAtShot = 1;
                radius = 125;
                break;
        }

        setType(Game.EntityType.Tower);
        final Group view = new Group();
        final Circle outerCircle = new Circle(15, Color.BLACK);
        outerCircle.setTranslateX(15);
        outerCircle.setTranslateY(15);
        view.getChildren().add(outerCircle);
        final Circle innerCircle = new Circle(12, color);
        innerCircle.setTranslateX(15);
        innerCircle.setTranslateY(15);
        view.getChildren().add(innerCircle);
        setView(view);
        addComponent(new Shift(15));

        context.runAtInterval(this::makeShoot, Duration.millis(duration));
    }

    public void shoot(Entity target) {
        context.spawn(new Bullet(target, bulletType), getComponent(Shift.class).getTarget());
    }

    public void makeShoot() {
        for (int i = 0; i < bulletCountAtShot; i++) {
            final Entity enemy = List.getRandomElement(context.getEntitiesInRange(
                    Game.EntityType.Enemy,
                    getComponent(Shift.class).getTarget(),
                    radius
            ));
            if (enemy == null)
                break;
            shoot(enemy);
        }
    }
}
