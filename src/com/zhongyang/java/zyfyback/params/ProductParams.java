package com.zhongyang.java.zyfyback.params;

import java.io.Serializable;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyback.pojo.Product;

public class ProductParams implements Serializable{
	
	private Product product;
	
	private Page<Product> page;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Page<Product> getPage() {
		return page;
	}

	public void setPage(Page<Product> page) {
		this.page = page;
	}

}
