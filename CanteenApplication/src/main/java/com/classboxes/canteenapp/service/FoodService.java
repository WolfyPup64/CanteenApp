package com.classboxes.canteenapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.classboxes.canteenapp.model.Food;
import com.classboxes.canteenapp.repository.FoodRepository;

@Service
@Transactional
public class FoodService {
	
    @Autowired
    private FoodRepository repo;
     
    public List<Food> listAll() {
        return repo.findAll();
    }
     
    public void save(Food food) {
        repo.save(food);
    }
     
    public Food get(long id) {
        return repo.findById(id).get();
    }
     
    public void delete(long id) {
        repo.deleteById(id);
    }
	
	
	
}
