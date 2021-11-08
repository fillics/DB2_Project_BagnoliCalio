package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "service", schema = "dbtelco")
public class ServiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    private int serviceId;
    private String typeOfService;
    private Integer numMinutes;
    private Integer numSms;
    private Integer feeExtraMinute;
    private Integer feeExtraSmSs;
    private Integer numberOfGigabytes;
    private Integer feeForExtraGigabytes;

    @Id
    @Column(name = "service_id")
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "typeOfService")
    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    @Basic
    @Column(name = "numMinutes")
    public Integer getNumMinutes() {
        return numMinutes;
    }

    public void setNumMinutes(Integer numMinutes) {
        this.numMinutes = numMinutes;
    }

    @Basic
    @Column(name = "numSMS")
    public Integer getNumSms() {
        return numSms;
    }

    public void setNumSms(Integer numSms) {
        this.numSms = numSms;
    }

    @Basic
    @Column(name = "feeExtraMinute")
    public Integer getFeeExtraMinute() {
        return feeExtraMinute;
    }

    public void setFeeExtraMinute(Integer feeExtraMinute) {
        this.feeExtraMinute = feeExtraMinute;
    }

    @Basic
    @Column(name = "FeeExtraSMSs")
    public Integer getFeeExtraSmSs() {
        return feeExtraSmSs;
    }

    public void setFeeExtraSmSs(Integer feeExtraSmSs) {
        this.feeExtraSmSs = feeExtraSmSs;
    }

    @Basic
    @Column(name = "NumberOfGigabytes")
    public Integer getNumberOfGigabytes() {
        return numberOfGigabytes;
    }

    public void setNumberOfGigabytes(Integer numberOfGigabytes) {
        this.numberOfGigabytes = numberOfGigabytes;
    }

    @Basic
    @Column(name = "FeeForExtraGigabytes")
    public Integer getFeeForExtraGigabytes() {
        return feeForExtraGigabytes;
    }

    public void setFeeForExtraGigabytes(Integer feeForExtraGigabytes) {
        this.feeForExtraGigabytes = feeForExtraGigabytes;
    }

}
