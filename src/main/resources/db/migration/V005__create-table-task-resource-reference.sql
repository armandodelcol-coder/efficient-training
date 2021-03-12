CREATE TABLE task_resource_reference (
    id bigint not null auto_increment,
    link varchar(255) not null,
    description text not null,
    task_resource_id bigint not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

ALTER TABLE task_resource_reference ADD CONSTRAINT fk_task_resource_reference_resource FOREIGN KEY (task_resource_id) REFERENCES task_resource (id);