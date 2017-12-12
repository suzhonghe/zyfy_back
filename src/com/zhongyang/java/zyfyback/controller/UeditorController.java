package com.zhongyang.java.zyfyback.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zhongyang.java.system.uitl.ueditor.ActionEnter;



/**
 * @package com.zhongyang.java.zyfyback.controller
 * @filename UeditorController.java
 * @date 2017年7月27日下午4:26:20
 * @author suzh
 */
@Controller
@CrossOrigin
@RequestMapping("/sys/ueditor")
public class UeditorController extends BaseController {
	
	@RequestMapping(value = "/exec", method = RequestMethod.GET)
	@ResponseBody
	public void exec(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String rootPath = request.getRealPath("/");
		response.setContentType("application/json");
		response.setHeader("Content-Type", "text/html");
		String exec = new ActionEnter(request, rootPath).exec();
		try {
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> upload(MultipartHttpServletRequest multipartRequest) throws IllegalStateException, IOException {
		MultipartFile multipartFile = multipartRequest.getFile("upfile");
	    String original = multipartFile.getOriginalFilename();
	    String type = original.substring(original.lastIndexOf("."));
	    String fileName = "UED_" + System.currentTimeMillis() + type;
	    String filePath = "/var/zyfybin/article/" + fileName;
	    File file = new File(filePath);
	    if (!file.exists()) {
	        file.mkdirs();
	    }
	    multipartFile.transferTo(file);
	    Map<String, String> result = new HashMap<String, String>(); 
	    result.put("state", "SUCCESS");
	    result.put("original", original);
	    result.put("size", String.valueOf(file.length()));
	    result.put("title", fileName);
	    result.put("type", type);    
	    result.put("url", fileName); 
		
	    return result;
	}
}
