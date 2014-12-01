# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table habilidade (
  id                        varchar(255) not null,
  min_dex                   integer,
  min_wis                   integer,
  min_str                   integer,
  dano                      integer,
  custo                     integer,
  nome                      varchar(255),
  constraint pk_habilidade primary key (id))
;

create table monstro (
  id                        varchar(255) not null,
  nome                      varchar(255),
  energia                   integer,
  dex                       integer,
  str                       integer,
  wis                       integer,
  constraint pk_monstro primary key (id))
;


create table monstro_habilidade (
  monstro_id                     varchar(255) not null,
  habilidade_id                  varchar(255) not null,
  constraint pk_monstro_habilidade primary key (monstro_id, habilidade_id))
;
create sequence habilidade_seq;

create sequence monstro_seq;




alter table monstro_habilidade add constraint fk_monstro_habilidade_monstro_01 foreign key (monstro_id) references monstro (id) on delete restrict on update restrict;

alter table monstro_habilidade add constraint fk_monstro_habilidade_habilid_02 foreign key (habilidade_id) references habilidade (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists habilidade;

drop table if exists monstro;

drop table if exists monstro_habilidade;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists habilidade_seq;

drop sequence if exists monstro_seq;

