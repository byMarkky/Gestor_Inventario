DELIMITER //

CREATE PROCEDURE getTopPurchasingClient()
BEGIN
    -- Seleccionar el cliente que ha comprado la mayor cantidad de unidades en total.
    SELECT CLIENT.*
    FROM CLIENT
    JOIN SALES ON CLIENT.id = SALES.CLIENT_ID
    GROUP BY CLIENT.ID
    ORDER BY SUM(SALES.QUANTITY) DESC
    LIMIT 1;
END //

DELIMITER ;
