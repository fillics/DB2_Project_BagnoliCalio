package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "order", schema = "dbtelco", catalog = "")
public class OrderEntity {
    private int orderId;
    private Timestamp dateAndHour;
    private int userOwner;
    private int servicepackage;
    private int totalvalueOrder;
    private String status;

    @Id
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "dateAndHour")
    public Timestamp getDateAndHour() {
        return dateAndHour;
    }

    public void setDateAndHour(Timestamp dateAndHour) {
        this.dateAndHour = dateAndHour;
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
    @Column(name = "servicepackage")
    public int getServicepackage() {
        return servicepackage;
    }

    public void setServicepackage(int servicepackage) {
        this.servicepackage = servicepackage;
    }

    @Basic
    @Column(name = "totalvalueOrder")
    public int getTotalvalueOrder() {
        return totalvalueOrder;
    }

    public void setTotalvalueOrder(int totalvalueOrder) {
        this.totalvalueOrder = totalvalueOrder;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (orderId != that.orderId) return false;
        if (userOwner != that.userOwner) return false;
        if (servicepackage != that.servicepackage) return false;
        if (totalvalueOrder != that.totalvalueOrder) return false;
        if (dateAndHour != null ? !dateAndHour.equals(that.dateAndHour) : that.dateAndHour != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (dateAndHour != null ? dateAndHour.hashCode() : 0);
        result = 31 * result + userOwner;
        result = 31 * result + servicepackage;
        result = 31 * result + totalvalueOrder;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
