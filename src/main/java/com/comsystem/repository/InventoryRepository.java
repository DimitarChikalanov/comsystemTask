package com.comsystem.repository;

import com.comsystem.model.entity.InventoryEntity;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    @Modifying
    @Query(
            value =
                    """
        INSERT INTO public.inventory (item_name, warehouse_name, unit_price, quantity, unit)
        VALUES (:itemName, :warehouseName, :unitPrice, :quantity, :unit)
        ON CONFLICT (item_name, warehouse_name, unit_price)
        DO UPDATE SET quantity = inventory.quantity + EXCLUDED.quantity
        """,
            nativeQuery = true)
    void upsertInventory(
            @Param("itemName") String itemName,
            @Param("warehouseName") String warehouseName,
            @Param("unitPrice") BigDecimal unitPrice,
            @Param("quantity") BigDecimal quantity,
            @Param("unit") String unit);
}
