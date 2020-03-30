package com.niugege.stock.service;

import com.niugege.stock.dao.StockDAO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService implements InitializingBean {

    @Autowired
    private StockDAO stockDAO;

    private BatchReduceThread batchReduceThread = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        batchReduceThread = new BatchReduceThread(System.currentTimeMillis(), 0, stockDAO);
        batchReduceThread.start();
    }

    public boolean reduce(Integer num){
        try {
            batchReduceThread.addNum(num);
            synchronized (batchReduceThread){
                batchReduceThread.wait(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
