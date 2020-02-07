package application;

import java.util.Comparator;

import javafx.collections.FXCollections;
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

public class FilterPage {
	
	Stage filterStage;

	public FilterPage(Stage filterStage) {
		
		this.filterStage  = filterStage;
			
		BorderPane filterRoot = new BorderPane();
		Scene filterScene = new Scene(filterRoot);
		filterStage.setTitle("Filter");
		filterStage.setMinHeight(450);
		filterStage.setMaxHeight(450);
		filterStage.setMinWidth(400);
		filterStage.setMaxWidth(400);
		
		//------------------LAYOUTS---------------------------
		HBox buttonBox = new HBox();
		VBox mainBox = new VBox();
		HBox caloriesBoxOut = new HBox();
		HBox caloriesBoxIn = new HBox();
		HBox fatBoxOut = new HBox();
		HBox fatBoxIn = new HBox();
		HBox proteinBoxOut = new HBox();
		HBox proteinBoxIn = new HBox();
		HBox carbsBoxOut = new HBox();
		HBox carbsBoxIn = new HBox();
		HBox fiberBoxOut = new HBox();
		HBox fiberBoxIn = new HBox();
		
		//-------------------------LABELS------------------------------
		Label title = new Label("Filter Food List");
		title.setFont(new Font("Arial", 20));
		Label calories = new Label("Calories: ");
		Label fat = new Label("Fat: ");
		Label protein = new Label("Protein: ");
		Label carbs = new Label("Carbs: ");
		Label fiber = new Label("Fiber: ");
		Label toLabel1 = new Label (" to ");
		Label toLabel2 = new Label (" to ");
		Label toLabel3 = new Label (" to ");
		Label toLabel4 = new Label (" to ");
		Label toLabel5 = new Label (" to ");
		
		//-------------------------TEXT FIELDS-----------------------------
		TextField caloriesFieldMin = new TextField();
		caloriesFieldMin.setMaxWidth(100);
		caloriesFieldMin.setPromptText("Min");
		
		TextField caloriesFieldMax = new TextField();
		caloriesFieldMax.setMaxWidth(100);
		caloriesFieldMax.setPromptText("Max");
		
		TextField fatFieldMin = new TextField();
		fatFieldMin.setMaxWidth(100);
		fatFieldMin.setPromptText("Min");
		
		TextField fatFieldMax = new TextField();
		fatFieldMax.setMaxWidth(100);
		fatFieldMax.setPromptText("Max");
		
		TextField proteinFieldMin = new TextField();
		proteinFieldMin.setMaxWidth(100);
		proteinFieldMin.setPromptText("Min");
		
		TextField proteinFieldMax = new TextField();
		proteinFieldMax.setMaxWidth(100);
		proteinFieldMax.setPromptText("Max");
		
		TextField carbsFieldMin = new TextField();
		carbsFieldMin.setMaxWidth(100);
		carbsFieldMin.setPromptText("Min");
		
		TextField carbsFieldMax = new TextField();
		carbsFieldMax.setMaxWidth(100);
		carbsFieldMax.setPromptText("Max");
		
		TextField fiberFieldMin = new TextField();
		fiberFieldMin.setMaxWidth(100);
		fiberFieldMin.setPromptText("Min");
		
		TextField fiberFieldMax = new TextField();
		fiberFieldMax.setMaxWidth(100);
		fiberFieldMax.setPromptText("Max");
		
		//------------------------FORMAT BOXES----------------------------
		caloriesBoxIn.getChildren().addAll(caloriesFieldMin, toLabel1, caloriesFieldMax);
		fatBoxIn.getChildren().addAll(fatFieldMin, toLabel2, fatFieldMax);
		proteinBoxIn.getChildren().addAll(proteinFieldMin, toLabel3, proteinFieldMax);
		carbsBoxIn.getChildren().addAll(carbsFieldMin, toLabel4, carbsFieldMax);
		fiberBoxIn.getChildren().addAll(fiberFieldMin, toLabel5, fiberFieldMax);
		
		caloriesBoxOut.getChildren().addAll(calories, caloriesBoxIn);
		caloriesBoxOut.setSpacing(2);
		fatBoxOut.getChildren().addAll(fat, fatBoxIn);
		fatBoxOut.setSpacing(34);
		proteinBoxOut.getChildren().addAll(protein, proteinBoxIn);
		proteinBoxOut.setSpacing(7);
		carbsBoxOut.getChildren().addAll(carbs, carbsBoxIn);
		carbsBoxOut.setSpacing(17);
		fiberBoxOut.getChildren().addAll(fiber, fiberBoxIn);
		fiberBoxOut.setSpacing(20);
		
		//-------------------------BUTTONS----------------------------
		Button applyBtn = new Button();
		applyBtn.setText("Apply Filter");
		applyBtn.setPrefWidth(150);
		applyBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

			}
		});
		
		Button closeBtn = new Button();
		closeBtn.setText("Close");
		closeBtn.setPrefWidth(150);
		closeBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				filterStage.close();
			}
		});
		
		// Format buttonBox
		buttonBox.getChildren().addAll(applyBtn, closeBtn);
		buttonBox.setSpacing(15);

		// Format mainBox
		mainBox.getChildren().addAll(caloriesBoxOut, fatBoxOut, proteinBoxOut, carbsBoxOut, fiberBoxOut);
		mainBox.setSpacing(25);
		
		// Add layouts to root
		filterRoot.setTop(title);
		filterRoot.setCenter(mainBox);
		filterRoot.setBottom(buttonBox);
		filterRoot.setAlignment(title, Pos.TOP_CENTER);
		filterRoot.getTop().setTranslateY(15);
		filterRoot.getCenter().setTranslateX(25);
		filterRoot.getCenter().setTranslateY(35);
		filterRoot.getBottom().setTranslateX(35);
		filterRoot.getBottom().setTranslateY(-10);
			
		// Display
		filterScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		filterStage.setScene(filterScene);
		filterStage.show();
	}	
}
