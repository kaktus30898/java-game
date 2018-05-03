package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import td.Components.Shift;
import td.Game;

public class RoadElement extends Entity {
    public RoadElement() {
        super();

        setType(Game.EntityType.Road);
        setView(new Rectangle(10, 10, Color.rgb(0x03, 0x9b, 0xe5)));
        addComponent(new Shift(5));
    }
}
