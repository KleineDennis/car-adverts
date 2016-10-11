# --- !Ups

create table "CAR" ("ID" bigserial primary key not null, "TITLE" varchar not null, "FUEL" varchar not null, "PRICE" int not null, "NEWCAR" boolean not null, "MILEAGE" int, "FIRST_REGISTRATION" bigint);

# --- !Downs

drop table "CAR";
