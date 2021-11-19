package animation;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import models.ParticleSystem;

public class ParticleAnimationService{
    private static ParticleAnimationService particleAnimationService;
    private final Timeline timeline = new Timeline();
    private ParticleAnimationService(){
        System.out.println("ParticleAnimationService Instance Created");
    }

    public static ParticleAnimationService getInstance(){
        if(particleAnimationService == null) {
            particleAnimationService = new ParticleAnimationService();
        }
        return particleAnimationService;
    }

    public void animate(ParticleSystem particleSystem, Pane animationPane){
        timeline.getKeyFrames().clear();
        particleSystem.init(animationPane.getWidth(), animationPane.getHeight());
        animationPane.getChildren().addAll(particleSystem.getParticles());

        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.seconds(0.017), event -> particleSystem.updateParticlePositions(animationPane.getWidth(), animationPane.getHeight())
        );
        timeline.getKeyFrames().add(kf);
        timeline.playFromStart();

    }

    public void stopAnimation(Pane animationPane){
        timeline.stop();
        animationPane.getChildren().clear();
    }
}
