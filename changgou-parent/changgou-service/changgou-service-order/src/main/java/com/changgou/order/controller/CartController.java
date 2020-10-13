package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author xiekecheng
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 加入购物车
     * 商品数量
     * 商品ID
     */
    @GetMapping("/add")
    public Result add(Integer num,Long id){
        cartService.add(num,id,"test");
        return new Result(true, StatusCode.OK,"加入购物车成功");
    }

    /**
     * 查询购物车
     */
    @GetMapping(value = "/list")
    public Result<List<OrderItem>> list(String username){
        List<OrderItem> list = cartService.list(username);
        return new Result<>(true,StatusCode.OK,"查询购物车成功",list);
    }

}
