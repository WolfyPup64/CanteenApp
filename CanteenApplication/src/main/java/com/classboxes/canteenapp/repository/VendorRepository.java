package com.classboxes.canteenapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.classboxes.canteenapp.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

	@Query("SELECT u FROM Vendor u WHERE customer_id = ?1")
	public Vendor areYouAVendor(Long id);
	
}
