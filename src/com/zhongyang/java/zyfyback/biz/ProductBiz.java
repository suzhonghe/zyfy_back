package com.zhongyang.java.zyfyback.biz;

import com.zhongyang.java.zyfyback.params.ProductParams;
import com.zhongyang.java.zyfyback.returndata.ProductReturn;

/**
 * 
* @Title: ProductBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:产品业务处理接口 
* @author 苏忠贺   
* @date 2015年12月17日 下午3:57:13 
* @version V1.0
 */
public interface ProductBiz {
	/**
	 * 
	* @Title: addProduct 
	* @Description: 添加产品 
	* @return void    返回类型 
	* @throws
	 */
	public ProductReturn addProduct(ProductParams params);
	/**
	 * 
	* @Title: queryAllProducts 
	* @Description:查询所有产品 
	* @return List<Product>    返回类型 
	* @throws
	 */
	public ProductReturn queryAllProducts();
	/**
	 * 
	* @Title: modifyProduct 
	* @Description:修改产品 
	* @return Message    返回类型 
	* @throws
	 */
	public ProductReturn modifyProduct(ProductParams params);
	/**
	 * 
	* @Title: queryProductsByStatus 
	* @Description:根据产品状态查询产品列表
	* @return List<Product>    返回类型 
	* @throws
	 */
	public ProductReturn queryProductsByParams(ProductParams params);

}
