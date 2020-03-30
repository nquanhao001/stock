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

    //进行优化的扣减接口，通过hold住请求线程，把多个库存扣减请求，进行合并为1次db的扣减
    @RequestMapping("/reduce")
    public String reduce(){
        boolean success = stockService.reduce(20);
        return success ? "ok" : "失败";
    }

    //正常进行库存扣减，每一次扣减都是走到db层
    @RequestMapping("/reduceNormal")
    public String reduceNormal(){
        boolean success = stockDAO.reduceStock( 20);
        return success ? "ok" : "失败";
    }

}
