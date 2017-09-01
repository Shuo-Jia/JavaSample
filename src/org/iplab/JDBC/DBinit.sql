drop database if exists DBjavasample;

create database DBjavasample;

use DBjavasample;

create table Book_table
(
 bookId int unsigned UNIQUE key default null auto_increment,
 name varchar(50) default null,
 author varchar(20) default null,
 isbn char(20) default null
 );
