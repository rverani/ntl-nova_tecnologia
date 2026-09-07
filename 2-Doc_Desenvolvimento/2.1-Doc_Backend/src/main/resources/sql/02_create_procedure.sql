-- ============================================================
-- Procedure que retorna, via cursor de saida, a lista de
-- usuarios de uma determinada origem (tipo de usuario).
-- ============================================================
CREATE OR REPLACE PROCEDURE PRC_LISTAR_USUARIO_POR_ORIGEM (
    P_ORIGEM   IN  VARCHAR2,
    P_CURSOR   OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN P_CURSOR FOR
        SELECT
            U.ID_USU,
            U.NOME_USU,
            U.MATR_USU,
            U.DATA_NASC,
            U.EMAIL,
            U.ORIGEM,
            T.DESCR
        FROM USUARIO U
        INNER JOIN TIPO_USUARIO T ON T.ORIGEM = U.ORIGEM
        WHERE U.ORIGEM = P_ORIGEM
        ORDER BY U.NOME_USU;
END PRC_LISTAR_USUARIO_POR_ORIGEM;
/
