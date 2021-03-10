SET foreign_key_checks = 0;

DELETE FROM project;
DELETE FROM task;

SET foreign_key_checks = 1;

ALTER TABLE project auto_increment = 1;
ALTER TABLE task auto_increment = 1;

INSERT INTO project
(name, description, created_at)
VALUES (
    "Curso Programação Orientada a Objetos com Java - Algaworks",
    "É um curso da Algaworks que ganhei como bonûs por adquirir o curso 'Especialista Spring Rest'. É uma introdução a linguagem Java e ao paradigma de programação 'orientação a objetos'",
    utc_timestamp
);

INSERT INTO task
(name, description, complexity_level, observation, status, created_at, project_id)
VALUES (
    "Módulo 1 - Introdução",
    "No módulo 1 as aulas são mais teóricas, temos história do Java, explicação dos contextos em que é usado o Java, explicação sobre a JVM e instalação do Java",
    1,
    "Focar bem no entendimento de JVM e entender bem o processo de instalação do JDK",
    "TO_DO",
    utc_timestamp,
    1
);

INSERT INTO task
(name, description, complexity_level, status, created_at, project_id)
VALUES (
    "Módulo 2 - Fundamentos da linguagem",
    "No módulo 2 as aulas tem uma pegada prática para demonstrar os conceitos mais básicos da programação em Java, apresentando os tipos de variáveis, comentarios, palavras resenadas, estrutura de dados, funções de controle, etc.",
    1,
    "TO_DO",
    utc_timestamp,
    1
);

INSERT INTO task
(name, description, complexity_level, status, created_at, project_id)
VALUES (
    "Teste de Task",
    "Apenas uma Task para Testar",
    2,
    "DONE",
    utc_timestamp,
    1
);