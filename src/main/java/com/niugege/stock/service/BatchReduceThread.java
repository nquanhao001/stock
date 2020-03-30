package com.niugege.stock.service;

import com.niugege.stock.dao.StockDAO;

public class BatchReduceThread extends Thread {

    private volatile Long beginTime ;

    private volatile Integer totalNum;

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
        System.out.println("线程开始启动了");
        while (true){
            if (System.currentTimeMillis() - beginTime > 20 && totalNum > 0){
                beginTime = System.currentTimeMillis();
                int toReduceNum = totalNum;
                stockDAO.reduceStock(toReduceNum);
                totalNum = totalNum - toReduceNum;
                synchronized (this){
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
