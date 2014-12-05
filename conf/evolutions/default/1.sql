# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table generator (
  id                        varchar(255) not null,
  name                      varchar(255),
  cost                      integer,
  energy_per_instant        integer,
  constraint pk_generator primary key (id))
;

create table monster (
  id                        varchar(255) not null,
  name                      varchar(255),
  stored_energy             integer,
  dexterity                 integer,
  strength                  integer,
  wisdom                    integer,
  constraint pk_monster primary key (id))
;

create table monster_generator_link (
  generator_id              varchar(255),
  monster_id                varchar(255),
  amount                    integer,
  constraint pk_monster_generator_link primary key (generator_id, monster_id))
;

create table skill (
  id                        varchar(255) not null,
  required_dexterity        integer,
  required_wisdom           integer,
  required_strength         integer,
  damage                    integer,
  cost                      integer,
  name                      varchar(255),
  constraint pk_skill primary key (id))
;


create table monster_skill (
  monster_id                     varchar(255) not null,
  skill_id                       varchar(255) not null,
  constraint pk_monster_skill primary key (monster_id, skill_id))
;
create sequence generator_seq;

create sequence monster_seq;

create sequence monster_generator_link_seq;

create sequence skill_seq;

alter table monster_generator_link add constraint fk_monster_generator_link_mons_1 foreign key (monster_id) references monster (id) on delete restrict on update restrict;
create index ix_monster_generator_link_mons_1 on monster_generator_link (monster_id);
alter table monster_generator_link add constraint fk_monster_generator_link_gene_2 foreign key (generator_id) references generator (id) on delete restrict on update restrict;
create index ix_monster_generator_link_gene_2 on monster_generator_link (generator_id);



alter table monster_skill add constraint fk_monster_skill_monster_01 foreign key (monster_id) references monster (id) on delete restrict on update restrict;

alter table monster_skill add constraint fk_monster_skill_skill_02 foreign key (skill_id) references skill (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists generator;

drop table if exists monster;

drop table if exists monster_skill;

drop table if exists monster_generator_link;

drop table if exists skill;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists generator_seq;

drop sequence if exists monster_seq;

drop sequence if exists monster_generator_link_seq;

drop sequence if exists skill_seq;

