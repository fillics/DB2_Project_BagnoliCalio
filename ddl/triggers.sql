DROP TABLE IF EXISTS totalpurchasesperpackage;
DROP TRIGGER IF EXISTS addPurchaseToPackage;
DROP TRIGGER IF EXISTS updatePurchaseToPackage;
DROP TRIGGER IF EXISTS createServicePackageToSelect1;

DROP TABLE IF EXISTS totalpurchasesperpackageandvalperiod;
DROP TRIGGER IF EXISTS createServPackageAndValPeriod;
DROP TRIGGER IF EXISTS addPurchaseToPackageAndValPeriod;
DROP TRIGGER IF EXISTS updatePurchaseToPackageAndValPeriod;

DROP TABLE IF EXISTS salespackage;
DROP TRIGGER IF EXISTS addSales;
DROP TRIGGER IF EXISTS updateSales;
DROP TRIGGER IF EXISTS createServicePackageToSelect2;

DROP TABLE IF EXISTS totalnumberofoptionalproduct;
DROP TABLE IF EXISTS avgnumofoptproductssoldperpackage;
DROP TRIGGER IF EXISTS createServicePackageToSelect3;
DROP TRIGGER IF EXISTS addOptProduct;
DROP TRIGGER IF EXISTS updateOptProduct;

DROP TABLE IF EXISTS suspendedorders;
DROP TABLE IF EXISTS alerts;
DROP TABLE IF EXISTS insolventusers;
DROP TRIGGER IF EXISTS addAlert;
DROP TRIGGER IF EXISTS updateInsolventUser;
DROP TRIGGER IF EXISTS addSuspendedOrder;
DROP TRIGGER IF EXISTS updateSuspendedOrder;

DROP TABLE IF EXISTS optProductsOrder;
DROP TABLE IF EXISTS bestoptionalproduct;
DROP TABLE IF EXISTS salesperoptproduct;
DROP TRIGGER IF EXISTS insertOptProduct;
DROP TRIGGER IF EXISTS addSalesOptProduct;
DROP TRIGGER IF EXISTS updateSalesOptProduct;


delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER addPurchaseToPackage
    AFTER INSERT ON `order` FOR EACH ROW
BEGIN
    IF NEW.isValid = true THEN
UPDATE totalpurchasesperpackage SET totalPurchases = totalPurchases + 1
WHERE package_id IN (SELECT s.packageSelected
                     FROM servicepackage s
                     WHERE s.servicePackage_id = NEW.servicePackageAssociated);
END IF;
end //
delimiter ;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER updatePurchaseToPackage
    AFTER UPDATE ON `order` FOR EACH ROW BEGIN
                                   IF NEW.isValid = true THEN
UPDATE dbtelco.totalpurchasesperpackage SET totalPurchases = totalPurchases + 1
WHERE package_id IN (SELECT s.packageSelected
                     FROM dbtelco.servicepackage s
                     WHERE s.servicePackage_id = NEW.servicePackageAssociated);
END IF;
end //
delimiter ;


delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER createServicePackageToSelect1
    AFTER INSERT ON servicepackagetoselect FOR EACH ROW
BEGIN
INSERT INTO dbtelco.totalpurchasesperpackage(package_id)
VALUES(NEW.servicePackageToSelect_id);
end //
delimiter ;


create table totalpurchasesperpackage
(
    package_id int not null
        primary key,
    totalPurchases int default 0 not null,
    constraint numberOfTotalPurchasesPerPackage_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);



delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER createServPackageAndValPeriod
    AFTER INSERT ON proposal FOR EACH ROW BEGIN
    INSERT INTO totalpurchasesperpackageandvalperiod(package_id, valPeriod_id)
    VALUES(NEW.servicePackageToSelect_id, NEW.validityPeriod_id);

end //
delimiter ;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER addPurchaseToPackageAndValPeriod
    AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF NEW.isValid = true THEN
UPDATE dbtelco.totalpurchasesperpackageandvalperiod SET totalPurchases = totalPurchases + 1
WHERE (package_id, valPeriod_id) IN (SELECT s.packageSelected, s.validityPeriod
                                     FROM dbtelco.servicepackage s
                                     WHERE s.servicePackage_id = NEW.servicePackageAssociated);

END IF;
end //
delimiter ;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER updatePurchaseToPackageAndValPeriod
    AFTER UPDATE ON `order` FOR EACH ROW BEGIN
                                   IF NEW.isValid = true THEN
UPDATE dbtelco.totalpurchasesperpackageandvalperiod SET totalPurchases = totalPurchases + 1
WHERE (package_id, valPeriod_id) IN (SELECT s.packageSelected, s.validityPeriod
                                     FROM dbtelco.servicepackage s
                                     WHERE s.servicePackage_id = NEW.servicePackageAssociated);

END IF;
end //
delimiter ;

create table totalpurchasesperpackageandvalperiod
(
    package_id     int not null,
    valPeriod_id   int not null,
    totalPurchases int not null DEFAULT 0,
    constraint totalpurchasesperpackageandvalperiod_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id),
    constraint totalpurchasesperpackageandvalperiod_fk1
        foreign key (valPeriod_id) references validityperiod (validityPeriod_Id)
);

create index totalpurchasesperpackageandvalperiod_fk0_idx
    on totalpurchasesperpackageandvalperiod (package_id);

create index totalpurchasesperpackageandvalperiod_fk1_idx
    on totalpurchasesperpackageandvalperiod (valPeriod_id);



delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER addSales
    AFTER INSERT ON `order` FOR EACH ROW
BEGIN
    DECLARE x,y float;
    IF NEW.isValid = true THEN
SELECT sp.valuePackage, sp.totalValueOptionalProducts INTO x,y
FROM servicepackage sp
WHERE sp.servicePackage_id = NEW.servicePackageAssociated;

UPDATE salespackage s
SET s.totalSalesWithOptProduct = s.totalSalesWithOptProduct + x + y,
    s.totalSalesWithoutOptProduct = s.totalSalesWithoutOptProduct + x
WHERE s.package_id IN (SELECT s.packageSelected
                       FROM servicepackage s
                       WHERE s.servicePackage_id = NEW.servicePackageAssociated );
END IF;
end //
delimiter ;


delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER updateSales
    AFTER UPDATE ON `order` FOR EACH ROW
BEGIN
    DECLARE x,y float;
    IF NEW.isValid = true THEN
SELECT sp.valuePackage, sp.totalValueOptionalProducts INTO x,y
FROM servicepackage sp
WHERE sp.servicePackage_id = NEW.servicePackageAssociated;

UPDATE salespackage s
SET s.totalSalesWithOptProduct = s.totalSalesWithOptProduct + x + y,
    s.totalSalesWithoutOptProduct = s.totalSalesWithoutOptProduct + x
WHERE s.package_id IN (SELECT s.packageSelected
                       FROM servicepackage s
                       WHERE s.servicePackage_id = NEW.servicePackageAssociated );
END IF;
end //
delimiter ;



delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER createServicePackageToSelect2
    AFTER INSERT ON servicepackagetoselect FOR EACH ROW BEGIN

    INSERT INTO salespackage(package_id)
    VALUES(NEW.servicePackageToSelect_id);

end //
delimiter ;



create table salespackage
(
    package_id int not null
        primary key,
    totalSalesWithOptProduct int not null DEFAULT 0,
    totalSalesWithoutOptProduct int not null DEFAULT 0,
    constraint salespackage_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);




delimiter //
CREATE DEFINER  = CURRENT_USER trigger createServicePackageToSelect3
    after insert
    on servicepackagetoselect
    for each row
begin
INSERT INTO dbtelco.avgnumofoptproductssoldperpackage(PACKAGE_ID)
VALUES(NEW.servicePackageToSelect_id);
end //
delimiter ;



delimiter //
CREATE DEFINER  = CURRENT_USER trigger addOptProduct
    after insert
    on `order`
    for each row
begin

    IF NEW.isValid = true THEN
DELETE FROM totalnumberofoptionalproduct t
WHERE t.package_id IN (SELECT s.packageSelected
                       FROM servicepackage s
                       WHERE s.servicePackage_id = NEW.servicePackageAssociated );


INSERT INTO totalnumberofoptionalproduct
SELECT ss.servicePackageToSelect_id, count(*)
FROM `order` as o
         JOIN dbtelco.servicepackage as s on o.servicePackageAssociated = s.servicePackage_id
         JOIN dbtelco.servicepackagetoselect as ss on s.packageSelected = ss.servicePackageToSelect_id
         JOIN dbtelco.addedproduct as a on a.servicePackage_id = o.servicePackageAssociated
WHERE o.isValid = true AND ss.servicePackageToSelect_id IN (SELECT s.packageSelected
                                                            FROM servicepackage s
                                                            WHERE s.servicePackage_id = NEW.servicePackageAssociated)

GROUP BY ss.servicePackageToSelect_id;


DELETE FROM avgnumofoptproductssoldperpackage
WHERE package_id IN (   SELECT s.packageSelected
                        FROM servicepackage s
                        WHERE s.servicePackage_id = NEW.servicePackageAssociated);

INSERT INTO avgnumofoptproductssoldperpackage
SELECT t.package_id, IFNULL((o.total / t.totalPurchases), 0.0)
FROM dbtelco.totalpurchasesperpackage as t
         LEFT OUTER JOIN dbtelco.totalnumberofoptionalproduct as o on t.package_id = o.package_id

WHERE t.package_id IN (SELECT s.packageSelected
                       FROM servicepackage s
                       WHERE s.servicePackage_id = NEW.servicePackageAssociated);


END IF;
end //
delimiter ;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger updateOptProduct
    after update
                                   on `order`
                                   for each row
begin

    IF NEW.isValid = true THEN
DELETE FROM totalnumberofoptionalproduct t
WHERE t.package_id IN (SELECT s.packageSelected
                       FROM servicepackage s
                       WHERE s.servicePackage_id = NEW.servicePackageAssociated );


INSERT INTO totalnumberofoptionalproduct
SELECT ss.servicePackageToSelect_id, count(*)
FROM `order` as o
         JOIN dbtelco.servicepackage as s on o.servicePackageAssociated = s.servicePackage_id
         JOIN dbtelco.servicepackagetoselect as ss on s.packageSelected = ss.servicePackageToSelect_id
         JOIN dbtelco.addedproduct as a on a.servicePackage_id = o.servicePackageAssociated
WHERE o.isValid = true AND ss.servicePackageToSelect_id IN (SELECT s.packageSelected
                                                            FROM servicepackage s
                                                            WHERE s.servicePackage_id = NEW.servicePackageAssociated)

GROUP BY ss.servicePackageToSelect_id;


DELETE FROM avgnumofoptproductssoldperpackage
WHERE package_id IN (   SELECT s.packageSelected
                        FROM servicepackage s
                        WHERE s.servicePackage_id = NEW.servicePackageAssociated);

INSERT INTO avgnumofoptproductssoldperpackage
SELECT t.package_id, IFNULL((o.total / t.totalPurchases), 0.0)
FROM dbtelco.totalpurchasesperpackage as t
         LEFT OUTER JOIN dbtelco.totalnumberofoptionalproduct as o on t.package_id = o.package_id

WHERE t.package_id IN (SELECT s.packageSelected
                       FROM servicepackage s
                       WHERE s.servicePackage_id = NEW.servicePackageAssociated);


END IF;


end //
delimiter ;



create table totalnumberofoptionalproduct
(
    package_id int not null
        primary key,
    total      int not null DEFAULT 0,
    constraint totalnumberofoptionalproduct_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);


create table avgnumofoptproductssoldperpackage
(
    package_id int              not null
        primary key,
    average    float default -1 not null,
    constraint avgnumofoptproductssoldperpackage_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);



delimiter //
CREATE DEFINER  = CURRENT_USER trigger addAlert
    after insert
    on alert
    for each row
begin
INSERT INTO alerts
VALUES (NEW.alert_id);
end //
delimiter ;

create table alerts
(
    alert_id int not null,
    constraint alerts_fk0
        foreign key (alert_id) references alert (alert_id)
);

create index alerts_fk0_idx
    on alerts (alert_id);




delimiter //
CREATE DEFINER  = CURRENT_USER trigger updateInsolventUser
    after update
                               on user
                               for each row
begin
    IF NEW.isInsolvent = true THEN
        IF(NEW.user_id NOT IN (SELECT user_id FROM insolventusers)) THEN
            INSERT INTO insolventusers
            VALUES (NEW.user_id);
END IF;

ELSE
DELETE FROM insolventusers i
WHERE i.user_id = NEW.user_id;
END IF;
end //
delimiter ;


create table insolventusers
(
    user_id int not null,
    constraint insolventUsers_fk0
        foreign key (user_id) references user (user_id)
);

create index insolventUsers_fk0_idx
    on insolventusers (user_id);



delimiter //
CREATE DEFINER  = CURRENT_USER trigger addSuspendedOrder
    after insert
    on `order`
    for each row
begin
    IF(NEW.isValid = false) THEN
        IF(NEW.servicePackageAssociated NOT IN (SELECT order_id FROM suspendedorders)) THEN
            INSERT INTO suspendedorders
SELECT s.servicePackage_id
FROM servicepackage s
WHERE NEW.servicePackageAssociated = s.servicePackage_id;
END IF;
ELSE
        IF(NEW.servicePackageAssociated IN (SELECT order_id FROM suspendedorders)) THEN
DELETE FROM suspendedorders s
WHERE s.order_id = NEW.servicePackageAssociated;
END IF;
END IF;
end //
delimiter ;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger updateSuspendedOrder
    after update
                                   on `order`
                                   for each row
begin
    IF(NEW.isValid = false) THEN
        IF(NEW.servicePackageAssociated NOT IN (SELECT order_id FROM suspendedorders)) THEN
            INSERT INTO suspendedorders
SELECT s.servicePackage_id
FROM servicepackage s
WHERE NEW.servicePackageAssociated = s.servicePackage_id;
END IF;
ELSE
        IF(NEW.servicePackageAssociated IN (SELECT order_id FROM suspendedorders)) THEN
DELETE FROM suspendedorders s
WHERE s.order_id = NEW.servicePackageAssociated;
END IF;
END IF;

end //
delimiter ;


create table suspendedorders
(
    order_id int not null,
    constraint suspendedOrders_fk0
        foreign key (order_id) references `order`  (order_id)
);

create index suspendedOrders_fk0_idx
    on suspendedorders (order_id);

delimiter //
CREATE DEFINER  = CURRENT_USER trigger addSalesOptProduct
    after insert
    on `order`
    for each row
begin

    DECLARE id int;
    DECLARE amount int;
    IF NEW.isValid = true THEN
DELETE FROM optProductsOrder;
INSERT INTO optProductsOrder

SELECT op.optionalProduct_id, (op.monthlyFee * v.numOfMonths)
FROM `order` o
         JOIN servicepackage s on s.servicePackage_id = o.servicePackageAssociated
         JOIN addedproduct a on a.servicePackage_id = s.servicePackage_id
         JOIN validityperiod v on v.validityPeriod_Id = s.validityPeriod
         JOIN optionalproduct op on op.optionalProduct_Id = a.optionalProduct_id
WHERE s.servicePackage_id = NEW.servicePackageAssociated;

UPDATE salesperoptproduct s, optProductsOrder op
SET s.salesOfOptProd = s.salesOfOptProd + op.salesOfOptProd
WHERE s.optionalProduct_id = op.optionalProduct_id;


DELETE FROM bestoptionalproduct;
INSERT INTO bestoptionalproduct
SELECT s1.optionalProduct_id, s1.salesOfOptProd
FROM salesperoptproduct s1
WHERE s1.optionalProduct_id is not null and s1.salesOfOptProd IN (SELECT MAX(s2.salesOfOptProd) FROM salesperoptproduct s2);
end if;

end //
delimiter ;


delimiter //
CREATE DEFINER  = CURRENT_USER trigger updateSalesOptProduct
    after update
                                   on `order`
                                   for each row
begin

    DECLARE id int;
    DECLARE amount int;
    IF NEW.isValid = true THEN
DELETE FROM optProductsOrder;
INSERT INTO optProductsOrder

SELECT op.optionalProduct_id, (op.monthlyFee * v.numOfMonths)
FROM `order` o
         JOIN servicepackage s on s.servicePackage_id = o.servicePackageAssociated
         JOIN addedproduct a on a.servicePackage_id = s.servicePackage_id
         JOIN validityperiod v on v.validityPeriod_Id = s.validityPeriod
         JOIN optionalproduct op on op.optionalProduct_Id = a.optionalProduct_id
WHERE s.servicePackage_id = NEW.servicePackageAssociated;

UPDATE salesperoptproduct s, optProductsOrder op
SET s.salesOfOptProd = s.salesOfOptProd + op.salesOfOptProd
WHERE s.optionalProduct_id = op.optionalProduct_id;


DELETE FROM bestoptionalproduct;
INSERT INTO bestoptionalproduct
SELECT s1.optionalProduct_id, s1.salesOfOptProd
FROM salesperoptproduct s1
WHERE s1.optionalProduct_id is not null and s1.salesOfOptProd IN (SELECT MAX(s2.salesOfOptProd) FROM salesperoptproduct s2);
end if;

end //
delimiter ;


delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER insertOptProduct
    AFTER INSERT ON optionalproduct FOR EACH ROW BEGIN
    INSERT INTO dbtelco.salesperoptproduct(optionalProduct_id)
    VALUES(NEW.optionalProduct_Id);
end //
delimiter ;

create table bestoptionalproduct
(
    optionalProduct_id int   not null
        primary key,
    sales              float not null,
    constraint bestoptionalproduct_fk0
        foreign key (optionalProduct_id) references optionalproduct (optionalProduct_Id)
);


create table optProductsOrder
(
    optionalProduct_id int   not null
        primary key,
    salesOfOptProd     float default 0 not null
);



create table salesperoptproduct
(
    optionalProduct_id int             not null,
    salesOfOptProd     float default 0 not null
);