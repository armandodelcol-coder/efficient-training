CREATE TABLE task (
    id bigint not null auto_increment,
    name varchar(60) not null,
    description text not null,
    complexity_level tinyint not null,
    observation text,
    status varchar(15) not null,
    created_at datetime not null,
    project_id bigint not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

ALTER TABLE task ADD CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES project (id);