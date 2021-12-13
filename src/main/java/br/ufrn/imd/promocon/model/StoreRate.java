package br.ufrn.imd.promocon.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "store_ratings")
@Entity
public class StoreRate extends GenericEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE_RATE")
    @SequenceGenerator(name = "SEQ_STORE_RATE", sequenceName = "seq_store_rate", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_store")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private User author;

    private Float rate;

    private String commentary;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
