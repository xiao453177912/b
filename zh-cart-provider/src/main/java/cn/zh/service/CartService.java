package cn.zh.service;

import java.util.List;

import cn.zh.common.vo.SysResult;
import cn.zh.pojo.Cart;

public interface CartService {
	public List<Cart> query(Long userId);
	public void delete(Long userId, Long itemId);
	public SysResult save(Cart cart);
	public void updateNum(Cart cart);
}
