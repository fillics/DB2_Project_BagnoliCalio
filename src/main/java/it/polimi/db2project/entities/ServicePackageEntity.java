package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "servicepackage", schema = "dbtelco")
public class ServicePackageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servicePackage_id", nullable = false)
    private Long servicePackage_id;

    @Column(name = "startDate", nullable=false)
    private Date startDate;

    @Column(name = "endDate", nullable=false)
    private Date endDate;

    @Column(name = "totalValuePackage", unique=true, nullable=false)
    private float totalValuePackage;

    //relationship definition part
    @ManyToOne @JoinColumn(name = "packageSelected")
    private ServicePackageToSelectEntity packageSelected;

    @ManyToOne @JoinColumn(name = "validityPeriod")
    private ValidityPeriodEntity validityPeriod;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="addedProduct",
        joinColumns={@JoinColumn(name="servicePackage_id")},
        inverseJoinColumns={@JoinColumn(name="optionalProduct_id")}
    )
    private List<OptionalProductEntity> optionalProducts;

    @ManyToOne @JoinColumn(name = "userOwner")
    private UserEntity userOwner;

    @OneToOne(mappedBy = "servicePackageAssociated", cascade = CascadeType.ALL)
    private OrderEntity order;


    public ServicePackageEntity(){
    }

    public ServicePackageEntity(Date startDate, Date endDate, float totalValuePackage, ServicePackageToSelectEntity packageSelected, ValidityPeriodEntity validityPeriod, List<OptionalProductEntity> optionalProducts, UserEntity userOwner) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalValuePackage = totalValuePackage;
        this.packageSelected = packageSelected;
        this.validityPeriod = validityPeriod;
        this.optionalProducts = optionalProducts;
        this.userOwner = userOwner;
    }

    public Long getServicePackage_id() {
        return servicePackage_id;
    }

    public void setServicePackage_id(Long servicePackage_id) {
        this.servicePackage_id = servicePackage_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getTotalValuePackage() {
        return totalValuePackage;
    }

    public void setTotalValuePackage(float totalValuePackage) {
        this.totalValuePackage = totalValuePackage;
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
}
