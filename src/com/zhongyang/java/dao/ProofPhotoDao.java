package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.zyfyback.pojo.ProofPhoto;

/**
 * 
* @Title: ProofPhotoDao.java 
* @Package com.zhongyang.java.dao 
* @Description:证明图片数据操作Dao 
* @author 苏忠贺   
* @date 2015年12月24日 上午10:11:17 
* @version V1.0
 */
public interface ProofPhotoDao {
	/**
	 * 
	* @Title: insertProofPhoto 
	* @Description:插入一条记录 
	* @return int    返回类型 
	* @throws
	 */
	public int insertProofPhoto(List<ProofPhoto> proofPhotos);
	/**
	 * 
	* @Title: selectByProjectId 
	* @Description:查询项目对应的图片 
	* @return List<ProofPhoto>    返回类型 
	* @throws
	 */
	public List<ProofPhoto> selectByParams(ProofPhoto proofPhoto);
	/**
	 * 
	* @Title: deleteByProjectId 
	* @Description:根据项目ID删除图片
	* @return int    返回类型 
	* @throws
	 */
	public int deleteByParams(ProofPhoto proofPhoto);
	
}
