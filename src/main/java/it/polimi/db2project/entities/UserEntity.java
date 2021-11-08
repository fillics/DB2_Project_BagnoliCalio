package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "dbtelco")
@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM UserEntity r  WHERE r.username = ?1 and r.password = ?2")

public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "username", unique=true, nullable=false)
    private String username;

    @Column(name = "password", nullable=false)
    private String password;

    @Column(name = "email", unique=true, nullable=false)
    private String email;

    //relationship definition part

    @OneToMany(mappedBy="userOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ServicePackageEntity> servicePackages;

    /**
     * Orders are few can be loaded eagerly.
     * Removing the user removes the orders too.
     */
    @OneToMany(mappedBy="userOwner", fetch=FetchType.EAGER,	cascade=CascadeType.REMOVE)
    private List<OrderEntity> orders;


    /**
     * Alerts are few can be loaded eagerly.
     * Removing the user removes the alerts too.
     */
    @OneToMany(mappedBy="userOwner", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    private List<AlertEntity> alerts;


    public UserEntity() {
    }

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
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
}
