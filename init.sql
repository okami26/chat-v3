
create table if not exists users(
                                    id int generated by default as identity primary key,
                                    username varchar(20),
                                    password varchar(20),
                                    firstname varchar(20),
                                    lastname varchar(20),
                                    email varchar(50),
                                    phone varchar(12)
);



create table if not exists users_images(
                                           id int primary key generated by default as identity,
                                           username varchar,
                                           name varchar,

                                           type varchar,
                                           path varchar
);

create table if not exists feed (
                                    id int primary key generated by default as identity ,
                                    content varchar,
                                    sender varchar,
                                    type varchar,
                                    date varchar

)