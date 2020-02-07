package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SaveFilePage {
	
	Stage saveFileStage;
	
	public SaveFilePage(Stage saveFileStage) {
		
		this.saveFileStage = saveFileStage;
		
		BorderPane saveFileRoot = new BorderPane(); // Root Pane
		Scene saveFileScene = new Scene(saveFileRoot, 500, 200);
		saveFileStage.setTitle("Save Food Data");
		saveFileStage.setMaxHeight(200);
		saveFileStage.setMaxWidth(500);
		saveFileStage.setMinHeight(200);
		saveFileStage.setMinWidth(500);
		
		// Layouts
		HBox centerBox = new HBox();
		HBox buttonBox = new HBox();
		
		// Labels
		Label title = new Label("Save File");
		title.setFont(new Font("Arial", 20));
		Label fileName = new Label("Enter File Path");
		
		// Text Fields
		TextField fileField = new TextField();
		fileField.setMaxWidth(350);
		fileField.setMinWidth(350);
		
		//-------------------------BUTTONS----------------------------
		Button saveBtn = new Button();
		saveBtn.setText("Save");
		saveBtn.setPrefWidth(150);
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

			}
		});
		
		Button cancelBtn = new Button();
		cancelBtn.setText("Cancel");
		cancelBtn.setPrefWidth(150);
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				saveFileStage.close();
			}
		});
		
		// Format buttonBox
		buttonBox.getChildren().addAll(saveBtn, cancelBtn);
		buttonBox.setSpacing(15);
		
		// Format centerBox
		centerBox.getChildren().addAll(fileName, fileField);
		centerBox.setSpacing(15);
		
		// Add Layouts to Root
		saveFileRoot.setTop(title);
		saveFileRoot.setCenter(centerBox);
		saveFileRoot.setBottom(buttonBox);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		saveFileRoot.getBottom().setTranslateX(90);
		saveFileRoot.getCenter().setTranslateX(5);
		saveFileRoot.getCenter().setTranslateY(25);
		
		// Display
		saveFileScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		saveFileStage.setScene(saveFileScene);
		saveFileStage.show();
	}
}
