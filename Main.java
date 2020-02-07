package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;


public class Main extends Application {
	
	boolean primaryStageOpen;
	FilterPage filterPage;
	boolean isFilterPageOpen;
	LoadFilePage loadFilePage;
	boolean isLoadFilePageOpen;
	SaveFilePage saveFilePage;
	boolean isSaveFilePageOpen;
	AddFoodItemPage addFoodItemPage;
	boolean isAddFoodItemPageOpen;
	MealCartPage mealCartPage;
	boolean isMealCartPageOpen;
	
	String removedItem = ""; // Will keep track of a removed item's name to display in the foodInfo text area
	String placeHolder = String.format("Click an item to see description"); // foodInfo text area place holder
	
	ListView<FoodItem> listView;
	ObservableList<FoodItem> list = FXCollections.observableArrayList();
	Meal initialMeal = new Meal(); // This is the "meal" that gets displayed on the main page
	Meal runningMeal = new Meal(); // This is the meal that is updated for the Meal Cart page
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// If an item was removed, replace the default foodInfo placeHolder with the removed item's name
			if (!removedItem.equals("")) {
				placeHolder = String.format("Removed: \n" +removedItem);
			}

			// ----------------------------LAYOUTS-------------------------------------
			BorderPane root = new BorderPane(); // The "base" pane that everything else is added to
			
			BorderPane titlePane = new BorderPane(); // This is the top of the window. It contains the addBtn and loadBtn (within addLoad hBox), and the title
			HBox addLoad = new HBox(); // This is within the titlePane. It contains the saveBtn and loadBtn
			
			BorderPane leftPane = new BorderPane(); // This is the left side of the window. It contains the name, filterBtn, quantity (within nameFilter), and foodItems
			BorderPane nameFilter = new BorderPane(); // This is within the leftPane. It contains the name, filterBtn, and quantity
			

			list = FXCollections.observableArrayList(initialMeal.toArray()); // This is an observable list that will be turned into a listView of FoodListCells
			listView = new ListView<>(list);
			listView.setCellFactory(new Callback<ListView<FoodItem>, ListCell<FoodItem>>() {
				@Override
				public ListCell<FoodItem> call(ListView<FoodItem> param) {
					return new FoodListCell(initialMeal, runningMeal);
				}
			});


			BorderPane rightPane = new BorderPane(); // This is the right side of the window. It contains the descriptionLabel, descriptionBody, removeBtn, and saveBtn
			BorderPane descriptionBody = new BorderPane(); // This is within the rightPane. It contains the foodInfo textField
			
			BorderPane bottomPane = new BorderPane(); // This is the bottom of the window. It contains the marketCart and exitBtn
			
			//-----------------------------LABELS / TEXTFIELDS / ETC------------------------------------
			
			Scene mainScene = new Scene(root);
			
			primaryStage.setTitle("Food Query");
			primaryStage.setMaxHeight(800);
			primaryStage.setMaxWidth(1275);
			primaryStage.setMinHeight(800);
			primaryStage.setMinWidth(1275);
			
			Label title = new Label("Food Query");
			title.setFont(new Font("Arial", 30));
			
			Label name = new Label("Name");
			name.setFont(new Font("Arial", 20));
			
			Label quantity = new Label("Quantity");
			quantity.setFont(new Font("Arial", 20));
			
			Label descriptionLabel = new Label("Description");
			descriptionLabel.setPrefHeight(30);
			descriptionLabel.setFont(new Font("Arial", 20));
			

			TextArea foodInfo = new TextArea();
			foodInfo.setEditable(false);
			foodInfo.setPrefSize(300, 400);
			foodInfo.setText(placeHolder);
			// Will set the text in the foodInfo text area to display the information regarding an item in the list when it is clicked on.
			listView.setOnMousePressed(new EventHandler<MouseEvent>() { // This will need to change once FoodData and FoodItem are closer to being finished.
				@Override 
				public void handle(MouseEvent event) {
					if (!list.isEmpty()) {
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

			
			//-----------------------------BUTTONS-----------------------------------
			Button filterBtn = new Button();
			filterBtn.setText("Filter");
			filterBtn.setPrefWidth(60);
			filterBtn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Stage filterStage = new Stage();
					filterPage = new FilterPage(filterStage);
					isFilterPageOpen = true;
				}
			});
			
			Button addFoodItemBtn = new Button(); // OPENS NEW WINDOW
			addFoodItemBtn.setText("Add Food Item");
			addFoodItemBtn.setPrefWidth(150);
			addFoodItemBtn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Stage addFoodItemStage = new Stage();
					addFoodItemPage = new AddFoodItemPage(list, addFoodItemStage);
					isAddFoodItemPageOpen = true;
				}
			});
			
			Button loadBtn = new Button(); // OPENS NEW WINDOW
			loadBtn.setText("Load");
			loadBtn.setPrefWidth(150);
			loadBtn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Stage loadFileStage = new Stage();
					loadFilePage = new LoadFilePage(loadFileStage, initialMeal, primaryStage);
					isLoadFilePageOpen = true;
					loadFilePage.loadFileStage.setOnHidden(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) {
							primaryStage.close();
							start(primaryStage);
						}
					});
				}
			});

			Button mealCartBtn = new Button();
			mealCartBtn.setText("Meal Cart");
			mealCartBtn.setPrefWidth(150);
			mealCartBtn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Stage mealCartStage = new Stage();
					mealCartPage = new MealCartPage(mealCartStage, initialMeal, runningMeal);
					
					isMealCartPageOpen = true;
				}
			});
			
			Button exitBtn = new Button(); // CLOSES ALL WINDOWS
			exitBtn.setText("Exit");
			exitBtn.setPrefWidth(150);
			exitBtn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) { // If statements check to see if a page has been opened (a null pointer exception will be thrown if trying to close an unopened stage)
					if (isFilterPageOpen == true) {
						filterPage.filterStage.close();
					}
					if (isLoadFilePageOpen == true) {
						loadFilePage.loadFileStage.close();
					}
					if (isSaveFilePageOpen == true) {
						saveFilePage.saveFileStage.close();
					}
					if (isAddFoodItemPageOpen == true) {
						addFoodItemPage.foodItemStage.close();
					}
					if (isMealCartPageOpen == true) {
						mealCartPage.mealCartStage.close();
					}
					Platform.exit();
				}
			});
			
			// Remove the item displayed in the foodInfo text area from the main page listView
			Button removeBtn = new Button();
			removeBtn.setText("Remove");
			removeBtn.setPrefWidth(150);
			removeBtn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (!list.isEmpty()) {
						removedItem = listView.getSelectionModel().getSelectedItem().getName();
						initialMeal.deleteItemByName(removedItem, 0);
						primaryStage.close(); 
						start(primaryStage);
					}
				}
			});
			
			Button saveBtn = new Button();
			saveBtn.setText("Save");
			saveBtn.setPrefWidth(150);
			saveBtn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Stage saveFileStage = new Stage();
					saveFilePage = new SaveFilePage(saveFileStage);
					isSaveFilePageOpen = true;
				}
			});
			
			// Format addLoad
			addLoad.getChildren().add(addFoodItemBtn);
			addLoad.getChildren().add(loadBtn);
			addLoad.setSpacing(15);
			
			// Format titlePane
			titlePane.setLeft(title);
			titlePane.setRight(addLoad);
			titlePane.getRight().setTranslateX(-55); // Use this to move addLoad (addFoodItemBtn & loadBtn)
			
			// Format nameFilter
			nameFilter.setLeft(name);
			nameFilter.setCenter(filterBtn);
			nameFilter.setRight(quantity);
			nameFilter.getLeft().setTranslateX(5); // Use this to move name
			nameFilter.getCenter().setTranslateX(-95); // Use this to move filterBtn
			nameFilter.getRight().setTranslateX(-110); // Use this to move quantity
			
			// Format leftPane
			leftPane.setTop(nameFilter);
			leftPane.setPrefWidth(850);
			leftPane.setCenter(listView);
			
			// Format descriptionBody
			descriptionBody.setTop(foodInfo);
			descriptionBody.setCenter(removeBtn);
			descriptionBody.setBottom(mealCartBtn);
			descriptionBody.setPrefWidth(400);
			descriptionBody.getCenter().setTranslateY(-80); // Use this to move removeBtn
			descriptionBody.getBottom().setTranslateX(125);
			descriptionBody.getBottom().setTranslateY(-150);

			// Format rightPane
			rightPane.setTop(descriptionLabel);
			rightPane.setCenter(descriptionBody);
			rightPane.setPrefWidth(400);
			rightPane.getTop().setTranslateX(130); // Use this to move descriptionLabel
			
			// Format bottomPane
			bottomPane.setRight(exitBtn);
			bottomPane.setCenter(saveBtn);
			bottomPane.getCenter().setTranslateX(415); // Use this to move saveBtn
			bottomPane.getRight().setTranslateX(-40); // Use this to move exitBtn
			
			// Add to root Pane
			root.setTop(titlePane);
			root.setRight(rightPane);
			root.setLeft(leftPane);
			root.setBottom(bottomPane);
			
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainScene);
			primaryStage.show();
			primaryStageOpen = true;
			
					
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
