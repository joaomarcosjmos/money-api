CREATE TABLE lancamento(
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(50) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(100),
    tipo VARCHAR(20) NOT NULL,
    codigo_categoria BIGINT(20) NOT NULL,
    codigo_pessoa BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_categoria) REFERENCES moneyapi.categoria(codigo),
    FOREIGN KEY (codigo_pessoa) REFERENCES moneyapi.pessoa(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Salario mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 5, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Extra', '2019-06-11', null, 7500.00, 'Desepesas pessoais', 'DESPESA', 2, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Medicamentos', '2017-12-25', null, 500.00, 'Remédio para tratamento de resfriado', 'DESPESA', 4, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Compra Semanal', '2019-12-11', null, 4000.00, 'Compra semanal de alimentos', 'DESPESA', 3, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Salario mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 4);