package cn.zh.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import cn.zh.common.vo.SysResult;
import cn.zh.mapper.CartMapper;
import cn.zh.pojo.Cart;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private CartMapper cartMapper;
	
	//我的购物车，查询：where id=?
	public List<Cart> query(Long userId){
		EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
		wrapper.where("user_id={0}", userId);
		
		return cartMapper.selectList(wrapper);
	}
	
	//新增商品到购物车，按对象方式接收参数
	public SysResult save(Cart cart){
		//1.判断此用户此商品是否存在
		Cart param = new Cart();
		param.setUserId(cart.getUserId());
		param.setItemId(cart.getItemId());
		
		Cart oldCart = cartMapper.selectOne(param);	//pojo，属性怎么出现在where条件中，属性是否为null，为null不出现，不为null就出现
		if(oldCart==null){	//2.如果不存在，新增操作
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
			return SysResult.ok();
		}else{	//3.如果存在，修改数量操作=旧的数量+新的数量
			oldCart.setNum(oldCart.getNum() + cart.getNum());
			cartMapper.updateById(oldCart);
			return SysResult.build(202, "此用户此商品已经存在购物车中");
		}
	}
	
	//更新商品数量+-，页面的值替换掉数据库
	public void updateNum(Cart cart){
		EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
		//where user_id=? and item_id?
		wrapper.where("user_id={0}", cart.getUserId());
		wrapper.and("item_id={0}", cart.getItemId());
		
		cart.setUpdated(new Date());
		cartMapper.update(cart, wrapper);
	}
	
	
	//删除某用户的某商品
	public void delete(Long userId, Long itemId){
		EntityWrapper<Cart> wrapper = new EntityWrapper<Cart>();
		//where user_id=? and item_id=? 方式QBC
		wrapper.where("user_id={0}", userId);
		wrapper.and("item_id={0}", itemId);
		
		cartMapper.delete(wrapper);
	}
}
