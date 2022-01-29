package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@NamedQuery(
    name = "ServicePackage.findServicePackageThatContainServicePackageToSelect",
    query = "SELECT s " +
        "FROM ServicePackageEntity s " +
        "WHERE s.packageSelected = : servicePackageToSelect_id "
)

@NamedQuery(
    name = "ServicePackage.findServicePackageThatContainServicePackageToSelectAndValPeriod",
    query = "SELECT s " +
        "FROM ServicePackageEntity s " +
        "WHERE s.packageSelected = : servicePackageToSelect_id AND s.validityPeriod =: validityPeriod_id "
)

@NamedQuery(
        name = "ServicePackage.findServicePackageOfUser",
        query = "SELECT s " +
                "FROM ServicePackageEntity s " +
                "WHERE s.userOwner = : user"
)

@Table(name = "servicepackage", schema = "dbtelco")
public class ServicePackageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servicePackage_id", nullable = false)
    private Long servicePackage_id;

    @Temporal(TemporalType.DATE)
    @Column(name = "startDate")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "valuePackage", unique=true, nullable=false)
    private float valuePackage;

    @Column(name = "totalValueOptionalProducts", unique=true, nullable=false)
    private float totalValueOptionalProducts;

    //relationship definition part
    @ManyToOne (fetch = FetchType.EAGER, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REFRESH,
        CascadeType.DETACH}
    )
    @JoinColumn(name = "packageSelected")
    private ServicePackageToSelectEntity packageSelected;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REFRESH,
        CascadeType.DETACH}
    ) @JoinColumn(name = "validityPeriod")
    private ValidityPeriodEntity validityPeriod;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="addedProduct",
            joinColumns={@JoinColumn(name="servicePackage_id")},
            inverseJoinColumns={@JoinColumn(name="optionalProduct_id")}
    )
    private List<OptionalProductEntity> optionalProducts;

    @ManyToOne (fetch = FetchType.EAGER, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REFRESH,
        CascadeType.DETACH}
    )
    @JoinColumn(name = "userOwner")
    private UserEntity userOwner;

    @OneToOne(mappedBy = "servicePackageAssociated", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderEntity order;


    public ServicePackageEntity(){
    }

    public ServicePackageEntity(
            ServicePackageToSelectEntity packageSelected,
            ValidityPeriodEntity validityPeriod,
            java.sql.Date startDate,
            java.sql.Date endDate,
            float valuePackage,
            float totalValueOptionalProducts,
            List<OptionalProductEntity> optionalProducts
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.valuePackage = valuePackage;
        this.packageSelected = packageSelected;
        this.validityPeriod = validityPeriod;
        this.totalValueOptionalProducts = totalValueOptionalProducts;
        this.optionalProducts = optionalProducts;
    }

    public Long getServicePackage_id() {
        return this.servicePackage_id;
    }

    public void setServicePackage_id(Long servicePackage_id) {
        this.servicePackage_id = servicePackage_id;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getValuePackage() {
        return valuePackage;
    }

    public void setValuePackage(float valuePackage) {
        this.valuePackage = valuePackage;
    }

    public ServicePackageToSelectEntity getPackageSelected() {
        return packageSelected;
    }

    public void setPackageSelected(ServicePackageToSelectEntity packageSelected) {
        this.packageSelected = packageSelected;
    }

    public ValidityPeriodEntity getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriodEntity validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public List<OptionalProductEntity> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProductEntity> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }

    public UserEntity getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(UserEntity userOwner) {
        this.userOwner = userOwner;
    }

    public float getTotalValueOptionalProducts() {
        return totalValueOptionalProducts;
    }

    public void setTotalValueOptionalProducts(float totalValueOptionalProducts) {
        this.totalValueOptionalProducts = totalValueOptionalProducts;
    }

    @Override
    public String toString() {
        return "ServicePackageEntity{" +
                "servicePackage_id=" + servicePackage_id +
                ", packageSelected=" + packageSelected +
                ", optionalProducts=" + optionalProducts +
                '}';
    }
}
