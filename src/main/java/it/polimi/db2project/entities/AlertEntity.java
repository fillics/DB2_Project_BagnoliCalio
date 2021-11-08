package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "alert", schema = "dbtelco")
public class AlertEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public AlertEntity() {
    }

    public AlertEntity(float amount, Timestamp dateAndTime, UserEntity user) {
        this.amount = amount;
        this.dateAndTime = dateAndTime;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id", nullable = false)
    private Long alertId;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "dateAndTime", nullable = false)
    private Timestamp dateAndTime;

    @ManyToOne
    @JoinColumn(name = "userOwner")
    private UserEntity user;


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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


}
