package com.hampcode.articlesapp.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;



@Entity
@Table(name = "purchaseorders")
public class PurchaseOrder {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty(message="Error de validación: se necesita un valor")
	@Column(name = "product")
	private String product;
	
	@NotEmpty(message="Error de validación: se necesita un valor")
	@Column(name = "responsible")
	private String responsible;
	
/*
	@NotEmpty(message="Error de validación: se necesita un valor")
	@Column(name = "suppliers")
	private List<Supplier> suppliers;*/
	
	
	@NotEmpty(message="Error de validación: se necesita un valor")
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	/*
	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	*/

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