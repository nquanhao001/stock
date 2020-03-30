package com.niugege.stock.web;

import com.niugege.stock.dao.StockDAO;
import com.niugege.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class StockController {

    @Autowired
    private StockDAO stockDAO;

    @Autowired
    private StockService stockService;

    @RequestMapping("/reduce")
    public String reduce(){
        /*boolean success = stockDAO.reduceStock( 20);
        return success ? "ok" : "失败";*/
        boolean success = stockService.reduce(20);
        return success ? "ok" : "失败";
    }

}
