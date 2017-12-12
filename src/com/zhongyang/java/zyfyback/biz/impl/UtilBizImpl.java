package com.zhongyang.java.zyfyback.biz.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.uitl.CheckUtil;
import com.zhongyang.java.system.uitl.VerificationCode;
import com.zhongyang.java.zyfyback.biz.UtilBiz;
@Service
public class UtilBizImpl implements UtilBiz {
	
	private static Logger logger=Logger.getLogger(UtilBizImpl.class);
	
	@Override
	public void getVerificationImg(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("获取验证码sessionID："+req.getSession().getId());
		VerificationCode vc = CheckUtil.getVerificationCode();
		req.getSession().setAttribute("code", vc.getCode());
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		try {
			BufferedImage buffImg = vc.getBuffImg();
			 ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			ServletOutputStream sos = resp.getOutputStream();
			ImageIO.write(buffImg, "jpeg", out);
			byte[] byteArray = out.toByteArray();
			byte[] encodeBase64 = Base64.encodeBase64(byteArray);
			sos.write(encodeBase64);
			sos.flush();
			out.close(); 
			sos.close();
		} catch (IOException e) {
			throw new UException(e);
		}
	}
}
