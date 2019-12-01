CREATE TABLE pessoa(
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR (50) NOT NULL,
    logradouro VARCHAR(50),
    numero VARCHAR(10),
    complemento VARCHAR(10),
    bairro VARCHAR(50),
    cep VARCHAR(10),
    cidade VARCHAR(50),
    estado VARCHAR(20),
    ativo BOOLEAN
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, logradouro, numero, bairro, cep, cidade, estado ,ativo) VALUES ('João Marcos', 'Rua Barbara Lais', '155', 'Vl Lais', '25581001', 'São João de Meriti', 'RJ',true);
INSERT INTO pessoa (nome, ativo) VALUES ('Marcos Paulo', true);
INSERT INTO pessoa (nome, ativo) VALUES ('Felipe', true);
INSERT INTO pessoa (nome, ativo) VALUES ('João Marcos', true);
INSERT INTO pessoa (nome, ativo) VALUES ('Camilla', true);
INSERT INTO pessoa (nome, ativo) VALUES ('Rayssa', true);
INSERT INTO pessoa (nome, ativo) VALUES ('Niviane', true);
INSERT INTO pessoa (nome, ativo) VALUES ('João Batista', true);
INSERT INTO pessoa (nome, ativo) VALUES ('Robson', true);
INSERT INTO pessoa (nome, ativo) VALUES ('Fábio', true);



