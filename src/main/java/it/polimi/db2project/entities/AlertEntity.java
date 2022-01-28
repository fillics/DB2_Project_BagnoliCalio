package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity

@NamedQuery(
        name = "Alert.findByUser",
        query = "SELECT a " +
                "FROM AlertEntity a " +
                "WHERE a.userOwner = : user"
)

@Table(name = "alert", schema = "dbtelco")
public class AlertEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public AlertEntity() {
    }

    public AlertEntity(float amount, Timestamp dateAndTime, UserEntity userOwner) {
        this.amount = amount;
        this.dateAndTime = dateAndTime;
        this.userOwner = userOwner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id", nullable = false)
    private Long alertId;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "dateAndTime", nullable = false)
    private Timestamp dateAndTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REFRESH,
        CascadeType.DETACH}
    )
    @JoinColumn(name = "userOwner")
    private UserEntity userOwner;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public UserEntity getUserOwner() {
        return userOwner;
    }

    public void setUser(UserEntity user) {
        this.userOwner = userOwner;
    }


}
