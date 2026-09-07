-- ============================================================
-- Tabela de tipos de usuario
-- ============================================================
CREATE TABLE TIPO_USUARIO (
    ID_TIPOUSUARIO  NUMBER(10)      NOT NULL,
    ORIGEM          VARCHAR2(1)     NOT NULL,
    DESCR           VARCHAR2(100)   NOT NULL,
    CONSTRAINT PK_TIPO_USUARIO PRIMARY KEY (ID_TIPOUSUARIO),
    CONSTRAINT UK_TIPO_USUARIO_ORIGEM UNIQUE (ORIGEM)
);

-- ============================================================
-- Tabela de usuario, com FK para TIPO_USUARIO via ORIGEM
-- ============================================================
CREATE TABLE USUARIO (
    ID_USU      NUMBER(10)      NOT NULL,
    NOME_USU    VARCHAR2(150)   NOT NULL,
    MATR_USU    VARCHAR2(30),
    DATA_NASC   DATE,
    EMAIL       VARCHAR2(150),
    ORIGEM      VARCHAR2(1)     NOT NULL,
    CONSTRAINT PK_USUARIO PRIMARY KEY (ID_USU),
    CONSTRAINT FK_USUARIO_ORIGEM FOREIGN KEY (ORIGEM)
        REFERENCES TIPO_USUARIO (ORIGEM)
);

-- ============================================================
-- Sequence usada pelo JPA para gerar ID_USU
-- ============================================================
CREATE SEQUENCE SEQ_USUARIO
    START WITH 50000
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
