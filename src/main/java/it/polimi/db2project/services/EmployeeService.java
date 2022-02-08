package it.polimi.db2project.services;

import it.polimi.db2project.entities.*;
import it.polimi.db2project.entities.employeeQueries.*;
import it.polimi.db2project.exception.CredentialsException;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Stateless
public class EmployeeService {
    @PersistenceContext
    private EntityManager em;

    public Optional<ServicePackageToSelectEntity> findByNameServicePackage(String namePackage) {
        return em.createNamedQuery("ServicePackageToSelect.findByName", ServicePackageToSelectEntity.class)
                .setParameter("name", namePackage)
                .getResultStream().findFirst();
    }

    public Optional<OptionalProductEntity> findByNameOptProd(String nameOptProd) {
        return em.createNamedQuery("OptionalProduct.findByName", OptionalProductEntity.class)
                .setParameter("name", nameOptProd)
                .getResultStream().findFirst();
    }



    public Optional<EmployeeEntity> findByUsername(String usrn) {
        return em.createNamedQuery("Employee.findByUsername", EmployeeEntity.class)
            .setParameter("username", usrn)
            .getResultStream().findFirst();
    }


    public Optional<EmployeeEntity> findByEmail(String email) {
        return em.createNamedQuery("Employee.findByEmail", EmployeeEntity.class)
            .setParameter("email", email)
            .getResultStream().findFirst();
    }


    public EmployeeEntity checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {

        List<EmployeeEntity> eList;
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


    public ServicePackageToSelectEntity createServicePackageToSelect(
        String name,
        ArrayList<ServiceEntity> services,
        List<OptionalProductEntity> optionalProducts,
        List<ValidityPeriodEntity> validityPeriods
    ) throws SQLException {
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

    public List<ValidityPeriodEntity> findValPeriodsOfServicePackage(Long servicePackageToSelect_id){
        return em.createNamedQuery("ValidityPeriod.findValPeriodsByServPackage", ValidityPeriodEntity.class)
                .setParameter("servicePackageToSelect_id", servicePackageToSelect_id)
                .getResultList();
    }

    public Optional<ServicePackageToSelectEntity> findServicePackageToSelectByID(Long servicePackageToSelect_id) {
        return em.createNamedQuery("ServicePackageToSelect.findByID", ServicePackageToSelectEntity.class)
                .setParameter("servicePackageToSelect_id", servicePackageToSelect_id)
                .getResultStream().findFirst();
    }


    public TotalPurchasesPerPackageEntity purchasesPerPackage(Long package_id){
        TotalPurchasesPerPackageEntity totalPurchasesPerPackageEntity;
        try{
            totalPurchasesPerPackageEntity = em.createNamedQuery("TotalPurchasesPerPackage.findByServPackage", TotalPurchasesPerPackageEntity.class)
                    .setParameter("package_id", package_id).getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            totalPurchasesPerPackageEntity = new TotalPurchasesPerPackageEntity(package_id, findServicePackageToSelectByID(package_id).get());
        }

        return totalPurchasesPerPackageEntity;
    }

    public TotalPurchasesPerPackageAndValPeriodEntity purchasesPerPackageAndValPeriod(Long package_id, Long valPeriod_id){
        TotalPurchasesPerPackageAndValPeriodEntity totalPurchasesPerPackageAndValPeriodEntity;
        try{
            totalPurchasesPerPackageAndValPeriodEntity = em.createNamedQuery("TotalPurchasesPerPackageAndValPeriod.findByServPackageAndValPeriod", TotalPurchasesPerPackageAndValPeriodEntity.class)
                    .setParameter("package_id", package_id)
                    .setParameter("valPeriod_id", valPeriod_id)
                    .getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            totalPurchasesPerPackageAndValPeriodEntity = new TotalPurchasesPerPackageAndValPeriodEntity(package_id, findServicePackageToSelectByID(package_id).get(), valPeriod_id, findByValPeriodID(valPeriod_id).get());
        }

        return totalPurchasesPerPackageAndValPeriodEntity;
    }

    public SalesPackage valuesOfSales(Long package_id){
        SalesPackage salesPackage;
        try{
            salesPackage = em.createNamedQuery("SalesPackage.findByServPackage", SalesPackage.class)
                    .setParameter("package_id", package_id)
                    .getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            salesPackage = new SalesPackage(package_id, findServicePackageToSelectByID(package_id).get());
        }
        return salesPackage;
    }


    public AvgNumOfOptProductsSoldPerPackage avgNumOfOptProductsSoldPerPackage(Long package_id){
        AvgNumOfOptProductsSoldPerPackage avgNumOfOptProductsSoldPerPackage;
        try{
            avgNumOfOptProductsSoldPerPackage = em.createNamedQuery("AvgNumOfOptProductsSoldPerPackage.findByServPackage", AvgNumOfOptProductsSoldPerPackage.class)
                    .setParameter("package_id", package_id)
                    .getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            avgNumOfOptProductsSoldPerPackage = new AvgNumOfOptProductsSoldPerPackage(package_id, findServicePackageToSelectByID(package_id).get());
        }
        return avgNumOfOptProductsSoldPerPackage;
    }


    public List<InsolventUsers> findAllInsolventUsers(){
        return em.createNamedQuery("InsolventUsers.findAll", InsolventUsers.class).getResultList();
    }

    public List<SuspendedOrders> findAllSuspendedOrders(){
        return em.createNamedQuery("SuspendedOrders.findAll", SuspendedOrders.class).getResultList();
    }

    public List<Alerts> findAllAlerts(){
        return em.createNamedQuery("Alerts.findAll", Alerts.class).getResultList();
    }

    public BestOptionalProduct findBest(){
        BestOptionalProduct bestOptionalProduct = null;
        try{
            if (em.createNamedQuery("BestOptionalProduct.findMax", BestOptionalProduct.class).getResultList().size() != 0)
                bestOptionalProduct = em.createNamedQuery("BestOptionalProduct.findMax", BestOptionalProduct.class).getSingleResult();
        }catch(NoResultException ignored){}

        return bestOptionalProduct;
    }
}
