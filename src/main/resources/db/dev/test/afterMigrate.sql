-- Desabilita checagem de foreign keys para poder fazer a limpeza da base
set foreign_key_checks = 0;

-- Limpa todas as tabelas
delete from estado;
delete from cidade;
delete from cozinha;
delete from restaurante;
delete from produto;
delete from forma_pagamento;
delete from restaurante_forma_pagamento;
delete from permissao;
delete from usuario;
delete from grupo;
delete from grupo_permissao;
delete from usuario_grupo;
delete from pedido;
delete from item_pedido;

-- Habilita checagem de foreign keys para poder fazer a limpeza da base
set foreign_key_checks = 1;

-- Reseta as sequences
alter table estado auto_increment = 1;
alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table produto auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table permissao auto_increment = 1;
alter table usuario auto_increment = 1;
alter table grupo auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;


-- Estado
insert into estado (id, nome_estado, uf) values (1, 'Rio Grande do Norte', 'RN');
insert into estado (id, nome_estado, uf) values (2, 'Paraíba', 'PB');

-- Cidade
insert into cidade (id, nome, estado_id) values (1, 'Natal', 1);
insert into cidade (id, nome, estado_id) values (2, 'Parnamirim', 1);
insert into cidade (id, nome, estado_id) values (3, 'João Pessoa', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campina Grande', 2);

-- Cozinha
insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Brasileira');

-- Restaurante
-- Dependências: Cozinha
insert into restaurante (id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, data_cadastro, data_atualizacao) values (1, 'Churrascaria do Arnaldo', 8.99, 3, 1, '59073-100', 'Rua Paracati', 545, 'Condomínio Rosa dos Ventos', 'Planalto', utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, data_cadastro, data_atualizacao) values (2, 'Restaurante Pitel', 5.99, 3, 1, '59069-470', 'Rua Deputado Marcílio Furtado', 570, 'Próximo a escola CEEP', 'Pitimbu', utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (3, 'Lanchonete do Tio Sam', 11, 3, utc_timestamp, utc_timestamp);

-- Produtos do Restaurante
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Carne Assada', 'Carne assada na brasa', 10.99, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Picanha', 'Suculenta picanha', 69.99, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Feijão Verde', 'O melhor feijão verde', 6.99, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 2);

-- Forma de Pagamento
insert into forma_pagamento (id, descricao) values (1, 'Cartão de Crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de Débito');
insert into forma_pagamento (id, descricao) values (3, 'PIX');

-- Permissões
insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

-- Formas de Pagamento associados ao Restaurante
insert into restaurante_forma_pagamento values (1, 1);
insert into restaurante_forma_pagamento values (1, 2);
insert into restaurante_forma_pagamento values (1, 3);
insert into restaurante_forma_pagamento values (2, 2);
insert into restaurante_forma_pagamento values (2, 3);

-- Usuarios

-- Grupos de usuarios

-- Permissao dos grupos

-- Grupo associado ao usuario

-- Pedido

-- Item do Pedido