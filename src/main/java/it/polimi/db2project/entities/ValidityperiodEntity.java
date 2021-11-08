package it.polimi.db2project.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "validityperiod", schema = "dbtelco", catalog = "")
public class ValidityperiodEntity {
    private int validityPeriodId;
    private int numOfMonths;
    private int monthlyFee;

    @Id
    @Column(name = "validityPeriod_Id")
    public int getValidityPeriodId() {
        return validityPeriodId;
    }

    public void setValidityPeriodId(int validityPeriodId) {
        this.validityPeriodId = validityPeriodId;
    }

    @Basic
    @Column(name = "numOfMonths")
    public int getNumOfMonths() {
        return numOfMonths;
    }

    public void setNumOfMonths(int numOfMonths) {
        this.numOfMonths = numOfMonths;
    }

    @Basic
    @Column(name = "monthlyFee")
    public int getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(int monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidityperiodEntity that = (ValidityperiodEntity) o;

        if (validityPeriodId != that.validityPeriodId) return false;
        if (numOfMonths != that.numOfMonths) return false;
        if (monthlyFee != that.monthlyFee) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = validityPeriodId;
        result = 31 * result + numOfMonths;
        result = 31 * result + monthlyFee;
        return result;
    }
}
