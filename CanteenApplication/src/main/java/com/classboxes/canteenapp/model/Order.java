package com.classboxes.canteenapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "orders")
@DynamicInsert
public class Order {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    
    @Column()
    private Date orderDate;
    
    @Column()
    private Long orderNumber;
    
    @Column()
    private boolean hadBeenAccepted;
    
    @Column()
    private boolean hadBeenCompleted;
    
    @Column()
    private boolean hadBeenCanceled;
    
    @Column()
    private float cost;
    
    @Column()
    private Long userId;

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", orderNumber=" + orderNumber
				+ ", hadBeenAccepted=" + hadBeenAccepted + ", hadBeenCompleted=" + hadBeenCompleted
				+ ", hadBeenCanceled=" + hadBeenCanceled + ", cost=" + cost + ", userId=" + userId + "]";
	}

	public Order() {
		super();
	}

	public Order(Date orderDate, Long orderNumber, boolean hadBeenAccepted, boolean hadBeenCompleted,
			boolean hadBeenCanceled, float cost, Long userId) {
		super();
		this.orderDate = orderDate;
		this.orderNumber = orderNumber;
		this.hadBeenAccepted = hadBeenAccepted;
		this.hadBeenCompleted = hadBeenCompleted;
		this.hadBeenCanceled = hadBeenCanceled;
		this.cost = cost;
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public boolean isHadBeenAccepted() {
		return hadBeenAccepted;
	}

	public void setHadBeenAccepted(boolean hadBeenAccepted) {
		this.hadBeenAccepted = hadBeenAccepted;
	}

	public boolean isHadBeenCompleted() {
		return hadBeenCompleted;
	}

	public void setHadBeenCompleted(boolean hadBeenCompleted) {
		this.hadBeenCompleted = hadBeenCompleted;
	}

	public boolean isHadBeenCanceled() {
		return hadBeenCanceled;
	}

	public void setHadBeenCanceled(boolean hadBeenCanceled) {
		this.hadBeenCanceled = hadBeenCanceled;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    

}
