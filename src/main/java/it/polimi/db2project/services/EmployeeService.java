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



    public List<OptionalProductEntity> findOptProdOfServicePackageToSelect(Long servicePackageToSelect_id){
        return em.createNamedQuery("OptionalProduct.findOptProdOfServicePackageToSelect", OptionalProductEntity.class)
                .setParameter("servicePackageToSelect_id", servicePackageToSelect_id)
                .getResultList();
    }

    public List<ServicePackageEntity> findServicePackageThatContainServicePackageToSelect (Long servicePackageToSelect_id){
        Optional<ServicePackageToSelectEntity> servicePackageEntity = findServicePackageToSelectByID(servicePackageToSelect_id);
        return em.createNamedQuery("ServicePackage.findServicePackageThatContainServicePackageToSelect", ServicePackageEntity.class)
            .setParameter("servicePackageToSelect_id", servicePackageEntity.get())
            .getResultList();
    }



    public List<ServicePackageEntity> findServicePackageThatContainServicePackageToSelectAndValPeriod (
        Long servicePackageToSelect_id,
        Long validityPeriod_id
    ){
        return em.createNamedQuery(
            "ServicePackage.findServicePackageThatContainServicePackageToSelectAndValPeriod",
                ServicePackageEntity.class
        )
            .setParameter("servicePackageToSelect_id", servicePackageToSelect_id)
            .setParameter("validityPeriod_id", validityPeriod_id)
            .getResultList();
    }


    public List<OptionalProductEntity> findOptProdOfServicePackage (Long servicePackage_id){
        return em.createNamedQuery("OptionalProduct.findOptProdOfServicePackage", OptionalProductEntity.class)
            .setParameter("servicePackage_id", servicePackage_id)
            .getResultList();
    }

    public TotalPurchasesPerPackageEntity purchasesPerPackage(Long package_id){
        TotalPurchasesPerPackageEntity totalPurchasesPerPackageEntity = null;
        try{
            totalPurchasesPerPackageEntity = em.createNamedQuery("TotalPurchasesPerPackage.findByServPackage", TotalPurchasesPerPackageEntity.class)
                    .setParameter("package_id", package_id).getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            totalPurchasesPerPackageEntity = new TotalPurchasesPerPackageEntity(package_id, findServicePackageToSelectByID(package_id).get(), 0);
        }

        return totalPurchasesPerPackageEntity;
    }

    public TotalPurchasesPerPackageAndValPeriodEntity purchasesPerPackageAndValPeriod(Long package_id, Long valPeriod_id){
        TotalPurchasesPerPackageAndValPeriodEntity totalPurchasesPerPackageAndValPeriodEntity = null;
        try{
            totalPurchasesPerPackageAndValPeriodEntity = em.createNamedQuery("TotalPurchasesPerPackageAndValPeriod.findByServPackageAndValPeriod", TotalPurchasesPerPackageAndValPeriodEntity.class)
                    .setParameter("package_id", package_id)
                    .setParameter("valPeriod_id", valPeriod_id)
                    .getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            totalPurchasesPerPackageAndValPeriodEntity = new TotalPurchasesPerPackageAndValPeriodEntity(package_id, findServicePackageToSelectByID(package_id).get(), valPeriod_id, findByValPeriodID(valPeriod_id).get(), 0);
        }

        return totalPurchasesPerPackageAndValPeriodEntity;
    }

    public SalesPerPackageWithOptProduct valueOfSalesWithOptProduct(Long package_id){
        SalesPerPackageWithOptProduct salesPerPackageWithOptProduct = null;
        try{
            salesPerPackageWithOptProduct = em.createNamedQuery("SalesPerPackageWithOptProduct.findByServPackage", SalesPerPackageWithOptProduct.class)
                    .setParameter("package_id", package_id)
                    .getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            salesPerPackageWithOptProduct = new SalesPerPackageWithOptProduct(package_id, findServicePackageToSelectByID(package_id).get(), 0);
        }

        return salesPerPackageWithOptProduct;
    }

    public SalesPerPackageWithoutOptProduct valueOfSalesWithoutOptProduct(Long package_id){
        SalesPerPackageWithoutOptProduct salesPerPackageWithoutOptProduct = null;
        try{
            salesPerPackageWithoutOptProduct = em.createNamedQuery("SalesPerPackageWithoutOptProduct.findByServPackage", SalesPerPackageWithoutOptProduct.class)
                    .setParameter("package_id", package_id)
                    .getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            salesPerPackageWithoutOptProduct = new SalesPerPackageWithoutOptProduct(package_id, findServicePackageToSelectByID(package_id).get(), 0);
        }

        return salesPerPackageWithoutOptProduct;
    }

    public AvgNumOfOptProductsSoldPerPackage avgNumOfOptProductsSoldPerPackage(Long package_id){
        AvgNumOfOptProductsSoldPerPackage avgNumOfOptProductsSoldPerPackage;
        try{
            avgNumOfOptProductsSoldPerPackage = em.createNamedQuery("AvgNumOfOptProductsSoldPerPackage.findByServPackage", AvgNumOfOptProductsSoldPerPackage.class)
                    .setParameter("package_id", package_id)
                    .getResultList().stream().findFirst().get();
        }catch (NoSuchElementException exception){
            avgNumOfOptProductsSoldPerPackage = new AvgNumOfOptProductsSoldPerPackage(package_id, findServicePackageToSelectByID(package_id).get(), -1);
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


    public SalesPerOptProduct findMax(){
        SalesPerOptProduct salesPerOptProduct = null;

        try{
            salesPerOptProduct = em.createNamedQuery("SalesPerOptProduct.findMax", SalesPerOptProduct.class).getSingleResult();
        }catch(NoResultException e){}

        return salesPerOptProduct;
    }
}
