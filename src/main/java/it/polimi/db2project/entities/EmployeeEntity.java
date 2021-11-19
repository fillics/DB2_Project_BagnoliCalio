package it.polimi.db2project.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@NamedQueries(
    {
        @NamedQuery(
            name = "Employee.findByUsername",
            query = "SELECT u " +
                "FROM UserEntity u " +
                "WHERE u.username = :username"
        ),
        @NamedQuery(
            name = "Employee.findByEmail",
            query = "SELECT u " +
                "FROM UserEntity u " +
                "WHERE u.email = :email"
        ),
        @NamedQuery(
            name = "Employee.checkCredentials",
            query = "SELECT e FROM EmployeeEntity " +
                "e  WHERE e.username = ?1 and e.password = ?2"
        )
    }
)

@Table(name = "employee", schema = "dbtelco")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employee_id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;


    public EmployeeEntity() {
    }

    public EmployeeEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
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

}
