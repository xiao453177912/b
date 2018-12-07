package cn.zh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.zh.common.vo.SysResult;
import cn.zh.pojo.Cart;
import cn.zh.service.CartService;

@RestController
public class CartController {
	@Autowired
	private CartService cartService;

	@RequestMapping("/cart/query/{userId}")
	public SysResult query(@PathVariable("userId") Long userId) {
		try {
			List<Cart> cartList = cartService.query(userId);
			return SysResult.ok(cartList);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "查询我的购物车失败!");
		}
	}

	@RequestMapping("/cart/save")		//以post请求来访问
	public SysResult save(@RequestBody Cart cart) {
		return cartService.save(cart);
	}
	
	@RequestMapping("/cart/update/num")
	public SysResult updateNum(@RequestBody Cart cart){
		try{
			cartService.updateNum(cart);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "修改商品数量出错!");
		}
	}

	@RequestMapping("/cart/delete/{userId}/{itemId}")
	public SysResult delete(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId) {
		try {
			cartService.delete(userId, itemId);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "删除商品出错!");
		}
	}
}
