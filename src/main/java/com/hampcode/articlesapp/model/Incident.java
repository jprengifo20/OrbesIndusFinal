package com.hampcode.articlesapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "incidents")
public class Incident {
	
	@Id
	@Column(name = "incident_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="gravity")
	@NotEmpty(message="Debe ingresar el campo de gravedad")
	private String gravity;

	/*@NotEmpty(message="Debe ingresar el campo del proveedor")*/
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "incident_supplier", 
				joinColumns = { @JoinColumn(name = "incident_id") }, 
				inverseJoinColumns = {@JoinColumn(name = "supplier_id") 
			})
	private List<Supplier> suppliers = new ArrayList<>();
	

	@Column(name = "date")
	@NotEmpty(message="Debe ingresar la fecha")
	private String date;

	@Lob
	@Column(name="observations")
	@NotEmpty(message="Debe ingresar el campo de las observaciones")
	@Type(type = "org.hibernate.type.TextType")
	private String observations;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGravity() {
		return gravity;
	}

	public void setGravity(String gravity) {
		this.gravity = gravity;
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	
	
}