package com.ceva.base.common.dao;

import com.ceva.base.common.bean.PayBillBean;
import com.ceva.base.common.dto.RequestDTO;
import com.ceva.base.common.dto.ResponseDTO;

public interface PayBillDAO {
	public final String qry = "select (SELECT SI.INSTITUTION_NAME  FROM SERVICES_INSTITUTIONS SI WHERE SI.INSTITUTION_ID = bcmt.INSTID_ID ) instituteText, bcmt.INSTID_ID institute,(SELECT CAT_NAME FROM BILLER_CATEGORY BC WHERE BC.CAT_ID= BCMT.OPERATOR ) AS operator,  BCMT.ACCOUNT bfubCreditAccount, BCMT.BILLER_DESC billerTypeDescription, BCMT.FIXED_PERCENT fixedamountcheckval, BCMT.MAKER makerId, BCMT.BILLERIDPREFIX billerIdPrefix, to_char(MAKER_DTTM, 'dd/mm/yyyy') makerDttm, REF_NUM refNum from biller_channel_mapping BCMT WHERE 1=1 ";

	public ResponseDTO getBillerChannel(String billerId);

	public ResponseDTO getChannels();

	public ResponseDTO getChannelBillers(String ChannelId);

	public ResponseDTO getChannelById(String id);

	public ResponseDTO getBillerById(String id);

	public ResponseDTO insertBillerChannelMap(RequestDTO dto);

	public ResponseDTO insertBiller(RequestDTO dto);

	public ResponseDTO authorizeBiller(RequestDTO dto);

	public ResponseDTO updateBiller(RequestDTO dto);

	public ResponseDTO deleteBiller(RequestDTO dto);

	public ResponseDTO getDashboard(RequestDTO dto);

	public ResponseDTO getChannelServices(String id);

	public PayBillBean get(PayBillBean bean);

}
