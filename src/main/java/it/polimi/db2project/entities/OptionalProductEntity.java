package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;


@Entity
@NamedQuery(
        name = "OptionalProduct.findByID",
        query = "SELECT o " +
                "FROM OptionalProductEntity o " +
                "WHERE o.optionalProduct_id = :optionalProduct_id"
)
@NamedQuery(
        name = "OptionalProduct.findAll",
        query = "SELECT o " +
                "FROM OptionalProductEntity o "
)

@Table(name = "optionalproduct", schema = "dbtelco")
public class OptionalProductEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionalProduct_id", nullable = false)
    private Long optionalProduct_id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "monthlyFee", nullable=false)
    private float monthlyFee;

    //relationship definition part
// gia controllato
    @ManyToMany(mappedBy = "optionalProducts", fetch = FetchType.EAGER)
    private List<ServicePackageEntity> servicePackages;

    @ManyToMany(mappedBy = "optionalProducts", fetch = FetchType.EAGER)
    private List<ServicePackageToSelectEntity> servicePackagesToSelect;

    public OptionalProductEntity(){
    }

    public OptionalProductEntity(String name, float monthlyFee) {
        this.name = name;
        this.monthlyFee = monthlyFee;
    }

    /**
     * getter and setter
     */

    public Long getOptionalProduct_id() {
        return optionalProduct_id;
    }

    public void setOptionalProduct_id(Long optionalProduct_id) {
        this.optionalProduct_id = optionalProduct_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public List<ServicePackageEntity> getServicePackages() {
        return servicePackages;
    }

    public void setServicePackages(List<ServicePackageEntity> servicePackages) {
        this.servicePackages = servicePackages;
    }

    public List<ServicePackageToSelectEntity> getServicePackagesToSelect() {
        return servicePackagesToSelect;
    }

    public void setServicePackagesToSelect(List<ServicePackageToSelectEntity> servicePackagesToSelect) {
        this.servicePackagesToSelect = servicePackagesToSelect;
    }
}
