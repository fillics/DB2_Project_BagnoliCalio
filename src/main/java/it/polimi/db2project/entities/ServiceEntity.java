package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NamedQuery(
        name = "Service.findByID",
        query = "SELECT s " +
                "FROM ServiceEntity s " +
                "WHERE s.service_id = :service_id"
)

@NamedQuery(
    name = "Service.findAll",
    query = "SELECT o " +
        "FROM ServiceEntity o "
)

@Table(name = "service", schema = "dbtelco")
public class ServiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Long service_id;

    @Column(name = "typeOfService", nullable=false)
    private String typeOfService;

    @Column(name = "numMinutes")
    private int numMinutes;

    @Column(name = "numSMS")
    private int numSMS;

    @Column(name = "feeExtraMinute")
    private float feeExtraMinute;

    @Column(name = "feeExtraSMSs")
    private float feeExtraSMSs;

    @Column(name = "numGigabytes")
    private int numGigabytes;

    @Column(name = "feeForExtraGigabytes")
    private float feeForExtraGigabytes;


    //relationship definition part
    @ManyToMany(mappedBy = "services", fetch = FetchType.EAGER)
    private List<ServicePackageToSelectEntity> servicePackagesToSelect;

    public ServiceEntity(){
    }

    public ServiceEntity(
        Long service_id,
        String typeOfService,
        int numMinutes,
        int numSMS,
        float feeExtraMinute,
        float feeExtraSMSs,
        int numGigabytes,
        float feeForExtraGigabytes
    ) {
        this.service_id = service_id;
        this.typeOfService = typeOfService;
        this.numMinutes = numMinutes;
        this.numSMS = numSMS;
        this.feeExtraMinute = feeExtraMinute;
        this.feeExtraSMSs = feeExtraSMSs;
        this.numGigabytes = numGigabytes;
        this.feeForExtraGigabytes = feeForExtraGigabytes;
    }

    /**
     * getter and setter
     */

    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public int getNumMinutes() {
        return numMinutes;
    }

    public void setNumMinutes(int numMinutes) {
        this.numMinutes = numMinutes;
    }

    public int getNumSMS() {
        return numSMS;
    }

    public void setNumSMS(int numSMS) {
        this.numSMS = numSMS;
    }

    public float getFeeExtraMinute() {
        return feeExtraMinute;
    }

    public void setFeeExtraMinute(float feeExtraMinute) {
        this.feeExtraMinute = feeExtraMinute;
    }

    public float getFeeExtraSMSs() {
        return feeExtraSMSs;
    }

    public void setFeeExtraSMSs(float feeExtraSMSs) {
        this.feeExtraSMSs = feeExtraSMSs;
    }

    public int getNumGigabytes() {
        return numGigabytes;
    }

    public void setNumGigabytes(int numGigabytes) {
        this.numGigabytes = numGigabytes;
    }

    public float getFeeForExtraGigabytes() {
        return feeForExtraGigabytes;
    }

    public void setFeeForExtraGigabytes(float feeForExtraGigabytes) {
        this.feeForExtraGigabytes = feeForExtraGigabytes;
    }

    public List<ServicePackageToSelectEntity> getServicePackagesToSelect() {
        return servicePackagesToSelect;
    }

    public void setServicePackagesToSelect(List<ServicePackageToSelectEntity> servicePackagesToSelect) {
        this.servicePackagesToSelect = servicePackagesToSelect;
    }

    @Override
    public String toString() {
        return typeOfService;
    }
}
