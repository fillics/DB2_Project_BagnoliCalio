package it.polimi.db2project.entities.employeeQueries;

import it.polimi.db2project.entities.AlertEntity;
import it.polimi.db2project.entities.UserEntity;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity

@NamedQuery(
        name = "Alerts.findAll",
        query = "SELECT n " +
                "FROM Alerts n "
)

@Table(name = "alerts", schema = "dbtelco")
public class Alerts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "alert_id", nullable = false)
    private Long alert_id;

    @OneToOne
    @JoinColumn(name = "alert_id")
    private AlertEntity alert;


    public Alerts() {
    }

    public Alerts(AlertEntity alert) {
        this.alert = alert;
    }

    public Long getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(Long alert_id) {
        this.alert_id = alert_id;
    }

    public AlertEntity getAlert() {
        return alert;
    }

    public void setAlert(AlertEntity alert) {
        this.alert = alert;
    }
}