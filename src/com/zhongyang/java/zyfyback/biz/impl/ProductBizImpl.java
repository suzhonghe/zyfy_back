package com.zhongyang.java.zyfyback.biz.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.zyfyback.biz.ProductBiz;
import com.zhongyang.java.zyfyback.params.ProductParams;
import com.zhongyang.java.zyfyback.pojo.Product;
import com.zhongyang.java.zyfyback.returndata.ProductReturn;
import com.zhongyang.java.zyfyback.service.ProductService;

/**
 * 
 * @Title: ProductBizImpl.java
 * @Package com.zhongyang.java.biz.impl
 * @Description: 产品业务处理接口实现
 * @author 苏忠贺
 * @date 2015年12月17日 下午3:59:00
 * @version V1.0
 */
@Service
public class ProductBizImpl implements ProductBiz {

	private static Logger logger = Logger.getLogger(ProductBizImpl.class);

	@Autowired
	private ProductService productService;

	@Override
	@Transactional
	public ProductReturn addProduct(ProductParams params) {
		ProductReturn pr = new ProductReturn();
		Product product = params.getProduct();
		if (params == null || product == null) {
			logger.info("未获得页面产品相关信息");
			pr.setMessage(new Message(SystemEnum.PARAMS_ERROR.value(), "参数不正确"));
			return pr;
		}
		params.getProduct();
		product.setId(GetUUID.getUniqueKey());
		if (product.getRate() == null || product.getRepayMethod() == null || product.getMaxInvestAmount() == null
				|| product.getMinInvestAmount() == null || product.getMonths() == null || product.getName() == null) {
			logger.info("页面获得的数据不全" + product.toString());
			pr.setMessage(new Message(SystemEnum.PARAMS_ERROR.value(), "参数不正确"));
			return pr;
		}
		product.setRate(product.getRate());
		product.setStatus(0);
		product.setTimeCreate(new Date());
		productService.addProduct(product);
		logger.info("添加成功");
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "添加成功"));
		return pr;
	}

	@Override
	public ProductReturn queryAllProducts() {

		ProductReturn pr = new ProductReturn();
		List<Product> products = productService.queryAllProduct();
		
		pr.setProducts(products);
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		return pr;

	}

	@Override
	@Transactional
	public ProductReturn modifyProduct(ProductParams params) {
		ProductReturn pr = new ProductReturn();
		if (params == null || params.getProduct() == null) {
			logger.info("未获得修改的产品信息");
			pr.setMessage(new Message(SystemEnum.PARAMS_ERROR.value(), "数据接收异常，请重试"));
			return pr;
		}
		productService.modifyProduct(params.getProduct());

		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "修改成功"));

		return pr;

	}

	@Override
	public ProductReturn queryProductsByParams(ProductParams params) {
		ProductReturn pr = new ProductReturn();
		List<Product> res = productService.queryProductsByParams(params.getProduct());
		if (res.size() == 1)
			pr.setProduct(res.get(0));
		else
			pr.setProducts(res);
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "查询成功"));
		return pr;
	}
}
