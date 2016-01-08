package com.jdc.fb;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlyingBird extends Application implements Initializable{
	
	private static final String FXML = "FlyingBird.fxml";

	@FXML
	private AnchorPane frame;

	private ImageView cloudView;
	private ImageView stoneView;
	private ImageView treeView;
	
	private static final int BACK_START = 840; 
	private static final int BACK_END = -250; 
	
	
	private void setBackGround() throws IOException {
		

		Image tree = new Image(Files.newInputStream(Paths.get("tree.png"), StandardOpenOption.READ));
		treeView = new ImageView(tree);
		treeView.setFitHeight(200);
		treeView.setPreserveRatio(true);
		treeView.setLayoutY(200);
		treeView.setLayoutX(BACK_START);
		
		Image stone = new Image(Files.newInputStream(Paths.get("stone.png"), StandardOpenOption.READ));
		stoneView = new ImageView(stone);
		stoneView.setPreserveRatio(true);
		stoneView.setFitWidth(220);
		stoneView.setLayoutY(300);
		stoneView.setLayoutX(BACK_START);
		
		Image cloud = new Image(Files.newInputStream(Paths.get("cloud.png"), StandardOpenOption.READ));
		cloudView = new ImageView(cloud);
		cloudView.setPreserveRatio(true);
		cloudView.setFitHeight(160);
		cloudView.setLayoutY(50);
		cloudView.setLayoutX(BACK_START);
		
		frame.getChildren().addAll(treeView, stoneView, cloudView);
		
		this.moveBackground();
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			setBackGround();
			
			moveBackground();
			
			flyBird();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private void flyBird() throws IOException {
		Image b1 = new Image(Files.newInputStream(Paths.get("bird1.png"), StandardOpenOption.READ));
		Image b2 = new Image(Files.newInputStream(Paths.get("bird2.png"), StandardOpenOption.READ));
		
		
		ImageView bird = new ImageView(b1);
		
		bird.setPreserveRatio(true);
		bird.setFitWidth(120);
		bird.setLayoutY(80);
		bird.setLayoutX(400);
		frame.getChildren().add(bird);
		
		Timeline time = new Timeline();
		KeyFrame f0 = new KeyFrame(Duration.millis(100), new KeyValue(bird.imageProperty(), b1));
		KeyFrame f1 = new KeyFrame(Duration.millis(100), new KeyValue(bird.imageProperty(), b2));
		KeyFrame f2 = new KeyFrame(Duration.millis(200), new KeyValue(bird.imageProperty(), b1));
		
		time.getKeyFrames().addAll(f0, f1, f2);
		time.setCycleCount(Timeline.INDEFINITE);


		TranslateTransition moveBird = new TranslateTransition(Duration.millis(5000), bird);
		moveBird.setFromX(BACK_START);
		moveBird.setToX(BACK_END - 400);
		
		moveBird.setCycleCount(Timeline.INDEFINITE);
		
		ParallelTransition moveBirdAni = new ParallelTransition(time, moveBird);
		bird.setOnMouseClicked(a -> {
			if(time.getStatus().equals(Status.RUNNING)) {
				moveBirdAni.stop();
				
				RotateTransition rotate = new RotateTransition(Duration.millis(100), bird);
				rotate.setToAngle(360);
				rotate.setCycleCount(10);
				
				TranslateTransition moveDown = new TranslateTransition(Duration.millis(800), bird);
				moveDown.setToY(500);
				
				ParallelTransition down = new ParallelTransition(rotate, moveDown);
				
				SequentialTransition seq = new SequentialTransition(down, new PauseTransition(Duration.millis(1000)));
				
				seq.setOnFinished(b -> {
					try {
						flyBird();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				
				seq.play();
			}
		});
		moveBirdAni.play();
		
	}



	private void moveBackground() {
		Timeline time = new Timeline();
		
		KeyFrame f1 = new KeyFrame(Duration.ZERO, new KeyValue(treeView.layoutXProperty(), BACK_START));
		KeyFrame f2 = new KeyFrame(Duration.millis(200), new KeyValue(stoneView.layoutXProperty(), BACK_START));
		KeyFrame f3 = new KeyFrame(Duration.millis(400), new KeyValue(cloudView.layoutXProperty(), BACK_START));
		KeyFrame f4 = new KeyFrame(Duration.millis(1000), new KeyValue(treeView.layoutXProperty(), BACK_END));
		KeyFrame f5 = new KeyFrame(Duration.millis(1200), new KeyValue(stoneView.layoutXProperty(), BACK_END));
		KeyFrame f6 = new KeyFrame(Duration.millis(1300), new KeyValue(cloudView.layoutXProperty(), BACK_END));
		
		time.getKeyFrames().addAll(f1, f2, f3, f4, f5, f6);
		
		time.setCycleCount(Timeline.INDEFINITE);
		time.setRate(0.1);
		time.play();
	}



	@Override
	public void start(Stage primaryStage) throws Exception {
		// load fxml
		Parent root = FXMLLoader.load(getClass().getResource(FXML));

		// create scene
		Scene scene = new Scene(root);

		// set scene to stage
		primaryStage.setScene(scene);

		// show stage
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
