
-- Cozinha
insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Brasileira');

-- Restaurante
-- Dependências: Cozinha
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1, 'Churrascaria do Arnaldo', 8.99, 3);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (2, 'Restaurante Pitel', 5.99, 3);

-- Forma de Pagamento
insert into forma_pagamento (id, descricao) values (1, 'Cartão de Crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de Débito');
insert into forma_pagamento (id, descricao) values (3, 'PIX');

-- Estado
insert into estado (id, nome, uf) values (1, 'Rio Grande do Norte', 'RN');
insert into estado (id, nome, uf) values (2, 'Paraíba', 'PB')

-- Cidade
insert into cidade (id, nome, estado_id) values (1, 'Natal', 1);
insert into cidade (id, nome, estado_id) values (2, 'Parnamirim', 1);
insert into cidade (id, nome, estado_id) values (3, 'João Pessoa', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campina Grande', 2);

-- Permissões
insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

-- Formas de Pagamento associados ao Restaurante
insert into restaurante_forma_pagamento values (1, 1);
insert into restaurante_forma_pagamento values (1, 2);
insert into restaurante_forma_pagamento values (1, 3);
insert into restaurante_forma_pagamento values (2, 2);
insert into restaurante_forma_pagamento values (2, 3);