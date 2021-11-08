package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
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
}

