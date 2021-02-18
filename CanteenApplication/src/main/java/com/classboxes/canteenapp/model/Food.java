package com.classboxes.canteenapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "foods")
@DynamicInsert
public class Food {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;
     
    @Column()
    private String foodName;
     
    @Column()
    private float price;
     
    @Column()
    private int calories;
     
    @Column()
    private String description;

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Food [foodId=" + foodId + ", foodName=" + foodName + ", price=" + price + ", calories=" + calories
				+ ", description=" + description + "]";
	}

	public Food() {
		super();
	}

	public Food(String foodName, float price, int calories, String description) {
		super();
		this.foodName = foodName;
		this.price = price;
		this.calories = calories;
		this.description = description;
	}
    
    

}
