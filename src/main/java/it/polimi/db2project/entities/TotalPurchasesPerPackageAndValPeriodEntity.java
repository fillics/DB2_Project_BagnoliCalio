package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@NamedQuery(
        name = "TotalPurchasesPerPackageAndValPeriod.findByServPackageAndValPeriod",
        query = "SELECT n " +
                "FROM TotalPurchasesPerPackageAndValPeriodEntity n " +
                "WHERE n.package_id = :package_id AND " +
                "n.valPeriod_id =: valPeriod_id "
)

@Table(name = "totalpurchasesperpackageandvalperiod", schema = "dbtelco")
public class TotalPurchasesPerPackageAndValPeriodEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id", nullable = false)
    private Long package_id;

    @OneToOne
    @JoinColumn(name = "package_id")
    private ServicePackageToSelectEntity servicePackage;

    @Column(name = "valPeriod_id", nullable = false)
    private Long valPeriod_id;

    @OneToOne
    @JoinColumn(name = "valPeriod_id")
    private ValidityPeriodEntity validityPeriod;

    @Column(name = "totalPurchases", nullable = false)
    private int totalPurchases;

    public TotalPurchasesPerPackageAndValPeriodEntity() {
    }

    public TotalPurchasesPerPackageAndValPeriodEntity(Long package_id, ServicePackageToSelectEntity servicePackage, Long valPeriod_id, ValidityPeriodEntity validityPeriod, int totalPurchases) {
        this.package_id = package_id;
        this.servicePackage = servicePackage;
        this.valPeriod_id = valPeriod_id;
        this.validityPeriod = validityPeriod;
        this.totalPurchases = totalPurchases;
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

    public Long getValPeriod_id() {
        return valPeriod_id;
    }

    public void setValPeriod_id(Long valPeriod_id) {
        this.valPeriod_id = valPeriod_id;
    }

    public ValidityPeriodEntity getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriodEntity validityPeriod) {
        this.validityPeriod = validityPeriod;
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
                + "), with the validity period: "+ validityPeriod+ " has been sold " + totalPurchases + " times.";
    }
}
