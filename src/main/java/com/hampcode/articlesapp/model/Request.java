package com.hampcode.articlesapp.model;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.websocket.OnError;

import org.aspectj.lang.annotation.DeclareError;


@Entity
@Table(name = "purchaserequests")
public class Request {
	
	@Id
	@Column(name="request_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 Long id;
	
	@NotEmpty(message="Seleccione Area")
	private String area;
	
	@NotEmpty(message="Seleccione producto")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "request_product", 
				joinColumns = { @JoinColumn(name = "request_id") }, 
				inverseJoinColumns = {@JoinColumn(name = "product_id") 
			})
	private List<Product> products = new ArrayList<>();
	
	
	private String description;


	@NotNull(message="Error de validación: se necesita un valor")
	private Long quantity;
	
	@NotEmpty(message="Ingrese estado de solicitud")
	private String state;
	
	
	
	
	@NotEmpty(message="Ingrese fecha")
	private String date;
	
	@NotEmpty(message="Ingrese fecha")
	private String date2;
	
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



	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	
}