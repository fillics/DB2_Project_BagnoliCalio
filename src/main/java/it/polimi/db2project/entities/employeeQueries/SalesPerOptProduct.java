package it.polimi.db2project.entities.employeeQueries;

import it.polimi.db2project.entities.OptionalProductEntity;
import it.polimi.db2project.entities.UserEntity;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity


@NamedQuery(
        name = "SalesPerOptProduct.findMax",
        query = "SELECT s " +
                "FROM SalesPerOptProduct s " +
                "WHERE s.salesOfOptProd  = (SELECT MAX(s2.salesOfOptProd) " +
                "FROM SalesPerOptProduct s2 )"
)

@Table(name = "salesperoptproduct", schema = "dbtelco")
public class SalesPerOptProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "optionalProduct_id", nullable = false)
    private Long optionalProduct_id;

    @OneToOne
    @JoinColumn(name = "optionalProduct_id")
    private OptionalProductEntity optionalProduct;

    @Column(name = "salesOfOptProd", nullable = false)
    private float salesOfOptProd;

    public SalesPerOptProduct() {
    }

    public SalesPerOptProduct(Long optionalProduct_id, OptionalProductEntity optionalProduct, float salesOfOptProd) {
        this.optionalProduct_id = optionalProduct_id;
        this.optionalProduct = optionalProduct;
        this.salesOfOptProd = salesOfOptProd;
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

    public float getSalesOfOptProd() {
        return salesOfOptProd;
    }

    public void setSalesOfOptProd(float salesOfOptProd) {
        this.salesOfOptProd = salesOfOptProd;
    }

    @Override
    public String toString() {

        return "The optional product: "+optionalProduct.getName()+" (id: "+optionalProduct.getOptionalProduct_id()+")" +
                " has greatest value of sales across all the sold service packages: "+ salesOfOptProd+"€";

    }
}
