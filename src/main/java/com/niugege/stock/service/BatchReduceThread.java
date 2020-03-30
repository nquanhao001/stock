package com.niugege.stock.service;

import com.niugege.stock.dao.StockDAO;

public class BatchReduceThread extends Thread {

    private Long beginTime ;

    private Integer totalNum;

    private StockDAO stockDAO;

    public BatchReduceThread(Long beginTime, Integer totalNum, StockDAO stockDAO) {
        this.beginTime = beginTime;
        this.totalNum = totalNum;
        this.stockDAO = stockDAO;
    }

    public synchronized void addNum(Integer num){
        if (beginTime == null){
            beginTime = System.currentTimeMillis();
        }
        totalNum = totalNum + num;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() - beginTime > 20 && totalNum > 0){
            beginTime = null;
            stockDAO.reduceStock(totalNum);
            this.notifyAll();
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
