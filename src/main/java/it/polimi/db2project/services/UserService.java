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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    public UserService(){
    }

    boolean theUserWantsToBuyAndHeIsNotLogged = false;

    public boolean isTheUserWantsToBuyAndHeIsNotLogged() {
        return theUserWantsToBuyAndHeIsNotLogged;
    }

    public void setTheUserWantsToBuyAndHeIsNotLogged(boolean theUserWantsToBuyAndHeIsNotLogged) {
        this.theUserWantsToBuyAndHeIsNotLogged = theUserWantsToBuyAndHeIsNotLogged;
    }


    // function to call the external service
    boolean itIsTrue = true;
    public Boolean callExternalService(){
        if (itIsTrue){
            itIsTrue = false;
            return true;
        }
        else{
            itIsTrue = true;
            return false;
        }
    }
    public boolean randomPayment(){
        Random rd = new Random();
        return rd.nextBoolean();
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

    public Optional<UserEntity> findByUsername(String usrn) {
        return em.createNamedQuery("User.findByUsername", UserEntity.class)
            .setParameter("username", usrn)
            .getResultStream().findFirst();
    }

    public Optional<UserEntity> findByEmail(String email) {
        return em.createNamedQuery("User.findByEmail", UserEntity.class)
            .setParameter("email", email)
            .getResultStream().findFirst();
    }

    /*public ServicePackageEntity createServicePackage(java.sql.Date startDate, java.sql.Date endDate, float totalValuePackage,
                                                     ServicePackageToSelectEntity servicePackageToSelect,
                                                     ValidityPeriodEntity validityPeriod,
                                                     ArrayList<OptionalProductEntity> optionalProducts,
                                                     UserEntity userOwner) throws SQLException {
        ServicePackageEntity servicePackage = new ServicePackageEntity(servicePackageToSelect, validityPeriod,
                startDate, endDate, totalValuePackage, optionalProducts);
        servicePackage.setUserOwner(userOwner);

        try {
            em.persist(servicePackage);
            em.flush();
            return servicePackage;
        } catch (ConstraintViolationException e) {
            return null;
        }
    }*/

    public void createServicePackage(ServicePackageEntity servicePackage, UserEntity userOwner) throws SQLException {

        servicePackage.setUserOwner(userOwner);

        try {
            em.persist(servicePackage);
            em.flush();
        } catch (ConstraintViolationException ignored) {
        }
    }

    public List<ServicePackageToSelectEntity> findAllServicePackageToSelect(){
        return em.createNamedQuery("ServicePackageToSelect.findAll", ServicePackageToSelectEntity.class).getResultList();
    }

    public List<ServicePackageEntity> findServPackageUser(Long user_id){
        Optional<UserEntity> userEntity = findByUserID(user_id);
        return em.createNamedQuery("ServicePackage.findServicePackageOfUser", ServicePackageEntity.class)
            .setParameter("user", userEntity.get())
            .getResultList();
    }

    public Optional<UserEntity> findByUserID(Long user_id){
        return em.createNamedQuery("User.findByID", UserEntity.class)
            .setParameter("user_id", user_id)
            .getResultStream().findFirst();
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

    public Optional<OrderEntity> findOrderByID(Long order_id) {
        return em.createNamedQuery("Order.findByID", OrderEntity.class)
            .setParameter("order_id", order_id)
            .getResultStream().findFirst();
    }


    public List<ValidityPeriodEntity> findValPeriodsOfService(Long servicePackageToSelect_id){
        return em.createNamedQuery("ValidityPeriod.findValPeriodsByServPackage", ValidityPeriodEntity.class)
            .setParameter("servicePackageToSelect_id", servicePackageToSelect_id)
            .getResultList();
    }

    public List<OptionalProductEntity> findOptProdOfService(Long servicePackageToSelect_id){
        return em.createNamedQuery("OptionalProduct.findOptProdOfServicePackageToSelect", OptionalProductEntity.class)
            .setParameter("servicePackageToSelect_id", servicePackageToSelect_id)
            .getResultList();
    }

    public List<ServiceEntity> findAllService() {
        return em.createNamedQuery("Service.findAll", ServiceEntity.class).getResultList();
    }

    // TODO: 16/11/2021 metodo in comune con employee service
    public List<ValidityPeriodEntity> findAllValidityPeriods(){
        return em.createNamedQuery("ValidityPeriod.findAll", ValidityPeriodEntity.class).getResultList();
    }

    public OrderEntity createOrder(Timestamp now, UserEntity userOwner, ServicePackageEntity servicePackage){
        OrderEntity order = new OrderEntity(now, servicePackage.getTotalValuePackage(), userOwner, servicePackage);
        try {
            em.persist(order);
            em.flush();
            return order;
        } catch (ConstraintViolationException ignored) {
            return null;
        }
    }

    public void updateOrder (OrderEntity order, boolean isValid){
        OrderEntity orderEntity = em.find(OrderEntity.class, order.getOrder_id());
        orderEntity.setValid(isValid);
        em.merge(orderEntity);
    }

    public void setUserInsolvent(UserEntity user, boolean isInsolvent){
        UserEntity userEntity = em.find(UserEntity.class, user.getUser_id());
        userEntity.setInsolvent(isInsolvent);
        em.merge(userEntity);
    }
}
