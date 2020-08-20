# sistema-seguranca-digital
Sistema de Segurança Digital (Caso de Uso)

Executar a api backend para que as tabelas sejam criadas pelo Hibernate (é necessário ajustar os dados do banco no arquivo application.properties)

Criar usuário no sistema (usuário: admin, senha: teste):
INSERT INTO user_system(
id, active, password, roles, username)
VALUES (3, true, '$2a$14$65eM9uTMWoKd9iFT2DKeK.bpaxMbMkXNB/hapCgkEHxrYa.AjE3DO', 'ROLE_ADMIN', 'admin');
