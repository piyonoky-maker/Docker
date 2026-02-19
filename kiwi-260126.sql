create user 'kiwi'@'%' identified by 'kiwi';

create database kiwidb;

grant all privileges on *.* to 'kiwi'@'%';
