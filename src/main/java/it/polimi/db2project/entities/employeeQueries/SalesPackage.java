package it.polimi.db2project.entities.employeeQueries;

import it.polimi.db2project.entities.ServicePackageToSelectEntity;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(
        name = "SalesPackage.findByServPackage",
        query = "SELECT n FROM SalesPackage n " +
                "WHERE n.package_id = :package_id"
)

@Table(name = "salespackage", schema = "dbtelco")
public class SalesPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id", nullable = false)
    private Long package_id;


    @OneToOne
    @JoinColumn(name = "package_id")
    private ServicePackageToSelectEntity servicePackage;


    @Column(name = "totalSalesWithOptProduct", nullable = false)
    private float totalSalesWithOptProduct;

    @Column(name = "totalSalesWithoutOptProduct", nullable = false)
    private float totalSalesWithoutOptProduct;

    public SalesPackage() {
    }

    public SalesPackage(Long package_id, ServicePackageToSelectEntity servicePackage) {
        this.package_id = package_id;
        this.servicePackage = servicePackage;
        totalSalesWithOptProduct=0;
        totalSalesWithoutOptProduct=0;
    }

    public Long getPackage_id() {
        return package_id;
    }

    public void setPackage_id(Long package_id) {
        this.package_id = package_id;
    }

    public ServicePackageToSelectEntity getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackageToSelectEntity servicePackage) {
        this.servicePackage = servicePackage;
    }

    public float getTotalSalesWithOptProduct() {
        return totalSalesWithOptProduct;
    }

    public float getTotalSalesWithoutOptProduct() {
        return totalSalesWithoutOptProduct;
    }

    public void setTotalSalesWithOptProduct(float totalSalesWithOptProduct) {
        this.totalSalesWithOptProduct = totalSalesWithOptProduct;
    }

    public void setTotalSalesWithoutOptProduct(float totalSalesWithoutOptProduct) {
        this.totalSalesWithoutOptProduct = totalSalesWithoutOptProduct;
    }

    @Override
    public String toString() {
        return "Value of sales of the package: " + servicePackage.getName() + " (packageID: " + servicePackage.getServicePackageToSelect_id()
                + ")\n " + "without optional products: "+totalSalesWithoutOptProduct + " €.\n " +
                "with optional products: "+totalSalesWithOptProduct + " €.";
    }
}