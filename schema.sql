create database invskins;
use invskins;

create table skins(
	id bigint not null auto_increment,
	external_id VARCHAR(255),
	nome VARCHAR(200) not null,
	arma VARCHAR(200) not null,
	raridade VARCHAR(50),
	imagem VARCHAR(500),
	preco DOUBLE,
	primary key (id)
);

CREATE TABLE inventarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    data_criacao DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE itens_inventario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    inventario_id BIGINT NOT NULL,
    skin_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_item_inventario
        FOREIGN KEY (inventario_id) REFERENCES inventarios (id),
    CONSTRAINT fk_item_skin
        FOREIGN KEY (skin_id) REFERENCES skins (id)
);