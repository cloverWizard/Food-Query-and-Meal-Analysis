package application;

import java.io.IOException;
import java.util.ArrayList;
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

public class LoadFilePage {
	
	boolean fileNotFound;
	Stage loadFileStage;
	FoodData data = new FoodData();
	public LoadFilePage(Stage loadFileStage, Meal initialMeal, Stage primaryStage) {
		
		this.loadFileStage = loadFileStage;
		
		BorderPane loadFileRoot = new BorderPane(); // Root Pane
		Scene loadFileScene = new Scene(loadFileRoot, 500, 200);
		loadFileStage.setTitle("Load Food Data");
		loadFileStage.setMaxHeight(200);
		loadFileStage.setMaxWidth(500);
		loadFileStage.setMinHeight(200);
		loadFileStage.setMinWidth(500);
		
		// Layouts
		HBox centerBox = new HBox();
		HBox buttonBox = new HBox();
		
		// Labels
		Label title = new Label("Load File");
		title.setFont(new Font("Arial", 20));
		Label fileName = new Label("Enter File Path:");
		
		// Text Fields
		TextField fileField = new TextField();
		fileField.setMaxWidth(350);
		fileField.setMinWidth(350);
		
		//-------------------------BUTTONS----------------------------
		Button loadBtn = new Button();
		loadBtn.setText("Load");
		loadBtn.setPrefWidth(150);
		loadBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					data.loadFoodItems(fileField.getText());
					ArrayList<FoodItem> arrList = data.getFoodItemList();
					for (int i = 0; i < arrList.size(); i++) {
						FoodItem food = arrList.get(i);
						initialMeal.addItem(food);
					}
					loadFileStage.hide();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					fileField.setText("File Not Found"); // Doesn't work
					e.printStackTrace();
				}
			}
		});
		
		Button cancelBtn = new Button();
		cancelBtn.setText("Cancel");
		cancelBtn.setPrefWidth(150);
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				loadFileStage.close();
			}
		});
		
		// Format buttonBox
		buttonBox.getChildren().addAll(loadBtn, cancelBtn);
		buttonBox.setSpacing(15);
		
		// Format centerBox
		centerBox.getChildren().addAll(fileName, fileField);
		centerBox.setSpacing(15);
		
		// Add Layouts to Root
		loadFileRoot.setTop(title);
		loadFileRoot.setCenter(centerBox);
		loadFileRoot.setBottom(buttonBox);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		loadFileRoot.getBottom().setTranslateX(90);
		loadFileRoot.getCenter().setTranslateX(5);
		loadFileRoot.getCenter().setTranslateY(25);
		
		// Display
		loadFileScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		loadFileStage.setScene(loadFileScene);
		loadFileStage.show();
		
		
	}
	
	public FoodItem[] toArray(ArrayList<FoodItem> foodArray) {
		FoodItem[] arr = new FoodItem[foodArray.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = foodArray.get(i);
		}
		return arr;
	}
}
