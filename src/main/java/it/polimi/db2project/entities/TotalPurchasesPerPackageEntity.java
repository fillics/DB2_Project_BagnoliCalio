package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity

@NamedQuery(
        name = "TotalPurchasesPerPackage.findByServPackage",
        query = "SELECT n " +
                "FROM TotalPurchasesPerPackageEntity n " +
                "WHERE n.package_id = :package_id"
)


@Table(name = "totalpurchasesperpackage", schema = "dbtelco")
public class TotalPurchasesPerPackageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id", nullable = false)
    private Long package_id;

    @OneToOne
    @JoinColumn(name = "package_id")
    private ServicePackageToSelectEntity servicePackage;

    @Column(name = "totalPurchases", nullable = false)
    private int totalPurchases;


    public TotalPurchasesPerPackageEntity() {
    }

    public TotalPurchasesPerPackageEntity(Long package_id, ServicePackageToSelectEntity servicePackage, int totalPurchases) {
        this.package_id = package_id;
        this.servicePackage = servicePackage;
        this.totalPurchases = totalPurchases;
    }

    public ServicePackageToSelectEntity getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackageToSelectEntity servicePackage) {
        this.servicePackage = servicePackage;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    @Override
    public String toString() {
        return "The package: " + servicePackage.getName() + " (packageID: " + servicePackage.getServicePackageToSelect_id()
                + ") has been sold " + totalPurchases + " times.";
    }
}
