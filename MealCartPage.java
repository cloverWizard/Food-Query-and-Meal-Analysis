package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MealCartPage {
	
	Stage mealCartStage;
	ObservableList<FoodItem> mealCartList = FXCollections.observableArrayList();
	int testInt;
	

	public MealCartPage(Stage mealCartStage, Meal initialMeal, Meal runningMeal) {
		
		this.mealCartStage  = mealCartStage;
		
		BorderPane mealCartRoot = new BorderPane();
		Scene mealCartScene = new Scene(mealCartRoot);
		mealCartStage.setTitle("Meal Cart");
		mealCartStage.setMinHeight(600);
		mealCartStage.setMaxHeight(600);
		mealCartStage.setMinWidth(1400);
		mealCartStage.setMaxWidth(1400);
		
		//--------------------LAYOUTS--------------------------
		BorderPane leftPane = new BorderPane();
		BorderPane listLabelPane = new BorderPane();
		BorderPane rightPane = new BorderPane();
		
		//--------------------LABELS--------------------------
		Label title = new Label("Meal Cart");
		title.setFont(new Font("Arial", 30));
		Label name = new Label("Name");
		Label quantity = new Label("In Cart");
		Label quantityRem = new Label("Remove from Cart");
		Label descriptionLabel = new Label("Description");
		
		//-------------------TEXT AREAS-----------------------
		TextArea foodInfo = new TextArea();
		foodInfo.setText("Click an item to see description");
		foodInfo.setPrefSize(300, 400);
		foodInfo.setEditable(false);
		
		//--------------------BUTTONS------------------------
		Button cancelBtn = new Button();
		cancelBtn.setText("Cancel");
		cancelBtn.setPrefWidth(150);
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				mealCartStage.close();
			}
		});
		
		mealCartList = FXCollections.observableArrayList(runningMeal.toArray());
		ListView<FoodItem> listView = new ListView<>(mealCartList);
		listView.setCellFactory(new Callback<ListView<FoodItem>, ListCell<FoodItem>>() {
			@Override
			public ListCell<FoodItem> call(ListView<FoodItem> param) {
				return new MealCartCell(initialMeal, runningMeal, mealCartStage);
			}
		});
		
		listView.setOnMousePressed(new EventHandler<MouseEvent>() { // This will need to change once FoodData and FoodItem are closer to being finished.
			@Override 
			public void handle(MouseEvent event) {
				if (!mealCartList.isEmpty()) {
				foodInfo.clear();
				foodInfo.appendText(listView.getSelectionModel().getSelectedItem().getName()+ "\n");
				foodInfo.appendText("Calories: "+ listView.getSelectionModel().getSelectedItem().getNutrientValue("calories")+ " grams\n");
				foodInfo.appendText("Fat: "+ listView.getSelectionModel().getSelectedItem().getNutrientValue("fat")+ " grams\n");
				foodInfo.appendText("Protein: "+ listView.getSelectionModel().getSelectedItem().getNutrientValue("protein")+ " grams\n");
				foodInfo.appendText("Carbs: "+ listView.getSelectionModel().getSelectedItem().getNutrientValue("carbohydrate")+ " grams\n");
				foodInfo.appendText("Fiber: "+ listView.getSelectionModel().getSelectedItem().getNutrientValue("fiber")+ " grams\n");
			}
			}
		});
		
		// Format listTopBox
		listLabelPane.setLeft(quantity);
		listLabelPane.getLeft().setTranslateX(20);
		listLabelPane.setCenter(name);
		listLabelPane.getCenter().setTranslateX(-200);
		listLabelPane.setRight(quantityRem);
		listLabelPane.getRight().setTranslateX(-75);
		
		// Format rightPane
		rightPane.setPrefWidth(400);
		rightPane.setTop(descriptionLabel);
		rightPane.getTop().setTranslateX(160);
		rightPane.setCenter(foodInfo);
		
		// Format leftPane
		leftPane.setTop(listLabelPane);
		leftPane.setCenter(listView);
		leftPane.setPrefWidth(965);
		
		// Format root
		mealCartRoot.setTop(title);
		mealCartRoot.setLeft(leftPane);
		mealCartRoot.setRight(rightPane);
		mealCartRoot.setBottom(cancelBtn);
		mealCartRoot.getBottom().setTranslateX(1200);
		
		// Display
		mealCartScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		mealCartStage.setScene(mealCartScene);
		mealCartStage.show();
	}
}
