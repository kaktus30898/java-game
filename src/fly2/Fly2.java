//package fly2;
//
//import com.almasb.fxgl.app.GameApplication;
//import com.almasb.fxgl.entity.Entities;
//import com.almasb.fxgl.entity.Entity;
//import com.almasb.fxgl.entity.component.CollidableComponent;
//import com.almasb.fxgl.input.Input;
//import com.almasb.fxgl.input.UserAction;
//import com.almasb.fxgl.physics.CollisionHandler;
//import com.almasb.fxgl.settings.GameSettings;
//import com.almasb.fxgl.texture.Texture;
//import javafx.scene.input.KeyCode;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.text.Text;
//import java.util.Map;
////import com.almasb.fxgl.entity.component.CollidableComponent;
////import com.almasb.fxgl.physics.CollisionHandler;
//
//public class Fly2 extends GameApplication {
//
//    public enum EntityType {
//        PLAYER, COIN
//    }
//
//    @Override
//    protected void initSettings(GameSettings settings) {
//        settings.setWidth(600);
//        settings.setHeight(600);
//        settings.setTitle("Basic Game App3");
//        settings.setVersion("0.1");
//    }
//
//    @Override
//    protected void initInput() {
//        Input input = getInput(); // get input service
//
//        input.addAction(new UserAction("Move Right") {
//            @Override
//            protected void onAction() {
////                player.translateX(5); // move right 5 pixels
//                getGameState().increment("pixelsMoved", +5);
//                player.getControl(DudeControl.class).moveRight();
//            }
//        }, KeyCode.D);
//
//        input.addAction(new UserAction("Move Left") {
//            @Override
//            protected void onAction() {
////                player.translateX(-5); // move left 5 pixels
//                getGameState().increment("pixelsMoved", +5);
//                player.getControl(DudeControl.class).moveLeft();
//            }
//        }, KeyCode.A);
//
//        input.addAction(new UserAction("Move Up") {
//            @Override
//            protected void onAction() {
//                player.translateY(-5); // move up 5 pixels
//                getGameState().increment("pixelsMoved", +5);
//            }
//        }, KeyCode.W);
//
//        input.addAction(new UserAction("Move Down") {
//            @Override
//            protected void onAction() {
//                player.translateY(5); // move down 5 pixels
//                getGameState().increment("pixelsMoved", +5);
//            }
//        }, KeyCode.S);
//    }
//
//    @Override
//    protected void initGameVars(Map<String, Object> vars) {
//        vars.put("pixelsMoved", 0);
//    }
//
//    private Entity player;
//
//    @Override
//    protected void initGame() {
//        player = Entities.builder()
//                .type(EntityType.PLAYER)
//                .at(300, 300)
//                .viewFromTextureWithBBox("brick.png")
//                .with(new DudeControl())
//                .with(new CollidableComponent(true))
//                .buildAndAttach(getGameWorld());
//
//        Entities.builder()
//                .type(EntityType.COIN)
//                .at(500, 200)
//                .viewFromNodeWithBBox(new Circle(15, Color.YELLOW))
//                .with(new CollidableComponent(true))
//                .buildAndAttach(getGameWorld());
//
//        getGameState().<Integer>addListener("pixelsMoved", (prev, now) -> {
//            if (now % 100 == 0) {
//                getAudioPlayer().playSound("drop.wav");
//            }
//        });
//    }
//
//    @Override
//    protected void initPhysics() {
//        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
//            @Override
//            protected void onCollisionBegin(Entity player, Entity coin) {
//                coin.removeFromWorld();
//            }
//        });
//    }
//
//    @Override
//    protected void initUI() {
//        Text textPixels = new Text();
//        textPixels.setTranslateX(50); // x = 50
//        textPixels.setTranslateY(100); // y = 100
//
//        textPixels.textProperty().bind(getGameState().intProperty("pixelsMoved").asString());
//
//        getGameScene().addUINode(textPixels); // add to the scene graph
//
//        Texture brickTexture = getAssetLoader().loadTexture("brick.png");
//        brickTexture.setTranslateX(50);
//        brickTexture.setTranslateY(450);
//
//        getGameScene().addUINode(brickTexture);
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
