package com.zhongyang.java.zyfyback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.authority.Authorities;
import com.zhongyang.java.system.authority.FireAuthority;
import com.zhongyang.java.zyfyback.biz.ProductBiz;
import com.zhongyang.java.zyfyback.params.ProductParams;
import com.zhongyang.java.zyfyback.returndata.ProductReturn;
/**
 * 
* @Title: ProductController.java 
* @Package com.zhongyang.java.controller 
* @Description: 产品控制器 
* @author 苏忠贺   
* @date 2015年12月21日 上午10:48:32 
* @version V1.0
 */
@CrossOrigin
@Controller
public class ProductController extends BaseController{
	
	@Autowired
	private ProductBiz productBiz;
	
	/**
	 * 
	* @Title: addProduct 
	* @Description:添加产品 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRDADD)
	@RequestMapping(value="/back/product/addProduct", method = RequestMethod.POST)
	public @ResponseBody ProductReturn addProduct(@RequestBody ProductParams params){
		return productBiz.addProduct(params);
	}
	/**
	 * 
	* @Title: queryAllProducts 
	* @Description:查询所有产品 
	* @return List<Product>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRDLIST)
	@RequestMapping(value="/back/product/queryAllProducts", method = RequestMethod.POST)
	public @ResponseBody ProductReturn queryAllProducts(){
		return productBiz.queryAllProducts();
	}; 
	
	/**
	 * 
	* @Title: modifyProduct 
	* @Description:修改产品 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRDUPD)
	@RequestMapping(value="/back/product/modifyProduct", method = RequestMethod.POST)
	public @ResponseBody ProductReturn modifyProduct(@RequestBody ProductParams params){
		return productBiz.modifyProduct(params);
	}
	/**
	 * 
	* @Title: queryProductByStatus 
	* @Description:根据状态查询产品列表 
	* @return List<Product>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/back/product/queryProductByStatus", method = RequestMethod.POST)
	public @ResponseBody ProductReturn queryProductByParams(ProductParams params){
		return productBiz.queryProductsByParams(params);
	}
}
