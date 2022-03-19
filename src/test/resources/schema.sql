create table if not exists orders.t_user
(
    id        bigint auto_increment primary key,
    user_id   bigint,
    user_name varchar(255)
);


create table if not exists orders.t_order
(
    id       bigint auto_increment primary key,
    order_id bigint,
    price    int,
    user_id  int
);
