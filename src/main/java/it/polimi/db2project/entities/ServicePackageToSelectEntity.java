package it.polimi.db2project.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "servicepackagetoselect", schema = "dbtelco")
public class ServicePackageToSelectEntity {
    private int servicePackageToSelectId;
    private String name;
    private int services;
    private Integer optionalProduct;
    private int validityPeriod;

    @Id
    @Column(name = "servicePackageToSelect_id")
    public int getServicePackageToSelectId() {
        return servicePackageToSelectId;
    }

    public void setServicePackageToSelectId(int servicePackageToSelectId) {
        this.servicePackageToSelectId = servicePackageToSelectId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "services")
    public int getServices() {
        return services;
    }

    public void setServices(int services) {
        this.services = services;
    }

    @Basic
    @Column(name = "optionalProduct")
    public Integer getOptionalProduct() {
        return optionalProduct;
    }

    public void setOptionalProduct(Integer optionalProduct) {
        this.optionalProduct = optionalProduct;
    }

    @Basic
    @Column(name = "validityPeriod")
    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServicePackageToSelectEntity that = (ServicePackageToSelectEntity) o;

        if (servicePackageToSelectId != that.servicePackageToSelectId) return false;
        if (services != that.services) return false;
        if (validityPeriod != that.validityPeriod) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (optionalProduct != null ? !optionalProduct.equals(that.optionalProduct) : that.optionalProduct != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = servicePackageToSelectId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + services;
        result = 31 * result + (optionalProduct != null ? optionalProduct.hashCode() : 0);
        result = 31 * result + validityPeriod;
        return result;
    }
}
