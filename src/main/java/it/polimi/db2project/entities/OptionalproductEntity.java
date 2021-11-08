package it.polimi.db2project.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "optionalproduct", schema = "dbtelco", catalog = "")
public class OptionalproductEntity {
    private int optionalProductId;
    private String name;
    private double monthlyFee;

    @Id
    @Column(name = "optionalProduct_Id")
    public int getOptionalProductId() {
        return optionalProductId;
    }

    public void setOptionalProductId(int optionalProductId) {
        this.optionalProductId = optionalProductId;
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
    @Column(name = "monthlyFee")
    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionalproductEntity that = (OptionalproductEntity) o;

        if (optionalProductId != that.optionalProductId) return false;
        if (Double.compare(that.monthlyFee, monthlyFee) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = optionalProductId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(monthlyFee);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
