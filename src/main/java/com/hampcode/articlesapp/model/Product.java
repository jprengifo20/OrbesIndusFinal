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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;




@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 Long id;
	
	@NotEmpty(message="Ingrese nombre")
	private String name;
	
	@NotEmpty(message="Seleccione proveedor")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "product_supplier", 
				joinColumns = { @JoinColumn(name = "product_id") }, 
				inverseJoinColumns = {@JoinColumn(name = "supplier_id") 
			})
	private List<Supplier> suppliers = new ArrayList<>();
	
	
	
	@NotNull(message="Error de validación: Formato(#0.0000)")
	private double unit_price;
	
	@NotNull(message="Ingrese cantidad de stock")
	private Long unit_stock;
	
	private String description;

	
	


	//Setter and Getters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	public Long getUnit_stock() {
		return unit_stock;
	}

	public void setUnit_stock(Long unit_stock) {
		this.unit_stock = unit_stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
}