package it.polimi.db2project.entities.employeeQueries;

import it.polimi.db2project.entities.ServicePackageToSelectEntity;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity

@NamedQuery(
        name = "SalesPerPackageWithOptProduct.findByServPackage",
        query = "SELECT n FROM SalesPerPackageWithOptProduct n " +
                "WHERE n.package_id = :package_id"
)


@Table(name = "salesperpackagewithoptproducts", schema = "dbtelco")
public class SalesPerPackageWithOptProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id", nullable = false)
    private Long package_id;


    @OneToOne
    @JoinColumn(name = "package_id")
    private ServicePackageToSelectEntity servicePackage;


    @Column(name = "totalSales", nullable = false)
    private float totalSales;

    public SalesPerPackageWithOptProduct() {
    }

    public SalesPerPackageWithOptProduct(Long package_id, ServicePackageToSelectEntity servicePackage, float totalSales) {
        this.package_id = package_id;
        this.servicePackage = servicePackage;
        this.totalSales = totalSales;
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

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public String toString() {
        return "The package: " + servicePackage.getName() + " (packageID: " + servicePackage.getServicePackageToSelect_id()
                + "), with the optional products has the following value of sale = " + totalSales + " €.";
    }
}
