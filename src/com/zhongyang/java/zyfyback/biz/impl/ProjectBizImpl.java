package com.zhongyang.java.zyfyback.biz.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.SystemPro;
import com.zhongyang.java.zyfyback.biz.ProjectBiz;
import com.zhongyang.java.zyfyback.params.ProjectParams;
import com.zhongyang.java.zyfyback.pojo.CorporationUser;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.Product;
import com.zhongyang.java.zyfyback.pojo.Project;
import com.zhongyang.java.zyfyback.pojo.ProofPhoto;
import com.zhongyang.java.zyfyback.pojo.User;
import com.zhongyang.java.zyfyback.returndata.ProjectDea;
import com.zhongyang.java.zyfyback.returndata.ProjectReturn;
import com.zhongyang.java.zyfyback.service.CorporationUserService;
import com.zhongyang.java.zyfyback.service.LoanService;
import com.zhongyang.java.zyfyback.service.ProductService;
import com.zhongyang.java.zyfyback.service.ProjectService;
import com.zhongyang.java.zyfyback.service.ProofPhotoService;
import com.zhongyang.java.zyfyback.service.UserService;

@Service
public class ProjectBizImpl implements ProjectBiz {
	
	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_IP = (String) sysMap.get("ZYCF_IP");
	}
	
	private static String ZYCF_IP;

	private static Logger logger = Logger.getLogger(ProjectBizImpl.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProofPhotoService proofPhotoService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CorporationUserService corporationUserService;

	@Autowired
	private LoanService loanService;
	
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public ProjectReturn addProject(ProjectParams params) {
		ProjectReturn pr = new ProjectReturn();
		try{
			Date date = new Date();
			logger.info(sdf.format(date) + "添加项目");
			if (params == null || params.getProject() == null) {
				logger.info("未获得页面信息");
				throw new UException(SystemEnum.PARAMS_ERROR, "参数错误");
			}
			params.getProject().setId(GetUUID.getUniqueKey());// ID
			params.getProject().setPublishedAmount(new BigDecimal(0));// 已发标金额
			if (params.getProject().getAmount() == null) 
				throw new UException(SystemEnum.PARAMS_ERROR, "未获得到利率信息，请重试");
			
	
			params.getProject().setSurplusAmount(params.getProject().getAmount());// 剩余可发标金额
			params.getProject().setAutoable(0);// 不自动标的
			params.getProject().setStatus(0);// 启用状态
			params.getProject().setHidden(1);// 对所有人可见
	
			if (params.getProject().getLoanUserId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "未获得借款人信息，请重试");
			else{
				User user=new User();
				user.setId(params.getProject().getLoanUserId());
				user=userService.queryUserByParams(user);
				if(user==null)
					throw new UException(SystemEnum.PARAMS_ERROR, "借款人不存在，请重试");
				if(user.getUserType()==1)
					throw new UException(SystemEnum.PARAMS_ERROR, "投资用户不能作为借款人，请重试");
			}
			
			if (params.getProject().getComentSateUserId() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "未获得代偿（委托）人信息，请重试");
			else{
				User user=new User();
				user.setId(params.getProject().getLoanUserId());
				user=userService.queryUserByParams(user);
				if(user==null)
					throw new UException(SystemEnum.PARAMS_ERROR, "代偿（委托）人不存在，请重试");
//				if(user.getUserType()==1)
//					throw new UException(SystemEnum.PARAMS_ERROR, "投资用户不能作为借款人，请重试");
			}
			
			if (params.getProject().getProductId() == null) 
				throw new UException(SystemEnum.PARAMS_ERROR, "未获得到产品ID参数，请重试");
	
			Product pro = new Product();
			pro.setId(params.getProject().getProductId());
			List<Product> queryProduct = productService.queryProductsByParams(pro);
			if (queryProduct == null) 
				throw new UException(SystemEnum.PARAMS_ERROR, "产品信息获取失败，请重试");
			
	
			if (queryProduct.get(0).getMaxInvestAmount() != null) {
				params.getProject().setMaxAmount(queryProduct.get(0).getMaxInvestAmount());// 最大投资额
			}
			if (queryProduct.get(0).getMinInvestAmount() != null) {
				params.getProject().setMinAmount(queryProduct.get(0).getMinInvestAmount());// 最小投资额
			}
			params.getProject().setStepAmount(new BigDecimal(100));// 投资增量
	
			Timestamp timeStamp = new Timestamp(date.getTime());
			params.getProject().setTimeSubmit(timeStamp);// 项目创建时间
			// 添加项目记录
			projectService.addOneProject(params.getProject());
			logger.info("添加项目信息到数据库");
			// 图片处理
			String[] legalPersonPhoto = params.getLegalPersonPhoto();
			if (legalPersonPhoto!=null&&legalPersonPhoto.length != 0) {
				photoSave("法人身份证", legalPersonPhoto, params.getProject().getId());
			}
			String[] enterpriseInfoPhoto = params.getEnterpriseInfoPhoto();
			if (enterpriseInfoPhoto!=null&&enterpriseInfoPhoto.length != 0) {
				photoSave("企业信息", enterpriseInfoPhoto, params.getProject().getId());
			}
			String[] assetsPhoto = params.getAssetsPhoto();
			if (assetsPhoto!=null&&assetsPhoto.length != 0) {
				photoSave("合同文件", assetsPhoto, params.getProject().getId());
			}
			String[] contractPhoto = params.getContractPhoto();
			if (contractPhoto!=null&&contractPhoto.length != 0) {
				photoSave("资产信息", contractPhoto, params.getProject().getId());
			}
			String[] othersPhoto = params.getOthersPhoto();
			if (othersPhoto!=null&&othersPhoto.length != 0) {
				photoSave("其他资料", othersPhoto, params.getProject().getId());
			}
	
			logger.info("添加项目成功");
			pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "添加项目成功"));
		}catch(UException e){
			logger.info(e.fillInStackTrace());
			pr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
			
		return pr;

	}

	public void photoSave(String photoNme, String[] pic, String projectId){
		ProofPhoto proof = new ProofPhoto();
		proof.setProjectId(projectId);
		proof.setPhotoName(photoNme);
		List<ProofPhoto> result = proofPhotoService.queryByParams(proof);
		if (result != null) 
			proofPhotoService.deleteByParams(proof);
		

		List<ProofPhoto> proofPhotos = new ArrayList<ProofPhoto>();
		if (pic.length != 0) {
			for (String urlAddress : pic) {
				ProofPhoto proofPhoto = new ProofPhoto();
				proofPhoto.setId(UUID.randomUUID().toString().toUpperCase());
				proofPhoto.setPhotoName(photoNme);// 图片名称
				proofPhoto.setProjectId(projectId);// 对应项目ID
				proofPhoto.setSubmitTime(new Date());// 创建时间
				proofPhoto.setUrlPath("/upload"+urlAddress.substring(urlAddress.lastIndexOf("/")));
				proofPhotos.add(proofPhoto);
			}
			proofPhotoService.addProofPhoto(proofPhotos);

		}

	}


	@Override
	public ProjectReturn searchProjectByParams(ProjectParams params) {
		ProjectReturn projectReturn = new ProjectReturn();
		Date date = new Date();

		logger.info(sdf.format(date) + "根据ID查询项目详细信息");
		Page<Product> page = new Page<Product>();
		page.setPageNo(1);
		page.setPageSize(Integer.MAX_VALUE);
		List<Product> products = productService.queryAllProduct();
		projectReturn.setProducts(products);
		Project project;
		if (params != null && params.getProject() != null)
			project = projectService.queryProjectByParams(params.getProject());
		else {
			projectReturn.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), "查询失败"));
			return projectReturn;
		}
		projectReturn.setProject(project);
		List<CorporationUser> corporationUsers = corporationUserService.queryAllCorporationUser(null);
		projectReturn.setCorporationUsers(corporationUsers);

		// 根据项目ID查询的到对应的图片
		logger.info("根据项目ID查询对应的证明图片信息");
		ProofPhoto proof = new ProofPhoto();
		proof.setProjectId(project.getId());
		List<ProofPhoto> ptoofPhotos = proofPhotoService.queryByParams(proof);
		String photoIp = ZYCF_IP.substring(0, ZYCF_IP.lastIndexOf("/"));
		for (ProofPhoto proofPhoto : ptoofPhotos) {
			// 拼接身份证图片存储地址url
			
			if ("法人身份证".equals(proofPhoto.getPhotoName())) {
				if (projectReturn.getLegalPersonPhotoUrl() != null) {
					projectReturn.setLegalPersonPhotoUrl(
							projectReturn.getLegalPersonPhotoUrl() + "," + photoIp+proofPhoto.getUrlPath());
				} else {
					projectReturn.setLegalPersonPhotoUrl( photoIp+proofPhoto.getUrlPath());
				}
			}
			// 拼接企业信心图片存储地址url
			if ("企业信息".equals(proofPhoto.getPhotoName())) {
				if (projectReturn.getEnterpriseInfoPhotoUrl() != null) {
					projectReturn.setEnterpriseInfoPhotoUrl(
							projectReturn.getEnterpriseInfoPhotoUrl() + "," + photoIp+ proofPhoto.getUrlPath());
				} else {
					projectReturn.setEnterpriseInfoPhotoUrl( photoIp+proofPhoto.getUrlPath());
				}
			}
			// 拼接合同文件存储地址url
			if ("合同文件".equals(proofPhoto.getPhotoName())) {
				if (projectReturn.getAssetsPhotoUrl() != null) {
					projectReturn.setAssetsPhotoUrl(projectReturn.getAssetsPhotoUrl() + "," +  photoIp+proofPhoto.getUrlPath());
				} else {
					projectReturn.setAssetsPhotoUrl( photoIp+proofPhoto.getUrlPath());
				}
			}
			// 拼接资产信息图片存储地址url
			if ("资产信息".equals(proofPhoto.getPhotoName())) {
				if (projectReturn.getContractPhotoUrl() != null) {
					projectReturn
							.setContractPhotoUrl(projectReturn.getContractPhotoUrl() + "," +  photoIp+proofPhoto.getUrlPath());
				} else {
					projectReturn.setContractPhotoUrl( photoIp+proofPhoto.getUrlPath());
				}
			}
			// 拼接其他资料存储地址url
			if ("其他资料".equals(proofPhoto.getPhotoName())) {
				if (projectReturn.getOthersPhotoUrl() != null) {
					projectReturn.setOthersPhotoUrl(projectReturn.getOthersPhotoUrl() + "," +  photoIp+proofPhoto.getUrlPath());
				} else {
					projectReturn.setOthersPhotoUrl( photoIp+proofPhoto.getUrlPath());
				}
			}
		}
		projectReturn.setTotalAmount(new BigDecimal(0));
		Loan lo = new Loan();
		lo.setProjectId(project.getId());
		lo.setStatus(LoanStatus.ALL.equals(params.getStatus())?null:params.getStatus());
		List<Loan> loans = loanService.queryLoanByParams(lo);
		for (Loan loan : loans) {
			projectReturn.setTotalAmount(BigDecimalAlgorithm.add(projectReturn.getTotalAmount(), loan.getAmount()));
		}
		projectReturn.setLoans(loans);
		projectReturn.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		logger.info("返回项目信息");
		return projectReturn;

	}

	@Override
	public ProjectReturn queryAllProjects(ProjectParams params) {
		ProjectReturn pr = new ProjectReturn();
		List<ProjectDea> queryAllProjects = projectService.queryAllProjects(params.getPage());
		
		ProofPhoto proof = new ProofPhoto();
		String photoIp = ZYCF_IP.substring(0, ZYCF_IP.lastIndexOf("/"));
		for (ProjectDea projectDea : queryAllProjects) {
			
			proof.setProjectId(projectDea.getId());
			List<ProofPhoto> ptoofPhotos = proofPhotoService.queryByParams(proof);

			for (ProofPhoto proofPhoto : ptoofPhotos) {
				// 拼接身份证图片存储地址url
				if ("法人身份证".equals(proofPhoto.getPhotoName())) {
					if (projectDea.getLegalPersonPhotoUrl() != null) {
						projectDea.setLegalPersonPhotoUrl(
								projectDea.getLegalPersonPhotoUrl() + "," +  photoIp+proofPhoto.getUrlPath());
					} else {
						projectDea.setLegalPersonPhotoUrl( photoIp+proofPhoto.getUrlPath());
					}
				}
				// 拼接企业信心图片存储地址url
				if ("企业信息".equals(proofPhoto.getPhotoName())) {
					if (projectDea.getEnterpriseInfoPhotoUrl() != null) {
						projectDea.setEnterpriseInfoPhotoUrl(
								projectDea.getEnterpriseInfoPhotoUrl() + "," +  photoIp+proofPhoto.getUrlPath());
					} else {
						projectDea.setEnterpriseInfoPhotoUrl( photoIp+proofPhoto.getUrlPath());
					}
				}
				// 拼接合同文件存储地址url
				if ("资产信息".equals(proofPhoto.getPhotoName())) {
					if (projectDea.getAssetsPhotoUrl() != null) {
						projectDea.setAssetsPhotoUrl(projectDea.getAssetsPhotoUrl() + "," +  photoIp+proofPhoto.getUrlPath());
					} else {
						projectDea.setAssetsPhotoUrl( photoIp+proofPhoto.getUrlPath());
					}
				}
				// 拼接资产信息图片存储地址url
				if ("合同文件".equals(proofPhoto.getPhotoName())) {
					if (projectDea.getContractPhotoUrl() != null) {
						projectDea
								.setContractPhotoUrl(projectDea.getContractPhotoUrl() + "," +  photoIp+proofPhoto.getUrlPath());
					} else {
						projectDea.setContractPhotoUrl( photoIp+proofPhoto.getUrlPath());
					}
				}
				// 拼接其他资料存储地址url
				if ("其他资料".equals(proofPhoto.getPhotoName())) {
					if (projectDea.getOthersPhotoUrl() != null) {
						projectDea.setOthersPhotoUrl(projectDea.getOthersPhotoUrl() + "," +  photoIp+proofPhoto.getUrlPath());
					} else {
						projectDea.setOthersPhotoUrl( photoIp+proofPhoto.getUrlPath());
					}
				}
			}
			
		}
		
		params.getPage().setResults(queryAllProjects);
		pr.setPage(params.getPage());
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		return pr;

	}

	@Override
	@Transactional
	public ProjectReturn modifyProject(ProjectParams params) {
		ProjectReturn pr = new ProjectReturn();
		Date date = new Date();
		logger.info(sdf.format(date) + "修改项目信息");
		
		if(params.getLegalPersonPhoto()!=null&&params.getLegalPersonPhoto().length!=0){
			String[] legalPersonPhoto = params.getLegalPersonPhoto();
			photoSave("法人身份证", legalPersonPhoto, params.getProject().getId());
		}
		if(params.getEnterpriseInfoPhoto()!=null&&params.getEnterpriseInfoPhoto().length!=0){
			String[] enterpriseInfoPhoto = params.getEnterpriseInfoPhoto();
			photoSave("企业信息", enterpriseInfoPhoto, params.getProject().getId());
		}
		if(params.getAssetsPhoto()!=null&&params.getAssetsPhoto().length!=0){
			String[] assetsPhoto = params.getAssetsPhoto();
			photoSave("资产信息", assetsPhoto, params.getProject().getId());
		}
		if(params.getContractPhoto()!=null&&params.getContractPhoto().length!=0){
			String[] contractPhoto = params.getContractPhoto();
			photoSave("合同文件", contractPhoto, params.getProject().getId());
		}
		if(params.getOthersPhoto()!=null&&params.getOthersPhoto().length!=0){
			String[] othersPhoto = params.getOthersPhoto();
			photoSave("其他资料", othersPhoto, params.getProject().getId());
		}
		projectService.modifyProject(params.getProject());
		pr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "修改项目成功"));
		logger.info("修改项目成功");
		return pr;

	}

}
