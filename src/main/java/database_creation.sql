create table user
(
    id                  int         not null
        primary key,
    name                varchar(45) null,
    email               varchar(45) null,
    dob                 varchar(45) null,
    balance             double      null,
    `rank`              int         null,
    cards               int         null,
    transaction_history varchar(45) null,
    constraint user_email_uindex
        unique (email)
);

create table credentials
(
    id_user int         not null,
    passwd  varchar(45) not null,
    cvv     int         null,
    constraint credentials_id_user_uindex
        unique (id_user)
);

alter table credentials
    add primary key (id_user);

create table auctions
(
    id         int         not null
        primary key,
    object     varchar(45) not null,
    start_date varchar(45) not null,
    minRank    int         null
);

create table transaction_history
(
    id      int         not null
        primary key,
    id_user int         null,
    sum     double      null,
    status  varchar(45) null
);


