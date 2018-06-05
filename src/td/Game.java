package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import td.Components.Clickable;
import td.Components.Hittable;
import td.Components.MovementToTarget;
import td.Entities.EnemyFabric;
import td.Entities.Home;
import td.Entities.RoadElement;
import td.Entities.Tower;

public class Game extends GameApplication {
    public enum EntityType {
        Road,
        Enemy,
        Fabric,
        TowerPosition,
        Tower,
        Bullet,
        Home,
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(350);
        settings.setTitle("TD");
        settings.setVersion("0.0.3");
    }

    private Home home;

    @Override
    protected void initGame() {
        final GameContext context = new GameContext(this);

        final Point2D roadPointA = new Point2D(100, 100);
        final Point2D roadPointB = new Point2D(500, 100);
        final Point2D roadPointC = new Point2D(250, 300);
        RoadElement.DrawRoad(context, roadPointA, roadPointB);
        RoadElement.DrawRoad(context, roadPointB, roadPointC);

        final MovementToTarget.MovementPathBuilder road = new MovementToTarget.MovementPathBuilder(
                roadPointA,
                roadPointB,
                roadPointC
        );

        home = new Home(() -> {
            getMasterTimer().clear();
            Text gameOver = new Text();
            gameOver.setText("Game over");
            gameOver.setTranslateX(250);
            gameOver.setTranslateY(50);
            getGameScene().addUINode(gameOver);
        });
        context.spawn(home, roadPointC);

        EnemyFabric fabric = new EnemyFabric(context, road, home);
        context.spawn(fabric, 100, 100);

        context.spawn(new Tower(context), 350, 150);
    }

    final private Group towerMenu = new Group();
    final private Circle option0 = new Circle();
    final private Circle option1 = new Circle();
    final private Circle option2 = new Circle();

    @Override
    protected void initUI() {
        final GameScene scene = getGameScene();
        final Text hitPoints = new Text();
        hitPoints.setTranslateX(300);
        hitPoints.setTranslateY(300);

        hitPoints.textProperty().bind(
                home.getComponent(Hittable.class)
                        .hitPoints()
                        .asString()
        );

        scene.addUINode(hitPoints);

        final Rectangle background = new Rectangle();
        background.setTranslateX(0);
        background.setTranslateY(0);
        background.setWidth(90);
        background.setHeight(50);
        final int hue = 50;
        background.setFill(Color.rgb(hue, hue, hue));
        background.setArcHeight(15);
        background.setArcWidth(15);
        towerMenu.getChildren().add(background);

        option0.setCenterX(15);
        option0.setCenterY(35);
        option0.setRadius(10);
        option0.setFill(Color.RED);
        towerMenu.getChildren().add(option0);

        option1.setCenterX(45);
        option1.setCenterY(35);
        option1.setRadius(10);
        option1.setFill(Color.GREEN);
        towerMenu.getChildren().add(option1);

        option2.setCenterX(75);
        option2.setCenterY(35);
        option2.setRadius(10);
        option2.setFill(Color.BLUE);
        towerMenu.getChildren().add(option2);

        final Text caption = new Text();
        caption.setText("Выберите тип");
        caption.setTranslateX(15);
        caption.setTranslateY(15);
        caption.setFill(Color.WHITE);
        caption.setFont(new Font(10));
        towerMenu.getChildren().add(caption);

        towerMenu.setVisible(false);

        scene.addUINode(towerMenu);
    }

    private int isOptionClicked(final Point2D mousePosition) {
        if (!towerMenu.isVisible()) return -1;
        final Point2D position = mousePosition.subtract(towerMenu.getTranslateX(), towerMenu.getTranslateY());
        Point2D t = new Point2D(option0.getCenterX(), option0.getCenterY());
        if (position.distance(t) <= option0.getRadius()) return 0;
        t = new Point2D(option1.getCenterX(), option1.getCenterY());
        if (position.distance(t) <= option1.getRadius()) return 1;
        t = new Point2D(option2.getCenterX(), option2.getCenterY());
        if (position.distance(t) <= option2.getRadius()) return 2;
        return -1;
    }

    @Override
    protected void initInput() {
        final Input input = getInput();
        final GameWorld world = getGameWorld();
        input.addAction(new UserAction("Click") {
            @Override
            protected void onActionBegin() {
                Point2D position = input.getMousePositionWorld();
                System.out.printf("Clicked at x: %f, y: %f%n", position.getX(), position.getY());
                final int option = isOptionClicked(position);
                if (option < 0) {
                    Entity nearest = null;
                    double nearest_distance = Double.POSITIVE_INFINITY;
                    for (Entity clickable : world.getEntitiesByComponent(Clickable.class)) {
                        double distance = clickable.getPosition().distance(position);
                        if (distance < nearest_distance) {
                            nearest_distance = distance;
                            nearest = clickable;
                        }
                    }
                    if (nearest == null)
                        System.out.printf("Nearest clickable is not found.%n");
                    else
                        System.out.printf("Nearest clickable at x: %f, y: %f%n", nearest.getPosition().getX(), nearest.getPosition().getY());
                } else {
                    System.out.printf("Option %d clicked!.%n", option);
                }

                towerMenu.setVisible(false);
                towerMenu.setTranslateX(position.getX());
                towerMenu.setTranslateY(position.getY());
                towerMenu.setVisible(true);
            }
        }, MouseButton.PRIMARY);
    }
}
