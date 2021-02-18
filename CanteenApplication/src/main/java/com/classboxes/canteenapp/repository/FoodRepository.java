package com.classboxes.canteenapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classboxes.canteenapp.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

}
