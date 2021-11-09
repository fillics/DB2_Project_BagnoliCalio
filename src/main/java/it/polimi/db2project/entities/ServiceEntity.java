package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
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
    private int feeExtraMinute;

    @Column(name = "feeExtraSMSs")
    private int feeExtraSMSs;

    @Column(name = "numberOfGigabytes")
    private int numberOfGigabytes;

    @Column(name = "feeForExtraGigabytes")
    private int feeForExtraGigabytes;


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
        int feeExtraMinute,
        int feeExtraSMSs,
        int numberOfGigabytes,
        int feeForExtraGigabytes
    ) {
        this.service_id = service_id;
        this.typeOfService = typeOfService;
        this.numMinutes = numMinutes;
        this.numSMS = numSMS;
        this.feeExtraMinute = feeExtraMinute;
        this.feeExtraSMSs = feeExtraSMSs;
        this.numberOfGigabytes = numberOfGigabytes;
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

    public int getFeeExtraMinute() {
        return feeExtraMinute;
    }

    public void setFeeExtraMinute(int feeExtraMinute) {
        this.feeExtraMinute = feeExtraMinute;
    }

    public int getFeeExtraSMSs() {
        return feeExtraSMSs;
    }

    public void setFeeExtraSMSs(int feeExtraSMSs) {
        this.feeExtraSMSs = feeExtraSMSs;
    }

    public int getNumberOfGigabytes() {
        return numberOfGigabytes;
    }

    public void setNumberOfGigabytes(int numberOfGigabytes) {
        this.numberOfGigabytes = numberOfGigabytes;
    }

    public int getFeeForExtraGigabytes() {
        return feeForExtraGigabytes;
    }

    public void setFeeForExtraGigabytes(int feeForExtraGigabytes) {
        this.feeForExtraGigabytes = feeForExtraGigabytes;
    }

    public List<ServicePackageToSelectEntity> getServicePackagesToSelect() {
        return servicePackagesToSelect;
    }

    public void setServicePackagesToSelect(List<ServicePackageToSelectEntity> servicePackagesToSelect) {
        this.servicePackagesToSelect = servicePackagesToSelect;
    }
}
