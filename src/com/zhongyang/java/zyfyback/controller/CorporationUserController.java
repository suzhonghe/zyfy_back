package com.zhongyang.java.zyfyback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.zyfyback.biz.CorporationUserBiz;
import com.zhongyang.java.zyfyback.pojo.CorporationUser;
import com.zhongyang.java.zyfyback.returndata.CorporationReturn;
@CrossOrigin
@Controller
public class CorporationUserController extends BaseController{
	
	@Autowired
	private CorporationUserBiz corporationUserBiz;
	
	@RequestMapping(value="/back/cor/queryAllCorporationUserByCondition",method=RequestMethod.POST)
	public @ResponseBody CorporationReturn queryAllCorporationUserByCondition(@RequestBody CorporationUser corporationUser){
		return corporationUserBiz.queryAllCorporationUser(corporationUser);
	}
}
