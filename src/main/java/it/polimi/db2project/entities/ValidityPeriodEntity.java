package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;


@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "ValidityPeriod.findByID",
                        query = "SELECT v " +
                                "FROM ValidityPeriodEntity v " +
                                "WHERE v.validityPeriod_id = :validityPeriod_id"
                ),
                @NamedQuery(
                        name = "ValidityPeriod.findAll",
                        query = "SELECT v " +
                                "FROM ValidityPeriodEntity v "
                ),

                @NamedQuery(
                        name = "ValidityPeriod.findValPeriodsByServPackage",
                        query = "SELECT v " +
                                "FROM ValidityPeriodEntity v " +
                                "JOIN v.servicePackagesToSelect s " +
                                "WHERE s.servicePackageToSelect_id = :servicePackageToSelect_id "
                )
        }
)
@Table(name = "validityperiod", schema = "dbtelco")
public class ValidityPeriodEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "validityPeriod_id", nullable = false)
    private Long validityPeriod_id;

    @Column(name = "numOfMonths", nullable=false)
    private int numOfMonths;

    @Column(name = "monthlyFee")
    private float monthlyFee;

    //relationship definition part
    @ManyToMany(mappedBy = "validityPeriods", fetch = FetchType.EAGER)
    private List<ServicePackageToSelectEntity> servicePackagesToSelect;


    @OneToMany(mappedBy="validityPeriod", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    private List<ServicePackageEntity> servicePackages;

    public Long getValidityPeriod_id() {
        return validityPeriod_id;
    }

    public void setValidityPeriod_id(Long validityPeriod_id) {
        this.validityPeriod_id = validityPeriod_id;
    }

    public int getNumOfMonths() {
        return numOfMonths;
    }

    public void setNumOfMonths(int numOfMonths) {
        this.numOfMonths = numOfMonths;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public String toString() {
        return numOfMonths +" months at " + monthlyFee +"€/month";
    }
}

