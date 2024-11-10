DELIMITER //

CREATE PROCEDURE getMostPurchasedProduct()
BEGIN
    -- Seleccionar el producto que ha tenido la mayor cantidad de unidades vendidas en total.
    SELECT PRODUCT.*
    FROM PRODUCT
    JOIN SALES ON PRODUCT.id = SALES.PRODUCT_ID
    GROUP BY PRODUCT.id
    ORDER BY SUM(SALES.QUANTITY) DESC
    LIMIT 1;
END //

DELIMITER ;
