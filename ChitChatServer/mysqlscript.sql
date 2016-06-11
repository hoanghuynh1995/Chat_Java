CREATE SCHEMA `chatapplication` ;

create table `chatapplication`.`user`(
	id varchar(20) not null,
    pass varchar(50) not null,
    gender varchar(6),
    status varchar(15),
    primary key(id)
);

create table `chatapplication`.`conversation`(
	id int auto_increment,
    name nvarchar(50),
    groupchat bool,
    totalsentence int,
    primary key(id)
);

create table `chatapplication`.`sentence`(
	id int auto_increment,
    userid varchar(20),
    conversationid int,
    content nvarchar(256),
    store mediumblob,
    primary key(id)
);


create table `chatapplication`.`userconversation`(
	id int auto_increment,
	userid varchar(20),
    conversationid int,
    primary key(id)
);

create table `chatapplication`.`friend`(
	id int auto_increment,
    userid varchar(20),
    friendid varchar(20),
    primary key(id)
);


        
        
        