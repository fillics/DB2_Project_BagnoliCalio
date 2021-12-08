package it.polimi.db2project.entities.employeeQueries;


import it.polimi.db2project.entities.ServicePackageToSelectEntity;
import it.polimi.db2project.entities.UserEntity;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity

@NamedQuery(
        name = "InsolventUsers.findAll",
        query = "SELECT n " +
                "FROM InsolventUsers n "
)

@Table(name = "insolventusers", schema = "dbtelco")
public class InsolventUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public InsolventUsers() {
    }

    public InsolventUsers(UserEntity user) {
        this.user = user;
    }


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}