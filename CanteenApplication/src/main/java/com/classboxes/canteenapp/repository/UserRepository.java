package com.classboxes.canteenapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.classboxes.canteenapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
 
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.id = ?1")
	public User findCurrentUser(Long id);
	
	@Query("SELECT wallet FROM User u WHERE u.id = ?1")
	public float findCurrentWallet(Long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE User SET wallet = wallet + :amount WHERE id = :id")
	public int addToWallet(@Param("amount") float amount, @Param("id") long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE User SET first_name = :firstName, last_name = :lastName, address = :address, town = :town, state = :state WHERE id = :id")
	public void updateUser(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("address") String address, @Param("town") String town, @Param("state") String state, @Param("id") long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE User SET wallet = wallet - :amount WHERE id = :id")
	public int removeFromWallet(@Param("amount") float amount, @Param("id") long id);
}
