create table if not exists despesas
(
    id      bigint auto_increment,
    data       date,
    descricao  varchar,
    valor      decimal,
    categorias varchar,
    constraint DESPESAS_PK
        primary key (id)
);

create table if not exists perfil
(
    id   bigint auto_increment,
    nome varchar,
    constraint PERFIL_PK
        primary key (id)
);

create table if not exists usuario
(
    id    bigint auto_increment,
    email varchar,
    nome  varchar,
    senha varchar,
    constraint USUARIO_PK
        primary key (id)
);

create table  if not exists receitas
(
    id       bigint auto_increment,
    data       date,
    descricao  varchar,
    valor      decimal,
    constraint RECEITAS_PK
        primary key (id)
);

create table if not exists usuario_perfil
(
    usuario_id bigint not null ,
    perfil_id  bigint not null,
    constraint USUARIO_PERFIL_FK
        foreign key (perfil_id) references PERFIL (ID),
    constraint USUARIO_PERFIL__FK
        foreign key (usuario_id) references USUARIO (ID)
);

INSERT INTO `receita` VALUES (12,2040,'2040-01-01','Salario',1,50000.00),(24,2024,'2024-01-01','Salario',1,10000.00),(28,2024,'2024-01-01','Frella',1,10000.00),(32,2024,'2024-04-01','Salario',4,10000.00),(54,2040,'2040-07-01','Salario',7,50000.00),(65,2012,'2012-08-01','Salario',8,50000.00),(67,2012,'2012-01-01','Salario',1,10000.00),(68,2013,'2013-08-01','Salario',8,50000.00);
INSERT INTO `despesa` VALUES (3,2024,'2024-01-10','Pneu',1,'OUTRAS',2000.00),(4,2024,'2024-01-10','Ifood',1,'ALIMENTACAO',500.00),(6,2024,'2024-01-10','Roupa',1,'IMPREVISTOS',150.00),(7,2024,'2024-01-10','Capa Celular',1,'IMPREVISTOS',150.00),(8,2024,'2024-01-10','Escola Davi',1,'EDUCACAO',1800.00),(10,2024,'2024-01-10','Gasolina',1,'TRANSPORTE',540.00),(14,2024,'2024-12-10','Gasolina',12,'TRANSPORTE',520.00),(17,2024,'2024-01-10','Farmacia',1,'SAUDE',220.00),(19,2024,'2024-01-10','Cinema',1,'LAZER',140.00),(20,2022,'2022-01-10','Conserto cano',1,'IMPREVISTOS',350.00),(21,2024,'2024-10-01','Mercado',10,'OUTRAS',2000.00),(22,2022,'2022-01-01','Mercado',1,'ALIMENTACAO',1000.00),(23,2024,'2024-11-01','Mercado',11,'ALIMENTACAO',2000.00),(40,2025,'2025-05-01','Mercado',5,'ALIMENTACAO',2000.00),(41,2025,'2025-05-01','Pneu',5,'OUTRAS',2000.00),(42,2025,'2025-06-01','Mercado',6,'ALIMENTACAO',2000.00),(43,2025,'2025-06-01','Pneu',6,'OUTRAS',2000.00),(44,2025,'2025-01-01','Mercado',1,'ALIMENTACAO',1000.00),(45,2025,'2025-01-01','Pneu',1,'OUTRAS',2000.00),(46,2026,'2026-01-01','Mercado',1,'ALIMENTACAO',2000.00),(47,2026,'2026-01-01','Pneu',1,'OUTRAS',2000.00),(48,2026,'2026-02-01','Mercado',2,'ALIMENTACAO',2000.00),(49,2026,'2026-02-01','Pneu',2,'OUTRAS',2000.00),(50,2026,'2026-03-01','Mercado',3,'ALIMENTACAO',2000.00),(51,2026,'2026-03-01','Pneu',3,'OUTRAS',2000.00),(53,2026,'2026-04-01','Pneu',4,'OUTRAS',2000.00),(54,2026,'2026-05-01','Mercado',5,'ALIMENTACAO',2000.00),(55,2026,'2026-05-01','Pneu',5,'OUTRAS',2000.00),(58,2012,'2012-01-01','Mercado',1,'ALIMENTACAO',2000.00),(59,2012,'2012-01-01','Pneu',1,'OUTRAS',2000.00),(60,2012,'2012-02-01','Mercado',2,'ALIMENTACAO',2000.00),(61,2012,'2012-02-01','Pneu',2,'OUTRAS',2000.00);
INSERT INTO `usuario` VALUES (1,'daniel@email.com','daniel','$2a$10$FT7KC5dg6dpLP8vXgE/gEO0qSrHHukEBWdbFN3F33kAqRVj5pRq7e'),(2,'davi@email.com','davi','$2a$10$FT7KC5dg6dpLP8vXgE/gEO0qSrHHukEBWdbFN3F33kAqRVj5pRq7e');
INSERT INTO `perfil` VALUES (1,'ROLE_USUARIO'),(2,'ROLE_ADMIN');
INSERT INTO `usuario_perfis` VALUES (1,1),(2,2);

