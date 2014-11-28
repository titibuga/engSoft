# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table monstro (
  id                        varchar(255) not null,
  nome                      varchar(255),
  energia                   integer,
  constraint pk_monstro primary key (id))
;

create sequence monstro_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists monstro;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists monstro_seq;

