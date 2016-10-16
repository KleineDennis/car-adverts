# --- !Ups

create table "CAR" ("ID" bigserial primary key not null, "TITLE" varchar not null, "FUEL" varchar not null, "PRICE" int not null, "NEWCAR" boolean not null, "MILEAGE" int, "FIRST_REGISTRATION" bigint);
insert into "CAR" values (1011, 'Audi A4 Avant', 'Gasoline', 10001, false, 0, 1355266800000);
insert into "CAR" values (1017, 'Audi', 'Gasoline', 16000, false, 0, 1355266800000);
insert into "CAR" values (1020, 'BMW', 'Diesel', 16001, true, 100000, 1355266800000);
# --- !Downs

drop table "CAR";
