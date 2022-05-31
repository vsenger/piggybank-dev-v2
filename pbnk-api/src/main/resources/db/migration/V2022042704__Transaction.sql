create table Transactions (
       id bigint not null,
        balanceOrder integer not null,
        description varchar(255),
        timeStamp date,
        value decimal(19,2),
        account_id bigint,
        category_id bigint,
        primary key (id)
    );

alter table Transactions
       add constraint FK60ogq0ga4x4y0fkeu24tgm0kv
       foreign key (account_id)
       references Account (id);

alter table Transactions
       add constraint FKdv2mk0d5egsjapqkwcjgyw4ta
       foreign key (category_id)
       references Category (id);
