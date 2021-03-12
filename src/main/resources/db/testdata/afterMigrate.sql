SET foreign_key_checks = 0;

DELETE FROM project;
DELETE FROM task;
DELETE FROM task_resource;

SET foreign_key_checks = 1;

ALTER TABLE project auto_increment = 1;
ALTER TABLE task auto_increment = 1;
ALTER TABLE task_resource auto_increment = 1;

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

INSERT INTO project
(name, description, created_at)
VALUES (
    "Criar um sistema de controle de gastos pessoais",
    "Um sistema simples para cadastrar entradas, saídas, planejamentos financeiros, objetivos de compra, etc.",
    utc_timestamp
);
INSERT INTO task
(name, description, complexity_level, observation, status, created_at, project_id)
VALUES (
    "Fazer o Brainstorm",
    "Tirar da cabeça as ideias relacionadas ao sistema sem se importar com nenhum tipo de ordem de execução, dificuldade de implementação, possíbilidade, etc.",
    2,
    "Não é necessário um documento formal e extramente organizado, porém é preciso manter um sentido relacionado ao sistema",
    "TO_DO",
    utc_timestamp,
    2
);
INSERT INTO task
(name, description, complexity_level, observation, status, created_at, project_id)
VALUES (
    "Definir a UML",
    "Definir a UML para documentar e nortear o desenvolvimento do sistema, esse passo é super importante",
    2,
    "Costumo utilizar o StarUML",
    "TO_DO",
    utc_timestamp,
    2
);

INSERT INTO task_resource
(name, description, status, task_id)
VALUES (
    "Capturar ideias",
    "A ideia aqui é conversar com pessoas sobre planejamento financeiro e entender como elas imaginam controlar suas contas pessoas de uma forma fácil e produtiva, tendo em vista objetivos especificos",
    "MESSY",
    4
);