package com.classboxes.canteenapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "vendors")
@DynamicInsert
public class Vendor {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;
    
    @Column
    private Long customerId;

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Vendor [vendorId=" + vendorId + ", customerId=" + customerId + "]";
	}

	public Vendor(Long customerId) {
		super();
		this.customerId = customerId;
	}

	public Vendor() {
		super();
	}
    
    
}
