package com.ceva.base.common.dao;

import java.util.List;

import com.ceva.base.common.bean.BillerTransaction;

public interface BillerTransactionDAO {

	public static final String qry = "SELECT TXNID txnId, (SELECT SI.INSTITUTION_NAME  FROM SERVICES_INSTITUTIONS SI WHERE SI.INSTITUTION_ID = BT.INSTID ) instText, INSTID instId, OPERATOR operator, (SELECT BCT.CAT_NAME FROM BILLER_CATEGORY BCT WHERE BCT.CAT_ID= BT.OPERATOR ) AS operatorText, DATETIME dateTime, STATUS status, RESPONSECODE responseCode, AMOUNT amount, MAKERID makerId FROM BILLERTRANSACTION BT WHERE 1=1 ";

	public List<BillerTransaction> getTransactions(BillerTransaction transaction);

}
