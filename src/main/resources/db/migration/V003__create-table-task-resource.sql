CREATE TABLE task_resource (
    id bigint not null auto_increment,
    name varchar(60) not null,
    description text not null,
    status varchar(15) not null,
    task_id bigint not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

ALTER TABLE task_resource ADD CONSTRAINT fk_task_resource_task FOREIGN KEY (task_id) REFERENCES task (id);