package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "servicepackage", schema = "dbtelco")
public class ServicePackageEntity {
    private int servicePackageId;
    private int packageSelected;
    private int validityPeriod;
    private int optionalProduct;
    private int userOwner;
    private Date startDate;
    private Date endDate;
    private int totalValuePackage;

    @Id
    @Column(name = "servicePackage_id")
    public int getServicePackageId() {
        return servicePackageId;
    }

    public void setServicePackageId(int servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    @Basic
    @Column(name = "packageSelected")
    public int getPackageSelected() {
        return packageSelected;
    }

    public void setPackageSelected(int packageSelected) {
        this.packageSelected = packageSelected;
    }

    @Basic
    @Column(name = "validityPeriod")
    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    @Basic
    @Column(name = "optionalProduct")
    public int getOptionalProduct() {
        return optionalProduct;
    }

    public void setOptionalProduct(int optionalProduct) {
        this.optionalProduct = optionalProduct;
    }

    @Basic
    @Column(name = "userOwner")
    public int getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(int userOwner) {
        this.userOwner = userOwner;
    }

    @Basic
    @Column(name = "startDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "endDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "totalValuePackage")
    public int getTotalValuePackage() {
        return totalValuePackage;
    }

    public void setTotalValuePackage(int totalValuePackage) {
        this.totalValuePackage = totalValuePackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServicePackageEntity that = (ServicePackageEntity) o;

        if (servicePackageId != that.servicePackageId) return false;
        if (packageSelected != that.packageSelected) return false;
        if (validityPeriod != that.validityPeriod) return false;
        if (optionalProduct != that.optionalProduct) return false;
        if (userOwner != that.userOwner) return false;
        if (totalValuePackage != that.totalValuePackage) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = servicePackageId;
        result = 31 * result + packageSelected;
        result = 31 * result + validityPeriod;
        result = 31 * result + optionalProduct;
        result = 31 * result + userOwner;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + totalValuePackage;
        return result;
    }
}
