package com.classboxes.canteenapp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;


@Entity
@Table(name = "carts")
@DynamicInsert
public class Cart {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    
    @Column()
    private Long userId;
    
    @Column()
    private Long productId;
    
    @Column()
    private float cost;
    
    @Column()
    private String itemName;
    
    @Column()
    private int amount;
    
    @Column()
    private boolean hadBeenOrder;
    
    @Column()
    private float singlePrice;
    
    @Column()
    private Long orderNumber;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isHadBeenOrder() {
		return hadBeenOrder;
	}

	public void setHadBeenOrder(boolean hadBeenOrder) {
		this.hadBeenOrder = hadBeenOrder;
	}

	public float getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(float singlePrice) {
		this.singlePrice = singlePrice;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", userId=" + userId + ", productId=" + productId + ", cost=" + cost
				+ ", itemName=" + itemName + ", amount=" + amount + ", hadBeenOrder=" + hadBeenOrder + ", singlePrice="
				+ singlePrice + ", orderNumber=" + orderNumber + "]";
	}

	public Cart() {
		super();
	}

	public Cart(Long userId, Long productId, float cost, String itemName, int amount, boolean hadBeenOrder,
			float singlePrice, Long orderNumber) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.cost = cost;
		this.itemName = itemName;
		this.amount = amount;
		this.hadBeenOrder = hadBeenOrder;
		this.singlePrice = singlePrice;
		this.orderNumber = orderNumber;
	}
    
    

}
