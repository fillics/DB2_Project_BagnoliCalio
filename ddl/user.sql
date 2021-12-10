create table user
(
    user_id           int auto_increment,
    username          varchar(255)  not null,
    password          varchar(255)  not null,
    email             varchar(255)  not null,
    isInsolvent       tinyint       null,
    numFailedPayments int default 0 null,
    constraint email
        unique (email),
    constraint user_id
        unique (user_id),
    constraint username
        unique (username)
);

alter table user
    add primary key (user_id);

create definer = admin@`%` trigger updateInsolventUser
    after update
    on user
    for each row
begin
    DELETE FROM dbtelco.insolventusers;
    INSERT INTO dbtelco.insolventusers
    SELECT u.user_id
    FROM dbtelco.user as u
    where u.isInsolvent = true;
end;

