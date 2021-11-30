package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@NamedQuery(
        name = "Order.findByID",
        query = "SELECT o " +
                "FROM OrderEntity o " +
                "WHERE o.order_id = :order_id"
)
@NamedQuery(
        name = "Order.findRejectedOrdersOfUser",
        query = "SELECT o " +
                "FROM OrderEntity o " +
                "WHERE o.userOwner = :user AND " +
                "o.isValid=false"
)


@Table(name = "order", schema = "dbtelco")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public OrderEntity(
            Timestamp dateAndHour,
            float totalValueOrder,
            UserEntity userOwner,
            ServicePackageEntity servicePackageAssociated
    ) {
        this.dateAndHour = dateAndHour;
        this.totalValueOrder = totalValueOrder;
        this.userOwner = userOwner;
        this.servicePackageAssociated = servicePackageAssociated;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long order_id;

    @Column(name = "dateAndHour", nullable=false)
    private Timestamp dateAndHour;

    @Column(name = "totalValueOrder", nullable=false)
    private float totalValueOrder;

    @Column(name = "isValid")
    private boolean isValid;

    //relationship definition part
    @ManyToOne (cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "userOwner")
    private UserEntity userOwner;

    @OneToOne
    @JoinColumn(name = "servicePackageAssociated")
    private ServicePackageEntity servicePackageAssociated;

    public OrderEntity(){
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long user_id) {
        this.order_id = order_id;
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

    public UserEntity getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(UserEntity userOwner) {
        this.userOwner = userOwner;
    }

    public ServicePackageEntity getServicePackage() {
        return servicePackageAssociated;
    }

    public void setServicePackageAssociated(ServicePackageEntity servicePackage) {
        this.servicePackageAssociated = servicePackageAssociated;
    }
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
