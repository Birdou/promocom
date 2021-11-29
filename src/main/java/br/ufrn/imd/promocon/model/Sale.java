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

@Table(name = "sales")
@Entity
public class Sale extends GenericEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALE")
	@SequenceGenerator(name = "SEQ_SALE", sequenceName = "seq_sale", allocationSize = 1)
	private Long id;
	
	private String title;
	
	private String description;
	
	private Double originalPrice;
	
	private Double salePrice;
	
	private Double discount;
	
	private String image;
	
	@Enumerated(EnumType.STRING)
	private EnumCategories category;
	
	@ManyToOne
	@JoinColumn(name = "id_store")
	private Store store;
	
	@ManyToOne
	@JoinColumn(name = "id_author")
	private User author;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public EnumCategories getCategory() {
		return category;
	}

	public void setCategory(EnumCategories category) {
		this.category = category;
	}
}
