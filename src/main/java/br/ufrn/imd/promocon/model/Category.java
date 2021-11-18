package br.ufrn.imd.promocon.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ufrn.imd.promocon.enums.EnumCategories;

@Table(name = "categories")
@Entity
public class Category extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEGORY")
	@SequenceGenerator(name = "SEQ_CATEGORY", sequenceName = "seq_category", allocationSize = 1)
	private Long id;

	@Enumerated(EnumType.STRING)
	private EnumCategories categoryEnum;
	
	@ManyToOne
	@JoinColumn(name = "id_sale")
	private Sale sale;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnumCategories getCategoryEnum() {
		return categoryEnum;
	}

	public void setCategoryEnum(EnumCategories categoryEnum) {
		this.categoryEnum = categoryEnum;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
	
}
