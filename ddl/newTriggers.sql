DROP TABLE IF EXISTS totalpurchasesperpackage;
DROP TRIGGER IF EXISTS createServicePackageToSelect;
DROP TRIGGER IF EXISTS addPurchaseToPackage;
DROP TRIGGER IF EXISTS updatePurchaseToPackage;

DROP TABLE IF EXISTS totalpurchasesperpackageandvalperiod;
DROP TRIGGER IF EXISTS trigger_suspended_orders;



-- materialized view 1 --ok

CREATE DEFINER=`root`@`localhost` TRIGGER `addPurchaseToPackage`
    AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF NEW.isValid = true THEN
        UPDATE dbtelco.totalpurchasesperpackage SET totalPurchases = totalPurchases + 1
        WHERE package_id IN (SELECT s.packageSelected
                             FROM dbtelco.servicepackage s
                             WHERE s.servicePackage_id = NEW.servicePackageAssociated);
    END IF;
END;


CREATE DEFINER=`root`@`localhost` TRIGGER `updatePurchaseToPackage`
    AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    IF NEW.isValid = true THEN
        UPDATE dbtelco.totalpurchasesperpackage SET totalPurchases = totalPurchases + 1
        WHERE package_id IN (SELECT s.packageSelected
                             FROM dbtelco.servicepackage s
                             WHERE s.servicePackage_id = NEW.servicePackageAssociated);
    END IF;
END;


CREATE DEFINER=`root`@`localhost` TRIGGER `createServicePackageToSelect`
    AFTER INSERT ON `servicepackagetoselect` FOR EACH ROW BEGIN

    INSERT INTO dbtelco.totalpurchasesperpackage(PACKAGE_ID)
    VALUES(NEW.servicePackageToSelect_id);

END;


create table totalpurchasesperpackage
(
    package_id     int           not null
        primary key,
    totalPurchases int default 0 not null,
    constraint numberOfTotalPurchasesPerPackage_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);




-- materialized view 2 --ok

CREATE DEFINER=`root`@`localhost` TRIGGER `createServPackageAndValPeriod`
    AFTER INSERT ON `proposal` FOR EACH ROW BEGIN
    INSERT INTO totalpurchasesperpackageandvalperiod(package_id, valPeriod_id)
    VALUES(NEW.servicePackageToSelect_id, NEW.validityPeriod_id);

END;

CREATE DEFINER=`root`@`localhost` TRIGGER `addPurchaseToPackageAndValPeriod`
    AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF NEW.isValid = true THEN
        UPDATE dbtelco.totalpurchasesperpackageandvalperiod SET totalPurchases = totalPurchases + 1
        WHERE (package_id, valPeriod_id) IN (SELECT s.packageSelected, s.validityPeriod
                                             FROM dbtelco.servicepackage s
                                             WHERE s.servicePackage_id = NEW.servicePackageAssociated);

    END IF;
end;

CREATE DEFINER=`root`@`localhost` TRIGGER `updatePurchaseToPackageAndValPeriod`
    AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    IF NEW.isValid = true THEN
        UPDATE dbtelco.totalpurchasesperpackageandvalperiod SET totalPurchases = totalPurchases + 1
        WHERE (package_id, valPeriod_id) IN (SELECT s.packageSelected, s.validityPeriod
                                             FROM dbtelco.servicepackage s
                                             WHERE s.servicePackage_id = NEW.servicePackageAssociated);

    END IF;
end;

create table totalpurchasesperpackageandvalperiod
(
    package_id     int not null,
    valPeriod_id   int not null,
    totalPurchases int not null,
    constraint totalpurchasesperpackageandvalperiod_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id),
    constraint totalpurchasesperpackageandvalperiod_fk1
        foreign key (valPeriod_id) references validityperiod (validityPeriod_Id)
);

create index totalpurchasesperpackageandvalperiod_fk0_idx
    on totalpurchasesperpackageandvalperiod (package_id);

create index totalpurchasesperpackageandvalperiod_fk1_idx
    on totalpurchasesperpackageandvalperiod (valPeriod_id);


-- materialized view 3 --


