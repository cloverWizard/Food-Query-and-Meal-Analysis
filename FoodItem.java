package application;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a food item with all its properties.
 * 
 * @author aka
 */
public class FoodItem {
    // The name of the food item.
    private String name;
    
    // The quantity of this food item.
    private int quantity;

    // The id of the food item.
    private String id;

    // Map of nutrients and value.
    private HashMap<String, Double> nutrients;
    
    /**
     * Constructor
     * @param name name of the food item
     * @param id unique id of the food item 
     */
    public FoodItem(String id, String name, int quantity) {
        // TODO : Complete
    		this.name = name;
    		this.id = id;
    		this.quantity = quantity;
    		nutrients = new HashMap<String, Double>();
    		nutrients.put("calories", (double) 0);
    		nutrients.put("fat", (double) 0);
    		nutrients.put("carbohydrate", (double) 0);
    		nutrients.put("fiber", (double) 0);
    		nutrients.put("protein", (double) 0);
    		
    }
    
    /**
     * Gets the name of the food item
     * 
     * @return name of the food item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unique id of the food item
     * 
     * @return id of the food item
     */
    public String getID() {
        // TODO : Complete
        return id;
    }
    
    /**
     * Gets the nutrients of the food item
     * 
     * @return nutrients of the food item
     */
    public HashMap<String, Double> getNutrients() {
        // TODO : Complete
        return nutrients;
    }

    /**
     * Adds a nutrient and its value to this food. 
     * If nutrient already exists, updates its value.
     */
    public void addNutrient(String name, double value) {
        // TODO : Complete
    		nutrients.replace(name, value);
    }

    /**
     * Returns the value of the given nutrient for this food item. 
     * If not present, then returns 0.
     */
    public double getNutrientValue(String name) {
        // TODO : Complete
    		if (nutrients == null)
    			System.out.print("null");
        return nutrients.get(name);
    }
    
    public int getQuantity() {
    	return this.quantity;
    }
    
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
    
}
