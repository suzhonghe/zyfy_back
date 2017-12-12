package com.zhongyang.java.zyfyback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProductDao;
import com.zhongyang.java.zyfyback.pojo.Product;
import com.zhongyang.java.zyfyback.service.ProductService;

/**
 * 
* @Title: ProductAerviceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:项目管理接口实现
* @author 苏忠贺   
* @date 2015年12月17日 下午3:50:36 
* @version V1.0
 */
@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;

	@Override
	public int addProduct(Product product){
		return productDao.insertProduct(product);
	}

	@Override
	public List<Product> queryAllProduct(){
		return productDao.selectAllProduct();
	}

	@Override
	public void modifyProduct(Product product){
		productDao.updateProduct(product);
	}

	@Override
	public List<Product> queryProductsByParams(Product product){
		return productDao.selectProductsByParams(product);
	}
}
