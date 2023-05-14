create TABLE orders (
    id serial primary key not null,
    user_id int not null,
    dish_id int not null,
    order_status varchar (100) not null
);


