package it.polimi.db2project.entities.employeeQueries;

import it.polimi.db2project.entities.OptionalProductEntity;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity

@NamedQuery(
        name = "BestOptionalProduct.findMax",
        query = "SELECT s FROM BestOptionalProduct s "
)

@Table(name = "bestoptionalproduct", schema = "dbtelco")
public class BestOptionalProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "optionalProduct_id", nullable = false)
    private Long optionalProduct_id;

    @OneToOne
    @JoinColumn(name = "optionalProduct_id")
    private OptionalProductEntity optionalProduct;

    @Column(name = "sales", nullable = false)
    private float sales;

    public BestOptionalProduct() {
    }

    public BestOptionalProduct(Long optionalProduct_id, OptionalProductEntity optionalProduct, float sales) {
        this.optionalProduct_id = optionalProduct_id;
        this.optionalProduct = optionalProduct;
        this.sales = sales;
    }

    public Long getOptionalProduct_id() {
        return optionalProduct_id;
    }

    public void setOptionalProduct_id(Long optionalProduct_id) {
        this.optionalProduct_id = optionalProduct_id;
    }

    public OptionalProductEntity getOptionalProduct() {
        return optionalProduct;
    }

    public void setOptionalProduct(OptionalProductEntity optionalProduct) {
        this.optionalProduct = optionalProduct;
    }

    public float getSales() {
        return sales;
    }

    public void setSales(float salesOfOptProd) {
        this.sales = salesOfOptProd;
    }

    @Override
    public String toString() {

        return "The optional product: "+optionalProduct.getName()+" (id: "+optionalProduct.getOptionalProduct_id()+")" +
                " has greatest value of sales across all the sold service packages: "+ sales +"€";

    }
}
