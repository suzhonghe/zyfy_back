package com.zhongyang.java.zyfyback.service;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.ProofPhoto;

/**
 * 
* @Title: ProofPhotoService.java 
* @Package com.zhongyang.java.zyfyback.service 
* @Description:ProofPhotoService
* @author 苏忠贺   
* @date 2017年6月9日 下午1:25:25 
* @version V1.0
 */
public interface ProofPhotoService {
	/**
	 * 
	* @Title: addProofPhoto 
	* @Description:添加记录
	* @return int    返回类型 
	* @throws
	 */
	public int addProofPhoto(List<ProofPhoto> proofPhotos);
	/**
	 * 
	* @Title: queryByProjectId 
	* @Description:根据项目ID查询证明信息 
	* @return List<ProofPhoto>    返回类型 
	* @throws
	 */
	public List<ProofPhoto> queryByParams(ProofPhoto proofPhoto);
	/**
	 * 
	* @Title: deleteByProjectId 
	* @Description:根据项目ID删除图片
	* @return int    返回类型 
	* @throws
	 */
	public int deleteByParams(ProofPhoto proofPhoto);
	
}
