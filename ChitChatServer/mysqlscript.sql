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
    primary key(id)
);
drop table `chatapplication`.`conversation`;
create table `chatapplication`.`sentence`(
	id int,
    userid varchar(20),
    conversationid int,
    content nvarchar(256),
    sequence int,
    primary key(id)
);

create table `chatapplication`.`userconversation`(
	id int,
	userid varchar(20),
    conversationid int,
    primary key(id)
);

insert into `chatapplication`.`conversation`(name)
values ('conversation1'),
		('conversation2');
        
	

insert into `chatapplication`.`user`(id,pass,gender,status)
values ('usernumber1','password1','Male','offline'),
		('usernumber2','password1','Female','invisible');        
        
insert into `chatapplication`.`sentence`(id,userid,conversationid,content,sequence)
values ('1','usernumber1','1','usernumber1 content','1'),
		('2','usernumber2','1','usernumber2 content','2');    
        
insert into `chatapplication`.`userconversation`(id,userid,conversationid)
values ('1','usernumber1','1'),
		('2','usernumber2','1');  
        
        
        