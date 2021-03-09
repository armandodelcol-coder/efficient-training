SET foreign_key_checks = 0;

DELETE FROM project;

SET foreign_key_checks = 1;

ALTER TABLE project auto_increment = 1;

INSERT INTO project
(name, description, created_at)
VALUES (
    "Curso Programação Orientada a Objetos com Java - Algaworks",
    "É um curso da Algaworks que ganhei como bonûs por adquirir o curso 'Especialista Spring Rest'. É uma introdução a linguagem Java e ao paradigma de programação 'orientação a objetos'",
    utc_timestamp
);