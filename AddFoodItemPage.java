package application;

import java.util.Comparator;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class AddFoodItemPage {
	
	Stage foodItemStage;
	
	public AddFoodItemPage(ObservableList<FoodItem> list, Stage foodItemStage) {
		
		this.foodItemStage = foodItemStage;
		
		BorderPane addFoodItemRoot = new BorderPane(); // Root Pane
		Scene foodItemScene = new Scene(addFoodItemRoot, 500, 500);
		foodItemStage.setTitle("Add New Food Item");
		foodItemStage.setMaxHeight(500);
		foodItemStage.setMaxWidth(500);
		foodItemStage.setMinHeight(500);
		foodItemStage.setMinWidth(500);
		
		//-------------------------LAYOUTS----------------------------
		
		HBox buttonBox = new HBox();
		VBox mainBox = new VBox();
		HBox nameBox = new HBox();
		HBox caloriesBox = new HBox();
		HBox fatBox = new HBox();
		HBox proteinBox = new HBox();
		HBox carbsBox = new HBox();
		HBox fiberBox = new HBox();
		
		//-------------------------LABELS------------------------------
		Label title = new Label("Add New Food Item");
		title.setFont(new Font("Arial", 20));
		Label name = new Label("Name: ");
		Label calories = new Label("Calories: ");
		Label fat = new Label("Fat: ");
		Label protein = new Label("Protein: ");
		Label carbs = new Label("Carbs: ");
		Label fiber = new Label("Fiber: ");
		
		//-------------------------TEXT FIELDS-----------------------------
		TextField nameField = new TextField();
		nameField.setMinWidth(350);
		TextField caloriesField = new TextField();
		caloriesField.setMinWidth(350);
		TextField fatField = new TextField();
		fatField.setMinWidth(350);
		TextField proteinField = new TextField();
		proteinField.setMinWidth(350);
		TextField carbsField = new TextField();
		carbsField.setMinWidth(350);
		TextField fiberField = new TextField();
		fiberField.setMinWidth(350);
		
		//------------------------FORMAT BOXES----------------------------
		nameBox.getChildren().addAll(name, nameField);
		nameBox.setSpacing(15);
		caloriesBox.getChildren().addAll(calories, caloriesField);
		caloriesBox.setSpacing(2);
		fatBox.getChildren().addAll(fat, fatField);
		fatBox.setSpacing(34);
		proteinBox.getChildren().addAll(protein, proteinField);
		proteinBox.setSpacing(7);
		carbsBox.getChildren().addAll(carbs, carbsField);
		carbsBox.setSpacing(17);
		fiberBox.getChildren().addAll(fiber, fiberField);
		fiberBox.setSpacing(20);
		
		//-------------------------BUTTONS----------------------------
		Button addBtn = new Button();
		addBtn.setText("Add Item");
		addBtn.setPrefWidth(150);
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Comparator<String> comparator = Comparator.naturalOrder();
				String name = nameField.getText();
				if (!name.isEmpty()) {
				list.add(new FoodItem("blah", name, 0));
//				FXCollections.sort(list, {list.get));
				addBtn.setText("Added!");
				addBtn.setDisable(true);
				nameField.clear();
				caloriesField.clear();
				fatField.clear();
				proteinField.clear();
				carbsField.clear();
				fiberField.clear();
				EventHandler<ActionEvent> resetFields = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						addBtn.setText("Add Item");
						addBtn.setDisable(false);
					}	
				};
				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
				pause.setOnFinished(resetFields);
				pause.play();
				}
			}
		});
		
		Button cancelBtn = new Button();
		cancelBtn.setText("Cancel");
		cancelBtn.setPrefWidth(150);
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				foodItemStage.close();
			}
		});
		
		// Format buttonBox
		buttonBox.getChildren().addAll(addBtn, cancelBtn);
		buttonBox.setSpacing(15);
		
		// Format mainBox
		mainBox.getChildren().addAll(nameBox, caloriesBox, fatBox, proteinBox, carbsBox, fiberBox);
		mainBox.setSpacing(25);
		
		// Add layouts to root
		addFoodItemRoot.setTop(title);
		addFoodItemRoot.setCenter(mainBox);
		addFoodItemRoot.setAlignment(title, Pos.TOP_CENTER);
		addFoodItemRoot.getTop().setTranslateY(15);
		addFoodItemRoot.getCenter().setTranslateX(25);
		addFoodItemRoot.getCenter().setTranslateY(35);
		addFoodItemRoot.setBottom(buttonBox);
		addFoodItemRoot.getBottom().setTranslateX(85);
		addFoodItemRoot.getBottom().setTranslateY(-10);
		
		// Display
		foodItemScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		foodItemStage.setScene(foodItemScene);
		foodItemStage.show();
	}
}
