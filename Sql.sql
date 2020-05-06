use movies;

drop table if exists UserSession;
create table UserSession 
(
	id int not null auto_increment,
    userName varchar(30),
	sessionId varchar(30),
      PRIMARY KEY (`id`)
);
/*Alter table recommendation drop foreign key fk_recommendation_userName; */

drop table if exists users;
create table users 
(
	userId int not null auto_increment,
    userName varchar(30) unique,
	passwd varchar(30),
    picUrl varchar(100),
      PRIMARY KEY (`userId`)
);

insert into users (userName,passwd,picUrl) values ('kangde','123456','picture');
select * from users;


Alter table recommendation drop foreign key fk_tag_uploader; 
Alter table recommendation drop foreign key fk_tag_movieName;

drop table if exists userPreference;
create table userPreference 
(
	upId int not null auto_increment,
    userName varchar(30) ,
	uploader varchar(30),
    movieName varchar(30),
    rating int, 
	recId int,
      PRIMARY KEY (upId),
       constraint  fk_tag_uploader FOREIGN KEY (uploader) REFERENCES  recommendation(userName),
        constraint  fk_tag_movieName FOREIGN KEY (movieName) REFERENCES  recommendation(movieName)
);

insert into userPreference (userName,uploader,movieName,rating)VALUES('kangde','test','avatar',1);
select * from userPreference;


/*Alter table tagvalue drop foreign key fk_tagValue_tagId; */
drop table if exists tag ;
create table tag
(
	tagId int auto_increment primary key,
    recomId int not null,
     constraint  fk_tag_recomId FOREIGN KEY (recomId) REFERENCES  recommendation(recId)
);
insert into tag (recomId)value (1);
select * from tag;
select tagid ,recomId,contexts, count(contexts)as cnt from tag
where recomId=3
group by contexts
order by cnt desc ;
/*
drop table if exists tagValue; 
create table tagValue
(
	tvId int auto_increment primary key,
    tagId int not null,
    names varchar(100),
  
   constraint  fk_tagValue_tagId FOREIGN KEY (tagId) REFERENCES  tag(tagId)
);
insert into tagValue (tagId,names)value (1,'3d');
select * from tagValue;

*/
/*Alter table recommendation drop foreign key fk_recommendation_userName; */
Alter table tag drop foreign key fk_tag_recomId; 
drop table if exists Recommendation;
create table recommendation
(
	recId int not null auto_increment,
    userName varchar(30) ,
	movieName varchar(30),
    comments varchar(30),
    rating float,
    picUrl varchar(100),
    likes int,
      PRIMARY KEY (recId), 
      constraint  fk_recommendation_userName FOREIGN KEY (userName) REFERENCES  users(userName)
);
insert into recommendation (userName,movieName,comments,rating,picUrl,likes)VALUES('kangde','avatar','Best 3d movies',1,'image.jpg',0);
select * from recommendation;


Drop  Function if exists calculate_sum;
DELIMITER //
CREATE Function calculate_sum
(  userName varchar(30),
  movieName        varchar(30),
  uploader 		varchar(30)

)
RETURNS DECIMAL(9,2)
BEGIN
  DECLARE sum_rating DECIMAL(9,2); 
    select sum(rating)
	into sum_rating
	from userPreference
    
	where userPreference.movieName =movieName OR userPreference.uploader= uploader and userPreference.userName =userName;
 RETURN sum_rating;
END//

select calculate_sum("avatar","kangde","harumaki");	

select * recommendation 
order by
calculate_sum("KANGDE",recommendation.movieName,recommendation.userName,);	


select *  from recommendation order by calculate_sum("kangde",recommendation.movieName,recommendation.userName) desc;	

	

