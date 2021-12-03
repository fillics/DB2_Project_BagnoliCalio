package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "dbtelco")
@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM UserEntity r  WHERE r.username = ?1 and r.password = ?2")


@NamedQuery(
    name = "User.findByUsername",
    query = "SELECT u " +
        "FROM UserEntity u " +
        "WHERE u.username = :username"
)

@NamedQuery(
    name = "User.findByEmail",
    query = "SELECT u " +
        "FROM UserEntity u " +
        "WHERE u.email = :email"
)

@NamedQuery(
    name = "User.findByID",
    query = "SELECT u " +
        "FROM UserEntity u " +
        "WHERE u.user_id = : user_id"
)

public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable=false)
    private Long user_id;

    @Column(name = "username", unique=true, nullable=false)
    private String username;

    @Column(name = "password", nullable=false)
    private String password;

    @Column(name = "email", unique=true, nullable=false)
    private String email;

    @Column(name = "isInsolvent")
    private Boolean isInsolvent;

    @Column(name = "numFailedPayments")
    private int numFailedPayments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="userOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicePackageEntity> servicePackages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="userOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="userOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlertEntity> alerts;


    public UserEntity() {
    }

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        numFailedPayments=0;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ServicePackageEntity> getServicePackages() {
        return servicePackages;
    }

    public void setServicePackages(List<ServicePackageEntity> servicePackages) {
        this.servicePackages = servicePackages;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public List<AlertEntity> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<AlertEntity> alerts) {
        this.alerts = alerts;
    }

    public Boolean getInsolvent() {
        return isInsolvent;
    }

    public void setInsolvent(Boolean insolvent) {
        isInsolvent = insolvent;
    }

    public int getNumFailedPayments() {
        return numFailedPayments;
    }

    public void setNumFailedPayments(int numFailedPayments) {
        this.numFailedPayments = numFailedPayments;
    }
    public void incrementFailedPayments() {
        numFailedPayments++;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                '}';
    }
}