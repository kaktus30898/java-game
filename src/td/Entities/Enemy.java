package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Components.Hittable;
import td.Components.MovementToTarget;
import td.Components.Shift;
import td.Game;

/**
 * Сущность "Враг" движется по заданному маршруту.
 * По достижении цели "бьёт" её, после чего умирает сам.
 * "Врага" так же можно "бить" и он может умирать.
 */
public class Enemy extends Entity {
    /**
     * @param path Путь следования "врага".
     * @param target "Цель" врага.
     * @param hitPoints Количество очков здоровья "врага".
     */
    public Enemy(final MovementToTarget.MovementPath path, final Home target, final int hitPoints) {
        super();

        setType(Game.EntityType.Enemy);
        final int radius = 10 + (hitPoints - 10) / 100;
        setView(new Circle(radius, Color.rgb(0xe5, 0x73, 0x73)));
        addComponent(new Shift(radius));
        addComponent(new MovementToTarget(
                path,
                60,
                () -> {
                    if (target.isActive())
                        target.getComponent(Hittable.class).hit(1);
                    removeFromWorld();
                }
        ));
        addComponent(new Hittable(hitPoints, this::removeFromWorld));
    }
}
