package com.niugege.stock.dao;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StockDAO {

    //初始库存是10w
    private Integer initStock = 60000;

    private Long lockObject = 22222222L;

    public boolean reduceStock( Integer num){
        System.out.println("开始数据库操作了");
        //模拟对一个sku进行库存扣减时间，数据库会加上行锁
        synchronized (lockObject){
            try {
                if (initStock < num){
                    System.out.println("库存被扣完了，当前时间为:" + new Date());

                    System.exit(1);
                    return false;
                }
                //模拟扣减库存需要10ms
                Thread.sleep(10);
                initStock = initStock - num;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
