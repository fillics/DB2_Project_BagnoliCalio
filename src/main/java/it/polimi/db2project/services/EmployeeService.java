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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Stateless
public class EmployeeService {
    @PersistenceContext
    private EntityManager em;

    public EmployeeService() {
    }


//    public EmployeeEntity findByUsername(String usrn) {
//        List<EmployeeEntity> listForUsrn = null;

        public Optional<EmployeeEntity> findByUsername(String usrn) {
            return em.createNamedQuery("Employee.findByUsername", EmployeeEntity.class)
                .setParameter("username", usrn)
                .getResultStream().findFirst();
        }
//        listForUsrn = em.createNamedQuery("Employee.findByUsername", EmployeeEntity.class)
//            .getResultList();
//
//        if (listForUsrn.isEmpty())
//            return null;
//        else
//            return listForUsrn.get(0);
//
//    }

//    public EmployeeEntity findByEmail(String email) {
//        List<EmployeeEntity> listForEmail = null;
//
//        listForEmail = em.createNamedQuery("Employee.findByEmail", EmployeeEntity.class)
//            .getResultList();
//
//        if (listForEmail.isEmpty())
//            return null;
//        else
//            return listForEmail.get(0);
//    }

    public Optional<EmployeeEntity> findByEmail(String email) {
        return em.createNamedQuery("Employee.findByEmail", EmployeeEntity.class)
            .setParameter("email", email)
            .getResultStream().findFirst();
    }


    public EmployeeEntity checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {

        List<EmployeeEntity> eList = null;
        try {
            eList = em.createNamedQuery("Employee.checkCredentials", EmployeeEntity.class).setParameter(1, usrn).setParameter(2, pwd)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentials");
        }
        if (eList.isEmpty())
            return null;
        else if (eList.size() == 1)
            return eList.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }

    public EmployeeEntity createEmployee(String username, String email, String password) throws SQLException {
        EmployeeEntity employee = new EmployeeEntity(username, email, password);
        try {
            em.persist(employee);
            em.flush();
            return employee;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }


    public OptionalProductEntity createOptionalProduct(String name, float monthlyFee) throws SQLException {
        OptionalProductEntity optionalProduct = new OptionalProductEntity(name, monthlyFee);
        try {
            em.persist(optionalProduct);
            em.flush();
            return optionalProduct;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }


    public ServicePackageToSelectEntity createServicePackageToSelect(String name, ArrayList<ServiceEntity> services, List<OptionalProductEntity> optionalProducts, List<ValidityPeriodEntity> validityPeriods) throws SQLException {
        ServicePackageToSelectEntity servicePackageToSelect = new ServicePackageToSelectEntity(name, services, optionalProducts, validityPeriods);
        try {
            em.persist(servicePackageToSelect);
            em.flush();
            return servicePackageToSelect;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }


    public List<OptionalProductEntity> findAllOptProd(){
        return em.createNamedQuery("OptionalProduct.findAll", OptionalProductEntity.class).getResultList();
    }

    public List<ServiceEntity> findAllServices(){
        return em.createNamedQuery("Service.findAll", ServiceEntity.class).getResultList();
    }

    public List<ValidityPeriodEntity> findAllValidityPeriods(){
        return em.createNamedQuery("ValidityPeriod.findAll", ValidityPeriodEntity.class).getResultList();
    }

    public List<ServicePackageToSelectEntity> findAllServicePackageToSelect(){
        return em.createNamedQuery("ServicePackageToSelect.findAll", ServicePackageToSelectEntity.class).getResultList();
    }


    public Optional<ServiceEntity> findByServiceID(Long service_id) {
        return em.createNamedQuery("Service.findByID", ServiceEntity.class)
                .setParameter("service_id", service_id)
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
