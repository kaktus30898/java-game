package td.Entities;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import td.Game;

public class RoadElement {
    public static Entity SpawnRoad(GameWorld world, int x, int y) {
        return Entities.builder()
                .type(Game.EntityType.Road)
                .at(x - 5, y - 5)
                .viewFromNode(new Rectangle(10, 10, Color.rgb(0x03, 0x9b, 0xe5)))
                .buildAndAttach(world);
    }
}
