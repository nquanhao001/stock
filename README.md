# stock
电商中有这种场景，扣减某个sku的库存，基于mysql的方案，扣减库存的sql如下：

update stock = stock - #{num} where sku_id = #{skuID} and stock >= #{num}；

这样的写法正常情况下是没有问题的，但是在热点商品秒杀情况下，因为库存扣减的sql是会有数据库的行锁，假设单次sql的行锁是10ms，那么qps最多就是100，无法满足大促的需求。

  

优化思路：

既然瓶颈是db的行锁，那么能不能不要每次请求都来执行sql，把多个库存扣减的请求进行合并为1次sql的操作

  ![](http://chuantu.xyz/t6/726/1585558133x3752237043.png)