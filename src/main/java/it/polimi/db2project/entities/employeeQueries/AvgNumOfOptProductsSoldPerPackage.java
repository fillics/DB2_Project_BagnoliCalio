package it.polimi.db2project.entities.employeeQueries;

import it.polimi.db2project.entities.ServicePackageToSelectEntity;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity

@NamedQuery(
        name = "AvgNumOfOptProductsSoldPerPackage.findByServPackage",
        query = "SELECT n " +
                "FROM AvgNumOfOptProductsSoldPerPackage n " +
                "WHERE n.package_id = :package_id"
)


@Table(name = "avgnumofoptproductssoldperpackage", schema = "dbtelco")
public class AvgNumOfOptProductsSoldPerPackage implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "package_id", nullable = false)
    private Long package_id;

    @OneToOne
    @JoinColumn(name = "package_id")
    private ServicePackageToSelectEntity servicePackage;


    @Column(name = "average", nullable = false)
    private float average;


    public AvgNumOfOptProductsSoldPerPackage() {
    }

    public AvgNumOfOptProductsSoldPerPackage(Long package_id, ServicePackageToSelectEntity servicePackage, float average) {
        this.package_id = package_id;
        this.servicePackage = servicePackage;
        this.average = average;

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

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    @Override
    public String toString() {
        if(average==-1) return "The package: " + servicePackage.getName() + " (packageID: " + servicePackage.getServicePackageToSelect_id()
                + ") has not been sold!";
        else return "The average number of optional products sold together with: " + servicePackage.getName() + " (packageID: " + servicePackage.getServicePackageToSelect_id()
                + ") is " + average + ".";
    }
}
