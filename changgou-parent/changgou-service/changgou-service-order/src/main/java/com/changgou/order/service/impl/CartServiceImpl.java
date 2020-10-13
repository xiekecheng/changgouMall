package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SpuFeign spuFeign;

    @Override
    public void add(Integer num,Long id,String username) {

        //当商品数量<=0时,移除购物车该商品
        if(num<=0){
            redisTemplate.boundHashOps("Cart_"+username).delete(id);
            Long size = redisTemplate.boundHashOps("Cart_" + username).size();
            //若购物车数量为0,则删除该购物车
            if (size==null||size<=0){
                redisTemplate.delete("Cart_" + username);
            }
            return;
        }

        //查询商品详情
        Result<Sku> skuResult = skuFeign.findById(id);
        Sku sku = skuResult.getData();

        Result<Spu> spuResult = spuFeign.findById(sku.getSpuId());
        Spu spu = spuResult.getData();

        OrderItem orderItem = createOrderItem(num, id, sku, spu);
        //存入到redis中
        redisTemplate.boundHashOps("Cart_"+username).put(id,orderItem);

    }

    @Override
    public List<OrderItem> list(String username) {
        return (List<OrderItem>) redisTemplate.boundHashOps("Cart_" + username).values();
    }

    public OrderItem createOrderItem(Integer num, Long id, Sku sku, Spu spu) {
        //封装成OrderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSpuId(spu.getId().toString());
        orderItem.setSkuId(id.toString());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setImage(spu.getImage());
        orderItem.setMoney(num *orderItem.getPrice());
        return orderItem;
    }
}
