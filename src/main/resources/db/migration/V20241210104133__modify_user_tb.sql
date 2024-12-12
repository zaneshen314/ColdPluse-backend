UPDATE user
SET status = '0';

alter table user
    modify status char default '0' null;
