package org.mystock.repository;

import org.mystock.entity.ClientOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrderEntity, Long> {

    @Query("""
                SELECT c
                FROM ClientOrderEntity c
                WHERE (:orderNumber IS NULL OR c.orderNumber = :orderNumber)
                AND (:clientId IS NULL OR c.client.id = :clientId)
                AND (:fromOrderDate IS NULL OR c.orderDate >= :fromOrderDate)
                AND (:toOrderDate IS NULL OR c.orderDate <= :toOrderDate)
                ORDER BY c.orderDate DESC
            """)
    List<ClientOrderEntity> findAll(@Param("orderNumber") Integer orderNumber, @Param("clientId") Long clientId,
                                           @Param("fromOrderDate") LocalDate fromOrderDate, @Param("toOrderDate") LocalDate toOrderDate);

    @Query("""
            select c from 
            ClientOrderEntity c 
            inner join c.orderItems items
            where 
            (:orderNumber IS NULL OR c.orderNumber = :orderNumber) 
            and (:fromOrderDate IS NULL OR c.orderDate >= :fromOrderDate) 
            and (:toOrderDate IS NULL OR c.orderDate <= :toOrderDate) 
            and (:clientId IS NULL OR c.client.id = :clientId)  
            and (:designId IS NULL OR items.design.id = :designId)  
            and (:colorId IS NULL OR items.color.id = :colorId) 
            and (:qualityId IS NULL OR items.quality.id = :qualityId)
            """)
    List<ClientOrderEntity> findAll(@Param("orderNumber") Integer orderNumber,
                                    @Param("fromOrderDate") LocalDate fromOrderDate, @Param("toOrderDate") LocalDate toOrderDate,
                                    @Param("clientId") Long clientId,
                                    @Param("designId") Long designId, @Param("colorId") Long colorId, @Param("qualityId") Long qualityId);


    @Query("""
                SELECT c
                FROM ClientOrderEntity c
                WHERE (c.orderDate = :orderDate)
                ORDER BY c.id DESC
            """)
    List<ClientOrderEntity> getRecentOrders(@Param("orderDate") LocalDate orderDate);

    @Query(value = "SELECT COUNT(*) FROM CLIENT_ORDER_INFO WHERE ORDER_DATE >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-01') AND ORDER_DATE < DATE_FORMAT(CURRENT_DATE + INTERVAL 1 MONTH, '%Y-%m-01')", nativeQuery = true)
    Integer getCurrentMonthOrderCount();
}