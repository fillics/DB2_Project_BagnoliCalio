
create table user
(
    user_id           int auto_increment,
    username          varchar(64)       not null,
    password          varchar(64)       not null,
    email             varchar(64)       not null,
    isInsolvent       tinyint default 0 null,
    numFailedPayments int     default 0 null,
    constraint email
        unique (email),
    constraint user_id
        unique (user_id),
    constraint username
        unique (username)
);

alter table user
    add primary key (user_id);

create table validityperiod
(
    validityPeriod_Id int auto_increment
        primary key,
    numOfMonths       int not null,
    monthlyFee        int not null
);

create table employee
(
    employee_id int auto_increment
        primary key,
    username    varchar(64) not null,
    password    varchar(64) not null,
    email       varchar(64) not null,
    constraint email
        unique (email),
    constraint username
        unique (username)
);

create table optionalproduct
(
    optionalProduct_Id int auto_increment
        primary key,
    name               varchar(64) not null,
    monthlyFee         float        not null,
    constraint name
        unique (name)
);

create table service
(
    service_id           int auto_increment
        primary key,
    typeOfService        varchar(64) not null,
    numMinutes           int          null,
    numSMS               int          null,
    feeExtraMinute       float        null,
    feeExtraSMSs         float        null,
    numGigabytes         int          null,
    feeForExtraGigabytes float        null
);

create table servicepackagetoselect
(
    servicePackageToSelect_id int auto_increment
        primary key,
    name                      varchar(64) not null,
    constraint name_UNIQUE
        unique (name)
);

create table alert
(
    alert_id    int auto_increment
        primary key,
    amount      int      not null,
    dateAndTime datetime not null,
    userOwner   int      not null,
    constraint alert_fk0
        foreign key (userOwner) references user (user_id)
);

create table alerts
(
    alert_id int not null,
    constraint alerts_fk0
        foreign key (alert_id) references alert (alert_id)
);

create index alerts_fk0_idx
    on alerts (alert_id);

create table avgnumofoptproductssoldperpackage
(
    package_id int   not null
        primary key,
    average    float not null
);


create table insolventusers
(
    user_id int not null,
    constraint insolventUsers_fk0
        foreign key (user_id) references user (user_id)
);

create index insolventUsers_fk0_idx
    on insolventusers (user_id);

create table offer
(
    servicePackageToSelect_id int not null,
    service_id                int not null,
    primary key (servicePackageToSelect_id, service_id),
    constraint offer_fk0
        foreign key (servicePackageToSelect_id) references servicepackagetoselect (servicePackageToSelect_id),
    constraint offer_fk1
        foreign key (service_id) references service (service_id)
);

create table offerproduct
(
    servicePackageToSelect_id int not null,
    optionalProduct_id        int not null,
    primary key (servicePackageToSelect_id, optionalProduct_id),
    constraint offerProduct_fk0
        foreign key (servicePackageToSelect_id) references servicepackagetoselect (servicePackageToSelect_id),
    constraint offerProduct_fk1
        foreign key (optionalProduct_id) references optionalproduct (optionalProduct_Id)
);

create table proposal
(
    servicePackageToSelect_id int not null,
    validityPeriod_id         int not null,
    primary key (servicePackageToSelect_id, validityPeriod_id),
    constraint proposal_fk0
        foreign key (servicePackageToSelect_id) references servicepackagetoselect (servicePackageToSelect_id),
    constraint proposal_fk1
        foreign key (validityPeriod_id) references validityperiod (validityPeriod_Id)
);


create table salesperoptproduct
(
    optionalProduct_id int   not null,
    salesOfOptProd     float not null,
    constraint salesperoptproduct_fk0
        foreign key (optionalProduct_id) references optionalproduct (optionalProduct_Id)
);

create table salesperpackagewithoptproducts
(
    package_id int not null
        primary key,
    totalSales int not null,
    constraint salesperpackagewithoptproducts_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);

create table salesperpackagewithoutoptproducts
(
    package_id int not null
        primary key,
    totalSales int not null,
    constraint salesperpackagewithoutoptproducts_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);

create table totalnumberofoptionalproduct
(
    package_id int not null
        primary key,
    total      int not null,
    constraint totalnumberofoptionalproduct_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);


create table totalpurchasesperpackage
(
    package_id     int           not null
        primary key,
    totalPurchases int default 0 not null,
    constraint numberOfTotalPurchasesPerPackage_fk0
        foreign key (package_id) references servicepackagetoselect (servicePackageToSelect_id)
);

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


create table servicepackage
(
    servicePackage_id          int auto_increment
        primary key,
    packageSelected            int             not null,
    validityPeriod             int             not null,
    userOwner                  int             not null,
    startDate                  date            not null,
    endDate                    date            not null,
    valuePackage               float           not null,
    totalValueOptionalProducts float default 0 not null,
    constraint servicePackage_fk
        foreign key (packageSelected) references servicepackagetoselect (servicePackageToSelect_id),
    constraint servicePackage_fk1
        foreign key (validityPeriod) references validityperiod (validityPeriod_Id),
    constraint servicePackage_fk2
        foreign key (userOwner) references user (user_id)
);

create index servicePackage_fk_idx
    on servicepackage (packageSelected);


create table `order`
(
    order_id                 int auto_increment
        primary key,
    dateAndHour              datetime not null,
    userOwner                int      not null,
    servicePackageAssociated int      not null,
    totalValueOrder          float    not null,
    isValid                  tinyint  null,
    constraint order_fk0
        foreign key (userOwner) references user (user_id),
    constraint order_fk1
        foreign key (servicePackageAssociated) references servicepackage (servicePackage_id)
);

create table addedproduct
(
    servicePackage_id  int not null,
    optionalProduct_id int not null,
    primary key (servicePackage_id, optionalProduct_id),
    constraint addedProduct_fk0
        foreign key (servicePackage_id) references servicepackage (servicePackage_id),
    constraint addedProduct_fk1
        foreign key (optionalProduct_id) references optionalproduct (optionalProduct_Id)
);


create table suspendedorders
(
    order_id int not null,
    constraint suspendedOrders_fk0
        foreign key (order_id) references `order` (order_id)
);

create index suspendedOrders_fk0_idx
    on suspendedorders (order_id);
