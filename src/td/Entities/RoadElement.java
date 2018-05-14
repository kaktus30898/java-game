package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import td.Components.Shift;
import td.Game;
import td.GameContext;

public class RoadElement extends Entity {
    public RoadElement() {
        super();

        setType(Game.EntityType.Road);
        setView(new Rectangle(10, 10, Color.rgb(0x03, 0x9b, 0xe5)));
        addComponent(new Shift(5));
    }

    public static void DrawRoad(GameContext context, Point2D start, Point2D end) {
        double x = start.getX();
        final double endX = end.getX();
        double y = start.getY();
        final double endY = end.getY();

        final double _dx = endX - x;
        final double _dy = endY - y;
        final double difLength = Math.sqrt((_dx * _dx) + (_dy * _dy));
        final double dx = 10 * _dx / difLength;
        final double dy = 10 * _dy / difLength;

        boolean condX, condY;
        do {
            condX = Math.abs(endX - x) > 7;
            if (condX) {
                context.spawn(new RoadElement(), x, y);
                x += dx;
            }
            condY = Math.abs(endY - y) > 7;
            if (condY) {
                context.spawn(new RoadElement(), x, y);
                y += dy;
            }
        } while (condX || condY);
    }
}
