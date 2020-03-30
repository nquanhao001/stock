package com.niugege.stock.service;

import com.niugege.stock.dao.StockDAO;

public class BatchReduceThread extends Thread {

    /**
     * 当前线程开始等待时间
     */
    private volatile Long beginTime ;

    /**
     * 要统一扣减的数量
     */
    private volatile Integer totalNum;


    private StockDAO stockDAO;

    /**
     * 当前等待的线程数量
     */
    private volatile Integer waitThreadCount;

    public BatchReduceThread(Long beginTime, Integer totalNum, StockDAO stockDAO) {
        this.beginTime = beginTime;
        this.totalNum = totalNum;
        this.stockDAO = stockDAO;
        this.waitThreadCount = 0;
    }

    public synchronized void addNum(Integer num){
        if (beginTime == null){
            beginTime = System.currentTimeMillis();
        }
        totalNum = totalNum + num;
        waitThreadCount = waitThreadCount + 1;
    }

    @Override
    public void run() {
        System.out.println("线程开始启动了");
        while (true){
            //如果当前阻塞了15个调用线程，那么会进行触发真正的db库存。  如果被阻塞的第一个调用线程已经等待 20ms，也要触发真正的db扣减库存
            if ((System.currentTimeMillis() - beginTime > 20 || waitThreadCount > 15)&& totalNum > 0){
                synchronized (this){
                    //db扣库存
                    stockDAO.reduceStock(totalNum);
                    totalNum = 0;
                    //线程数量归0
                    waitThreadCount = 0;
                    //重置库存归0时间
                    beginTime = System.currentTimeMillis();
                    this.notifyAll();
                }
            }
        }

    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public StockDAO getStockDAO() {
        return stockDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }
}
