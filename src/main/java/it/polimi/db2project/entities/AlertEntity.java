package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
// ciaoooooooo
@Entity
@Table(name = "alert", schema = "dbtelco", catalog = "")
public class AlertEntity {
    private int alertId;
    private int amount;
    private Timestamp dateAndTime;
    private int userOwner;

    @Id
    @Column(name = "alert_id")
    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    @Basic
    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "dateAndTime")
    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Basic
    @Column(name = "userOwner")
    public int getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(int userOwner) {
        this.userOwner = userOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlertEntity that = (AlertEntity) o;

        if (alertId != that.alertId) return false;
        if (amount != that.amount) return false;
        if (userOwner != that.userOwner) return false;
        if (dateAndTime != null ? !dateAndTime.equals(that.dateAndTime) : that.dateAndTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = alertId;
        result = 31 * result + amount;
        result = 31 * result + (dateAndTime != null ? dateAndTime.hashCode() : 0);
        result = 31 * result + userOwner;
        return result;
    }
}
