package com.classboxes.canteenapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.classboxes.canteenapp.model.Cart;
import com.classboxes.canteenapp.model.Order;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long>{
	
	@Query("SELECT c FROM Cart c WHERE c.orderNumber = ?1")
	public List<Cart> findCartItems(long on);
	
	@Modifying
	@Transactional
	@Query(value = "insert into carts (amount, cost, single_price, had_been_order, item_name, product_id, user_id) VALUES(:amount, :amount * :cost, :sp, FALSE, :name, :pId, :uId)" , nativeQuery = true)
	public void insertIntoCart(int amount, float sp, float cost, String name, long pId, long uId);
	
	@Query("SELECT c FROM Cart c WHERE user_id = ?1 AND had_been_order = false")
	public List<Cart> inYourCart(Long id);
	
	@Query("SELECT CASE WHEN COUNT(c)>0 THEN 1 ELSE 0 END FROM Cart c WHERE user_id = ?1 AND had_been_order = false")
	public int count(Long id);
	
	@Transactional
	@Modifying
	@Query("update Cart set cost = single_price * :amount, amount = :amount WHERE cart_id = :id")
	public void updateCart(@Param("amount") int amount, @Param("id") long id);
	
	@Transactional
	@Modifying
	@Query("delete from Cart where cart_id = :cartId")
	public void deleteFromCart(Long cartId);
	

	@Query("SELECT SUM(cost) FROM Cart c WHERE user_id = ?1 AND had_been_order = false")
	public Object total(Long userId);
	
	@Transactional
	@Modifying
	@Query("update Cart set had_been_order = true, order_number = :on WHERE had_been_order = false AND user_id = :id")
	public void ordered(@Param("id") long id, @Param("on") long on);

}
