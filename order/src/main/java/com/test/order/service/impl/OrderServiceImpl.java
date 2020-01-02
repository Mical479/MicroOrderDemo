package com.test.order.service.impl;

import com.netflix.discovery.converters.Auto;
import com.test.order.beans.*;
import com.test.order.dao.*;
import com.test.order.enums.CommonEnum;
import com.test.order.enums.OrderStatus;
import com.test.order.exception.CustomException;
import com.test.order.service.OrderService;
import com.test.order.service.component.RabbitProduct;
import com.test.order.utils.OrderCodeUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author MicalJ
 * @create 2019/12/5 10:54
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private DetailOrderDao detailOrderDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private static final String STOCK_PREFIX_LOCK = "goods_stock_lock_";

    public static final String ORDER_PREFIX = "order_";

    @Autowired
    private RabbitProduct rabbitProduct;

    /**
     * 根据传过来的购物车id，生成订单信息
     *
     * @param list
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public Order insertOrderForm(List<Integer> list, Integer userId) {

        /*** 首先判断用户是否存在默认地址，没有默认地址就让他去设置 ***/

        //获取默认地址,并判断
        Address defaultAddress = addressDao.getDefaultAddress(userId);
        if (defaultAddress == null) {
            throw new CustomException(CommonEnum.NO_DEFAULT_ADDRESS);
        }

        //根据cart的list获取提交的订单信息
        List<Cart> cartList = cartDao.getCartListByCartId(list);
        if (cartList == null || cartList.size() == 0) {
            throw new CustomException(CommonEnum.PURCHARSE_CART_NO_THIS_GOODS);
        }

        //订单物品总数量
        int sum = cartList.stream().mapToInt(Cart::getCartNumber).sum();
        //订单物品总价格
        Double price = cartList.stream().mapToDouble(item -> item.getCartNumber() * item.getGoods().getGoodsPrice()).sum();
        //订单ID
        String orderId = OrderCodeUtil.getOrderCode(userId);

        //封装订单实体
        Order order = Order.builder().addressId(defaultAddress.getAddressId())
                .orderId(orderId)
                .userId(userId)
                .orderCreateTime(new Date())
                .orderStatus(OrderStatus.NEW_ORDER)
                .orderPrice(price)
                .orderCount(sum)
                .build();

        //封装订单详情列表
        List<DetailOrder> detailOrders = new ArrayList<>();
        cartList.forEach(item -> {
            detailOrders.add(DetailOrder.builder()
                    .goods(item.getGoods())
                    .detailOrderCount(item.getCartNumber())
                    .detailOrderPrice(item.getCartNumber() * item.getGoods().getGoodsPrice())
                    .orderId(orderId)
                    .build());
        });

        //将订单数据和订单详情数据插入数据库
        if (orderDao.insertOrder(order) <= 0 || detailOrderDao.insertOrderList(detailOrders) <= 0) {
            throw new CustomException(CommonEnum.ORDER_BUILD_ERROR);
        }

        //将购物车中的列表从数据库中删除
        if (cartDao.deleteCartListByCartId(list) <= 0) {
            throw new CustomException(CommonEnum.ORDER_BUILD_ERROR);
        }

        //更新库存
        for (int i = 0; i < cartList.size(); i++) {
            Cart item = cartList.get(i);
            Goods goods = new Goods();
            goods.setGoodsId(item.getGoods().getGoodsId());
            goods.setGoodsStock(-item.getCartNumber());
            if (!updateGoodsStock(goods)) {
                throw new CustomException(CommonEnum.UPDATE_GOODS_STOCK_ERROR);
            }
        }

        order.setOrderList(detailOrders);
        redisTemplate.opsForValue().set(ORDER_PREFIX + orderId, "locked", 20, TimeUnit.MINUTES);
        rabbitProduct.sendDelayMessage(orderId);
        return order;
    }

    /**
     * 根据订单id，查询订单
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderByOrderId(String orderId) {
        Order orderById = orderDao.getOrderById(orderId);
        if (orderById != null) {
            return orderById;
        }
        throw new CustomException(CommonEnum.ORDER_NOT_EXIST);
    }

    /**
     * 更新订单状态、支付时间等信息
     *
     * @param order
     */
    @Override
    public void updateOrder(Order order) {
        if (orderDao.updateOrder(order) <= 0) {
            throw new CustomException(CommonEnum.ORDER_UPDATE_ERROR);
        }
    }

    /**
     * 根据订单状态和user_id获取订单
     *
     * @param order
     * @return
     */
    @Override
    public List<Order> getOrderByStatus(Order order) {
        return orderDao.getPayOrders(order);
    }

    /**
     * 更新库存操作
     *
     * @param goods
     */
    @Override
    public boolean updateGoodsStock(Goods goods) {
        RLock lock = redissonClient.getLock(STOCK_PREFIX_LOCK + goods.getGoodsId());
        boolean flag = false;
        //设置锁超时时间
        Boolean cacheRes = null;
        try {
            cacheRes = lock.tryLock(60, 10, TimeUnit.SECONDS);
            if (cacheRes) {
                Integer goodsStock = goodsDao.getGoodsStock(goods.getGoodsId());
//                Integer goodsStock = (Integer) redisTemplate.opsForValue().get("goods_stock_" + goods.getGoodsId());
                flag = goodsStock >= (-goods.getGoodsStock());
                System.out.println(Thread.currentThread().getName() + "------》加锁goods" + goods.getGoodsId() + " 库存：" + goodsStock + "， 待减库存：" + goods.getGoodsStock() + ", 是否有库存：" + flag + "-" + lock.isLocked() + "===" + lock.getName());
                System.out.println("==================================");
                //判断库存是否剩余
                if (flag) {
                    goodsDao.updateGoodsStock(goods);
//                    redisTemplate.opsForValue().set("goods_stock_" + goods.getGoodsId(), goodsStock + goods.getGoodsStock());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Integer goodsStock2 = goodsDao.getGoodsStock(goods.getGoodsId());
//            Object goodsStock2 = redisTemplate.opsForValue().get("goods_stock_" + goods.getGoodsId());
            System.out.println(Thread.currentThread().getName() + "------》解锁goods" + goods.getGoodsId() + ":" + goodsStock2);
            lock.unlock();
        }

        return flag;
    }

}
