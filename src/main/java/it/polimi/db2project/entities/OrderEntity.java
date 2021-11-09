package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "order", schema = "dbtelco")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderEntity(
            Long user_id,
            Timestamp dateAndHour,
            float totalValueOrder,
            String status,
            UserEntity userOwner,
            ServicePackageEntity servicePackage
    ) {
        this.user_id = user_id;
        this.dateAndHour = dateAndHour;
        this.totalValueOrder = totalValueOrder;
        this.status = status;
        this.userOwner = userOwner;
        this.servicePackage = servicePackage;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long user_id;

    @Column(name = "dateAndHour", nullable=false)
    private Timestamp dateAndHour;

    @Column(name = "totalValueOrder", nullable=false)
    private float totalValueOrder;

    @Column(name = "status", nullable=false)
    private String status;

    //relationship definition part
    @ManyToOne (cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "userOwner")
    private UserEntity userOwner;

    @OneToOne(mappedBy = "servicePackageAssociated", cascade = CascadeType.ALL, orphanRemoval = true)
    private ServicePackageEntity servicePackage;

    public OrderEntity(){
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Timestamp getDateAndHour() {
        return dateAndHour;
    }

    public void setDateAndHour(Timestamp dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    public float getTotalValueOrder() {
        return totalValueOrder;
    }

    public void setTotalValueOrder(float totalValueOrder) {
        this.totalValueOrder = totalValueOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserEntity getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(UserEntity userOwner) {
        this.userOwner = userOwner;
    }

    public ServicePackageEntity getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackageEntity servicePackage) {
        this.servicePackage = servicePackage;
    }
}
