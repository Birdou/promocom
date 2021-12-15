package br.ufrn.imd.promocon.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "stores")
@Entity
public class Store extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE")
	@SequenceGenerator(name = "SEQ_STORE", sequenceName = "seq_store", allocationSize = 1)
	private Long id;

	private String name;

	// private String owner;
	@ManyToOne
	@JoinColumn(name = "id_owner")
	private User owner;

	// localização
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_address")
	private Address address;

	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
	private List<Sale> sales;

	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
	private List<StoreRate> ratings;

	public Float getRating() {
		Float rateSum = 0f;
		for (StoreRate rate : ratings)
			rateSum += rate.getRate();

		Float rating = 0f;
		if (!ratings.isEmpty())
			rating = rateSum / ratings.size();

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');

		return Float.valueOf(new DecimalFormat("#.#", symbols).format(rating));
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<StoreRate> getRatings() {
		return ratings;
	}

	public void setRatings(List<StoreRate> ratings) {
		this.ratings = ratings;
	}
}
