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
import javafx.stage.Stage;
import javafx.util.Duration;

public class MealCartCell extends ListCell<FoodItem> {
	HBox foodQuantityCounter = new HBox();
	Button minusBtn = new Button();
	Button plusBtn = new Button();
	Button removeBtn = new Button();
	TextField foodQuantityChange = new TextField("0");
	Label foodLabel = new Label();
	Pane spacingPane = new Pane();
	TextField foodQuantityCurrent = new TextField("");
	FoodItem currentFood;
	
	public MealCartCell(Meal initialMeal, Meal runningMeal, Stage mealCartStage) {
		super();
		foodQuantityCounter.getChildren().addAll(foodQuantityCurrent, foodLabel, spacingPane, minusBtn, foodQuantityChange, plusBtn, removeBtn);
		HBox.setHgrow(spacingPane,  Priority.ALWAYS);

		
		foodQuantityChange.setPrefSize(70, 30);
		foodQuantityChange.setEditable(false);
		
		// This seems to need to be here. A bunch of null pointer exceptions are thrown, but without the pause transition, the current food quantity is not shown.
		EventHandler<ActionEvent> resetFoodQuantityCurrent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentFood = runningMeal.getByName(foodLabel.getText());
				foodQuantityCurrent.setText(Integer.toString(runningMeal.getByName(foodLabel.getText()).getQuantity()));
			}
		};
		PauseTransition pause = new PauseTransition(Duration.seconds(.5));
		pause.setOnFinished(resetFoodQuantityCurrent);
		pause.play();

		foodQuantityCurrent.setPrefSize(70, 30);
		foodQuantityCurrent.setEditable(false);
//		}
		
		minusBtn.setText("-");
		minusBtn.setPrefWidth(32);
		minusBtn.setPrefHeight(30);
		minusBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int foodNum = Integer.parseInt(foodQuantityChange.getText());
				foodNum--;
				if (foodNum >= 0) { // Prevents foodQuantity from being negative
					foodQuantityChange.setText(Integer.toString(foodNum));
				}
				else foodQuantityChange.setText("0");
			}
		});
		
		plusBtn.setText("+");
		plusBtn.setPrefWidth(32);
		plusBtn.setPrefHeight(30);
		plusBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int foodNum = Integer.parseInt(foodQuantityChange.getText());
				foodNum++;
				foodQuantityChange.setText(Integer.toString(foodNum));

			}
		});
		
		removeBtn.setText("REMOVE");
		removeBtn.setPrefWidth(100);
		removeBtn.setPrefHeight(30);
		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// If the value in foodQuantity is greater than 0, briefly display a confirmation message that the food was added before returning to 0.
				if (Integer.parseInt(foodQuantityCurrent.getText()) > 0) {
					// Display confirmation message and disable buttons (to prevent a parsing error while foodQuantity is not a number)
					currentFood = runningMeal.getByName(foodLabel.getText());
					runningMeal.deleteItemByName(foodLabel.getText(), Integer.parseInt(foodQuantityChange.getText()));
					if (Integer.parseInt(foodQuantityCurrent.getText()) - Integer.parseInt(foodQuantityChange.getText()) < 0) {
						foodQuantityCurrent.setText("0");
					}
					foodQuantityCurrent.setText((Integer.toString(currentFood.getQuantity())));
					removeBtn.setDisable(true);
					plusBtn.setDisable(true);
					minusBtn.setDisable(true);
					foodQuantityChange.setText("Removed!");
					
					// This event will occur once the confirmation message is displayed. It re-enables the buttons and sets foodQuantity back to 0.
					EventHandler<ActionEvent> resetFoodQuantity = new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							foodQuantityChange.setText("0");
							removeBtn.setDisable(false);
							plusBtn.setDisable(false);
							minusBtn.setDisable(false);
						}
					};
					
					// After half a second, the reset event will occur.
					PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
					pause.setOnFinished(resetFoodQuantity);
					pause.play();
					
					mealCartStage.close();
					new MealCartPage(mealCartStage, initialMeal, runningMeal);
				} else {
					foodQuantityChange.setText("0");
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
