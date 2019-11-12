package com.hampcode.articlesapp.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.websocket.OnError;

import org.aspectj.lang.annotation.DeclareError;


@Entity
@Table(name = "purchaseRequests")
public class Request {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 Long id;
	
	@NotEmpty(message="Seleccione Area")
	private String area;
	
	@NotEmpty(message="Ingrese producto")
	private String product;
	
	
	private String description;


	@NotNull(message="Error de validación: se necesita un valor")
	private Long quantity;
	
	@NotEmpty(message="Ingrese estado de solicitud")
	private String state;
	
	@NotEmpty(message="Ingrese fecha")
	private String date;
	
	@NotNull(message="Error de validación: Formato(#0.0000)")
	private double unit_price;
	
	
	


	//Setter and Getters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}



	public String getProduct() {
		return product;
	}



	public void setProduct(String product) {
		this.product = product;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public double getUnit_price() {
		return unit_price;
	}



	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}



	public Long getQuantity() {
		return quantity;
	}



	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	
}