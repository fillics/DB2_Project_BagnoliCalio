delimiter //
create definer = CURRENT_USER trigger addAlert
    after insert
    on alert
    for each row
begin
    DELETE FROM dbtelco.alerts;
    INSERT INTO dbtelco.alerts
    SELECT a.alert_id
    FROM dbtelco.alert as a;
end //
delimiter ;


delimiter //
create definer = CURRENT_USER trigger addPurchaseToPackage
    after insert
    on `order`
    for each row
begin
    DELETE FROM dbtelco.totalpurchasesperpackage;
    INSERT INTO dbtelco.totalpurchasesperpackage
    SELECT ss.servicePackageToSelect_id, count(*) as purchases
    FROM dbtelco.order as o
             join dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
             join dbtelco.servicepackagetoselect as ss on ss.servicePackageToSelect_id = s.packageSelected
    where o.isValid = true
    group by ss.servicePackageToSelect_id;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger addPurchaseToPackageAndValPeriod
    after insert
    on `order`
    for each row
begin
    DELETE FROM dbtelco.totalpurchasesperpackageandvalperiod;
    INSERT INTO dbtelco.totalpurchasesperpackageandvalperiod
    SELECT s.packageSelected as package_id, s.validityPeriod, count(*)
    FROM dbtelco.order as o
             join dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
    where o.isValid = true
    group by s.packageSelected, s.validityPeriod;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger addSalesOptProduct
    after insert
    on `order`
    for each row
begin
    DELETE FROM dbtelco.salesperoptproduct;
    INSERT INTO dbtelco.salesperoptproduct
    SELECT a.optionalProduct_id, sum(v.monthlyFee * v.numOfMonths) as sales
    FROM dbtelco.order as o
             JOIN dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
             JOIN dbtelco.addedproduct as a on a.servicePackage_id = s.servicePackage_id
             JOIN dbtelco.validityperiod as v on v.validityPeriod_Id = s.validityPeriod
    WHERE o.isValid = true
    GROUP BY a.optionalProduct_id;


end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger addSalesWithOptProduct
    after insert
    on `order`
    for each row
begin
    DELETE FROM dbtelco.salesperpackagewithoptproducts;
    INSERT INTO dbtelco.salesperpackagewithoptproducts
    SELECT ss.servicePackageToSelect_id, SUM(o.totalValueOrder)
    FROM dbtelco.order as o
             join dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
             join dbtelco.servicepackagetoselect as ss on ss.servicePackageToSelect_id = s.packageSelected
    where o.isValid = true
    group by s.packageSelected;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger addSalesWithoutOptProduct
    after insert
    on `order`
    for each row
begin
    DELETE FROM dbtelco.salesperpackagewithoutoptproducts;
    INSERT INTO dbtelco.salesperpackagewithoutoptproducts
    SELECT ss.servicePackageToSelect_id, SUM(s.valuePackage)
    FROM dbtelco.order as o
             join dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
             join dbtelco.servicepackagetoselect as ss on ss.servicePackageToSelect_id = s.packageSelected
    where o.isValid = true
    group by ss.servicePackageToSelect_id;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger addSuspendedOrder
    after insert
    on `order`
    for each row
begin
    DELETE FROM dbtelco.suspendedorders;
    INSERT INTO dbtelco.suspendedorders
    SELECT o.order_id
    FROM dbtelco.order as o
    WHERE o.isValid = 0;
end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger insertOptProduct
    after insert
    on `order`
    for each row
begin

    DELETE FROM dbtelco.totalnumberofoptionalproduct;
    INSERT INTO dbtelco.totalnumberofoptionalproduct
    SELECT ss.servicePackageToSelect_id, count(*)
    FROM dbtelco.order as o
             JOIN dbtelco.servicepackage as s on o.servicePackageAssociated = s.servicePackage_id
             JOIN dbtelco.servicepackagetoselect as ss on s.packageSelected = ss.servicePackageToSelect_id
             JOIN dbtelco.addedproduct as a on a.servicePackage_id = o.servicePackageAssociated
    WHERE o.isValid = true
    GROUP BY ss.servicePackageToSelect_id;

    DELETE FROM dbtelco.avgnumofoptproductssoldperpackage;
    INSERT INTO dbtelco.avgnumofoptproductssoldperpackage
    SELECT t.package_id, IFNULL((o.total / t.totalPurchases), 0.0)
    FROM dbtelco.totalpurchasesperpackage as t
             LEFT OUTER JOIN dbtelco.totalnumberofoptionalproduct as o on t.package_id = o.package_id;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger updateOptProduct
    after update
    on `order`
    for each row
begin

    DELETE FROM dbtelco.totalnumberofoptionalproduct;
    INSERT INTO dbtelco.totalnumberofoptionalproduct
    SELECT ss.servicePackageToSelect_id, count(*)
    FROM dbtelco.order as o
             JOIN dbtelco.servicepackage as s on o.servicePackageAssociated = s.servicePackage_id
             JOIN dbtelco.servicepackagetoselect as ss on s.packageSelected = ss.servicePackageToSelect_id
             JOIN dbtelco.addedproduct as a on a.servicePackage_id = o.servicePackageAssociated
    WHERE o.isValid = true
    GROUP BY ss.servicePackageToSelect_id;

    DELETE FROM dbtelco.avgnumofoptproductssoldperpackage;
    INSERT INTO dbtelco.avgnumofoptproductssoldperpackage
    SELECT t.package_id, IFNULL((o.total / t.totalPurchases), 0.0)
    FROM dbtelco.totalpurchasesperpackage as t
             LEFT OUTER JOIN dbtelco.totalnumberofoptionalproduct as o on t.package_id = o.package_id;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger updatePurchaseToPackage
    after update
    on `order`
    for each row
begin
    DELETE FROM dbtelco.totalpurchasesperpackage;
    INSERT INTO dbtelco.totalpurchasesperpackage
    SELECT ss.servicePackageToSelect_id, count(*) as purchases
    FROM dbtelco.order as o
             join dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
             join dbtelco.servicepackagetoselect as ss on ss.servicePackageToSelect_id = s.packageSelected
    where o.isValid = true
    group by ss.servicePackageToSelect_id;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger updatePurchaseToPackageAndValPeriod
    after update
    on `order`
    for each row
begin
    DELETE FROM dbtelco.totalpurchasesperpackageandvalperiod;
    INSERT INTO dbtelco.totalpurchasesperpackageandvalperiod
    SELECT s.packageSelected as package_id, s.validityPeriod, count(*)
    FROM dbtelco.order as o
             join dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
    where o.isValid = true
    group by s.packageSelected, s.validityPeriod;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger updateSalesOptProduct
    after update
    on `order`
    for each row
begin
    DELETE FROM dbtelco.salesperoptproduct;
    INSERT INTO dbtelco.salesperoptproduct
    SELECT a.optionalProduct_id, sum(v.monthlyFee * v.numOfMonths) as sales
    FROM dbtelco.order as o
             JOIN dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
             JOIN dbtelco.addedproduct as a on a.servicePackage_id = s.servicePackage_id
             JOIN dbtelco.validityperiod as v on v.validityPeriod_Id = s.validityPeriod
    WHERE o.isValid = true
    GROUP BY a.optionalProduct_id;


end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger updateSalesWithOptProduct
    after update
    on `order`
    for each row
begin
    DELETE FROM dbtelco.salesperpackagewithoptproducts;
    INSERT INTO dbtelco.salesperpackagewithoptproducts
    SELECT ss.servicePackageToSelect_id, SUM(o.totalValueOrder)
    FROM dbtelco.order as o
             join dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
             join dbtelco.servicepackagetoselect as ss on ss.servicePackageToSelect_id = s.packageSelected
    where o.isValid = true
    group by s.packageSelected;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger updateSalesWithoutOptProduct
    after update
    on `order`
    for each row
begin
    DELETE FROM dbtelco.salesperpackagewithoutoptproducts;
    INSERT INTO dbtelco.salesperpackagewithoutoptproducts
    SELECT ss.servicePackageToSelect_id, SUM(s.valuePackage)
    FROM dbtelco.order as o
             join dbtelco.servicepackage as s on s.servicePackage_id = o.servicePackageAssociated
             join dbtelco.servicepackagetoselect as ss on ss.servicePackageToSelect_id = s.packageSelected
    where o.isValid = true
    group by ss.servicePackageToSelect_id;

end //
delimiter ;

delimiter //
create definer = CURRENT_USER trigger updateSuspendedOrder
    after update
    on `order`
    for each row
begin
    DELETE FROM dbtelco.suspendedorders;
    INSERT INTO dbtelco.suspendedorders
    SELECT o.order_id
    FROM dbtelco.order as o
    WHERE o.isValid = 0;
end //
delimiter ;


delimiter //
create definer = CURRENT_USER trigger updateInsolventUser
    after update
    on user
    for each row
begin
    DELETE FROM dbtelco.insolventusers;
    INSERT INTO dbtelco.insolventusers
    SELECT u.user_id
    FROM dbtelco.user as u
    where u.isInsolvent = true;
end //
delimiter ;
