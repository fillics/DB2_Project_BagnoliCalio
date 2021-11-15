package it.polimi.db2project.services;

import it.polimi.db2project.entities.*;
import it.polimi.db2project.exception.CredentialsException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public ServicePackageEntity createServicePackage(
        Date startDate,
        Date endDate,
        float totalValuePackage,
        ServicePackageToSelectEntity servicePackageToSelect,
        ValidityPeriodEntity validityPeriod,
        List<OptionalProductEntity> optionalProducts,
        UserEntity userOwner
    ) throws SQLException {
        ServicePackageEntity servicePackage = new ServicePackageEntity(
            startDate,
            endDate,
            totalValuePackage,
            servicePackageToSelect,
            validityPeriod,
            optionalProducts,
            userOwner);
        try {
            em.persist(servicePackage);
            em.flush();
            return servicePackage;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }

    public Optional<ServicePackageToSelectEntity> findByServicePackageToSelectID(Long servicePackageToSelect_id) {
        return em.createNamedQuery("ServicePackageToSelect.findByID", ServicePackageToSelectEntity.class)
            .setParameter("servicePackageToSelect_id", servicePackageToSelect_id)
            .getResultStream().findFirst();
    }

    public Optional<OptionalProductEntity> findByOptProdID(Long optionalProduct_id) {
        return em.createNamedQuery("OptionalProduct.findByID", OptionalProductEntity.class)
            .setParameter("optionalProduct_id", optionalProduct_id)
            .getResultStream().findFirst();
    }

    public Optional<ValidityPeriodEntity> findByValPeriodID(Long validityPeriod_id) {
        return em.createNamedQuery("ValidityPeriod.findByID", ValidityPeriodEntity.class)
            .setParameter("validityPeriod_id", validityPeriod_id)
            .getResultStream().findFirst();
    }


}
