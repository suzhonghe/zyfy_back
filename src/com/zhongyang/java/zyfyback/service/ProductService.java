package com.zhongyang.java.zyfyback.service;
import java.util.List;

import com.zhongyang.java.zyfyback.pojo.Product;


/**
 * 
* @Title: ProductService.java 
* @Package com.zhongyang.java.zyfyback.service 
* @Description: ProductService 
* @author 苏忠贺   
* @date 2017年6月8日 下午4:38:31 
* @version V1.0
 */
public interface ProductService {
	/**
	 * 
	* @Title: insertProduct 
	* @Description: 添加一条产品记录 
	* @return int    返回类型 
	* @throws
	 */
	public int addProduct(Product product);
	/**
	 * 
	* @Title: selectAllProduct 
	* @Description:查询所有产品 
	* @return List<Product>    返回类型 
	* @throws
	 */
	public List<Product> queryAllProduct();
	
	/**
	 * 
	* @Title: modifyProduct 
	* @Description: 修改产品 
	* @return int    返回类型 
	* @throws
	 */
	public void modifyProduct(Product product);
	/**
	 * 
	* @Title: queryProductsByStatus 
	* @Description:根据状态查询产品列表 
	* @return List<Product>    返回类型 
	* @throws
	 */
	public List<Product> queryProductsByParams(Product product);
	
}
