package javafxml;

import animation.ParticleAnimationService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import models.IdealParticleSystem;
import models.ParticleSystem;
import models.VanderWaalsParticleSystem;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFXMLController implements Initializable {
    private final ParticleAnimationService particleAnimationService;
    private final IdealParticleSystem idealParticleSystem;
    private final VanderWaalsParticleSystem vanderWaalsParticleSystem;

    private enum PlayBackStatus { STARTED, STOPPED}

    @FXML private Button playBackBtn;
    @FXML private Pane animationPane;
    @FXML private CheckBox enableVanderWaalCheckBox;

    public DashboardFXMLController(){
        this.particleAnimationService = ParticleAnimationService.getInstance();
        this.idealParticleSystem = new IdealParticleSystem();
        this.vanderWaalsParticleSystem = new VanderWaalsParticleSystem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.playBackBtn.setOnAction(new playBackHandler());
        this.playBackBtn.setUserData(PlayBackStatus.STOPPED);
        this.enableVanderWaalCheckBox.setDisable(false);

    }

    @FXML
    public void startSimulation(){
        ParticleSystem particleSystem = (enableVanderWaalCheckBox.isSelected()) ? vanderWaalsParticleSystem : idealParticleSystem;
        particleSystem.setNumberOfParticles(150);
        particleAnimationService.animate(particleSystem, this.animationPane);
        this.playBackBtn.setUserData(PlayBackStatus.STARTED);
        this.playBackBtn.setStyle("-fx-background-color: indianred");
        this.playBackBtn.setText("Stop Simulation");
        this.enableVanderWaalCheckBox.setDisable(true);
    }

    @FXML
    public void stopSimulation(){
        particleAnimationService.stopAnimation(this.animationPane);
        this.playBackBtn.setUserData(PlayBackStatus.STOPPED);
        this.playBackBtn.setStyle("-fx-background-color: lightgreen");
        this.playBackBtn.setText("Start Simulation");
        this.enableVanderWaalCheckBox.setDisable(false);
    }
    private class playBackHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e) {
            Object userData = playBackBtn.getUserData();
            if (PlayBackStatus.STOPPED.equals(userData)) {
                startSimulation();
            } else if (PlayBackStatus.STARTED.equals(userData)) {
                stopSimulation();
            }
        }
    }
}