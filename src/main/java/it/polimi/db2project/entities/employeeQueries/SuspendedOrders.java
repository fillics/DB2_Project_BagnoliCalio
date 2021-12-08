package it.polimi.db2project.entities.employeeQueries;

import it.polimi.db2project.entities.OrderEntity;
import it.polimi.db2project.entities.UserEntity;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity

@NamedQuery(
        name = "SuspendedOrders.findAll",
        query = "SELECT n " +
                "FROM SuspendedOrders n "
)

@Table(name = "suspendedorders", schema = "dbtelco")
public class SuspendedOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "order_id", nullable = false)
    private Long order_id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;


    public SuspendedOrders() {
    }

    public SuspendedOrders(OrderEntity order) {
        this.order = order;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}