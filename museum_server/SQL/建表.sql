 create table museum_type
(
	type_id numeric(6,0) not null,
	type varchar(20),
	primary key(type_id)
	);
	
create table museum_hall
(
	hall_id numeric(2,0) not null,
	hall_addr varchar(50),
	build_name varchar(20),
	primary key(hall_id)
	);


create table museum_exhibit
(
	ex_id numeric(6,0)not null,
	name varchar(20),
	type_id numeric(6,0),
	music_addr varchar(50),
	cover_addr varchar(50),
	hall_id numeric(2,0),
	favorites numeric(6,0),
	text varchar(500),
	primary key(ex_id),
	foreign key(type_id) references museum_type,
	foreign key(hall_id)references museum_hall
	);

create table museum_pic
(
	pic_id numeric(6,0),
	e_id numeric(6,0),
	pic_addr varchar(50),
	primary key(pic_id),
	foreign key(e_id)references museum_exhibit
);
 create table museumn_yijian
 (
 	yijian varchar(500),
 	primary key(yijian)
 );