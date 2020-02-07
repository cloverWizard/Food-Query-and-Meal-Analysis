package application;

import java.util.ArrayList;
import java.util.List;

public class Meal {
	private List<FoodItem> mealList;
	private double mealCalories;
	private double mealProtein;
	private double mealFiber;
	private double mealCarbohydrates;
	private double mealFat;
	
	public Meal() {
		mealList = new ArrayList<FoodItem>();
		mealProtein = 0;
		mealProtein = 0;
		mealFiber = 0;
		mealCarbohydrates = 0;
		mealFat = 0;
	}
	
	
	public void  addItem (FoodItem food) {
		mealList.add(food);
	}
	
	public boolean contains(FoodItem food) {
		for (int i = 0; i < mealList.size(); i++) {
			if (food.getName().equals(mealList.get(i).getName())) {
				if (food.getID().equals(mealList.get(i).getID())) {
					return true;
				}
			}
			if (food.getID().equals(mealList.get(i).getID())) {
				if (food.getName().equals(mealList.get(i).getID()))
					return true;
			}
		}
		return false;
//		return mealList.contains(food);
	}
	
	public FoodItem getByName (String name) {
		for (int i = 0; i < mealList.size(); i++) {
			FoodItem food = mealList.get(i);
			if (food.getName().equals(name)) {
				return food;
			}
		}
		return null;
	}
	
	public void deleteItem(FoodItem food) {
		if(mealList.contains(food)) {
			mealList.remove(food);
		}
	}
	
	public void deleteItemByName(String foodName, int deleteNumber) {
		for (int i = 0; i < mealList.size(); i++) {
			FoodItem food = mealList.get(i);
			if (food.getName().equals(foodName)) {
				if (food.getQuantity() <= deleteNumber) {
					food.setQuantity(0);
					mealList.remove(food);
				}
				else {
					food.setQuantity(food.getQuantity() - deleteNumber);
				}
			}
		}
	}
	
	public void deleteItemById(String id, int quantity) {
		for (int i = 0; i < mealList.size(); i++) {
			FoodItem food = mealList.get(i);
			if (food.getID().equals(id)) {
				mealList.remove(food);
			}
		}
	}
	
	public boolean isEmpty() {
		return mealList.isEmpty();
	}


	public double calcMealCalories(List<FoodItem> mealList) {
		if (!mealList.isEmpty()) {
			for (int i = 0; i < mealList.size(); i++) {
				mealCalories = mealCalories + mealList.get(i).getNutrientValue("calories");
			}
		}
		return mealCalories;
	}
	
	public double calcMealProtein(List<FoodItem> mealList) {
		if (!mealList.isEmpty()) {
			for (int i = 0; i < mealList.size(); i++) {
				mealProtein = mealProtein + mealList.get(i).getNutrientValue("protein");
			}
		}
		return mealProtein;
	}
	
	public double calcMealFat(List<FoodItem> mealList) {
		if (!mealList.isEmpty()) {
			for (int i = 0; i < mealList.size(); i++) {
				mealFat = mealFat + mealList.get(i).getNutrientValue("fat");
			}
		}
		return mealFat;
	}
	
	public double calcMealCarboHydrates(List<FoodItem> mealList) {
		if (!mealList.isEmpty()) {
			for (int i = 0; i < mealList.size(); i++) {
				mealCarbohydrates = mealCarbohydrates + mealList.get(i).getNutrientValue("carbohydrate");
			}
		}
		return mealCarbohydrates;
	}
	
	public double calcMealFiber(List<FoodItem> mealList) {
		if (!mealList.isEmpty()) {
			for (int i = 0; i < mealList.size(); i++) {
				mealFiber = mealFiber + mealList.get(i).getNutrientValue("fiber");
			}
		}
		return mealFiber;
	}
	
	public FoodItem[] toArray() {
		FoodItem[] arr = new FoodItem[mealList.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = mealList.get(i);
		}
		return arr;
	}
}
