This one is coupled with the blog post here - https://medium.com/@neuw/spring-boot-passwordless-login-using-ott-along-with-custom-ui-470f117b8e13

Add these entries with two users, **user** and **admin**, and password = **test**

```sql
-- create database
create database spring_security_ott;

-- insert two users, one named user & another one is admin, both with password = test
insert into spring_security_ott.users (id, created, deleted, email, email_verified, enabled, first_name, last_login, last_name, middle_name, modified, password, phone, username)
values  ('0193d2b3-2fb6-7e3d-829a-a97e463db956', '2024-12-23 14:23:25.000000', 0, 'some@email.com', 0, 1, null, '2024-12-23 14:24:02.000000', null, null, '2024-12-23 14:24:07.000000', '$2a$10$GC9kHwD3PvHrtcSBA7TAPe8j92nGFJmQKW5IKxe6bBEf8ouZjuS2u', null, 'user'),
('0193d2bc-ecb8-700d-bfeb-7a2c0f7424c3', '2024-12-23 14:23:25.000000', 0, 'other@email.com', 0, 1, null, '2024-12-23 14:24:02.000000', null, null, '2024-12-23 14:24:07.000000', '$2a$10$GC9kHwD3PvHrtcSBA7TAPe8j92nGFJmQKW5IKxe6bBEf8ouZjuS2u', null, 'admin');
```


