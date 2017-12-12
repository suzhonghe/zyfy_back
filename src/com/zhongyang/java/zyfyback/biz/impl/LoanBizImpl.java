package com.zhongyang.java.zyfyback.biz.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.uitl.FormatUtils;
import com.zhongyang.java.system.uitl.GetUUID;
import com.zhongyang.java.system.uitl.Message;
import com.zhongyang.java.system.uitl.SystemPro;
import com.zhongyang.java.zyfyback.biz.LoanBiz;
import com.zhongyang.java.zyfyback.params.LoanParams;
import com.zhongyang.java.zyfyback.pojo.CorporationUser;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.Loan;
import com.zhongyang.java.zyfyback.pojo.LoanRepayment;
import com.zhongyang.java.zyfyback.pojo.Product;
import com.zhongyang.java.zyfyback.pojo.Project;
import com.zhongyang.java.zyfyback.pojo.ProofPhoto;
import com.zhongyang.java.zyfyback.returndata.LoanReturn;
import com.zhongyang.java.zyfyback.service.CorporationUserService;
import com.zhongyang.java.zyfyback.service.InvestService;
import com.zhongyang.java.zyfyback.service.LoanRepaymentService;
import com.zhongyang.java.zyfyback.service.LoanService;
import com.zhongyang.java.zyfyback.service.ProductService;
import com.zhongyang.java.zyfyback.service.ProjectService;
import com.zhongyang.java.zyfyback.service.ProofPhotoService;
import com.zhongyang.java.zyfyback.vo.CorporationUserVo;
import com.zhongyang.java.zyfyback.vo.LoanDetail;
import com.zhongyang.java.zyfyback.vo.LoanListVo;
import com.zhongyang.java.zyfyback.vo.ProductVo;
import com.zhongyang.java.zyfyback.vo.ProjectDetail;

/**
 * 
 * @Title: LoanBizImpl.java
 * @Package com.zhongyang.java.biz.impl
 * @Description:标的业务处理实现类
 * @author 苏忠贺
 * @date 2015年12月4日 上午9:09:55
 * @version V1.0
 */
@Service
public class LoanBizImpl implements LoanBiz {
	
	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ip = (String) sysMap.get("ZYCF_IP");
	}

	private static String ip;

	private static Logger logger = Logger.getLogger(LoanBizImpl.class);

	@Autowired
	private LoanService loanService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CorporationUserService corporationUserService;
	
	@Autowired
	private ProofPhotoService proofPhotoService;
	
	@Autowired
	private LoanRepaymentService loanRepaymentService;
	
	@Autowired
	private InvestService investService;
	
	@Override
	@Transactional
	public LoanReturn addLoan(LoanParams params) {
		LoanReturn lr=new LoanReturn();
		Message message = new Message();
		Date date = new Date();
		try {
			logger.info(FormatUtils.millisDateFormat(date) + "标的申请");
			if (params == null || params.getLoan() == null||params.getLoan().getProjectId()==null) {
				logger.info("标的申请错误：未获得标的信息");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到标的信息，请重试");
			}
			Project prj=new Project();
			prj.setId(params.getLoan().getProjectId());
			Project queryProject = projectService.queryProjectByParams(prj);
			if (queryProject.getSurplusAmount().subtract(params.getLoan().getAmount())
					.doubleValue() < 0) {
				message.setCode(SystemEnum.PARAMS_ERROR.value());
				message.setMessage("标的金额超出剩余可发标金额");
				logger.info("标的申请错误：标的金额超出剩余可发标金额");
				lr.setMessage(message);
				return lr;
			}
			if (params.getLoan().getTitle() == null || "".equals(params.getLoan().getTitle()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到标的名称，请重试");
			if (params.getLoan().getSerial() == null || "".equals(params.getLoan().getSerial()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到标的唯一号，请重试");
			if (params.getLoan().getLoanUserId() == null || "".equals(params.getLoan().getLoanUserId()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到借款人ID，请重试");
			if (params.getLoan().getMonths() == null || "".equals(params.getLoan().getMonths()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到借款期限，请重试");
			if (params.getLoan().getMethod() == null || "".equals(params.getLoan().getMethod()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到偿还方式，请重试");
			if (params.getLoan().getMinAmount() == null || "".equals(params.getLoan().getMinAmount()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到起投金额，请重试");
			if (params.getLoan().getMaxAmount() == null || "".equals(params.getLoan().getMaxAmount()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到最大投资金额，请重试");
			if (params.getLoan().getStepAmount() == null || "".equals(params.getLoan().getStepAmount()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到投资增量，请重试");
			if (params.getLoan().getAmount() == null || "".equals(params.getLoan().getAmount())||params.getLoan().getAmount().intValue()==0)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得标的金额，请重试");
		
			if(params.getLoan().getInvestInterestFee()==null)
				params.getLoan().setInvestInterestFee(new BigDecimal(0));
			
			if(params.getLoan().getLoanGuaranteeFee()==null)
				params.getLoan().setLoanGuaranteeFee(new BigDecimal(0));
			
			if(params.getLoan().getLoanInterestFee()==null)
				params.getLoan().setLoanManageFee(new BigDecimal(0));
			
			if(params.getLoan().getLoanManageFee()==null)
				params.getLoan().setLoanManageFee(new BigDecimal(0));
			
			if(params.getLoan().getLoanRiskFee()==null)
				params.getLoan().setLoanRiskFee(new BigDecimal(0));
			
			if(params.getLoan().getLoanServiceFee()==null)
				params.getLoan().setLoanServiceFee(new BigDecimal(0));
			
			params.getLoan().setId(GetUUID.getUniqueKey());// id
			params.getLoan().setBidNumber(0);// 投标人数
			params.getLoan().setBidAmount(new BigDecimal(0));
			params.getLoan().setStatus(LoanStatus.INITIATED);// 标的状态
			params.getLoan().setAddRate(params.getLoan().getAddRate()==null?0:params.getLoan().getAddRate());
			
			// 相关费率不能为空
			if (params.getLoan().getRate() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
			params.getLoan().setTimeSubmit(new Date());
			logger.info("添加标的信息到数据库");
			try {
				loanService.addLoan(params.getLoan());
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("标的添加入库异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据入库异常");
			}

			Project project = new Project();
			project.setId(queryProject.getId());

			// 修改剩余可发标金额 当前数值页面要传递过来
			project.setSurplusAmount(queryProject.getSurplusAmount().subtract(params.getLoan().getAmount()));
			// 修改已发标金额 、当前数值页面要传递过来
			project.setPublishedAmount(queryProject.getSurplusAmount().add(params.getLoan().getAmount()));
			// 修改项目字段
			logger.info("修改项目剩余可发标金额以及已发标金额");
			projectService.modifyProject(project);

			message.setCode(SystemEnum.OPRARION_SUCCESS.value());
			message.setMessage("标的申请成功");
			logger.info("标的申请状态：标的申请成功");
			lr.setMessage(message);
			return lr;

		} catch (UException e) {
			e.printStackTrace();
			logger.info("标的申请异常");
			logger.info("异常信息:" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			message.setCode(e.getCode().value());
			message.setMessage(e.getMessage());
			lr.setMessage(message);
			return lr;
		}
	}

	@Override
	public LoanReturn searchLoanList(LoanParams params) {
		LoanReturn lr=new LoanReturn();
		Date date = new Date();
		List<LoanListVo>loanListVo=new ArrayList<LoanListVo>();
		Page<LoanListVo>page=new Page<LoanListVo>();
		try {
			if(params.getPage().getStrStartTime()!=null&&!"".equals(params.getPage().getStrStartTime()))
				params.getPage().setStartTime(FormatUtils.millisFormat(params.getPage().getStrStartTime()+" 00:00:00"));
			
			if(params.getPage().getStrEndTime()!=null&&!"".equals(params.getPage().getStrEndTime()))
				params.getPage().setEndTime(FormatUtils.millisFormat(params.getPage().getStrEndTime()+" 23:59:59"));
			
			
			List<Loan>loans = loanService.queryLoanByPage(params.getPage());
			if (loans!= null) {
				for (Loan loan : loans) {
					LoanListVo llv = new LoanListVo();
					llv.setId(loan.getId());
					llv.setAmount(loan.getAmount());
					llv.setBidAmount(loan.getBidAmount());
					llv.setBidNumber(loan.getBidNumber());
					llv.setGuaranteeRealm(loan.getGuaranteeRealm());
					llv.setMethod(loan.getMethod());
					llv.setMonths(loan.getMonths());
					llv.setTitle(loan.getTitle());
					llv.setStatus(loan.getStatus());
					llv.setUserName(loan.getLoanUserName());
					llv.setLoanUserId(loan.getLoanUserId());
					llv.setRate(loan.getRate());
					llv.setAddRate(loan.getAddRate());
					llv.setReason(loan.getReason());
					Timestamp timeStamp = new Timestamp(date.getTime());
					llv.setNowTime(timeStamp);
					llv.setTenderAmount(loan.getTenderAmount());
					
					if (loan.getTimeOpen() != null) {
						Timestamp timeOpen = new Timestamp(loan.getTimeOpen().getTime());
						llv.setTimeOpen(FormatUtils.millisDateFormat(timeOpen));
						llv.setDivTime(timeOpen.getTime() - timeStamp.getTime());
					}
					
					if(loan.getTimeOpen() != null&&loan.getTimeFinished()!=null)
						llv.setRaiseTime(loan.getTimeFinished().getTime()-loan.getTimeOpen().getTime());
					
					if (loan.getTimeFailed() != null) 
						llv.setTimeFailed(FormatUtils.millisDateFormat(loan.getTimeFailed()));
					
					
					if(loan.getTimeSettled()!=null)
						llv.setTimeSettled(FormatUtils.millisDateFormat(loan.getTimeSettled()));
					
					llv.setPlaning(
							BigDecimalAlgorithm
									.mul(BigDecimalAlgorithm.divScale(loan.getBidAmount(), loan.getAmount(), 6,
											RoundingMode.HALF_UP), new BigDecimal(100))
							.setScale(2, BigDecimal.ROUND_DOWN));
					loanListVo.add(llv);
				}
			}
			
			Map<String, Object> map = params.getPage().getParams();
			map.put("startTime", params.getPage().getStartTime());
			map.put("endTime", params.getPage().getEndTime());
			BigDecimal totalAmount = loanService.queryResult(map);
			lr.setTotalAmount(totalAmount);
			
			page.setResults(loanListVo);
			page.setPageNo(params.getPage().getPageNo());
			page.setPageSize(params.getPage().getPageSize());
			page.setTotalRecord(params.getPage().getTotalRecord());
			page.setTotalPage(params.getPage().getTotalPage());
			lr.setPage(page);
			lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			logger.info("查询结果：" + page);
			return lr;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询出现异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			lr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),e.getMessage()));
			return lr;
		}
	}
	
	public LoanReturn queryLoanByParams(LoanParams params) {
		LoanReturn lr=new LoanReturn();
		Date date = new Date();
		try {
			logger.info(FormatUtils.millisDateFormat(date) + "根据ID查询标的详细信息");
			LoanDetail loanDetail = new LoanDetail();
			List<Loan> loans = loanService.queryLoanByParams(params.getLoan());
			if(loans==null||loans.size()<=0)
				throw new UException(SystemEnum.PARAMS_ERROR,"未查询到标的信息");
				
			loanDetail.setLoan(loans.get(0));
			
			logger.info("查询标的相关的产品信息");
			Product product=new Product();
			product.setId(loans.get(0).getProductId());
			List<Product> queryProduct = productService.queryProductsByParams(product);
			loanDetail.setProduct(queryProduct.get(0));

			ProjectDetail projectDetail = new ProjectDetail();
			Page<Product> page = new Page<Product>();
			page.setPageSize(Integer.MAX_VALUE);
			List<Product> products = productService.queryAllProduct();
			List<ProductVo> pvs = new ArrayList<ProductVo>();
			for (Product p : products) {
				ProductVo pv = new ProductVo();
				pv.setId(p.getId());
				pv.setProductName(p.getName());
				pvs.add(pv);
			}
			projectDetail.setProductVos(pvs);
			List<CorporationUser> corporationUsers = corporationUserService.queryAllCorporationUser(null);
			List<CorporationUserVo> corporationUserVos = new ArrayList<CorporationUserVo>();
			for (CorporationUser corporationUser : corporationUsers) {
				CorporationUserVo cuv = new CorporationUserVo();
				cuv.setUserId(corporationUser.getUserId());
				cuv.setName(corporationUser.getName());
				cuv.setShortname(corporationUser.getShortname());
				cuv.setType(corporationUser.getType());
				corporationUserVos.add(cuv);
			}
			projectDetail.setCorporationUserVo(corporationUserVos);
			Project project=new Project();
			project.setId(loans.get(0).getProjectId());
			Project pro = projectService.queryProjectByParams(project);
			// 根据项目ID查询的到对应的图片
			logger.info("查询项目相关的正面材料图片");
			ProofPhoto proof = new ProofPhoto();
			proof.setProjectId(pro.getId());
			List<ProofPhoto> ptoofPhotos = proofPhotoService.queryByParams(proof);
			projectDetail.setProject(pro);
			for (ProofPhoto proofPhoto : ptoofPhotos) {
				// 拼接身份证图片存储地址url
				if ("法人身份证".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getLegalPersonPhotoUrl() != null) {
						projectDetail.setLegalPersonPhotoUrl(
								projectDetail.getLegalPersonPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setLegalPersonPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
				// 拼接企业信心图片存储地址url
				if ("企业信息".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getEnterpriseInfoPhotoUrl() != null) {
						projectDetail.setEnterpriseInfoPhotoUrl(
								projectDetail.getEnterpriseInfoPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setEnterpriseInfoPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
				// 拼接合同文件存储地址url
				if ("合同文件".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getAssetsPhotoUrl() != null) {
						projectDetail.setAssetsPhotoUrl(
								projectDetail.getAssetsPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setAssetsPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
				// 拼接资产信息图片存储地址url
				if ("资产信息".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getContractPhotoUrl() != null) {
						projectDetail.setContractPhotoUrl(
								projectDetail.getContractPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setContractPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
				// 拼接其他资料存储地址url
				if ("其他资料".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getOthersPhotoUrl() != null) {
						projectDetail.setOthersPhotoUrl(
								projectDetail.getOthersPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setOthersPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
			}
			Invest invest=new Invest();
			invest.setLoanId(loans.get(0).getId());
			loanDetail.setInvests(investService.queryInvestsByParams(invest));
			
			LoanRepayment loanRepayment=new LoanRepayment();
			loanRepayment.setLoanId(loans.get(0).getId());
			loanDetail.setLoanRepayments(loanRepaymentService.queryLoanRepaymentsByParams(loanRepayment));
				
			loanDetail.setProjectDtail(projectDetail);
			lr.setLoanDetail(loanDetail);
			lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
			logger.info("返回查询结果");
			return lr;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询出现异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			lr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),e.getMessage()));
			return lr;
		}
	}
	
	
	@Override
	@Transactional
	public LoanReturn modifyUnpublishedLoan(LoanParams params) {
		LoanReturn lr=new LoanReturn();
		try {
			Loan loan=new Loan();
			loan.setId(params.getLoan().getId());
			List<Loan> queryLoan = loanService.queryLoanByParams(loan);
			Project pro=new Project();
			pro.setId(queryLoan.get(0).getProjectId());
			Project queryProject = projectService.queryProjectByParams(pro);
			Project project = new Project();
			project.setId(queryProject.getId());
			if (params.getLoan().getAmount() != null) {
				BigDecimal subAmount = BigDecimalAlgorithm.subt(queryLoan.get(0).getAmount(), params.getLoan().getAmount());
				if (subAmount.doubleValue() > 0) {
					project.setSurplusAmount(BigDecimalAlgorithm.add(queryProject.getSurplusAmount(), subAmount));// 剩余可发标金额
					project.setPublishedAmount(BigDecimalAlgorithm.subt(queryProject.getPublishedAmount(), subAmount));// 已发标金额
					projectService.modifyProject(project);
					logger.info("标的金额发生改变，需要对应修改项目已发标金额以及剩余可发标金额");
				}
				if (subAmount.doubleValue() < 0) {
					subAmount = BigDecimalAlgorithm.subt(params.getLoan().getAmount(), queryLoan.get(0).getAmount());
					BigDecimal subt = BigDecimalAlgorithm.subt(queryProject.getSurplusAmount(), subAmount);
					if(subt.doubleValue()<0){
						lr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),"超出项目剩余发标金额"));
						return lr;
					}
					project.setSurplusAmount(subt);// 剩余可发标金额
					project.setPublishedAmount(BigDecimalAlgorithm.add(queryProject.getPublishedAmount(), subAmount));// 已发标金额
					projectService.modifyProject(project);
					logger.info("标的金额发生改变，需要对应修改项目已发标金额以及剩余可发标金额");
				}
			}
			
			if(LoanStatus.CANCELED.equals(params.getLoan().getStatus())){
				params.getLoan().setTimeCancle(new Date());
				project.setSurplusAmount(queryProject.getSurplusAmount().add(queryLoan.get(0).getAmount()));
				projectService.modifyProject(project);
				logger.info("取消标的修改项目剩余可发标金额");
			}
			if(LoanStatus.UNPLANED.equals(params.getLoan().getStatus()))
				params.getLoan().setTimeScheduled(new Date());
			
			try {
				loanService.modifyLoanByparams(params.getLoan());
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("标的修改异常"+e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED,"数据修改异常");
			}
			
			lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"修改成功"));
			
			logger.info("标的修改成功");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("修改标的异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			lr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),"修改失败"));
		}
		return lr;
	}

	@Override
	public LoanReturn searchLoanRecord(LoanParams params) {
		LoanReturn lr=new LoanReturn();
		try{
			
			if(params==null||params.getLoanPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数错误");
			
			List<LoanListVo> loanRecords = loanService.queryLoanRecordByPage(params.getLoanPage());
			for (LoanListVo loanListVo : loanRecords) {
				loanListVo.setStrMethod(loanListVo.getMethod().getKey());
				loanListVo.setStrStatus(loanListVo.getStatus().getKey());
			}
			
			params.getLoanPage().setResults(loanRecords);
			lr.setPage(params.getLoanPage());
			lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"借入历史查询成功"));
			
		}catch(UException e){
			logger.info("借入历史查询失败");
			logger.info(e.fillInStackTrace());
			lr.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return lr;
	}
	
	
}
