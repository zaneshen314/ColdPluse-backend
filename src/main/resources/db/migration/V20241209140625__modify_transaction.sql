alter table transaction
    modify amount_in_local_curr double null;

alter table transaction
drop column column_name;