///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package fly2;
//
//import com.almasb.fxgl.core.math.FXGLMath;
//import com.almasb.fxgl.texture.AnimatedTexture;
//import com.almasb.fxgl.texture.AnimationChannel;
//import com.almasb.fxgl.entity.Entity;
//import javafx.util.Duration;
//
///**
// *
// * @author NotIntBook
// */
//class DudeControl extends Control {
//
//    private int speed = 0;
//
//    private AnimatedTexture texture;
//    private AnimationChannel animIdle, animWalk;
//
//    public DudeControl() {
//        animIdle = new AnimationChannel("newdude.png", 4, 32, 42, Duration.seconds(1), 1, 1);
//        animWalk = new AnimationChannel("newdude.png", 4, 32, 42, Duration.seconds(1), 0, 3);
//
//        texture = new AnimatedTexture(animIdle);
//    }
//
//    @Override
//    public void onAdded(Entity entity) {
//        entity.setView(texture);
//    }
//
//    @Override
//    public void onUpdate(Entity entity, double tpf) {
//        entity.translateX(speed * tpf);
//
//        if (speed == 0) {
//            texture.setAnimationChannel(animIdle);
//        } else {
//            texture.setAnimationChannel(animWalk);
//
//            speed = (int) (speed * 0.9);
//
//            if (FXGLMath.abs(speed) < 1) {
//                speed = 0;
//            }
//        }
//    }
//
//    public void moveRight() {
//        speed = 150;
//
//        getEntity().setScaleX(1);
//    }
//
//    public void moveLeft() {
//        speed = -150;
//
//        getEntity().setScaleX(-1);
//    }
//}
