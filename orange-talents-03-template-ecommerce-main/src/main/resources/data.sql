INSERT INTO tb_usuario (email, senha, data_cadastro) VALUES ('moderador@email.com', '$2a$10$bjSu8X6V1enJ8rQj2TIc/OlV8Q1GWm8Sbqo2.IbpltO9nJVdiENLK', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z');

INSERT INTO Perfil (nome) VALUES ('ROLE_MODERADOR');

INSERT INTO tb_usuario_perfis (usuario_id, perfis_id) VALUES (1, 1);

INSERT INTO tb_categoria (nome) VALUES ('Tecnologia');
INSERT INTO tb_categoria (nome) VALUES ('Livros');
INSERT INTO tb_categoria (nome) VALUES ('Veiculos');
INSERT INTO tb_categoria (nome, categoria_mae_id) VALUES ('Smartphones', 1);
INSERT INTO tb_categoria (nome, categoria_mae_id) VALUES ('Moto', 3);


INSERT INTO tb_produto (data_registro, descricao, nome, quantidade, valor, categoria_id, usuario_logado_id) VALUES (TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', 'Um celular moderno', 'Celular motorola', 5, 1000, 1, 1);

INSERT INTO caracteristica_produto (descricao, nome, produto_id) VALUES ('Celular bem compacto', 'Capacidade 50 GB', 1);
INSERT INTO caracteristica_produto (descricao, nome, produto_id) VALUES ('Uma textura bem fina', 'Gravação de video', 1);
INSERT INTO caracteristica_produto (descricao, nome, produto_id) VALUES ('cor preta', 'Camera frontal', 1);