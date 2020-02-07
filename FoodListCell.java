package application;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

public class FoodListCell extends ListCell<FoodItem> {
	HBox foodQuantityCounter = new HBox();
	Button minusBtn = new Button();
	Button plusBtn = new Button();
	Button addBtn = new Button();
	TextField foodQuantity = new TextField("0");
	Label foodLabel = new Label();
	Pane spacingPane = new Pane();
//	ObservableList<String> meal = FXCollections.observableArrayList();
	FoodItem thisItem;
	
	public FoodListCell(Meal initialMeal, Meal runningMeal) {
		super();
		foodQuantityCounter.getChildren().addAll(foodLabel, spacingPane, minusBtn, foodQuantity, plusBtn, addBtn);
		HBox.setHgrow(spacingPane,  Priority.ALWAYS);

		foodQuantity.setPrefSize(70, 30);
		foodQuantity.setEditable(false);
		
		minusBtn.setText("-");
		minusBtn.setPrefWidth(32);
		minusBtn.setPrefHeight(30);
		minusBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int foodNum = Integer.parseInt(foodQuantity.getText());
				foodNum--;
				if (foodNum >= 0) { // Prevents foodQuantity from being negative
					foodQuantity.setText(Integer.toString(foodNum));
				}
				else foodQuantity.setText("0");
			}
		});
		
		plusBtn.setText("+");
		plusBtn.setPrefWidth(32);
		plusBtn.setPrefHeight(30);
		plusBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int foodNum = Integer.parseInt(foodQuantity.getText());
				foodNum++;
				foodQuantity.setText(Integer.toString(foodNum));
			}
		});
		
		addBtn.setText("ADD");
		addBtn.setPrefWidth(75);
		addBtn.setPrefHeight(30);
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// If the value in foodQuantity is greater than 0, briefly display a confirmation message that the food was added before returning to 0.
				if (Integer.parseInt(foodQuantity.getText()) > 0) {
					// Display confirmation message and disable buttons (to prevent a parsing error while foodQuantity is not a number)
					FoodItem food = initialMeal.getByName(foodLabel.getText());
					// If the running meal already contains the food item, simply increase the quantity of the item
					if (runningMeal.contains(food)) {
						int quant1 = runningMeal.getByName(food.getName()).getQuantity(); // Initial Quantity
						int quant2 = Integer.parseInt(foodQuantity.getText()); // New Quantity
						runningMeal.getByName(food.getName()).setQuantity(quant1 + quant2);
						addBtn.setDisable(true);
						plusBtn.setDisable(true);
						minusBtn.setDisable(true);
						foodQuantity.setText("Added!");
					// Else add the item to the running meal with the given quantity
					} else {
						runningMeal.addItem(new FoodItem(food.getID(), food.getName(), Integer.parseInt(foodQuantity.getText())));
						double calories = food.getNutrientValue("calories");
						double fat = food.getNutrientValue("fat");
						double protein = food.getNutrientValue("protein");
						double carbohydrate = food.getNutrientValue("carbohydrate");
						double fiber = food.getNutrientValue("fiber");
						// Getting a null pointer exception -- Could be from FoodItem.addNutrient being implemented incorrectly?
//						runningMeal.getByName(food.getID()).addNutrient("calories", calories);
//						runningMeal.getByName(food.getID()).addNutrient("fat", fat);
//						runningMeal.getByName(food.getID()).addNutrient("protein", protein);
//						runningMeal.getByName(food.getID()).addNutrient("carbohydrate", carbohydrate);
//						runningMeal.getByName(food.getID()).addNutrient("fiber", fiber);
						addBtn.setDisable(true);
						plusBtn.setDisable(true);
						minusBtn.setDisable(true);
						foodQuantity.setText("Added!");
					}
					// This event will occur once the confirmation message is displayed. It re-enables the buttons and sets foodQuantity back to 0.
					EventHandler<ActionEvent> resetFoodQuantity = new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							foodQuantity.setText("0");
							addBtn.setDisable(false);
							plusBtn.setDisable(false);
							minusBtn.setDisable(false);
						}
					};
					
					// After half a second, the reset event will occur.
					PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
					pause.setOnFinished(resetFoodQuantity);
					pause.play();
				}
			}
		});
	}
	
	@Override
	protected void updateItem(FoodItem item, boolean empty) {
		super.updateItem(item, empty);
		setText(null);
		if (empty) {
			setGraphic(null);
		} else {
			foodLabel.setText(item != null ? item.getName() : "<null>");
			setGraphic(foodQuantityCounter);
		}
	}

}
