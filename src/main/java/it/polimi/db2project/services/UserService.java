package it.polimi.db2project.services;



import it.polimi.db2project.entities.UserEntity;
import it.polimi.db2project.exception.CredentialsException;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.sql.*;
import java.util.List;


@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;


    public UserService(){
    }

    public UserEntity checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
        List<UserEntity> uList = null;
        try {
            uList = em.createNamedQuery("User.checkCredentials", UserEntity.class).setParameter(1, usrn).setParameter(2, pwd)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentials");
        }
        if (uList.isEmpty())
            return null;
        else if (uList.size() == 1)
            return uList.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }

    public UserEntity checkLogin(String username, String password) throws SQLException,ClassNotFoundException {
        final String DB_URL = "jdbc:mysql://localhost:3306/dbtelco";
        final String USER = "admin";
        final String PASS = "admin";

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        String sql = "SELECT * FROM user WHERE username = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet result = statement.executeQuery();

        UserEntity user = null;

        if (result.next()) {
            user = new UserEntity();
            user.setUsername(result.getString("username"));
        }

        connection.close();

        return user;
    }



    public UserEntity createUser(String username, String email, String password) throws SQLException {
        UserEntity user = new UserEntity(username, email, password);
        try {
            em.persist(user);
            em.flush();
            return user;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }


}
