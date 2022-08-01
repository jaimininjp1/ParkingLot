package com.parking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_Ticket")
public class Ticket {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String car_number;
	
	private String car_color;
	
	private Integer slot;
	
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCarNumber() {
		return car_number;
	}

	public void setCarNumber(String carNumber) {
		this.car_number = carNumber;
	}

	public String getCarColor() {
		return car_color;
	}

	public void setCarColor(String carColor) {
		this.car_color = carColor;
	}

	public Integer getSlot() {
		return slot;
	}

	public void setSlot(Integer slot) {
		this.slot = slot;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Ticket(String carNumber, String carColor, Integer slot, String status) {
		super();
		this.car_number = carNumber;
		this.car_color = carColor;
		this.slot = slot;
		this.status = status;
	}

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
