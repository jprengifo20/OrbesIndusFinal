package com.hampcode.articlesapp.model;



import java.util.ArrayList;
import java.util.List;
import java.lang.*;

import javax.validation.*;
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
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForDate;



@Entity
@Table(name = "purchaseorders")
public class PurchaseOrder {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	
	@NotEmpty(message="Error de validación: se necesita un valor")
	@Column(name = "responsible")
	private String responsible;
	
	@NotEmpty(message="Seleccione producto")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "purchaseorder_product", 
				joinColumns = { @JoinColumn(name = "purchaseorder_id") }, 
				inverseJoinColumns = {@JoinColumn(name = "product_id") 
			})
	private List<Product> products = new ArrayList<>();
	
	@NotEmpty(message="Seleccione proveedor")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "purchaseorder_supplier", 
				joinColumns = { @JoinColumn(name = "purchaseorder_id") }, 
				inverseJoinColumns = {@JoinColumn(name = "supplier_id") 
			})
	private List<Supplier> suppliers = new ArrayList<>();
	

	
	@NotEmpty(message="Error de validación: se necesita un valor")	
	@Length(min = 10, max = 10, message = "Complete correctamente los datos")
	@Column(name = "date")
	private String date;
	
	
	@Column(name="detail")
	@NotEmpty(message="Error de validación: se necesita un valor")
	private String detail;

	
	/*@NotEmpty(message="Debe ingresar el campo del proveedor")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "purchaseorder_supplier", 
				joinColumns = { @JoinColumn(name = "purchaseorder_id") }, 
				inverseJoinColumns = {@JoinColumn(name = "supplier_id") 
			})
	private List<Supplier> suppliers = new ArrayList<>();*/


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	



	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}


	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}



} 