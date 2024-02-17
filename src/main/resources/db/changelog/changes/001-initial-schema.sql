-- drop table if exists vehicle;

create table vehicle(
    vehicle_plate_no    varchar(15) primary key,
    manufactured_by     varchar(50),
    vehicle_model       varchar,
    price_per_day       int NOT NULL
);

insert into vehicle (vehicle_plate_no, manufactured_by, vehicle_model, price_per_day) values ('CSR123', 'Volvo', 'S60', 1500);
insert into vehicle (vehicle_plate_no, manufactured_by, vehicle_model, price_per_day) values ('CSR223', 'Volkswagen', 'Golf', 1333);
insert into vehicle (vehicle_plate_no, manufactured_by, vehicle_model, price_per_day) values ('CSR143', 'Ford', 'Mustang', 3000);
insert into vehicle (vehicle_plate_no, manufactured_by, vehicle_model, price_per_day) values ('CSR127', 'Ford', 'Transit', 2400);

-- drop table if exists renting_entity;

create table renting_entity(
    email               varchar primary key,
    name                varchar NOT NULL,
    age                 int NOT NULL
);

-- drop table if exists rental_status;

create table rental_status (
    id                  int primary key,
    status              varchar
);

insert into rental_status values(1, 'COMPLETED');
insert into rental_status values(2, 'RESERVED');
insert into rental_status values(3, 'IN_PROGRESS');

-- drop table if exists rental_details;

create table rental_details(
    id                  int GENERATED ALWAYS AS IDENTITY primary key,
    vehicle_id          varchar(12) NOT NULL,
    renter_id           varchar NOT NULL,
    collected_at        date NOT NULL,
    dropped_at          date NOT NULL,
    total_price_in_sek  bigint,
    rental_status       int NOT NULL,
    constraint fk_vehicle foreign key(vehicle_id) references vehicle(vehicle_plate_no),
    constraint fk_renter foreign key(renter_id) references renting_entity(email),
    constraint fk_rental_status foreign key(rental_status) references rental_status(id)
);

--CREATE INDEX tbl_date_inverse_idx ON tbl(start_date, end_date DESC);