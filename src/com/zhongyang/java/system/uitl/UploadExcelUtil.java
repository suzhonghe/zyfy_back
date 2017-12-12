package com.zhongyang.java.system.uitl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.zhongyang.java.system.uitl.ExcelUtil.ExportSetInfo;

public class UploadExcelUtil<T> {
	
	public static ResponseEntity<byte[]> upload(HttpServletRequest request,List<String[]> headNames,List<String[]> fieldNames,List file,String name) throws IllegalArgumentException, IllegalAccessException, IOException {
		ResponseEntity<byte[]> responseEntity=null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		ExportSetInfo setInfo = new ExportSetInfo();
		LinkedHashMap<String, List> lhm=new LinkedHashMap<String, List>();
		lhm.put(name, file);
		setInfo.setObjsMap(lhm);
		setInfo.setFieldNames(fieldNames);
		setInfo.setTitles(new String[] {name});
		setInfo.setHeadNames(headNames);
		setInfo.setOut(baos);
		// 将需要导出的数据输出
		ExcelUtil.export2Excel(setInfo);
		HttpHeaders headers = new HttpHeaders();   
		String fileName=name+".xls";
		String transferFileName = DownloadFileName.encodeChineseDownloadFileName(request, fileName);//为了解决中文名称乱码问题  
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + transferFileName);
		//headers.setContentDispositionFormData("attachment", transferFileName);  
		responseEntity = new ResponseEntity<byte[]>(baos.toByteArray(),headers, HttpStatus.OK);
		return responseEntity;
	}
}
