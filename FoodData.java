package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, HashMap<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodData() {
        foodItemList = new ArrayList<FoodItem>();
        indexes = new HashMap<String, HashMap<Double, FoodItem>>();
        HashMap<Double, FoodItem> calories_tree = new HashMap<Double, FoodItem>(5); // choose 5
        HashMap<Double, FoodItem> fat_tree = new HashMap<Double, FoodItem>(5); // choose 5
        HashMap<Double, FoodItem> carbohydrate_tree = new HashMap<Double, FoodItem>(5); // choose 5
        HashMap<Double, FoodItem> fiber_tree = new HashMap<Double, FoodItem>(5); // choose 5
        HashMap<Double, FoodItem> protein_tree = new HashMap<Double, FoodItem>(5); // choose 5
        indexes.put("calories", calories_tree);
        indexes.put("fat", fat_tree);
        indexes.put("carbohydrate", carbohydrate_tree);
        indexes.put("fiber", fiber_tree);
        indexes.put("protein", protein_tree);
    }
    
    public ArrayList<FoodItem> getFoodItemList() {
    	return (ArrayList<FoodItem>) foodItemList;
    }
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) throws IOException {

    		FileReader fr = null; // reference to a FileReader object
		BufferedReader br = null; // reference to a BufferedReader object
    		String line = "";
    		try {
    				fr = new FileReader(filePath);
				br = new BufferedReader(fr);
				System.out.println(filePath);
				while ((line = br.readLine()) != null) {
					
	                // use comma as separator
	                String[] contents = line.split(",");
		            if(contents.length != 12)
		            		continue;
	                String id  = contents[0];
	                String name = contents[1];
	                Double calories = Double.parseDouble(contents[3]);
	                Double fat = Double.parseDouble(contents[5]);
	                Double carbohydrate = Double.parseDouble(contents[7]);
	                Double fiber = Double.parseDouble(contents[9]);
	                Double protein =  Double.parseDouble(contents[11]);
	                
	                // empty space should be skipped (haven't done yet)
	                FoodItem food = new FoodItem(id,name, 0);
	                foodItemList.add(food);
	                food.addNutrient("calories", calories);
	                food.addNutrient("fat", fat);
	                food.addNutrient("carbohydrate", carbohydrate);
	                food.addNutrient("fiber", fiber);
	                food.addNutrient("protein", protein);
	                
	                indexes.get("calories").put(calories, food);
	                indexes.get("fat").put(fat, food);
	                indexes.get("carbohydrate").put(carbohydrate, food);
	                indexes.get("fiber").put(fiber, food);
	                indexes.get("protein").put(protein, food);
		            }
	                
				br.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally { // close open resources
				if (br != null)
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
//				br.close();
			}
    		
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        // TODO : Complete
      	ArrayList<FoodItem> filterByName = new ArrayList<FoodItem>();
    		for(int i = 0; i < foodItemList.size(); i++) {  // case insensitive
    			if(foodItemList.get(i).getName().toUpperCase().contains(substring.toUpperCase())) {
    				filterByName.add(foodItemList.get(i));
    			}	
    		}
    		
        return filterByName;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
        // TODO : Complete
    		ArrayList<FoodItem> filterByNutrients = new ArrayList<FoodItem>();
    		for (int k = 0; k < foodItemList.size();k++) { //put all food items into the filterByNutrients initially
    			filterByNutrients.add(foodItemList.get(k));
    		} 
    		
    		for(int i = 0; i < rules.size(); i++) { // filter by each rule
    			
    			String[] contents = rules.get(i).split(" "); // <nutrient> <comparator> <value>
    			ArrayList<FoodItem> filter = new ArrayList<FoodItem>(intersection(filterByNutrients,rangeSearch(Double.parseDouble(contents[2]), contents[1], contents[0]))); // a list of foodItems
    			
    			// update a new filterByNutrients
    			filterByNutrients.clear();
    			for(int j = 0; j < filter.size(); j++) {
    				filterByNutrients.add(filter.get(j));
    			}
    		}
        return filterByNutrients;
    }
    
    public List<FoodItem> rangeSearch(Double key, String comparator, String nutrient) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<FoodItem>();
        
        ArrayList<FoodItem> result =  new ArrayList<FoodItem>();
        if (comparator.contentEquals(">=")) {
        		for(int i = 0; i < foodItemList.size(); i++) {
        			if(foodItemList.get(i).getNutrientValue(nutrient) >= key) {
        				result.add(foodItemList.get(i));
        			}
        		}
        }
        
        if (comparator.contentEquals("==")) {
	    		for(int i = 0; i < foodItemList.size(); i++) {
	    			if(foodItemList.get(i).getNutrientValue(nutrient) == key) {
	    				result.add(foodItemList.get(i));
	    			}
	    		}
	    }
        
        if (comparator.contentEquals("<=")) {
	    		for(int i = 0; i < foodItemList.size(); i++) {
	    			if(foodItemList.get(i).getNutrientValue(nutrient) <= key) {
	    				result.add(foodItemList.get(i));
	    			}
	    		}
	    }
        
        return result;
    }

    /**
     * It is a helper method for filter.
     * Do the intersection of two food lists.
     * @param food1
     * @param food2
     * @return
     */
    private List<FoodItem> intersection(List<FoodItem> food1, List<FoodItem> food2) {
     
        ArrayList<FoodItem> list = new ArrayList<FoodItem>();
        for(int i=0; i<food1.size(); i++){
                if(food2.contains(food1.get(i))){
                    list.add(food1.get(i));
                }
        }
        return list;
    }
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
    		foodItemList.add(foodItem);
    		indexes.get("calories").put(foodItem.getNutrientValue("calories"),foodItem);
    		indexes.get("fat").put(foodItem.getNutrientValue("fat"),foodItem);
    		indexes.get("carbohydrate").put(foodItem.getNutrientValue("carbohydrate"),foodItem);
    		indexes.get("fiber").put(foodItem.getNutrientValue("fiber"),foodItem);
    		indexes.get("protein").put(foodItem.getNutrientValue("protein"),foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }
    
    @Override
    public void saveFoodItems(String filename) {
//    		
//    	 	 FileOutputStream fileByteStream = null; // File output stream
//         PrintWriter outFS = null;               // Output stream
//
//         // Try to open file
//         try {
//			fileByteStream = new FileOutputStream(filename);
//
//			outFS = new PrintWriter(fileByteStream);
//
//	         // File is open and valid if we got this far (otherwise exception thrown)
//	         // Can now write to file
//			for(int i = 0; i < foodItemList.size(); i++) {
//				outFS.println(foodItemList.get(i).getID() + ",");
//				outFS.print(foodItemList.get(i).getName() + ",calories,");
//				outFS.print(foodItemList.get(i).getNutrientValue("calories") + ",fat,");
//				outFS.print(foodItemList.get(i).getNutrientValue("fat") + ",carbohydrate,");
//				outFS.print(foodItemList.get(i).getNutrientValue("carbohydrate") + ",fiber,");
//				outFS.print(foodItemList.get(i).getNutrientValue("fiber") + ",protein,");
//				outFS.print(foodItemList.get(i).getNutrientValue("protein"));
//			}
//         // Done with file, so try to close it
//	         try {
//				fileByteStream.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} // close() may throw IOException if fails
// 		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
// 		}
////		} finally {
////			fileByteStream.close();
////		}
    	try {
            PrintStream printStream = new PrintStream(filename); // open the file
            for (ListIterator<FoodItem> iter = foodItemList.listIterator(); iter.hasNext();) {
             FoodItem item = iter.next();
             printStream.println(item.getID() + "," + item.getName() + ",calories," + 
             item.getNutrientValue("calories") + ",fat," + item.getNutrientValue("fat") + 
             ",carbohydrate," + item.getNutrientValue("carbohydrate") + ",fiber," + 
             item.getNutrientValue("fiber") + ",protein," + item.getNutrientValue("protein"));
            }
            printStream.close();
           }
           catch (FileNotFoundException e) {
            e.printStackTrace();
           }
    }

//    public static void main(String[] args) throws IOException {
//    		FoodData data = new FoodData();
//    		Meal meal = new Meal();
//    		data.loadFoodItems("src/application/food.txt");
//    		FoodItem food = new FoodItem("Zhilin", "Wang", 0);
//    		food.addNutrient("calories", 1);
//    		data.addFoodItem(food);
//    		meal.addItem(food);
//    		System.out.println(data.getAllFoodItems().get(0).getName());
//    		System.out.println(data.getAllFoodItems().get(0).getNutrientValue("calories"));
//    		System.out.println(data.getAllFoodItems().get(0).getNutrientValue("fat"));
//    		data.saveFoodItems("src/foodItems.txt");
//    		System.out.print("end");
//    		
//
//    }
}
