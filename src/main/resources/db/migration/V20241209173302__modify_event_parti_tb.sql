alter table charity_event_participation
drop column enrolled;

alter table charity_event_participation
drop column finished;

alter table charity_event_participation
    add status varchar(24) default 'REGISTERED' not null;

alter table charity_event_participation
drop column closed;