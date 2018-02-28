package com.sidharth.demo.springcloud.core.Repo;

import com.sidharth.demo.springcloud.core.Model.Stocks;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StocksRepo  extends Repository<Stocks,Long> {
//
//    @Query(value = "SELECT * from  stocks WHERE stocks.id = :id", nativeQuery = true)
//    List<Stocks> findStocksById(Long id);

    @Query(value = "SELECT * from  stocks WHERE stocks.id = :id", nativeQuery = true)
    Stocks findById(@Param("id") Long id );

    @Query(value = "SELECT * from  stocks", nativeQuery = true)
    List<Stocks> findAll();

    @Modifying
    @Query(value = "insert into stocks (stock_name,stock_code) VALUES (:stockName,:stockCode)", nativeQuery = true)
    @Transactional
    void  createStocks(@Param("stockName") String stockName, @Param("stockCode") String stockCode);


    @Query(value = "SELECT * from  stocks WHERE stocks.stock_code = :stockCode", nativeQuery = true)
    Stocks findStocksByStockCode(@Param("stockCode") String stockCode);

    @Query(value = "SELECT * from  stocks WHERE stocks.stock_name = :stockName", nativeQuery = true)
    Stocks findStocksByStockName(@Param("stockName") String stockName);


    @Modifying
    @Transactional
    @Query(value = "UPDATE stocks SET stocks.stock_name= :stockName WHERE stocks.id= :stockId", nativeQuery = true)
    void updateStockName(@Param("stockName") String stockName, @Param("stockId") long stockId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM stocks WHERE stocks.stock_code = :stockCode", nativeQuery = true)
    void removeStock( @Param("stockCode") String stockCode);


    @Modifying
    @Transactional
    @Query(value = "TRUNCATE stocks", nativeQuery = true)
    void deleteAll();



}
