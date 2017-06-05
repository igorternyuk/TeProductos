/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  igor
 * Created: Jun 3, 2017
 */

create database bd_productos;
use bd_productos;

create table marca(
    id int auto_increment,
    nombre varchar(100) not null,
    primary key(id)
);

insert into marca values(null, 'Gloria'); --1
insert into marca values(null, 'Lavagi'); --2
insert into marca values(null, 'Molitalia'); --3
insert into marca values(null, 'Cocinero'); --4
insert into marca values(null, 'Kiko'); --5
insert into marca values(null, 'Mega'); --6
insert into marca values(null, 'Quaquer'); --7
insert into marca values(null, 'Romol'); --8
insert into marca values(null, 'Servilat'); --9

create table categoria(
    id int auto_increment,
    nombre varchar(100) not null,
    primary key(id)
);

insert into categoria values(null, 'Comida'); --1
insert into categoria values(null, 'Bebida'); --2
insert into categoria values(null, 'Salsamento'); --3
insert into categoria values(null, 'Dulce'); --4
insert into categoria values(null, 'Alcohol'); --5
insert into categoria values(null, 'Otro'); --6

create table producto(
     id int auto_increment,
     nombre varchar(100) not null,
     categoria int,
     marca int,
     precio float,
     fecha date,
     transgenic bit,
     disponible bit,
     primary key(id),
     foreign key(marca) references marca(id)
);

insert into producto values(null,'Leche evaporada', 2, 1, 3.5, '2017-05-14',true, true);
insert into producto values(null,'Salsa de tomate', 3, 2, 7.8, '2017-06-01', true, true);
insert into producto values(null,'Meremelada',4 ,3 , 54.3, '2017-04-25', false, true);
insert into producto values(null,'Lomito Atún en aceite', 1, 4, 92.4, '2017-03-08', true, true);
insert into producto values(null,'Vinagre de manzana', 6, 5, 77.5, '2017-02-09', true, true);
insert into producto values(null,'Galletas', 4, 4, 43.6, '2017-03-24', true, false);
insert into producto values(null,'Chocolate', 4, 7, 36.8, '2017-03-14',false, true);
insert into producto values(null,'Nata', 1, 8, 20.9, '2017-04-19', true, false);
insert into producto values(null,'Pan', 1, 6, 37.9, '2017-06-02', false, false);
insert into producto values(null,'Salchicha', 1, 9, 60.6, '2017-06-02',false, true);
insert into producto values(null,'Manteca de cerdo', 1, 7, 120.25, '2017-03-10', true, false);
insert into producto values(null,'Carne de vaca', 1, 9, 150.5, '2017-05-16', true, true);
insert into producto values(null,'Pescado', 1, 6, 60.5, '2017-05-22', true, true);
insert into producto values(null,'Aceite', 6, 4, 35.6,'2017-01-15', true, false);
insert into producto values(null,'Cerveza', 5, 4, 75.7,'2016-02-12', true, true);
insert into producto values(null,'Tequila', 5, 4, 235.89,'2015-11-13', true, false);
insert into producto values(null,'Jugo', 2, 4, 39.5,'2017-06-03', true, true);

--Супер запрос
select producto.nombre, categoria.nombre, marca.nombre, precio, fecha
from producto, categoria, marca where producto.marca = marca.id and
producto.categoria = categoria.id and
(producto.nombre like '%al%' or marca.nombre like '%o%') and
(producto.precio > 10 and producto.precio < 500) and
--(transgenic = true or disponible = false) and
(categoria.id = 1 or categoria.id = 2)
order by producto.precio desc;

--drop database bd_productos;

