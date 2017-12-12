package com.zhongyang.java.bankmanager.returndata;

import java.io.Serializable;
import java.util.List;

import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.system.uitl.Message;

/**
 *@package com.zhongyang.java.bankmanager.returndata
 *@filename BmSearchManager.java
 *@date 2017年7月19日上午10:49:42
 *@author suzh
 */
public class BmSearchManager implements Serializable{
	
	private Message message;

	private List<FundTransDetail>list;
	
	private BmAccountInfo accountInfo;
	
	private List<RepayDetail>repayDetails;
	
	private List<LoanInvestDetail>loanInvestDetails;
	
	private LoanInfo loanInfo;
	
	private AccountBalance accountBalance;
	
	private LoanAccount loanAccount;
	
	private OrderInfo orderInfo;
	
	private BmAccountVo account;

	public BmAccountVo getAccount() {
		return account;
	}

	public void setAccount(BmAccountVo account) {
		this.account = account;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public LoanAccount getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(LoanAccount loanAccount) {
		this.loanAccount = loanAccount;
	}

	public AccountBalance getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(AccountBalance accountBalance) {
		this.accountBalance = accountBalance;
	}

	public LoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}

	public List<LoanInvestDetail> getLoanInvestDetails() {
		return loanInvestDetails;
	}

	public void setLoanInvestDetails(List<LoanInvestDetail> loanInvestDetails) {
		this.loanInvestDetails = loanInvestDetails;
	}

	public List<RepayDetail> getRepayDetails() {
		return repayDetails;
	}

	public void setRepayDetails(List<RepayDetail> repayDetails) {
		this.repayDetails = repayDetails;
	}

	public BmAccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(BmAccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<FundTransDetail> getList() {
		return list;
	}

	public void setList(List<FundTransDetail> list) {
		this.list = list;
	}
	
}
