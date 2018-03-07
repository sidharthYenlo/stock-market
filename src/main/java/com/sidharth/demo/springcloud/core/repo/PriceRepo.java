package com.sidharth.demo.springcloud.core.repo;

import com.sidharth.demo.springcloud.core.model.Price;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PriceRepo extends Repository<Price,Long> {


    @Query(value = "SELECT * FROM Price  WHERE Price.stock_id= :stockId ORDER BY Price.updated_time_stamp DESC LIMIT :maxLimit", nativeQuery = true)
    List<Price> findById(@Param("stockId") Long stockId,@Param("maxLimit") int maxLimit);

    @Query(value = "SELECT * FROM Price  WHERE Price.stock_id = :stockId", nativeQuery = true)
    @Transactional
    List<Price> findAllById(@Param("stockId") Long stockId);


    @Modifying
    @Query(value = "INSERT INTO Price (stock_id,price) VALUES (:stockId,:price)", nativeQuery = true)
    @Transactional
     void createPrice(@Param("stockId") Long stockId, @Param("price") Double price) ;


    @Modifying
    @Transactional
    @Query(value = "TRUNCATE Price", nativeQuery = true)
    void deleteAll();



}
