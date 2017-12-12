package com.zhongyang.java.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhongyang.java.bankmanager.biz.BmSearchBiz;
import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.params.OrderInfoParams;
import com.zhongyang.java.bankmanager.returndata.BmSearchManager;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.InvestStatus;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;
import com.zhongyang.java.zyfyback.pojo.FundRecord;
import com.zhongyang.java.zyfyback.pojo.Invest;
import com.zhongyang.java.zyfyback.pojo.LoanTransRecord;
import com.zhongyang.java.zyfyback.pojo.PlatFundRecord;
import com.zhongyang.java.zyfyback.service.FundRecordService;
import com.zhongyang.java.zyfyback.service.InvestService;
import com.zhongyang.java.zyfyback.service.LoanTransRecordService;
import com.zhongyang.java.zyfyback.service.PlatFundRecordService;

/**
 * @package com.zhongyang.java.task
 * @filename FundRecordTask.java
 * @date 2017年9月11日下午3:31:20
 * @author suzh
 */
@Component("orderTask")
public class OrderTask {
	
	private static Logger logger=Logger.getLogger(OrderTask.class);

	@Autowired
	private FundRecordService fundRecordService;

	@Autowired
	private BmOrderService bmOrderService;

	@Autowired
	private BmSearchBiz bmSearchBiz;
	
	@Autowired
	private PlatFundRecordService platFundRecordService;
	
	@Autowired
	private InvestService investService;
	
	@Autowired
	private LoanTransRecordService loanTransRecordService;

	public void OrderModifyStatusTask() {
		List<BmOrder> orders = bmOrderService.queryOrdersByStatus();
		OrderInfoParams params = new OrderInfoParams();

		List<BmOrder> resltOrders = new ArrayList<BmOrder>();
		for (BmOrder bmOrder : orders) {
			params.setQuery_order_no(bmOrder.getOrderId());
			BmSearchManager searchOrderStatus = bmSearchBiz.searchOrderStatus(params);
			if (searchOrderStatus.getMessage().getCode() == 1000) {
				if ("交易成功".equals(searchOrderStatus.getOrderInfo().getStatus()))
					bmOrder.setOrderStatus(OrderStatus.RESULT_WATT_QUERY);
				else if ("交易失败".equals(searchOrderStatus.getOrderInfo().getStatus()))
					bmOrder.setOrderStatus(OrderStatus.FAILED);
				else
					bmOrder.setOrderStatus(OrderStatus.CLOSED);

				resltOrders.add(bmOrder);
			}else{
				if(searchOrderStatus.getOrderInfo()==null)
					bmOrder.setOrderStatus(OrderStatus.CLOSED);
				
				if (searchOrderStatus.getOrderInfo()!=null&&"交易成功".equals(searchOrderStatus.getOrderInfo().getStatus()))
					bmOrder.setOrderStatus(OrderStatus.RESULT_WATT_QUERY);
				resltOrders.add(bmOrder);
			}
		}

		List<FundRecord> fundRecords = new ArrayList<FundRecord>();
		List<PlatFundRecord> platFundRecords = new ArrayList<PlatFundRecord>();
		List<Invest> invests = new ArrayList<Invest>();
		List<LoanTransRecord> loanTransRecords = new ArrayList<LoanTransRecord>();

		for (BmOrder bmOrder : resltOrders) {

			if (OrderType.DEPOSIT.equals(bmOrder.getOrderType()) || OrderType.WITHDRAW.equals(bmOrder.getOrderType())
					|| OrderType.PLAT_TO_PERSON.equals(bmOrder.getOrderType())
					|| OrderType.LOAN_FAILED.equals(bmOrder.getOrderType())
					|| OrderType.LOAN_REPAY.equals(bmOrder.getOrderType())
					|| OrderType.BORROWER_REPAY.equals(bmOrder.getOrderType())
					|| OrderType.LOAN.equals(bmOrder.getOrderType())) {
				FundRecord record = new FundRecord();
				record.setOrderId(bmOrder.getOrderId());
				record.setStatus(FundRecordStatus.valueOf(bmOrder.getOrderStatus().toString()));
				fundRecords.add(record);
			}

			else if (OrderType.PLAT_TRANS.equals(bmOrder.getOrderType())
					|| OrderType.PLAT_TO_PERSON.equals(bmOrder.getOrderType())
					|| OrderType.LOAN_REPAY.equals(bmOrder.getOrderType())
					|| OrderType.BORROWER_REPAY.equals(bmOrder.getOrderType())
					|| OrderType.LOAN.equals(bmOrder.getOrderType())) {
				PlatFundRecord platFundRecord = new PlatFundRecord();
				platFundRecord.setOrderId(bmOrder.getOrderId());
				platFundRecord.setStatus(FundRecordStatus.valueOf(bmOrder.getOrderStatus().toString()));
				platFundRecords.add(platFundRecord);
			}

			else if (OrderType.INVEST.equals(bmOrder.getOrderType())
					|| OrderType.LOAN_FAILED.equals(bmOrder.getOrderType())) {
				Invest invest = new Invest();
				invest.setOrderId(bmOrder.getOrderId());
				invest.setStatus(InvestStatus.valueOf(bmOrder.getOrderStatus().toString()));
				invests.add(invest);
			} else if (OrderType.LOAN_FAILED.equals(bmOrder.getOrderType())
					|| OrderType.LOAN_REPAY.equals(bmOrder.getOrderType())
					|| OrderType.BORROWER_REPAY.equals(bmOrder.getOrderType())
					|| OrderType.LOAN.equals(bmOrder.getOrderType())) {
				LoanTransRecord loanTransRecord = new LoanTransRecord();
				loanTransRecord.setOrderId(bmOrder.getOrderId());
				loanTransRecord.setStatus(FundRecordStatus.valueOf(bmOrder.getOrderStatus().toString()));
				loanTransRecords.add(loanTransRecord);
			}
		}
		if(resltOrders.size()>0){
			for (BmOrder bo : resltOrders) {
				try {
					bmOrderService.bmOrderModify(bo);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("订单状态修改异常"+e.fillInStackTrace());
				}
			}
		}
		if(fundRecords.size()>0){
			for (FundRecord fr : fundRecords) {
				try {
					fundRecordService.modifyFundRecordByParams(fr);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("资金记录状态修改异常"+e.fillInStackTrace());
				}
			}
		}
		if(platFundRecords.size()>0){
			for (PlatFundRecord pfr : platFundRecords) {
				try {
					platFundRecordService.modifyPlatFundRecord(pfr);
				} catch (Exception e) {
					logger.info("平台资金记录状态修改异常"+e.fillInStackTrace());
				}
			}
		}
		if(invests.size()>0){
			for (Invest in : invests) {
				try {
					investService.modifyInvest(in);
				} catch (Exception e) {
					logger.info("投资记录状态修改异常"+e.fillInStackTrace());
				}
			}
		}
		if(loanTransRecords.size()>0){
			for (LoanTransRecord ltr : loanTransRecords) {
				try {
					loanTransRecordService.modifyLoanTransRecord(ltr);
				} catch (Exception e) {
					logger.info("标的交易记录状态修改异常"+e.fillInStackTrace());
				}
			}
		}
	}
}
