package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Components.Hittable;
import td.Components.Shift;
import td.Game;

/**
 * Сущность "Дом". Создан, чтобы умирать.
 */
public class Home extends Entity {
    public Home(final Runnable onDead) {
        super();

        setType(Game.EntityType.Home);
        setView(new Circle(25, Color.rgb(0x7c, 0xb3, 0x42)));
        addComponent(new Shift(25));
        addComponent(new Hittable(12, onDead));
    }
}
