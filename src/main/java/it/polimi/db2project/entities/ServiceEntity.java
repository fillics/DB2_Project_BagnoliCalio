package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "service", schema = "dbtelco")
public class ServiceEntity implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceEntity that = (ServiceEntity) o;

        if (serviceId != that.serviceId) return false;
        if (typeOfService != null ? !typeOfService.equals(that.typeOfService) : that.typeOfService != null)
            return false;
        if (numMinutes != null ? !numMinutes.equals(that.numMinutes) : that.numMinutes != null) return false;
        if (numSms != null ? !numSms.equals(that.numSms) : that.numSms != null) return false;
        if (feeExtraMinute != null ? !feeExtraMinute.equals(that.feeExtraMinute) : that.feeExtraMinute != null)
            return false;
        if (feeExtraSmSs != null ? !feeExtraSmSs.equals(that.feeExtraSmSs) : that.feeExtraSmSs != null) return false;
        if (numberOfGigabytes != null ? !numberOfGigabytes.equals(that.numberOfGigabytes) : that.numberOfGigabytes != null)
            return false;
        if (feeForExtraGigabytes != null ? !feeForExtraGigabytes.equals(that.feeForExtraGigabytes) : that.feeForExtraGigabytes != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceId;
        result = 31 * result + (typeOfService != null ? typeOfService.hashCode() : 0);
        result = 31 * result + (numMinutes != null ? numMinutes.hashCode() : 0);
        result = 31 * result + (numSms != null ? numSms.hashCode() : 0);
        result = 31 * result + (feeExtraMinute != null ? feeExtraMinute.hashCode() : 0);
        result = 31 * result + (feeExtraSmSs != null ? feeExtraSmSs.hashCode() : 0);
        result = 31 * result + (numberOfGigabytes != null ? numberOfGigabytes.hashCode() : 0);
        result = 31 * result + (feeForExtraGigabytes != null ? feeForExtraGigabytes.hashCode() : 0);
        return result;
    }
}
