package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.Product;

/**
 * 
* @Title: ProductDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 产品DAO 
* @author 苏忠贺   
* @date 2015年12月16日 下午3:53:17 
* @version V1.0
 */
public interface ProductDao {
	/**
	 * 
	* @Title: insertProduct 
	* @Description: 添加一条产品记录 
	* @return void    返回类型 
	* @throws
	 */
	public int insertProduct(Product product);
	/**
	 * 
	* @Title: selectAllProduct 
	* @Description:查询所有产品 
	* @return void    返回类型 
	* @throws
	 */
	public List<Product> selectAllProduct();
	
	/**
	 * 
	* @Title: updateProduct 
	* @Description: 更新产品
	* @return void    返回类型 
	* @throws
	 */
	public void updateProduct(Product product);
	/**
	 * 
	* @Title: selectProductsByStatus 
	* @Description:根据参数查询产品列表 
	* @return List<Product>    返回类型 
	* @throws
	 */
	public List<Product> selectProductsByParams(Product product);
}
