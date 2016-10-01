DELETE FROM contact_hobby_detail;
DELETE FROM contact_tel_detail;
DELETE FROM hobby;
DELETE FROM contact;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

insert into contact (first_name, last_name, birth_date) values ('Chris', 'Schaefer', '1981-05-03');
insert into contact (first_name, last_name, birth_date) values ('Scott', 'Tiger', '1990-11-02');
insert into contact (first_name, last_name, birth_date) values ('John', 'Smith', '1964-02-28');

insert into contact_tel_detail (contact_id, tel_type, tel_number) values (100000, 'Mobile', '1234567890');
insert into contact_tel_detail (contact_id, tel_type, tel_number) values (100000, 'Home', '1234567890');
insert into contact_tel_detail (contact_id, tel_type, tel_number) values (100001, 'Home', '1234567890');

insert into hobby (hobby_id) values ('Swimming');
insert into hobby (hobby_id) values ('Jogging');
insert into hobby (hobby_id) values ('Programming');
insert into hobby (hobby_id) values ('Movies');
insert into hobby (hobby_id) values ('Reading');

insert into contact_hobby_detail(contact_id, hobby_id) values (100000, 'Swimming');
insert into contact_hobby_detail(contact_id, hobby_id) values (100000, 'Movies');
insert into contact_hobby_detail(contact_id, hobby_id) values (100001, 'Swimming');
