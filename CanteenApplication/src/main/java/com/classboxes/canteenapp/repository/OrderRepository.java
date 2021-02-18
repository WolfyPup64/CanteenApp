package com.classboxes.canteenapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.classboxes.canteenapp.model.Order;
import com.classboxes.canteenapp.model.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{
	
	@Modifying
	@Transactional
	@Query(value = "insert into orders (cost, had_been_accepted, had_been_canceled, had_been_completed, order_date, order_number, user_id) VALUES(:amount, false, false, false, :orderDate, :orderNumber, :uId)" , nativeQuery = true)
	public void createOrder(float amount, Date orderDate, long orderNumber, long uId);
	
	@Query("SELECT o FROM Order o WHERE had_been_accepted = false AND had_been_canceled = false AND had_been_completed = false")
	public List<Order> orders();
	
	@Query("SELECT o FROM Order o WHERE had_been_accepted = true AND had_been_canceled = false AND had_been_completed = false")
	public List<Order> ordersAccepted();
	
	@Query("SELECT o FROM Order o WHERE o.orderNumber = ?1")
	public Order findOrder(long on);
	
	@Transactional
	@Modifying
	@Query("update Order set had_been_canceled = true WHERE order_id = :id")
	public void cancelOrder(@Param("id") long id);
	
	@Transactional
	@Modifying
	@Query("update Order set had_been_accepted = true WHERE order_id = :id")
	public void acceptedOrder(@Param("id") long id);
	
	@Transactional
	@Modifying
	@Query("update Order set had_been_completed = true WHERE order_id = :id")
	public void completedOrder(@Param("id") long id);
	
	
	@Query("SELECT o FROM Order o WHERE had_been_accepted = false AND had_been_canceled = false AND had_been_completed = false AND o.userId = ?1")
	public List<Order> ordersCustomer(long ui);
	
	@Query("SELECT o FROM Order o WHERE had_been_accepted = true AND had_been_canceled = false AND had_been_completed = false AND o.userId = ?1")
	public List<Order> ordersAcceptedCustomer(long ui);
	
	@Query("SELECT o FROM Order o WHERE had_been_accepted = true AND had_been_canceled = false AND had_been_completed = true AND o.userId = ?1")
	public List<Order> ordersCompletedCustomer(long ui);
	
	@Query("SELECT o FROM Order o WHERE had_been_accepted = false AND had_been_canceled = true AND had_been_completed = false AND o.userId = ?1")
	public List<Order> ordersCanceledCustomer(long ui);

}
