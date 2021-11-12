package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NamedQuery(
        name = "ServicePackageToSelect.findByID",
        query = "SELECT s " +
                "FROM ServicePackageToSelectEntity s " +
                "WHERE s.servicePackageToSelect_id = :id"
)
@Table(name = "servicepackagetoselect", schema = "dbtelco")
public class ServicePackageToSelectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servicePackageToSelect_id", nullable = false)
    private Long servicePackageToSelect_id;

    @Column(name = "name", nullable=false)
    private String name;

    public ServicePackageToSelectEntity(){
    }

    public ServicePackageToSelectEntity(
            String name,
            List<ServiceEntity> services,
            List<OptionalProductEntity> optionalProducts,
            List<ValidityPeriodEntity> validityPeriods
    ) {
        this.name = name;
        this.services = services;
        this.optionalProducts = optionalProducts;
        this.validityPeriods = validityPeriods;
    }


    //relationship definition part
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="offer",
        joinColumns={@JoinColumn(name="servicePackageToSelect_id")},
        inverseJoinColumns={@JoinColumn(name="service_id")}
    )
    private List<ServiceEntity> services;


    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="offerProduct",
        joinColumns={@JoinColumn(name="servicePackageToSelect_id")},
        inverseJoinColumns={@JoinColumn(name="optionalProduct_id")}
    )
    private List<OptionalProductEntity> optionalProducts;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="proposal",
        joinColumns={@JoinColumn(name="servicePackageToSelect_id")},
        inverseJoinColumns={@JoinColumn(name="validityPeriod_id")}
    )
    private List<ValidityPeriodEntity> validityPeriods;

    @OneToMany(mappedBy="packageSelected", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ServicePackageEntity> servicePackages;



    public Long getServicePackageToSelect_id() {
        return servicePackageToSelect_id;
    }

    public void setServicePackageToSelect_id(Long servicePackageToSelect_id) {
        this.servicePackageToSelect_id = servicePackageToSelect_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    public List<OptionalProductEntity> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProductEntity> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }

    public List<ValidityPeriodEntity> getValidityPeriods() {
        return validityPeriods;
    }

    public void setValidityPeriods(List<ValidityPeriodEntity> validityPeriods) {
        this.validityPeriods = validityPeriods;
    }
}
