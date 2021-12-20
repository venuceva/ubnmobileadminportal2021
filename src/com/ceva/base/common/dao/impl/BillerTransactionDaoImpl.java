package com.ceva.base.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.log4j.Logger;

import com.ceva.base.common.bean.BillerTransaction;
import com.ceva.base.common.dao.BillerTransactionDAO;
import com.ceva.base.common.utils.DBConnector;
import com.ceva.util.DBUtils;

public class BillerTransactionDaoImpl implements BillerTransactionDAO {

	Logger logger =Logger.getLogger(BillerTransactionDaoImpl.class);

	BeanProcessor beanProcessor = null;

	public BillerTransactionDaoImpl() {

	}

	@SuppressWarnings("resource")
	@Override
	public List<BillerTransaction> getTransactions(BillerTransaction transaction) {
		List<BillerTransaction> transactions = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {

			connection = connection == null ? DBConnector.getConnection()
					: connection;
			sql = prepareQuery(transaction);
			logger.info(sql);
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			beanProcessor = new BeanProcessor();
			if (rs.next()) {
				transactions = beanProcessor.toBeanList(rs,
						BillerTransaction.class);
				logger.info("records found " + transactions.size());
			} else {
				logger.info("no records found with inputs");
			}

		} catch (Exception e) {
			transactions = null;
			logger.error("Error..:" + e.getLocalizedMessage());
		} finally {
			DBUtils.closeResultSet(rs);
			DBUtils.closePreparedStatement(pstmt);
			DBUtils.closeConnection(connection);
			sql= null;
		}
		return transactions;
	}

	private String prepareQuery(BillerTransaction transaction) {
		StringBuilder sql = new StringBuilder(qry);
		if(transaction.getInstId() != null){
			sql.append(" AND BT.INSTID ='"+transaction.getInstId()+"'");
		}
		if(transaction.getOperator() != null){
			sql.append(" AND BT.OPERATOR ='"+transaction.getOperator()+"'");
		}
		if(transaction.getOperator() != null){
			sql.append(" AND BT.BILLERID ='"+transaction.getBillerId()+"'");
		}
		if(transaction.getTxnId() != null){
			sql.append(" AND BT.TXNID ='"+transaction.getTxnId()+"'");
		}
		return sql.toString();
	}

}
