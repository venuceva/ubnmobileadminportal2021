package com.ceva.unionbank.channel;

import java.util.ResourceBundle;

import org.json.JSONException;
import org.json.JSONObject;

import com.ceva.unionbank.services.core.ServiceCaller;



public class ServiceRequestClient {

	private static final String AIRTEL_REQUEST_ID="AT00C939";
	// private static final String URL
	// ="http://172.27.0.54:9999//UBNserviceintegrator/ServiceRequestReciever";

	public static void main(String[] args) {
		// ldap();
		try {
			 //String str=ServiceRequestClient.getUserProfile("ctokpalaugo");
			 //System.out.println("kailash :: "+getCustomerDetailsByAcc("0043018373"));
			// ServiceRequestClient.ldap("sasasas","sasasas");
			//System.out.println(ServiceRequestClient.getBillers());
			//System.out.println(ServiceRequestClient.getCustomerDetailsByAcc("0005534521"));
			//ServiceRequestClient.custandactbycustid();
			//custandactbycustid("005721253");
			//System.out.println(bvnInfo("22175561982"));
			
			//AccountDetails("0048482009");
			//getCustomerAccountDetail("collins");
			//getCustomerMobileValidation("2348036010740");
			
			custandactbycustid("004858566");
			//AccountDetails("0000790887");
			//getCustomerMobileValidation("2348036010740");
			
			//AddAccount("0064059214","yisedu","MOBILE"); /**ACT -- DEACT -- DEL*/
			//ModifyAccount("0064196331","collins","MOBILE","DEACT","CEVA");
			//AddAccount("0050707118","collins","MOBILE");
			//ModifyAccount("0050707118","collins","MOBILE","DEL","CEVA");
			//ChangePassword("Collins1","collins","MOBILE");
			//sms("2348081787685","hai kailash");
			
			
			//System.out.println(branchList());
			//System.out.println(getCustomerAccountDetail("COLLINS"));
			//getCustomerDetail("COLLINS");
			
			//System.out.println(getAirTelkyc("2349070855568"));
			
			//AccountOpen("ASHAOLU","TOLUWASE","DAVID","M","29-JAN-78","8038382068","SA_006","CEVA","011","5427016","22141796440","5000","ACPAY1534773616041","ACPAY1534773616041");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// sms();
	}

	public	static String  ldap(String user,String Password,String key)
	{//08036010740
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response="";
		try {
			
			long stime= System.currentTimeMillis();
			request.put("reqtype", "LDAP_AUTH");
			
			request.put("user", user);
			request.put("pwd", Password);
			//System.out.println("turnuptime|"+(System.currentTimeMillis()-stime)+" [ldap] User Name ::: "+user);
			 //response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			 //response = ServiceRequestClient.serviceURL(request);
			 response = "{\"respdesc\":\"SUCCESS\",\"respcode\":\"00\"}";
			//response = ServiceCaller.route(request).toString();
			System.out.println("ldap -- turnuptime|"+(System.currentTimeMillis()-stime));
			//System.out.println("Response [ldap] ["+response+"]");
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	public	static String  getAirTelkyc(String mobileno)
	{
		String response="";
		try {
			JSONObject jrequest = new JSONObject();
			jrequest.put("msisdn", mobileno);
			jrequest.put("flowid", ""+System.nanoTime());
			jrequest.put("requestkey", AIRTEL_REQUEST_ID);
			jrequest.put("channel", "WEB");
			response = AirtelKYCServiceClient.call(jrequest);
			//response ="{\"mname\":\"\",\"status\":\"ACTIVATED\",\"msisdn\":\"09070855568\",\"state\":\"\",\"residential_state\":\"LAGOS\",\"sname\":\"Pandey\",\"alt_number\":\"\",\"identification_type\":\"\",\"mother_maiden_name\":\"GEETHA\",\"nationality\":\"INDIA\",\"respcode\":\"00\",\"email\":\"\",\"address\":\"10 COMMONWELTH AVENUE ILUPEJU LAGOS\",\"respdesc\":\"Success\",\"dob\":\"28-10-1982\",\"residential_lga\":\"MUSHIN\",\"gender\":\"MALE\",\"fname\":\"Rajkumar\"}";
			
			System.out.println("Response :"+response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public	static String  tokenVal(String user,String tokens)
	{//08036010740
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response="";
		try {
			long stime= System.currentTimeMillis();
			request.put("reqtype", "TOKEN_AUTH");
			request.put("tokentype", "LDAP");
			request.put("authvalue", tokens);
			request.put("username", user);
			System.out.println(" [Token] User Name ::: "+user);
			//response = ServiceCaller.route(request).toString();
			//response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			//response = ServiceRequestClient.serviceURL(request);
			response = "{\"respdesc\":\"Success\",\"respcode\":\"00\"}";
			//System.out.println("Response [ldap] ["+response+"]");
			System.out.println("tokenVal -- turnuptime|"+(System.currentTimeMillis()-stime));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}

	public	static void  sms(String mobilno,String message)
	{
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		try {
			request.put("reqtype", "SMS");
			request.put("mobileNo", mobilno);
			request.put("message", message);
			System.out.println("Request ["+request+"]");
			String response = ServiceCaller.route(request).toString();
			//String response = ServiceCaller.route(request).toString();
			//String response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			//String response = ServiceRequestClient.serviceURL(request);
			System.out.println("Response ["+response+"]");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public	static String  getUserProfile(String userid)
	{
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response="";
		try {
			request.put("reqtype", "USER_PROFILE");
			request.put("user", userid.toLowerCase());
			//request.put("user", "ctokpalaugo");
			System.out.println("Request ["+request+"]");
			//response = ServiceCaller.route(request).toString();
			//response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			//response ="{\"MobileNumber\":\"+2348031808713\",\"Email\":\"yisedu@unionbankng.com\",\"FirstName\":\"Kailash\",\"DisplayName\":\"Yusuf Isedu\",\"LastName\":\"Isedu\",\"BranchName\":\"UBN HEAD OFFICE \",\"EmployeeId\":\"5428074\",\"JobTitle\":\"DEBIT TEAM\",\"BranchCode\":\"000\"}";
			//System.out.println("Response ["+response+"]");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}

public	static String  branchList()
{
	JSONObject request = new JSONObject();
	ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
	JSONObject resp = null;
	String response="";
	try {
		request.put("reqtype", "BRN_LIST");
		
		System.out.println("Request ["+request+"]");
		//response = ServiceCaller.route(request).toString();
		//response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
		//response = ServiceRequestClient.serviceURL(request);
	//response="{\"respdesc\":\"SUCCESS\",\"branchList\":[{\"branchCode\":\"000\",\"branchName\":\"UBN HEAD OFFICE \"},{\"branchCode\":\"001\",\"branchName\":\"PAYMENT AND COLLECTION\"},{\"branchCode\":\"006\",\"branchName\":\"BOTF  - 133, AHMADU BELLO WAY\"},{\"branchCode\":\"007\",\"branchName\":\"WEB SERVICES POSTING\"},{\"branchCode\":\"010\",\"branchName\":\"FACTORY ROAD, ABA\"},{\"branchCode\":\"011\",\"branchName\":\"UGBOWO, BENIN\"},{\"branchCode\":\"012\",\"branchName\":\"BANK ROAD MAKURDI\"},{\"branchCode\":\"013\",\"branchName\":\"ABA MAIN\"},{\"branchCode\":\"014\",\"branchName\":\"AWKA\"},{\"branchCode\":\"015\",\"branchName\":\"ANARA TOWN\"},{\"branchCode\":\"020\",\"branchName\":\"ABA MARKET\"},{\"branchCode\":\"021\",\"branchName\":\"ABAGANA\"},{\"branchCode\":\"023\",\"branchName\":\"ABBA\"},{\"branchCode\":\"024\",\"branchName\":\"ABAK\"},{\"branchCode\":\"025\",\"branchName\":\"NGWA ROAD ABA\"},{\"branchCode\":\"026\",\"branchName\":\"UMUOCHAM\"},{\"branchCode\":\"028\",\"branchName\":\"IBB WAY LOKOJA\"},{\"branchCode\":\"030\",\"branchName\":\"ABAKALIKI\"},{\"branchCode\":\"032\",\"branchName\":\"ABAYI\"},{\"branchCode\":\"034\",\"branchName\":\"ABEOKUTA\"},{\"branchCode\":\"037\",\"branchName\":\"ABUJA MAIN\"},{\"branchCode\":\"038\",\"branchName\":\"UAC ABUJA\"},{\"branchCode\":\"040\",\"branchName\":\"ADO-EKITI MAIN\"},{\"branchCode\":\"041\",\"branchName\":\"FEDERAL SECRETARIAT ABUJA\"},{\"branchCode\":\"042\",\"branchName\":\"AREA 10 GARKI ABUJA\"},{\"branchCode\":\"044\",\"branchName\":\"FAJUYI ROAD, ADO-EKITI\"},{\"branchCode\":\"046\",\"branchName\":\"AGAIE\"},{\"branchCode\":\"048\",\"branchName\":\"AGBANI\"},{\"branchCode\":\"049\",\"branchName\":\"AGBARA\"},{\"branchCode\":\"052\",\"branchName\":\"AJALLI\"},{\"branchCode\":\"055\",\"branchName\":\"OYIGBO PORT HARCOURT\"},{\"branchCode\":\"060\",\"branchName\":\"AKURE MAIN\"},{\"branchCode\":\"061\",\"branchName\":\"OBA ADESIDA, AKURE\"},{\"branchCode\":\"065\",\"branchName\":\"AKURE MARKET\"},{\"branchCode\":\"066\",\"branchName\":\"ALIADE\"},{\"branchCode\":\"070\",\"branchName\":\"YINKA FOLAWIYO\"},{\"branchCode\":\"073\",\"branchName\":\"ELEGANZA PLAZA, APAPA\"},{\"branchCode\":\"076\",\"branchName\":\"BORI\"},{\"branchCode\":\"077\",\"branchName\":\"OKO\"},{\"branchCode\":\"078\",\"branchName\":\"SULEJA\"},{\"branchCode\":\"079\",\"branchName\":\"UGHELLI\"},{\"branchCode\":\"080\",\"branchName\":\"ASABA MAIN\"},{\"branchCode\":\"089\",\"branchName\":\"AUCHI\"},{\"branchCode\":\"092\",\"branchName\":\"AWE\"},{\"branchCode\":\"095\",\"branchName\":\"H/O ANNEX ABUJA\"},{\"branchCode\":\"096\",\"branchName\":\"BACITA\"},{\"branchCode\":\"097\",\"branchName\":\"BADAGRY\"},{\"branchCode\":\"098\",\"branchName\":\"SEME BORDER\"},{\"branchCode\":\"099\",\"branchName\":\"BAGUDO\"},{\"branchCode\":\"103\",\"branchName\":\"OGWASHI-UKU\"},{\"branchCode\":\"104\",\"branchName\":\"BALI\"},{\"branchCode\":\"106\",\"branchName\":\"ZIK AVENUE ENUGU\"},{\"branchCode\":\"107\",\"branchName\":\"BONNY\"},{\"branchCode\":\"108\",\"branchName\":\"BAUCHI MAIN\"},{\"branchCode\":\"111\",\"branchName\":\"DAWSON ROAD, BENIN\"},{\"branchCode\":\"112\",\"branchName\":\"AGBOR ROAD, BENIN\"},{\"branchCode\":\"114\",\"branchName\":\"YANDOKA\"},{\"branchCode\":\"115\",\"branchName\":\"JADA\"},{\"branchCode\":\"116\",\"branchName\":\"MICHIKA\"},{\"branchCode\":\"119\",\"branchName\":\"MISSION ROAD BENIN\"},{\"branchCode\":\"123\",\"branchName\":\"AKPAKPAVA MAIN BENIN\"},{\"branchCode\":\"124\",\"branchName\":\"AKPAKPAVA II, BENIN\"},{\"branchCode\":\"129\",\"branchName\":\"BIDA\"},{\"branchCode\":\"131\",\"branchName\":\"AIRPORT ROAD BENIN\"},{\"branchCode\":\"135\",\"branchName\":\"2ND UYO BRANCH\"},{\"branchCode\":\"138\",\"branchName\":\"BIRNIN KEBBI\"},{\"branchCode\":\"139\",\"branchName\":\"TINAPA\"},{\"branchCode\":\"144\",\"branchName\":\"ILARO\"},{\"branchCode\":\"147\",\"branchName\":\"BODE SAADU\"},{\"branchCode\":\"150\",\"branchName\":\"PALMS SHOPPING MALL\"},{\"branchCode\":\"152\",\"branchName\":\"OJOMU SHOPPING PLAZA\"},{\"branchCode\":\"154\",\"branchName\":\"OGUDU\"},{\"branchCode\":\"157\",\"branchName\":\"IKOT-ABASI\"},{\"branchCode\":\"158\",\"branchName\":\"CALABAR MAIN\"},{\"branchCode\":\"159\",\"branchName\":\"CFTZ CALABAR\"},{\"branchCode\":\"161\",\"branchName\":\"DOGUWAR GIGINYA\"},{\"branchCode\":\"162\",\"branchName\":\"DEMSA\"},{\"branchCode\":\"164\",\"branchName\":\"DOEMAK\"},{\"branchCode\":\"167\",\"branchName\":\"DONGA\"},{\"branchCode\":\"168\",\"branchName\":\"OYINGBO LAGOS\"},{\"branchCode\":\"169\",\"branchName\":\"DUTSE\"},{\"branchCode\":\"170\",\"branchName\":\"DAMATURU\"},{\"branchCode\":\"172\",\"branchName\":\"EFFURUN MAIN\"},{\"branchCode\":\"173\",\"branchName\":\"EGUME\"},{\"branchCode\":\"176\",\"branchName\":\"EKET\"},{\"branchCode\":\"177\",\"branchName\":\"ELEME PET. CHEMICAL\"},{\"branchCode\":\"178\",\"branchName\":\"GARDEN AVENUE ENUGU\"},{\"branchCode\":\"179\",\"branchName\":\"PARK LANE, NKPOR\"},{\"branchCode\":\"180\",\"branchName\":\"DEI-DEI\"},{\"branchCode\":\"181\",\"branchName\":\"NKPOR-JUNCTION\"},{\"branchCode\":\"182\",\"branchName\":\"IGBO-UKWU\"},{\"branchCode\":\"183\",\"branchName\":\"2ND OKPARA AVENUE\"},{\"branchCode\":\"184\",\"branchName\":\"9TH MILE ENUGU\"},{\"branchCode\":\"185\",\"branchName\":\"OGBETE MARKET\"},{\"branchCode\":\"186\",\"branchName\":\"EDOEZEMEWI\"},{\"branchCode\":\"187\",\"branchName\":\"OGUI ROAD, ENUGU\"},{\"branchCode\":\"189\",\"branchName\":\"NKPOR NEW MARKET\"},{\"branchCode\":\"190\",\"branchName\":\"PPMC ELEME\"},{\"branchCode\":\"193\",\"branchName\":\"CTL EMPORIUM P/H\"},{\"branchCode\":\"194\",\"branchName\":\"OKPARA AVE ENUGU\"},{\"branchCode\":\"195\",\"branchName\":\"ENUGU  UKWU\"},{\"branchCode\":\"196\",\"branchName\":\"EMENE\"},{\"branchCode\":\"197\",\"branchName\":\"ERIN IJESHA\"},{\"branchCode\":\"198\",\"branchName\":\"FUNTUA\"},{\"branchCode\":\"199\",\"branchName\":\"ETE\"},{\"branchCode\":\"203\",\"branchName\":\"GBOKO\"},{\"branchCode\":\"205\",\"branchName\":\"GINDIRI\"},{\"branchCode\":\"208\",\"branchName\":\"GOMBE MAIN\"},{\"branchCode\":\"209\",\"branchName\":\"GUYUK\"},{\"branchCode\":\"212\",\"branchName\":\"BAJOGA\"},{\"branchCode\":\"218\",\"branchName\":\"GUSAU\"},{\"branchCode\":\"225\",\"branchName\":\"GYAWANA\"},{\"branchCode\":\"226\",\"branchName\":\"NSUKKA\"},{\"branchCode\":\"228\",\"branchName\":\"AGODI  IBADAN\"},{\"branchCode\":\"229\",\"branchName\":\"3, LEBANON STR, IBADAN\"},{\"branchCode\":\"232\",\"branchName\":\"OKENE\"},{\"branchCode\":\"233\",\"branchName\":\"CHALLENGE ROAD IBADAN\"},{\"branchCode\":\"235\",\"branchName\":\"NEW GBAGI MKT IBADAN\"},{\"branchCode\":\"237\",\"branchName\":\"BANK ROAD IBADAN\"},{\"branchCode\":\"239\",\"branchName\":\"UCH IBADAN\"},{\"branchCode\":\"248\",\"branchName\":\"76 LEBANON  IBADAN\"},{\"branchCode\":\"249\",\"branchName\":\"IBI BRANCH\"},{\"branchCode\":\"254\",\"branchName\":\"IBUSA\"},{\"branchCode\":\"260\",\"branchName\":\"IDAH\"},{\"branchCode\":\"262\",\"branchName\":\"IDANRE\"},{\"branchCode\":\"264\",\"branchName\":\"OKOPEDI\"},{\"branchCode\":\"265\",\"branchName\":\"IDI-ARABA\"},{\"branchCode\":\"266\",\"branchName\":\"6, HERBERT MACAULAY\"},{\"branchCode\":\"267\",\"branchName\":\"ILE IFE\"},{\"branchCode\":\"270\",\"branchName\":\"IFEWARA\"},{\"branchCode\":\"276\",\"branchName\":\"IGEDE EKITI\"},{\"branchCode\":\"282\",\"branchName\":\"IGUEBEN\"},{\"branchCode\":\"287\",\"branchName\":\"IGUOBAZUWA\"},{\"branchCode\":\"288\",\"branchName\":\"IJEBU ODE MAIN\"},{\"branchCode\":\"297\",\"branchName\":\"IJORA\"},{\"branchCode\":\"299\",\"branchName\":\"IJU\"},{\"branchCode\":\"303\",\"branchName\":\"ALAUSA, IKEJA\"},{\"branchCode\":\"306\",\"branchName\":\"AGIDINGBI\"},{\"branchCode\":\"307\",\"branchName\":\"OBA AKRAN\"},{\"branchCode\":\"308\",\"branchName\":\"IKOT-EDIBON\"},{\"branchCode\":\"309\",\"branchName\":\"IKOT-EKPENE\"},{\"branchCode\":\"311\",\"branchName\":\"IKIRUN\"},{\"branchCode\":\"312\",\"branchName\":\"ILAWE-EKITI\"},{\"branchCode\":\"314\",\"branchName\":\"ILESHA\"},{\"branchCode\":\"316\",\"branchName\":\"M.M. WAY ILORIN\"},{\"branchCode\":\"317\",\"branchName\":\"IKORODU\"},{\"branchCode\":\"318\",\"branchName\":\"ILORIN UNIVERSITY\"},{\"branchCode\":\"321\",\"branchName\":\"ILORIN  MARKET\"},{\"branchCode\":\"326\",\"branchName\":\"IWOPIN\"},{\"branchCode\":\"327\",\"branchName\":\"ILUTITUN\"},{\"branchCode\":\"328\",\"branchName\":\"ISA BRANCH\"},{\"branchCode\":\"329\",\"branchName\":\"ILUPEJU\"},{\"branchCode\":\"331\",\"branchName\":\"IRUEKPEN\"},{\"branchCode\":\"332\",\"branchName\":\"ISEYIN\"},{\"branchCode\":\"335\",\"branchName\":\"ISOLO\"},{\"branchCode\":\"337\",\"branchName\":\"BANK STR  JOS\"},{\"branchCode\":\"338\",\"branchName\":\"ITA-OGBOLU\"},{\"branchCode\":\"339\",\"branchName\":\"JIBIA\"},{\"branchCode\":\"346\",\"branchName\":\"AHMADU BELLO., KADUNA\"},{\"branchCode\":\"347\",\"branchName\":\"JOS MARKET\"},{\"branchCode\":\"348\",\"branchName\":\"JENGRE\"},{\"branchCode\":\"349\",\"branchName\":\"M.M WAY JOS\"},{\"branchCode\":\"350\",\"branchName\":\"JEGA\"},{\"branchCode\":\"351\",\"branchName\":\"PPMC KADUNA\"},{\"branchCode\":\"352\",\"branchName\":\"ABOH MBAISE\"},{\"branchCode\":\"356\",\"branchName\":\"YAKUBU GOWON WAY KADUNA\"},{\"branchCode\":\"365\",\"branchName\":\"KADUNA SOUTH\"},{\"branchCode\":\"371\",\"branchName\":\"MOHAMMED BUHARI WAY\"},{\"branchCode\":\"373\",\"branchName\":\"JAJI\"},{\"branchCode\":\"376\",\"branchName\":\"ADO BAYERO KANO\"},{\"branchCode\":\"377\",\"branchName\":\"POST OFFICE RD KANO\"},{\"branchCode\":\"390\",\"branchName\":\"CHALAWA LAYOUT\"},{\"branchCode\":\"391\",\"branchName\":\"HADEJIA\"},{\"branchCode\":\"393\",\"branchName\":\"KANO MAIN\"},{\"branchCode\":\"394\",\"branchName\":\"M.M. WAY KANO\"},{\"branchCode\":\"395\",\"branchName\":\"BANK ROAD KANO\"},{\"branchCode\":\"396\",\"branchName\":\"ZOO ROAD KANO\"},{\"branchCode\":\"400\",\"branchName\":\"KARU\"},{\"branchCode\":\"406\",\"branchName\":\"KATSINA MAIN\"},{\"branchCode\":\"407\",\"branchName\":\"KWAYA KUSAR\"},{\"branchCode\":\"408\",\"branchName\":\"ADEOLA-ODEKU\"},{\"branchCode\":\"409\",\"branchName\":\"LAFIAGI\"},{\"branchCode\":\"410\",\"branchName\":\"AWOLOWO ROAD IKOYI\"},{\"branchCode\":\"411\",\"branchName\":\"FORESHORE TOWERS, IKOYI\"},{\"branchCode\":\"413\",\"branchName\":\"ALLEN AVENUE\"},{\"branchCode\":\"414\",\"branchName\":\"ADENIJI ADELE\"},{\"branchCode\":\"415\",\"branchName\":\"BALOGUN MARKET\"},{\"branchCode\":\"416\",\"branchName\":\"AMUWO-ODOFIN\"},{\"branchCode\":\"418\",\"branchName\":\"FALOMO\"},{\"branchCode\":\"419\",\"branchName\":\"ERIC MOORE ROAD\"},{\"branchCode\":\"421\",\"branchName\":\"OIL ESTATE LEKKI\"},{\"branchCode\":\"422\",\"branchName\":\"FED SEC - LAGOS\"},{\"branchCode\":\"423\",\"branchName\":\"IJESHATEDO\"},{\"branchCode\":\"424\",\"branchName\":\"DOPEMU LAGOS\"},{\"branchCode\":\"425\",\"branchName\":\"OSHODI\"},{\"branchCode\":\"426\",\"branchName\":\"KAKAWA\"},{\"branchCode\":\"427\",\"branchName\":\"LADIPO\"},{\"branchCode\":\"428\",\"branchName\":\"LAWANSON\"},{\"branchCode\":\"430\",\"branchName\":\"EGBE\"},{\"branchCode\":\"431\",\"branchName\":\"DOCEMO IDUMOTA\"},{\"branchCode\":\"432\",\"branchName\":\"OBALENDE\"},{\"branchCode\":\"433\",\"branchName\":\"LEWIS STREET\"},{\"branchCode\":\"435\",\"branchName\":\"DAVIES STREET\"},{\"branchCode\":\"436\",\"branchName\":\"131 BROAD STREET\"},{\"branchCode\":\"437\",\"branchName\":\"118/120 BROAD STREET\"},{\"branchCode\":\"439\",\"branchName\":\"ORILE COKER\"},{\"branchCode\":\"440\",\"branchName\":\"AJAH\"},{\"branchCode\":\"441\",\"branchName\":\"IDIMU\"},{\"branchCode\":\"443\",\"branchName\":\"SANUSI OLUSI\"},{\"branchCode\":\"444\",\"branchName\":\"OGIDI\"},{\"branchCode\":\"445\",\"branchName\":\"2ND FACTORY ROAD ABA\"},{\"branchCode\":\"446\",\"branchName\":\"CONCORDE OWERRI\"},{\"branchCode\":\"448\",\"branchName\":\"MAITAMA ABUJA\"},{\"branchCode\":\"451\",\"branchName\":\"NASS ABUJA\"},{\"branchCode\":\"453\",\"branchName\":\"HOTORO KANO\"},{\"branchCode\":\"454\",\"branchName\":\"MOGADISHU LAYOUT KADUNA\"},{\"branchCode\":\"457\",\"branchName\":\"CREEK ROAD, APAPA\"},{\"branchCode\":\"458\",\"branchName\":\"AGEGE MAIN\"},{\"branchCode\":\"459\",\"branchName\":\"PEN CINEMA AGEGE\"},{\"branchCode\":\"460\",\"branchName\":\"ADEOLA HOPEWELL\"},{\"branchCode\":\"461\",\"branchName\":\"AJOSE ADEOGUN\"},{\"branchCode\":\"462\",\"branchName\":\"OYIN JOLAYEMI\"},{\"branchCode\":\"463\",\"branchName\":\"ADEYEMO ALAKIJA\"},{\"branchCode\":\"466\",\"branchName\":\"MOLONEY\"},{\"branchCode\":\"475\",\"branchName\":\"OBUN EKO\"},{\"branchCode\":\"483\",\"branchName\":\"LANGTANG\"},{\"branchCode\":\"487\",\"branchName\":\"3RD AVENUE FESTAC\"},{\"branchCode\":\"491\",\"branchName\":\"BBA (J ANYI) PLAZA\"},{\"branchCode\":\"492\",\"branchName\":\"TINUBU SQUARE LAGOS\"},{\"branchCode\":\"493\",\"branchName\":\"TRADE FAIR COMPLEX\"},{\"branchCode\":\"494\",\"branchName\":\"ASPAMDA MAIN GATE\"},{\"branchCode\":\"495\",\"branchName\":\"LOKOJA MAIN\"},{\"branchCode\":\"497\",\"branchName\":\"LAFIA\"},{\"branchCode\":\"500\",\"branchName\":\"MACE PLAZA, TRADE FAIR\"},{\"branchCode\":\"506\",\"branchName\":\"MAIDUGURI MAIN\"},{\"branchCode\":\"509\",\"branchName\":\"YAHAYA MADAKI, KATSINA\"},{\"branchCode\":\"514\",\"branchName\":\"AJAOKUTA\"},{\"branchCode\":\"516\",\"branchName\":\"MAKURDI MAIN\"},{\"branchCode\":\"534\",\"branchName\":\"MAYO BELWA\"},{\"branchCode\":\"536\",\"branchName\":\"MINNA\"},{\"branchCode\":\"544\",\"branchName\":\"MISAU\"},{\"branchCode\":\"545\",\"branchName\":\"MUBI\"},{\"branchCode\":\"549\",\"branchName\":\"AKAMKPA\"},{\"branchCode\":\"555\",\"branchName\":\"MUSHIN\"},{\"branchCode\":\"556\",\"branchName\":\"NNEWI\"},{\"branchCode\":\"557\",\"branchName\":\"ONNE NAFCON\"},{\"branchCode\":\"558\",\"branchName\":\"GAMBORU NGALLA\"},{\"branchCode\":\"559\",\"branchName\":\"OGBEDE\"},{\"branchCode\":\"560\",\"branchName\":\"ODE IRELE\"},{\"branchCode\":\"564\",\"branchName\":\"OGHARA\"},{\"branchCode\":\"565\",\"branchName\":\"OFFA\"},{\"branchCode\":\"566\",\"branchName\":\"OGERE\"},{\"branchCode\":\"567\",\"branchName\":\"OGBA LAGOS\"},{\"branchCode\":\"568\",\"branchName\":\"OGBOMOSO\"},{\"branchCode\":\"569\",\"branchName\":\"OGOJA\"},{\"branchCode\":\"570\",\"branchName\":\"OKENGWE\"},{\"branchCode\":\"571\",\"branchName\":\"OHAFIA\"},{\"branchCode\":\"572\",\"branchName\":\"OJU\"},{\"branchCode\":\"573\",\"branchName\":\"OKOKOMAIKO\"},{\"branchCode\":\"574\",\"branchName\":\"ONDO BRANCH\"},{\"branchCode\":\"575\",\"branchName\":\"OKIGWE\"},{\"branchCode\":\"576\",\"branchName\":\"OLEH\"},{\"branchCode\":\"581\",\"branchName\":\"OMU-ARAN\"},{\"branchCode\":\"582\",\"branchName\":\"ORAIFITE\"},{\"branchCode\":\"583\",\"branchName\":\"UPPER IWEKA ROAD\"},{\"branchCode\":\"585\",\"branchName\":\"BRIGHT STR., ONITSHA\"},{\"branchCode\":\"586\",\"branchName\":\"AJAEGBU EZE\"},{\"branchCode\":\"593\",\"branchName\":\"GASHUA\"},{\"branchCode\":\"595\",\"branchName\":\"18, NEW MKT RD. ONITSHA\"},{\"branchCode\":\"596\",\"branchName\":\"NIGER BRIDGE HEAD, ONITSHA\"},{\"branchCode\":\"597\",\"branchName\":\"ATANI ROAD, ONITSHA\"},{\"branchCode\":\"598\",\"branchName\":\"OREGUN LAGOS\"},{\"branchCode\":\"604\",\"branchName\":\"ORLU\"},{\"branchCode\":\"606\",\"branchName\":\"ORO\"},{\"branchCode\":\"609\",\"branchName\":\"OSOGBO\"},{\"branchCode\":\"611\",\"branchName\":\"OTTA\"},{\"branchCode\":\"612\",\"branchName\":\"OVWIAN ALADJA\"},{\"branchCode\":\"614\",\"branchName\":\"DOUGLAS RD, OWERRI\"},{\"branchCode\":\"615\",\"branchName\":\"2ND  OWERRI\"},{\"branchCode\":\"616\",\"branchName\":\"EKPOMA\"},{\"branchCode\":\"618\",\"branchName\":\"IKENEGBU LAYOUT OKIGWE\"},{\"branchCode\":\"629\",\"branchName\":\"OYO BRANCH\"},{\"branchCode\":\"631\",\"branchName\":\"PATEGI\"},{\"branchCode\":\"632\",\"branchName\":\"PANYAM\"},{\"branchCode\":\"633\",\"branchName\":\"PAIKO\"},{\"branchCode\":\"643\",\"branchName\":\"ORIJE PORT-HARCOURT\"},{\"branchCode\":\"645\",\"branchName\":\"171D ABA ROAD, PHC\"},{\"branchCode\":\"648\",\"branchName\":\"AGBOR\"},{\"branchCode\":\"653\",\"branchName\":\"STATION ROAD PHC\"},{\"branchCode\":\"654\",\"branchName\":\"KINGSWAY PHC\"},{\"branchCode\":\"655\",\"branchName\":\"101, IKWERRE ROAD P/H\"},{\"branchCode\":\"656\",\"branchName\":\"180, ABA ROAD P/H\"},{\"branchCode\":\"657\",\"branchName\":\"TRANS AMADI PH.\"},{\"branchCode\":\"659\",\"branchName\":\"SAGAMU\"},{\"branchCode\":\"660\",\"branchName\":\"QUA IBOE TERMINAL\"},{\"branchCode\":\"662\",\"branchName\":\"YENAGOA MAIN\"},{\"branchCode\":\"663\",\"branchName\":\"OVOM, YENAGOA\"},{\"branchCode\":\"664\",\"branchName\":\"SAMARU\"},{\"branchCode\":\"673\",\"branchName\":\"SAPELE\"},{\"branchCode\":\"674\",\"branchName\":\"SHARADA, KANO\"},{\"branchCode\":\"678\",\"branchName\":\"SOMOLU\"},{\"branchCode\":\"682\",\"branchName\":\"STALLION PLAZA\"},{\"branchCode\":\"683\",\"branchName\":\"SOKOTO  MAIN\"},{\"branchCode\":\"691\",\"branchName\":\"SOKOTO MARKET\"},{\"branchCode\":\"694\",\"branchName\":\"SURULERE\"},{\"branchCode\":\"695\",\"branchName\":\"TIN-CAN ISLAND LAGOS\"},{\"branchCode\":\"696\",\"branchName\":\"TALASSE\"},{\"branchCode\":\"698\",\"branchName\":\"UBURU OHAOZARA\"},{\"branchCode\":\"701\",\"branchName\":\"WHARF ROAD APAPA\"},{\"branchCode\":\"703\",\"branchName\":\"WUSE II ABUJA\"},{\"branchCode\":\"707\",\"branchName\":\"UMUAHIA\"},{\"branchCode\":\"709\",\"branchName\":\"UMUDIKE\"},{\"branchCode\":\"713\",\"branchName\":\"UROMI\"},{\"branchCode\":\"718\",\"branchName\":\"UYO MAIN\"},{\"branchCode\":\"719\",\"branchName\":\"AIRPORT ROAD WARRI\"},{\"branchCode\":\"723\",\"branchName\":\"WARRI MAIN\"},{\"branchCode\":\"724\",\"branchName\":\"EFFURUN II\"},{\"branchCode\":\"733\",\"branchName\":\"SABO YABA\"},{\"branchCode\":\"737\",\"branchName\":\"YELWA\"},{\"branchCode\":\"743\",\"branchName\":\"ARIARIA\"},{\"branchCode\":\"744\",\"branchName\":\"YOLA MAIN\"},{\"branchCode\":\"748\",\"branchName\":\"JALINGO\"},{\"branchCode\":\"753\",\"branchName\":\"ZARIA\"},{\"branchCode\":\"754\",\"branchName\":\"AROCHUKWU\"},{\"branchCode\":\"757\",\"branchName\":\"KETU\"},{\"branchCode\":\"761\",\"branchName\":\"ALABA INT MKT\"},{\"branchCode\":\"762\",\"branchName\":\"NEW ALABA MARKET\"},{\"branchCode\":\"765\",\"branchName\":\"OKE-ARIN\"},{\"branchCode\":\"785\",\"branchName\":\"FAULKS ROAD\"},{\"branchCode\":\"789\",\"branchName\":\"VENN ROAD, ONITSHA\"},{\"branchCode\":\"791\",\"branchName\":\"KIRIKASAMA MAID\"},{\"branchCode\":\"792\",\"branchName\":\"ASABA II\"},{\"branchCode\":\"794\",\"branchName\":\"AREA 8 GARKI\"},{\"branchCode\":\"795\",\"branchName\":\"CADASTRAL ZONE\"},{\"branchCode\":\"796\",\"branchName\":\"J7 AHMADU BELLO WAY, KADUNA\"},{\"branchCode\":\"798\",\"branchName\":\"BOMPAI KANO\"},{\"branchCode\":\"799\",\"branchName\":\"56 IBADAN ROAD, IJEBU ODE\"},{\"branchCode\":\"803\",\"branchName\":\"45 IKWERRE ROAD, PHC\"},{\"branchCode\":\"805\",\"branchName\":\"IWO ROAD\"},{\"branchCode\":\"829\",\"branchName\":\"OGIRI-OKO\"},{\"branchCode\":\"901\",\"branchName\":\"UYO CASH CENTRE\"},{\"branchCode\":\"902\",\"branchName\":\"ENUGU CASH CENTRE\"},{\"branchCode\":\"903\",\"branchName\":\"BAUCHI CASH CENTRE\"},{\"branchCode\":\"904\",\"branchName\":\"BENIN CASH CENTRE\"},{\"branchCode\":\"905\",\"branchName\":\"MAKURDI CASH CENTRE\"},{\"branchCode\":\"906\",\"branchName\":\"MAIDUGURI CASH CENTRE\"},{\"branchCode\":\"908\",\"branchName\":\"ABUJA CASH CENTRE\"},{\"branchCode\":\"909\",\"branchName\":\"YOLA CASH CENTRE\"},{\"branchCode\":\"910\",\"branchName\":\"OWERRI CASH CENTRE\"},{\"branchCode\":\"911\",\"branchName\":\"KADUNA CASH CENTRE\"},{\"branchCode\":\"912\",\"branchName\":\"KANO CASH CENTRE\"},{\"branchCode\":\"913\",\"branchName\":\"ABA CASH CENTRE\"},{\"branchCode\":\"914\",\"branchName\":\"ILORIN CASH CENTRE\"},{\"branchCode\":\"915\",\"branchName\":\"UBN CLEARING CENTRE\"},{\"branchCode\":\"916\",\"branchName\":\"MINNA CASH CENTRE\"},{\"branchCode\":\"917\",\"branchName\":\"ABEOKUTA CASH CENTRE\"},{\"branchCode\":\"918\",\"branchName\":\"AKURE CASH CENTRE\"},{\"branchCode\":\"919\",\"branchName\":\"IBADAN CASH CENTRE\"},{\"branchCode\":\"920\",\"branchName\":\"JOS CASH CENTRE\"},{\"branchCode\":\"922\",\"branchName\":\"SOKOTO CASH CENTRE\"},{\"branchCode\":\"924\",\"branchName\":\"EFFURUN CASH CENTRE\"},{\"branchCode\":\"925\",\"branchName\":\"KATSINA CASH CENTRE\"},{\"branchCode\":\"926\",\"branchName\":\"TINUBU CASH CENTRE\"},{\"branchCode\":\"931\",\"branchName\":\"ONITSHA CASH CENTRE\"},{\"branchCode\":\"937\",\"branchName\":\"AWKA CASH CENTRE\"},{\"branchCode\":\"939\",\"branchName\":\"LOKOJA CASH CENTRE\"},{\"branchCode\":\"965\",\"branchName\":\"BAGA\"},{\"branchCode\":\"992\",\"branchName\":\"PORT HARCOURT CASH CENTRE\"}],\"respcode\":\"00\"}";
		response="{\"respdesc\":\"SUCCESS\",\"branchList\":[{\"000-UBN HEAD OFFICE \":\"000-UBN HEAD OFFICE \",\"001-PAYMENT AND COLLECTION\":\"001-PAYMENT AND COLLECTION\",\"002-WEB SERVICES POSTING 9\":\"002-WEB SERVICES POSTING 9\",\"003-WEB SERVICES POSTING 3\":\"003-WEB SERVICES POSTING 3\",\"004-WEB SERVICES POSTING 4\":\"004-WEB SERVICES POSTING 4\",\"005-WEB SERVICES POSTING 5\":\"005-WEB SERVICES POSTING 5\",\"006-BOTF  - 133, AHMADU BELLO WAY\":\"006-BOTF  - 133, AHMADU BELLO WAY\",\"007-WEB SERVICES POSTING\":\"007-WEB SERVICES POSTING\",\"008-WEB SERVICES POSTING 2\":\"008-WEB SERVICES POSTING 2\",\"009-WEB SERVICES POST 10\":\"009-WEB SERVICES POST 10\",\"010-FACTORY ROAD, ABA\":\"010-FACTORY ROAD, ABA\",\"011-UGBOWO, BENIN\":\"011-UGBOWO, BENIN\",\"012-BANK ROAD MAKURDI\":\"012-BANK ROAD MAKURDI\",\"013-ABA MAIN\":\"013-ABA MAIN\",\"014-AWKA\":\"014-AWKA\",\"015-ANARA TOWN\":\"015-ANARA TOWN\",\"016-WEB SERVICES POSTING 6\":\"016-WEB SERVICES POSTING 6\",\"017-WEB SERVICES POSTING 7\":\"017-WEB SERVICES POSTING 7\",\"018-WEB SERVICES POSTING 8\":\"018-WEB SERVICES POSTING 8\",\"020-ABA MARKET\":\"020-ABA MARKET\",\"021-ABAGANA\":\"021-ABAGANA\",\"022-WEB SERVICES PSTING 11\":\"022-WEB SERVICES PSTING 11\",\"023-ABBA\":\"023-ABBA\",\"024-ABAK\":\"024-ABAK\",\"025-NGWA ROAD ABA\":\"025-NGWA ROAD ABA\",\"026-UMUOCHAM\":\"026-UMUOCHAM\",\"028-IBB WAY LOKOJA\":\"028-IBB WAY LOKOJA\",\"030-ABAKALIKI\":\"030-ABAKALIKI\",\"032-ABAYI\":\"032-ABAYI\",\"034-ABEOKUTA\":\"034-ABEOKUTA\",\"037-ABUJA MAIN\":\"037-ABUJA MAIN\",\"038-UAC ABUJA\":\"038-UAC ABUJA\",\"040-ADO-EKITI MAIN\":\"040-ADO-EKITI MAIN\",\"041-FEDERAL SECRETARIAT ABUJA\":\"041-FEDERAL SECRETARIAT ABUJA\",\"042-AREA 10 GARKI ABUJA\":\"042-AREA 10 GARKI ABUJA\",\"044-FAJUYI ROAD, ADO-EKITI\":\"044-FAJUYI ROAD, ADO-EKITI\",\"046-AGAIE\":\"046-AGAIE\",\"048-AGBANI\":\"048-AGBANI\",\"049-AGBARA\":\"049-AGBARA\",\"052-AJALLI\":\"052-AJALLI\",\"055-OYIGBO PORT HARCOURT\":\"055-OYIGBO PORT HARCOURT\",\"060-AKURE MAIN\":\"060-AKURE MAIN\",\"061-OBA ADESIDA, AKURE\":\"061-OBA ADESIDA, AKURE\",\"065-AKURE MARKET\":\"065-AKURE MARKET\",\"066-ALIADE\":\"066-ALIADE\",\"070-YINKA FOLAWIYO\":\"070-YINKA FOLAWIYO\",\"073-ELEGANZA PLAZA, APAPA\":\"073-ELEGANZA PLAZA, APAPA\",\"076-BORI\":\"076-BORI\",\"077-OKO\":\"077-OKO\",\"078-SULEJA\":\"078-SULEJA\",\"079-UGHELLI\":\"079-UGHELLI\",\"080-ASABA MAIN\":\"080-ASABA MAIN\",\"089-AUCHI\":\"089-AUCHI\",\"092-AWE\":\"092-AWE\",\"095-H/O ANNEX ABUJA\":\"095-H/O ANNEX ABUJA\",\"096-BACITA\":\"096-BACITA\",\"097-BADAGRY\":\"097-BADAGRY\",\"098-SEME BORDER\":\"098-SEME BORDER\",\"099-BAGUDO\":\"099-BAGUDO\",\"103-OGWASHI-UKU\":\"103-OGWASHI-UKU\",\"104-BALI\":\"104-BALI\",\"106-ZIK AVENUE ENUGU\":\"106-ZIK AVENUEENUGU\",\"107-BONNY\":\"107-BONNY\",\"108-BAUCHI MAIN\":\"108-BAUCHI MAIN\",\"111-DAWSON ROAD, BENIN\":\"111-DAWSON ROAD, BENIN\",\"112-AGBOR ROAD, BENIN\":\"112-AGBOR ROAD, BENIN\",\"114-YANDOKA\":\"114-YANDOKA\",\"115-JADA\":\"115-JADA\",\"116-MICHIKA\":\"116-MICHIKA\",\"119-MISSION ROAD BENIN\":\"119-MISSION ROAD BENIN\",\"123-AKPAKPAVA MAIN BENIN\":\"123-AKPAKPAVA MAIN BENIN\",\"124-AKPAKPAVA II, BENIN\":\"124-AKPAKPAVA II, BENIN\",\"129-BIDA\":\"129-BIDA\",\"131-AIRPORT ROAD BENIN\":\"131-AIRPORT ROAD BENIN\",\"135-2ND UYO BRANCH\":\"135-2ND UYO BRANCH\",\"138-BIRNIN KEBBI\":\"138-BIRNIN KEBBI\",\"139-TINAPA\":\"139-TINAPA\",\"144-ILARO\":\"144-ILARO\",\"147-BODE SAADU\":\"147-BODE SAADU\",\"150-PALMS SHOPPING MALL\":\"150-PALMS SHOPPING MALL\",\"152-OJOMU SHOPPING PLAZA\":\"152-OJOMU SHOPPING PLAZA\",\"154-OGUDU\":\"154-OGUDU\",\"157-IKOT-ABASI\":\"157-IKOT-ABASI\",\"158-CALABAR MAIN\":\"158-CALABAR MAIN\",\"159-CFTZ CALABAR\":\"159-CFTZ CALABAR\",\"161-DOGUWAR GIGINYA\":\"161-DOGUWAR GIGINYA\",\"162-DEMSA\":\"162-DEMSA\",\"164-DOEMAK\":\"164-DOEMAK\",\"167-DONGA\":\"167-DONGA\",\"168-OYINGBO LAGOS\":\"168-OYINGBO LAGOS\",\"169-DUTSE\":\"169-DUTSE\",\"170-DAMATURU\":\"170-DAMATURU\",\"172-EFFURUN MAIN\":\"172-EFFURUN MAIN\",\"173-EGUME\":\"173-EGUME\",\"176-EKET\":\"176-EKET\",\"177-ELEME PET. CHEMICAL\":\"177-ELEME PET. CHEMICAL\",\"178-GARDEN AVENUE ENUGU\":\"178-GARDEN AVENUE ENUGU\",\"179-PARK LANE, NKPOR\":\"179-PARK LANE, NKPOR\",\"180-DEI-DEI\":\"180-DEI-DEI\",\"181-NKPOR-JUNCTION\":\"181-NKPOR-JUNCTION\",\"182-IGBO-UKWU\":\"182-IGBO-UKWU\",\"183-2ND OKPARA AVENUE\":\"183-2ND OKPARA AVENUE\",\"184-9TH MILE ENUGU\":\"184-9TH MILE ENUGU\",\"185-OGBETE MARKET\":\"185-OGBETE MARKET\",\"186-EDOEZEMEWI\":\"186-EDOEZEMEWI\",\"187-OGUI ROAD, ENUGU\":\"187-OGUI ROAD, ENUGU\",\"189-NKPOR NEW MARKET\":\"189-NKPOR NEW MARKET\",\"190-PPMC ELEME\":\"190-PPMC ELEME\",\"193-CTL EMPORIUM P/H\":\"193-CTL EMPORIUM P/H\",\"194-OKPARA AVE ENUGU\":\"194-OKPARA AVE ENUGU\",\"195-ENUGU  UKWU\":\"195-ENUGU  UKWU\",\"196-EMENE\":\"196-EMENE\",\"197-ERIN IJESHA\":\"197-ERIN IJESHA\",\"198-FUNTUA\":\"198-FUNTUA\",\"199-ETE\":\"199-ETE\",\"203-GBOKO\":\"203-GBOKO\",\"205-GINDIRI\":\"205-GINDIRI\",\"208-GOMBE MAIN\":\"208-GOMBE MAIN\",\"209-GUYUK\":\"209-GUYUK\",\"212-BAJOGA\":\"212-BAJOGA\",\"218-GUSAU\":\"218-GUSAU\",\"225-GYAWANA\":\"225-GYAWANA\",\"226-NSUKKA\":\"226-NSUKKA\",\"228-AGODI  IBADAN\":\"228-AGODI  IBADAN\",\"229-3, LEBANON STR, IBADAN\":\"229-3, LEBANON STR, IBADAN\",\"232-OKENE\":\"232-OKENE\",\"233-CHALLENGE ROAD IBADAN\":\"233-CHALLENGE ROAD IBADAN\",\"235-NEW GBAGI MKT IBADAN\":\"235-NEW GBAGI MKT IBADAN\",\"237-BANK ROAD IBADAN\":\"237-BANK ROAD IBADAN\",\"239-UCH IBADAN\":\"239-UCH IBADAN\",\"248-76 LEBANON  IBADAN\":\"248-76 LEBANON  IBADAN\",\"249-IBI BRANCH\":\"249-IBI BRANCH\",\"250-LEKKI ONIRU\":\"250-LEKKI ONIRU\",\"251-KUBWA\":\"251-KUBWA\",\"252-GWARIMPA\":\"252-GWARIMPA\",\"253-ADETOKUNBO ADEMOLA\":\"253-ADETOKUNBO ADEMOLA\",\"254-IBUSA\":\"254-IBUSA\",\"255-GWAGWALADA\":\"255-GWAGWALADA\",\"256-SHENDAM\":\"256-SHENDAM\",\"257-KABBA\":\"257-KABBA\",\"258-KOFAR RUWA\":\"258-KOFARRUWA\",\"259-WARRI REFINERY\":\"259-WARRI REFINERY\",\"260-IDAH\":\"260-IDAH\",\"261-MARYLAND\":\"261-MARYLAND\",\"262-IDANRE\":\"262-IDANRE\",\"263-UMUNEKE\":\"263-UMUNEKE\",\"264-OKOPEDI\":\"264-OKOPEDI\",\"265-IDI-ARABA\":\"265-IDI-ARABA\",\"266-6, HERBERT MACAULAY\":\"266-6, HERBERT MACAULAY\",\"267-ILE IFE\":\"267-ILE IFE\",\"268-KASHERE\":\"268-KASHERE\",\"270-IFEWARA\":\"270-IFEWARA\",\"276-IGEDE EKITI\":\"276-IGEDE EKITI\",\"282-IGUEBEN\":\"282-IGUEBEN\",\"287-IGUOBAZUWA\":\"287-IGUOBAZUWA\",\"288-IJEBU ODE MAIN\":\"288-IJEBU ODE MAIN\",\"297-IJORA\":\"297-IJORA\",\"299-IJU\":\"299-IJU\",\"303-ALAUSA, IKEJA\":\"303-ALAUSA,IKEJA\",\"306-AGIDINGBI\":\"306-AGIDINGBI\",\"307-OBA AKRAN\":\"307-OBA AKRAN\",\"308-IKOT-EDIBON\":\"308-IKOT-EDIBON\",\"309-IKOT-EKPENE\":\"309-IKOT-EKPENE\",\"311-IKIRUN\":\"311-IKIRUN\",\"312-ILAWE-EKITI\":\"312-ILAWE-EKITI\",\"314-ILESHA\":\"314-ILESHA\",\"316-M.M. WAY ILORIN\":\"316-M.M. WAY ILORIN\",\"317-IKORODU\":\"317-IKORODU\",\"318-ILORIN UNIVERSITY\":\"318-ILORIN UNIVERSITY\",\"321-ILORIN  MARKET\":\"321-ILORIN  MARKET\",\"326-IWOPIN\":\"326-IWOPIN\",\"327-ILUTITUN\":\"327-ILUTITUN\",\"328-ISA BRANCH\":\"328-ISA BRANCH\",\"329-ILUPEJU\":\"329-ILUPEJU\",\"331-IRUEKPEN\":\"331-IRUEKPEN\",\"332-ISEYIN\":\"332-ISEYIN\",\"335-ISOLO\":\"335-ISOLO\",\"337-BANK STR  JOS\":\"337-BANK STR  JOS\",\"338-ITA-OGBOLU\":\"338-ITA-OGBOLU\",\"339-JIBIA\":\"339-JIBIA\",\"346-AHMADU BELLO., KADUNA\":\"346-AHMADU BELLO., KADUNA\",\"347-JOS MARKET\":\"347-JOS MARKET\",\"348-JENGRE\":\"348-JENGRE\",\"349-M.M WAY JOS\":\"349-M.M WAY JOS\",\"350-JEGA\":\"350-JEGA\",\"351-PPMC KADUNA\":\"351-PPMC KADUNA\",\"352-ABOH MBAISE\":\"352-ABOH MBAISE\",\"356-YAKUBU GOWON WAY KADUNA\":\"356-YAKUBU GOWON WAY KADUNA\",\"365-KADUNA SOUTH\":\"365-KADUNA SOUTH\",\"371-MOHAMMED BUHARI WAY\":\"371-MOHAMMED BUHARI WAY\",\"373-JAJI\":\"373-JAJI\",\"376-ADO BAYERO KANO\":\"376-ADO BAYERO KANO\",\"377-POST OFFICE RD KANO\":\"377-POST OFFICE RD KANO\",\"390-CHALAWA LAYOUT\":\"390-CHALAWA LAYOUT\",\"391-HADEJIA\":\"391-HADEJIA\",\"393-KANO MAIN\":\"393-KANO MAIN\",\"394-M.M. WAY KANO\":\"394-M.M. WAY KANO\",\"395-BANK ROAD KANO\":\"395-BANK ROAD KANO\",\"396-ZOO ROAD KANO\":\"396-ZOO ROAD KANO\",\"400-KARU\":\"400-KARU\",\"406-KATSINA MAIN\":\"406-KATSINA MAIN\",\"407-KWAYA KUSAR\":\"407-KWAYA KUSAR\",\"408-ADEOLA-ODEKU\":\"408-ADEOLA-ODEKU\",\"409-LAFIAGI\":\"409-LAFIAGI\",\"410-AWOLOWO ROAD IKOYI\":\"410-AWOLOWO ROAD IKOYI\",\"411-FORESHORE TOWERS, IKOYI\":\"411-FORESHORE TOWERS, IKOYI\",\"413-ALLEN AVENUE\":\"413-ALLEN AVENUE\",\"414-ADENIJI ADELE\":\"414-ADENIJI ADELE\",\"415-BALOGUN MARKET\":\"415-BALOGUN MARKET\",\"416-AMUWO-ODOFIN\":\"416-AMUWO-ODOFIN\",\"418-FALOMO\":\"418-FALOMO\",\"419-ERIC MOORE ROAD\":\"419-ERIC MOORE ROAD\",\"421-OIL ESTATE LEKKI\":\"421-OIL ESTATE LEKKI\",\"422-FED SEC - LAGOS\":\"422-FED SEC - LAGOS\",\"423-IJESHATEDO\":\"423-IJESHATEDO\",\"424-DOPEMU LAGOS\":\"424-DOPEMU LAGOS\",\"425-OSHODI\":\"425-OSHODI\",\"426-KAKAWA\":\"426-KAKAWA\",\"427-LADIPO\":\"427-LADIPO\",\"428-LAWANSON\":\"428-LAWANSON\",\"430-EGBE\":\"430-EGBE\",\"431-DOCEMO IDUMOTA\":\"431-DOCEMO IDUMOTA\",\"432-OBALENDE\":\"432-OBALENDE\",\"433-LEWIS STREET\":\"433-LEWIS STREET\",\"435-DAVIES STREET\":\"435-DAVIES STREET\",\"436-131 BROAD STREET\":\"436-131 BROAD STREET\",\"437-118/120 BROAD STREET\":\"437-118/120 BROAD STREET\",\"439-ORILE COKER\":\"439-ORILE COKER\",\"440-AJAH\":\"440-AJAH\",\"441-IDIMU\":\"441-IDIMU\",\"443-SANUSI OLUSI\":\"443-SANUSI OLUSI\",\"444-OGIDI\":\"444-OGIDI\",\"445-2ND FACTORY ROAD ABA\":\"445-2ND FACTORY ROAD ABA\",\"446-CONCORDE OWERRI\":\"446-CONCORDE OWERRI\",\"448-MAITAMA ABUJA\":\"448-MAITAMA ABUJA\",\"451-NASS ABUJA\":\"451-NASS ABUJA\",\"453-HOTORO KANO\":\"453-HOTORO KANO\",\"454-MOGADISHU LAYOUT KADUNA\":\"454-MOGADISHU LAYOUT KADUNA\",\"457-CREEK ROAD, APAPA\":\"457-CREEK ROAD, APAPA\",\"458-AGEGE MAIN\":\"458-AGEGE MAIN\",\"459-PEN CINEMA AGEGE\":\"459-PEN CINEMA AGEGE\",\"460-ADEOLA HOPEWELL\":\"460-ADEOLA HOPEWELL\",\"461-AJOSE ADEOGUN\":\"461-AJOSE ADEOGUN\",\"462-OYIN JOLAYEMI\":\"462-OYIN JOLAYEMI\",\"463-ADEYEMO ALAKIJA\":\"463-ADEYEMO ALAKIJA\",\"466-MOLONEY\":\"466-MOLONEY\",\"475-OBUN EKO\":\"475-OBUN EKO\",\"483-LANGTANG\":\"483-LANGTANG\",\"487-3RD AVENUE FESTAC\":\"487-3RD AVENUE FESTAC\",\"491-BBA (J ANYI) PLAZA\":\"491-BBA (J ANYI) PLAZA\",\"492-TINUBU SQUARE LAGOS\":\"492-TINUBU SQUARE LAGOS\",\"493-TRADE FAIR COMPLEX\":\"493-TRADE FAIR COMPLEX\",\"494-ASPAMDA MAIN GATE\":\"494-ASPAMDA MAIN GATE\",\"495-LOKOJA MAIN\":\"495-LOKOJA MAIN\",\"497-LAFIA\":\"497-LAFIA\",\"500-MACE PLAZA, TRADE FAIR\":\"500-MACE PLAZA, TRADE FAIR\",\"506-MAIDUGURI MAIN\":\"506-MAIDUGURI MAIN\",\"509-YAHAYA MADAKI, KATSINA\":\"509-YAHAYA MADAKI, KATSINA\",\"514-AJAOKUTA\":\"514-AJAOKUTA\",\"516-MAKURDI MAIN\":\"516-MAKURDI MAIN\",\"534-MAYO BELWA\":\"534-MAYO BELWA\",\"536-MINNA\":\"536-MINNA\",\"544-MISAU\":\"544-MISAU\",\"545-MUBI\":\"545-MUBI\",\"549-AKAMKPA\":\"549-AKAMKPA\",\"555-MUSHIN\":\"555-MUSHIN\",\"556-NNEWI\":\"556-NNEWI\",\"557-ONNE NAFCON\":\"557-ONNE NAFCON\",\"558-GAMBORU NGALLA\":\"558-GAMBORU NGALLA\",\"559-OGBEDE\":\"559-OGBEDE\",\"560-ODE IRELE\":\"560-ODE IRELE\",\"564-OGHARA\":\"564-OGHARA\",\"565-OFFA\":\"565-OFFA\",\"566-OGERE\":\"566-OGERE\",\"567-OGBA LAGOS\":\"567-OGBA LAGOS\",\"568-OGBOMOSO\":\"568-OGBOMOSO\",\"569-OGOJA\":\"569-OGOJA\",\"570-OKENGWE\":\"570-OKENGWE\",\"571-OHAFIA\":\"571-OHAFIA\",\"572-OJU\":\"572-OJU\",\"573-OKOKOMAIKO\":\"573-OKOKOMAIKO\",\"574-ONDO BRANCH\":\"574-ONDO BRANCH\",\"575-OKIGWE\":\"575-OKIGWE\",\"576-OLEH\":\"576-OLEH\",\"581-OMU-ARAN\":\"581-OMU-ARAN\",\"582-ORAIFITE\":\"582-ORAIFITE\",\"583-UPPER IWEKA ROAD\":\"583-UPPER IWEKA ROAD\",\"585-BRIGHT STR., ONITSHA\":\"585-BRIGHT STR., ONITSHA\",\"586-AJAEGBU EZE\":\"586-AJAEGBU EZE\",\"593-GASHUA\":\"593-GASHUA\",\"595-18, NEW MKT RD. ONITSHA\":\"595-18, NEW MKT RD. ONITSHA\",\"596-NIGER BRIDGE HEAD, ONITSHA\":\"596-NIGER BRIDGE HEAD, ONITSHA\",\"597-ATANI ROAD, ONITSHA\":\"597-ATANI ROAD, ONITSHA\",\"598-OREGUN LAGOS\":\"598-OREGUN LAGOS\",\"604-ORLU\":\"604-ORLU\",\"606-ORO\":\"606-ORO\",\"609-OSOGBO\":\"609-OSOGBO\",\"611-OTTA\":\"611-OTTA\",\"612-OVWIAN ALADJA\":\"612-OVWIAN ALADJA\",\"614-DOUGLAS RD, OWERRI\":\"614-DOUGLAS RD, OWERRI\",\"615-2ND  OWERRI\":\"615-2ND  OWERRI\",\"616-EKPOMA\":\"616-EKPOMA\",\"618-IKENEGBU LAYOUT OKIGWE\":\"618-IKENEGBU LAYOUT OKIGWE\",\"629-OYO BRANCH\":\"629-OYO BRANCH\",\"631-PATEGI\":\"631-PATEGI\",\"632-MANGU\":\"632-MANGU\",\"633-PAIKO\":\"633-PAIKO\",\"643-ORIJE PORT-HARCOURT\":\"643-ORIJE PORT-HARCOURT\",\"645-171D ABA ROAD, PHC\":\"645-171D ABA ROAD, PHC\",\"648-AGBOR\":\"648-AGBOR\",\"653-STATION ROAD PHC\":\"653-STATION ROAD PHC\",\"654-KINGSWAY PHC\":\"654-KINGSWAY PHC\",\"655-101, IKWERRE ROAD P/H\":\"655-101, IKWERRE ROAD P/H\",\"656-180, ABA ROAD P/H\":\"656-180, ABA ROAD P/H\",\"657-TRANS AMADI PH.\":\"657-TRANS AMADI PH.\",\"659-SAGAMU\":\"659-SAGAMU\",\"660-QUA IBOE TERMINAL\":\"660-QUA IBOE TERMINAL\",\"662-YENAGOA MAIN\":\"662-YENAGOA MAIN\",\"663-OVOM, YENAGOA\":\"663-OVOM, YENAGOA\",\"664-SAMARU\":\"664-SAMARU\",\"673-SAPELE\":\"673-SAPELE\",\"674-SHARADA, KANO\":\"674-SHARADA, KANO\",\"678-SOMOLU\":\"678-SOMOLU\",\"682-STALLION PLAZA\":\"682-STALLION PLAZA\",\"683-SOKOTO  MAIN\":\"683-SOKOTO  MAIN\",\"691-SOKOTO MARKET\":\"691-SOKOTO MARKET\",\"694-SURULERE\":\"694-SURULERE\",\"695-TIN-CAN ISLAND LAGOS\":\"695-TIN-CAN ISLAND LAGOS\",\"696-TALASSE\":\"696-TALASSE\",\"698-UBURU OHAOZARA\":\"698-UBURU OHAOZARA\",\"701-WHARF ROAD APAPA\":\"701-WHARF ROAD APAPA\",\"703-WUSE II ABUJA\":\"703-WUSE II ABUJA\",\"707-UMUAHIA\":\"707-UMUAHIA\",\"709-UMUDIKE\":\"709-UMUDIKE\",\"713-UROMI\":\"713-UROMI\",\"718-UYO MAIN\":\"718-UYO MAIN\",\"719-AIRPORT ROAD WARRI\":\"719-AIRPORT ROAD WARRI\",\"723-WARRI MAIN\":\"723-WARRI MAIN\",\"724-EFFURUN II\":\"724-EFFURUN II\",\"733-SABO YABA\":\"733-SABO YABA\",\"737-YELWA\":\"737-YELWA\",\"743-ARIARIA\":\"743-ARIARIA\",\"744-YOLA MAIN\":\"744-YOLA MAIN\",\"748-JALINGO\":\"748-JALINGO\",\"753-ZARIA\":\"753-ZARIA\",\"754-AROCHUKWU\":\"754-AROCHUKWU\",\"757-KETU\":\"757-KETU\",\"761-ALABA INT MKT\":\"761-ALABA INT MKT\",\"762-NEW ALABA MARKET\":\"762-NEW ALABA MARKET\",\"765-OKE-ARIN\":\"765-OKE-ARIN\",\"785-FAULKS ROAD\":\"785-FAULKS ROAD\",\"789-VENN ROAD, ONITSHA\":\"789-VENN ROAD, ONITSHA\",\"791-KIRIKASAMA MAID\":\"791-KIRIKASAMA MAID\",\"792-ASABA II\":\"792-ASABA II\",\"794-AREA 8 GARKI\":\"794-AREA 8 GARKI\",\"795-CADASTRAL ZONE\":\"795-CADASTRAL ZONE\",\"796-J7 AHMADU BELLO WAY, KADUNA\":\"796-J7 AHMADU BELLO WAY, KADUNA\",\"798-BOMPAI KANO\":\"798-BOMPAI KANO\",\"799-56 IBADAN ROAD, IJEBU ODE\":\"799-56 IBADAN ROAD, IJEBU ODE\",\"803-45 IKWERRE ROAD, PHC\":\"803-45 IKWERRE ROAD, PHC\",\"805-IWO ROAD\":\"805-IWO ROAD\",\"829-OGIRI-OKO\":\"829-OGIRI-OKO\",\"901-UYO CASH CENTRE\":\"901-UYO CASH CENTRE\",\"902-ENUGU CASH CENTRE\":\"902-ENUGU CASH CENTRE\",\"903-BAUCHI CASH CENTRE\":\"903-BAUCHI CASH CENTRE\",\"904-BENIN CASH CENTRE\":\"904-BENIN CASH CENTRE\",\"905-MAKURDI CASH CENTRE\":\"905-MAKURDI CASH CENTRE\",\"906-MAIDUGURI CASH CENTRE\":\"906-MAIDUGURI CASH CENTRE\",\"908-ABUJA CASH CENTRE\":\"908-ABUJA CASH CENTRE\",\"909-YOLA CASH CENTRE\":\"909-YOLA CASH CENTRE\",\"910-OWERRI CASH CENTRE\":\"910-OWERRI CASH CENTRE\",\"911-KADUNA CASH CENTRE\":\"911-KADUNA CASH CENTRE\",\"912-KANO CASH CENTRE\":\"912-KANO CASH CENTRE\",\"913-ABA CASH CENTRE\":\"913-ABA CASH CENTRE\",\"914-ILORIN CASH CENTRE\":\"914-ILORIN CASH CENTRE\",\"915-UBN CLEARING CENTRE\":\"915-UBN CLEARING CENTRE\",\"916-MINNA CASH CENTRE\":\"916-MINNA CASH CENTRE\",\"917-ABEOKUTA CASH CENTRE\":\"917-ABEOKUTA CASH CENTRE\",\"918-AKURE CASH CENTRE\":\"918-AKURE CASH CENTRE\",\"919-IBADAN CASH CENTRE\":\"919-IBADAN CASH CENTRE\",\"920-JOS CASH CENTRE\":\"920-JOS CASH CENTRE\",\"922-SOKOTO CASH CENTRE\":\"922-SOKOTO CASH CENTRE\",\"924-EFFURUN CASH CENTRE\":\"924-EFFURUN CASH CENTRE\",\"925-KATSINA CASH CENTRE\":\"925-KATSINA CASH CENTRE\",\"926-TINUBU CASH CENTRE\":\"926-TINUBU CASH CENTRE\",\"931-ONITSHA CASH CENTRE\":\"931-ONITSHA CASH CENTRE\",\"937-AWKA CASH CENTRE\":\"937-AWKA CASH CENTRE\",\"939-LOKOJA CASH CENTRE\":\"939-LOKOJA CASH CENTRE\",\"965-BAGA\":\"965-BAGA\",\"992-PORT HARCOURT CASH CENTRE\":\"992-PORT HARCOURT CASH CENTRE\"}],\"respcode\":\"00\"}";
		//System.out.println("Response ["+response+"]");
		
		//resp = new JSONObject(response);
		
		
	} catch (JSONException e) {
		e.printStackTrace();
	}
	return response;
}

	public static String getCustomerDetail(String Custid) {
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			request.put("reqtype", "CUST_DETAIL");
			request.put("username", Custid); /* tdamusan */
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			// response="{\"respdesc\":\"SUCCESS\",\"BVN\":\"BVN123456789\",\"respcode\":\"00\",\"CUST_INFO\":[{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0039148152\",\"accountname\":\"AMUSAN TITILOPE D\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[{\"netbalance\":\"155,271.23\",\"noofWithdrawals\":\"\",\"accountcurrency\":\"NGN\",\"daue\":\"\",\"clearedBalance\":\"155,271.23\",\"closingBalance\":\"180,371.23\",\"hasCOT\":\"1\",\"unclearedBalance\":\"0.00\",\"withdrawals\":\"\",\"noofLodgements\":\"\",\"flgempacct\":\"N\",\"dateOpened\":\"2013-06-11\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"ACCOUNT OPEN REGULAR\",\"accountNo\":\"0039148152\",\"flgstaff\":\"Y\",\"availabletowithdraw\":\"155,271.23\",\"amountHold\":\"0.00\",\"openingBalance\":\"0\",\"odlimit\":\"\",\"hasIMAGE\":\"\",\"totalbalance\":\"155,271.23\",\"accountproduct\":\"CURRENT ACCOUNT - STAFF\",\"lodgements\":\"\"}],\"transferlimit\":\"500000\",\"accright\":\"transfer\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"},{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0051358634\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[],\"transferlimit\":\"0\",\"accright\":\"view\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"},{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0052342539\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[],\"transferlimit\":\"0\",\"accright\":\"view\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"}],\"BRCODE\":\"682\"}";
			//System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public	static String  AccountDetails(String accountno)
	{
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response="";
		try {
			request.put("reqtype", "LDAP_AUTH");
			request.put("reqtype", "ACC_ENQ_BY_ACC");
			request.put("acctnumber", accountno);
			request.put("username", "");
			request.put("channel", "WEB");
			request.put("WEB", "Y");
			System.out.println("Request ["+request+"]");
			//response = ServiceCaller.route(request).toString();
			//response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			//response="{\"phone\":\"0803601030\",\"amountCreditYTD\":\"0.00\",\"custID\":\"003596147\",\"lastCreditDate\":\"02-JUN-2016\",\"lastDebitInterestAccrued\":\"0.00\",\"odlimit\":\"0.00\",\"unavailableBalance\":\"0.00\",\"strCity\":\"\",\"dateOpened\":\"31-MAY-2006\",\"description\":null,\"strState\":\"\",\"interestPaidYTD\":\"0.00\",\"lastCreditInterestAccrued\":\"0.00\",\"maintenanceAuthorizedBy\":\"Default Admin User4\",\"branchCode\":\"329\",\"amountCreditMTD\":\"1,822,563.41\",\"strZip\":\"22141796440\",\"amountDebitYTD\":\"0.00\",\"amountLastDebit\":\"0.00\",\"amountDebitMTD\":\"2,058,756.57\",\"branchName\":\"ILUPEJU\",\"lastMaintainedBy\":\"Default Admin User4\",\"profitCenter\":\"\",\"lastDebitDate\":\"02-JUN-2016\",\"dauelimit\":\"0\",\"interestReceivedYTD\":\"0.00\",\"atmstatus\":\"Y\",\"accountOfficer\":\"5428590\",\"daueendDate\":\"\",\"dauestartDate\":\"\",\"lastSerialofCheque\":\"0\",\"currency\":\"NIGERIAN NAIRA\",\"amountHold\":\"0.00\",\"staff\":\"Y\",\"accountStatus\":\"8-ACCOUNT OPEN REGULAR\",\"strAdd1\":\"C/O UNION BANK OF NIG PLC\",\"strAdd3\":\"ILUPEJU\",\"strAdd2\":\"STATIONERY DEPT\",\"customerName\":\"OKPALAUGO C T\",\"currencyCode\":\"NGN\",\"productCode\":\"CA_100~\",\"serviceChargeYTD\":\"0.00\",\"strCountry\":\"NG\",\"availableBalance\":\"157,326.82\",\"accountName\":\"COLLINS TOCHUKWU OKPALAUGO\",\"bookBalance\":\"156,342.82\",\"amountLastCredit\":\"0.00\",\"productName\":\"CURRENT ACCOUNT - STAFF\",\"lastUsedChequeNo\":\"0\"}";
			//System.out.println("Response ["+response+"]");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String getCustomerDetailsByAcc(String accno) {
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			request.put("reqtype", "ACC_ENQ_BY_ACC");
			request.put("acctnumber", accno); /* 0005534521 */
			request.put("WEB", "Y");
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			//System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	public	static String custandactbycustid(String custid)
	 {
		JSONObject request = new JSONObject();
	  JSONObject obj = new JSONObject();
	  ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
	  String response = "";
	  try {

		  request.put("custid", custid);
		  request.put("channel", "WEB");
		  request.put("reqtype", "ACC_CUST_INFO_BYCUSTID");
	   

	   System.out.println("Request ["+request+"]");
	   //response = ServiceCaller.route(request).toString();
	   //response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), obj);
	   response = ServiceRequestClient.serviceURL(request);
	   //System.out.println("Response ["+response+"]");
	   //response = "{\"custactinfo\":{\"acctsumm\":[{\"flgstaff\":\"N\",\"accountname\":\"MR\",\"withdrawals\":\"0.00\",\"clearedBalance\":\"0.00\",\"daue\":\"0.00\",\"availabletowithdraw\":\"0.00\",\"noofWithdrawals\":\"0\",\"accountNo\":\"0050704825\",\"accountstatus\":\"ACCOUNT OPEN - NO DEBIT\",\"odlimit\":\"0.00\",\"lodgements\":\"0.00\",\"totalbalance\":\"0.00\",\"hasIMAGE\":\"\",\"amountHold\":\"0.00\",\"accountcurrency\":\"NGN\",\"flgempacct\":\"0\",\"openingBalance\":\"\",\"dateOpened\":\"2016-06-09\",\"unclearedBalance\":\"0.00\",\"noofLodgements\":\"0\",\"hasCOT\":\"\",\"netbalance\":\"0.00\",\"accountproduct\":\"SA_001\",\"closingBalance\":\"0.00\"}],\"customerID\":\"006303894\"},\"respcode\":\"00\",\"respdesc\":\"SUCCESS\",\"custinfo\":{\"dateOfBirth\":\"21-MAR-1956\",\"nationalityID\":\"\",\"businessRegNo\":\"\",\"dateIncoporated\":\"\",\"maritalStatus\":\"\",\"description\":null,\"custEmail\":\"\",\"gender\":\"Male\",\"custNationality\":\"NG\",\"permanentAddress\":\"1\",\"custid\":\"006303894\",\"custType\":\"IND_IND\",\"customerName\":\"OKPA LINS COL\",\"flgCustType\":\"IND_IND\",\"custPhone\":\"08036010740\",\"maillingAddress\":\"NO_DATA1 NO_DATA2 NO_DATA3\r ,  \rNG\",\"datCustOpen\":\"09-JUN-2016\",\"miscode4\":\"NO_DATA3\",\"miscode5\":\"1\",\"businessCategory\":\"1\",\"spouseName\":\"\",\"signatory2\":\"\",\"signatory3\":\"COL\",\"signatory4\":\"lins\",\"signatory5\":\"OKPA\",\"miscode3\":\"NO_DATA2\",\"miscode2\":\"NO_DATA1\",\"miscode1\":\"\",\"director5\":\"1\",\"signatory1\":\"N\",\"director4\":\"1\",\"custFax\":\"\",\"branchName\":\"OKENE~N~22 Lafia Street, OKENE, KOGI, OKENE\",\"director3\":\"1\",\"director2\":\"1\",\"director1\":\"\"}}";
	  /* {"custactinfo":{"acctsumm":[{"flgstaff":"N","accountname":"MR","withdrawals":"0.00","clearedBalance":"0.00","daue":"0.00","availabletowithdraw":"0.00","noofWithdrawals":"0","accountNo":"0050704825","accountstatus":"ACCOUNT OPEN - NO DEBIT","odlimit":"0.00","lodgements":"0.00","totalbalance":"0.00","hasIMAGE":"","amountHold":"0.00","accountcurrency":"NGN","flgempacct":"0","openingBalance":"","dateOpened":"2016-06-09","unclearedBalance":"0.00","noofLodgements":"0","hasCOT":"","netbalance":"0.00","accountproduct":"SA_001","closingBalance":"0.00"}],"customerID":"006303894"},"respcode":"00","respdesc":"SUCCESS","custinfo":{"dateOfBirth":"21-MAR-1956","nationalityID":"","businessRegNo":"","dateIncoporated":"","maritalStatus":"","description":null,"custEmail":"","gender":"Male","custNationality":"NG","permanentAddress":"1","custid":"006303894","custType":"IND_IND","customerName":"OKPA LINS COL","flgCustType":"IND_IND","custPhone":"08036010740","maillingAddress":"NO_DATA1 NO_DATA2 NO_DATA3\r ,  \rNG","datCustOpen":"09-JUN-2016","miscode4":"NO_DATA3","miscode5":"1","businessCategory":"1","spouseName":"","signatory2":"","signatory3":"COL","signatory4":"lins","signatory5":"OKPA","miscode3":"NO_DATA2","miscode2":"NO_DATA1","miscode1":"","director5":"1","signatory1":"N","director4":"1","custFax":"","branchName":"OKENE~N~22 Lafia Street, OKENE, KOGI, OKENE","director3":"1","director2":"1","director1":""}}
	   Response [{"custactinfo":{"acctsumm":[{"flgstaff":"N","accountname":"MR","withdrawals":"0.00","clearedBalance":"0.00","daue":"0.00","availabletowithdraw":"0.00","noofWithdrawals":"0","accountNo":"0050704825","accountstatus":"ACCOUNT OPEN - NO DEBIT","odlimit":"0.00","lodgements":"0.00","totalbalance":"0.00","hasIMAGE":"","amountHold":"0.00","accountcurrency":"NGN","flgempacct":"0","openingBalance":"","dateOpened":"2016-06-09","unclearedBalance":"0.00","noofLodgements":"0","hasCOT":"","netbalance":"0.00","accountproduct":"SA_001","closingBalance":"0.00"}],"customerID":"006303894"},"respcode":"00","respdesc":"SUCCESS","custinfo":{"dateOfBirth":"21-MAR-1956","nationalityID":"","businessRegNo":"","dateIncoporated":"","maritalStatus":"","description":null,"custEmail":"","gender":"Male","custNationality":"NG","permanentAddress":"1","custid":"006303894","custType":"IND_IND","customerName":"OKPA LINS COL","flgCustType":"IND_IND","custPhone":"08036010740","maillingAddress":"NO_DATA1 NO_DATA2 NO_DATA3\r ,  \rNG","datCustOpen":"09-JUN-2016","miscode4":"NO_DATA3","miscode5":"1","businessCategory":"1","spouseName":"","signatory2":"","signatory3":"COL","signatory4":"lins","signatory5":"OKPA","miscode3":"NO_DATA2","miscode2":"NO_DATA1","miscode1":"","director5":"1","signatory1":"N","director4":"1","custFax":"","branchName":"OKENE~N~22 Lafia Street, OKENE, KOGI, OKENE","director3":"1","director2":"1","director1":""}}]
	   Response [{"custactinfo":{"acctsumm":[{"flgstaff":"N","accountname":"MR","withdrawals":"0.00","clearedBalance":"0.00","daue":"0.00","availabletowithdraw":"0.00","noofWithdrawals":"0","accountNo":"0050704825","accountstatus":"ACCOUNT OPEN - NO DEBIT","odlimit":"0.00","lodgements":"0.00","totalbalance":"0.00","hasIMAGE":"","amountHold":"0.00","accountcurrency":"NGN","flgempacct":"0","openingBalance":"","dateOpened":"2016-06-09","unclearedBalance":"0.00","noofLodgements":"0","hasCOT":"","netbalance":"0.00","accountproduct":"SA_001","closingBalance":"0.00"}],"customerID":"006303894"},"respcode":"00","respdesc":"SUCCESS","custinfo":{"dateOfBirth":"21-MAR-1956","nationalityID":"","businessRegNo":"","dateIncoporated":"","maritalStatus":"","description":null,"custEmail":"","gender":"Male","custNationality":"NG","permanentAddress":"1","custid":"006303894","custType":"IND_IND","customerName":"OKPA LINS COL","flgCustType":"IND_IND","custPhone":"08036010740","maillingAddress":"NO_DATA1 NO_DATA2 NO_DATA3\r ,  \rNG","datCustOpen":"09-JUN-2016","miscode4":"NO_DATA3","miscode5":"1","businessCategory":"1","spouseName":"","signatory2":"","signatory3":"COL","signatory4":"lins","signatory5":"OKPA","miscode3":"NO_DATA2","miscode2":"NO_DATA1","miscode1":"","director5":"1","signatory1":"N","director4":"1","custFax":"","branchName":"OKENE~N~22 Lafia Street, OKENE, KOGI, OKENE","director3":"1","director2":"1","director1":""}}]

*/
	  } catch (JSONException e) {
	   e.printStackTrace();
	  }
	  return response;
	 }
	
	public static String getBillers()
	 {
	JSONObject request = new JSONObject();
	  ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
	  String response = "";
	  try {
	      request.put("reqtype", "BILLERS");//BILLERS
	      //response = ServiceCaller.route(request).toString();
	      //response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
	      response = ServiceRequestClient.serviceURL(request);
	      //response="{\"billersdata\":[{\"category\":\"1004\",\"billerId\":\"516\",\"customFieldName2\": ,\"customFieldName\":\"FLAT NUMBER E.G D1-104\",\"shortName\":\"1004ESTATE\",\"billerName\":\"1004 Estates Limited\"},{\"category\":\"LFG\",\"billerId\":\"2676\",\"customFieldName2\": ,\"customFieldName\":\"DEALER CODE - TONNAGE - SHIP TO CODE\",\"shortName\":\"LAFARGEPAYMENTS\",\"billerName\":\"Lafarge Africa Plc\"},{\"category\":\"ABC\",\"billerId\":\"985\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"ABC TRANSPORT\",\"billerName\":\"ABC Transport\"},{\"category\":\"LCC\",\"billerId\":\"217\",\"customFieldName2\": ,\"customFieldName\":\"LCC ACCOUNT NUMBER\",\"shortName\":\"LCC\",\"billerName\":\"Lekki Concession Company (Lekki - Epe Expressway)\"},{\"category\":\"LTC\",\"billerId\":\"158\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"LINK BRIDGE\",\"billerName\":\"Lekki Concession Company (Lekki - Ikoyi Bridge)\"},{\"category\":\"ACN\",\"billerId\":\"215\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING CONFIRMATION NUMBER\",\"shortName\":\"AERO\",\"billerName\":\"AERO Book-On-Hold\"},{\"category\":\"SWIFT\",\"billerId\":\"202\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER ID\",\"shortName\":\"INTERNET SERVICES\",\"billerName\":\"Swift 4G Subscription\"},{\"category\":\"MTNV\",\"billerId\":\"109\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"MTN Direct Top-up (Prepaid)\"},{\"category\":\"MTNHC\",\"billerId\":\"507\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER ACCOUNT NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"MTN ePayment (HYCONNECT)\"},{\"category\":\"AIRTP\",\"billerId\":\"617\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"Airtel Topup (Postpaid)\"},{\"category\":\"GLOV\",\"billerId\":\"913\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"GLO Quick Charge (Topup)\"},{\"category\":\"DSTV\",\"billerId\":\"104\",\"customFieldName2\": ,\"customFieldName\":\"SMART CARD NUMBER\",\"shortName\":\"CABLE\",\"billerName\":\"DSTV Subcription\"},{\"category\":\"MTN\",\"billerId\":\"903\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"MTN Direct Top-up (Postpaid)\"},{\"category\":\"AIRTEL\",\"billerId\":\"901\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"Airtel  MobileTopup (Prepaid)\"},{\"category\":\"ETIV\",\"billerId\":\"908\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"Etisalat Recharge (E-Topup)\"},{\"category\":\"GOTV\",\"billerId\":\"459\",\"customFieldName2\": ,\"customFieldName\":\"Decoder Number (ICU)\r\n\",\"shortName\":\"CABLE\",\"billerName\":\"GoTV Subscription\r\n\"},{\"category\":\"AMBOH\",\"billerId\":\"915\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"AERO MOBILE\",\"billerName\":\"AERO Mobile Book-On-Hold\"},{\"category\":\"ARIK\",\"billerId\":\"667\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER REFERENCE E.G ( 3877XXXXXXXX)\",\"shortName\":\"ARIK AIR\",\"billerName\":\"Arik Air Book-On-Hold\"},{\"category\":\"DANAA\",\"billerId\":\"336\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"DANA AIR\",\"billerName\":\"Dana Air- Book On Hold\"},{\"category\":\"DSC\",\"billerId\":\"643\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING CONFIRMATION NUMBER\",\"shortName\":\"DISCOVERY AIR\",\"billerName\":\"Discovery Air Book-on-hold\"},{\"category\":\"HAKAIR\",\"billerId\":\"290\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"HAK AIR\",\"billerName\":\"Hak Air Book-On-Hold\"},{\"category\":\"MDV\",\"billerId\":\"281\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE NUMBER\",\"shortName\":\"MEDVIEW\",\"billerName\":\"Medview Airlines\"},{\"category\":\"SAAL\",\"billerId\":\"2712\",\"customFieldName2\": ,\"customFieldName\":\"PNR\",\"shortName\":\"SOUTH ARICAN AIRWAYS\",\"billerName\":\"South African Airlines\"},{\"category\":\"WKN\",\"billerId\":\"123\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID/BOOKING NUMBER\",\"shortName\":\"WAKANOW\",\"billerName\":\"Wakanow\"},{\"category\":\"POGN\",\"billerId\":\"2717\",\"customFieldName2\": ,\"customFieldName\":\"AUTHORITY TO PAY\",\"shortName\":\"PINNACLE OIL AND GAS\",\"billerName\":\"Pinnacle Oil and Gas\"},{\"category\":\"AIRT\",\"billerId\":\"102\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTEL POSTPAID\",\"billerName\":\"Airtel Bill Payment (Postpaid)\"},{\"category\":\"ETIPP\",\"billerId\":\"904\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"ETISALAT POSTPAID\",\"billerName\":\"Etisalat Postpaid Payments\"},{\"category\":\"MTNPP\",\"billerId\":\"506\",\"customFieldName2\": ,\"customFieldName\":\"MSISDN (ENTER AS 234XXXX)\",\"shortName\":\"MTN EPOSTPAID\",\"billerName\":\"MTN ePayment (POSTPAID)\"},{\"category\":\"ETIDP\",\"billerId\":\"917\",\"customFieldName2\": ,\"customFieldName\":\"DEALER NUMBER\",\"shortName\":\"ETISALAT DEALER\",\"billerName\":\"Etisalat Dealer Payments\"},{\"category\":\"FCE\",\"billerId\":\"998\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"FCE\",\"billerName\":\"Friends Colony Estates\"},{\"category\":\"BCE\",\"billerId\":\"979\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"BCE\",\"billerName\":\"Bourdillion Court Estate\"},{\"category\":\"CHINA\",\"billerId\":\"210\",\"customFieldName2\": ,\"customFieldName\":\"PICK-UP NUMBER\",\"shortName\":\"CHINESE EMBASSY\",\"billerName\":\"Chinese Embassy Visa Payments - LGS\"},{\"category\":\"COR\",\"billerId\":\"448\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"CORONA SCHOOLS\",\"billerName\":\"Corona Schools\"},{\"category\":\"LSP\",\"billerId\":\"272\",\"customFieldName2\": ,\"customFieldName\":\"REFERENCE NUMBER\",\"shortName\":\"LASPOTECH\",\"billerName\":\"Lagos State Polytechnic\"},{\"category\":\"DAAR\",\"billerId\":\"113\",\"customFieldName2\": ,\"customFieldName\":\"DECODER NUMBER\",\"shortName\":\"DAARSAT\",\"billerName\":\"DAARSAT Communications\"},{\"category\":\"ITV\",\"billerId\":\"308\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"INFINITYTV\",\"billerName\":\"Infinity TV Payments\"},{\"category\":\"IROKO\",\"billerId\":\"977\",\"customFieldName2\": ,\"customFieldName\":\"USER ID\",\"shortName\":\"IROKOTV\",\"billerName\":\"iROKOtv\"},{\"category\":\"MYTV\",\"billerId\":\"112\",\"customFieldName2\": ,\"customFieldName\":\"SMART CARD NUMBER\",\"shortName\":\"MYTV\",\"billerName\":\"MyTV Smart Payment\"},{\"category\":\"STIM\",\"billerId\":\"240\",\"customFieldName2\": ,\"customFieldName\":\"SMARTCARD NUMBER\",\"shortName\":\"STARTIMES\",\"billerName\":\"Startimes Payments\"},{\"category\":\"FPE\",\"billerId\":\"841\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"FEDERAL POLY EDE\",\"billerName\":\"Federal Polytechnic Ede\"},{\"category\":\"LCP\",\"billerId\":\"390\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"LAGOS CITY POLYTECHNIC\",\"billerName\":\"Lagos City Polytechnic\"},{\"category\":\"LASU\",\"billerId\":\"603\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"LASU\",\"billerName\":\"Lagos State University\"},{\"category\":\"RIS\",\"billerId\":\"449\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"REDEEMERS INTL SCHOOL\",\"billerName\":\"Redeemer's International School\"},{\"category\":\"SUN\",\"billerId\":\"412\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"SOUTHWESTERN\",\"billerName\":\"South Western University\"},{\"category\":\"FIRS\",\"billerId\":\"840\",\"customFieldName2\": ,\"customFieldName\":\"TIN\",\"shortName\":\"FIRS\",\"billerName\":\"FIRS\"},{\"category\":\"GLO3G\",\"billerId\":\"503\",\"customFieldName2\": ,\"customFieldName\":\"MOBILE PHONE NUMBER\",\"shortName\":\"GLO3G\",\"billerName\":\"Glo 3G Modem Plans\"},{\"category\":\"GLOHSI\",\"billerId\":\"504\",\"customFieldName2\": ,\"customFieldName\":\"MOBILE PHONE NUMBER\",\"shortName\":\"GLOHSI\",\"billerName\":\"Glo HSI Router Plans\"},{\"category\":\"MTLK\",\"billerId\":\"918\",\"customFieldName2\": ,\"customFieldName\":\"MOBILE NUMBER\",\"shortName\":\"MULTILINKS EVDO\",\"billerName\":\"Multilinks Internet e-PIN Voucher\"},{\"category\":\"SPEC\",\"billerId\":\"991\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT ID/USER ID\",\"shortName\":\"SPECTRANET\",\"billerName\":\"Spectranet Limited\"},{\"category\":\"MBTL\",\"billerId\":\"311\",\"customFieldName2\": ,\"customFieldName\":\"MAC ID\",\"shortName\":\"MOBITEL\",\"billerName\":\"Mobitel Payments\"},{\"category\":\"RGMFB\",\"billerId\":\"2474\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"RGMFB\",\"billerName\":\"Regent MFB\"},{\"category\":\"VDMFB\",\"billerId\":\"2452\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"VDMFB\",\"billerName\":\"Verdant MFB\"},{\"category\":\"DELTA\",\"billerId\":\"117\",\"customFieldName2\": ,\"customFieldName\":\"PAYEE ID\",\"shortName\":\"DELTA\",\"billerName\":\"Delta State Government\"},{\"category\":\"HLM\",\"billerId\":\"561\",\"customFieldName2\": ,\"customFieldName\":\"MATRIC NUMBER\",\"shortName\":\"HALL MARKUNI\",\"billerName\":\"Hallmark University\"},{\"category\":\"SSCOE\",\"billerId\":\"2678\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"SSCOE\",\"billerName\":\"Shehu Shagari College of Education\"},{\"category\":\"DELSP\",\"billerId\":\"666\",\"customFieldName2\": ,\"customFieldName\":\"APPLICANT ID/MATRIC NO.\",\"shortName\":\"DELTA STATE POLY\",\"billerName\":\"Delta State PolyTechnic\"},{\"category\":\"NIM\",\"billerId\":\"2506\",\"customFieldName2\": ,\"customFieldName\":\"MEMBERSHIP ID\",\"shortName\":\"NIM\",\"billerName\":\"Nigerian Institute of Management\"},{\"category\":\"JBM\",\"billerId\":\"391\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"JOBBERMAN\",\"billerName\":\"Jobberman\"},{\"category\":\"DBX\",\"billerId\":\"255\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"DOBOX\",\"billerName\":\"Dobox\"},{\"category\":\"PGTSL\",\"billerId\":\"963\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"99TAPS\",\"billerName\":\"99TAPS\"},{\"category\":\"3MAIN\",\"billerId\":\"230\",\"customFieldName2\": ,\"customFieldName\":\"MOBILE PHONE NUMBER\",\"shortName\":\"3RDMAIN\",\"billerName\":\"3rdMain\"},{\"category\":\"MFE\",\"billerId\":\"969\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"MFE\",\"billerName\":\"Milverton Flats Estate\"},{\"category\":\"NFE\",\"billerId\":\"961\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"NFE\",\"billerName\":\"Northern Foreshore Estate\"},{\"category\":\"EHC\",\"billerId\":\"1765\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"EHC\",\"billerName\":\"Executive Housing Co-Operative Society Limited\"},{\"category\":\"EDOWAS\",\"billerId\":\"201\",\"customFieldName2\": ,\"customFieldName\":\"HOUSE CODE\",\"shortName\":\"EDO WASTE\",\"billerName\":\"Edo Waste Management\"},{\"category\":\"NESG\",\"billerId\":\"256\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"NESG20\",\"billerName\":\"20th Nigerian Economic Summit\"},{\"category\":\"PLIGR\",\"billerId\":\"970\",\"customFieldName2\": ,\"customFieldName\":\"TIN -TAX IDENTIFICATION NUMBER\",\"shortName\":\"PLIGR\",\"billerName\":\"Plateau State IGR\"},{\"category\":\"KOGI\",\"billerId\":\"805\",\"customFieldName2\": ,\"customFieldName\":\"PAYEE ID\",\"shortName\":\"KOGI\",\"billerName\":\"Kogi State Government\"},{\"category\":\"IPNX\",\"billerId\":\"110\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER ID\",\"shortName\":\"IPNX\",\"billerName\":\"ipNX Subscription Payments\"},{\"category\":\"ETC\",\"billerId\":\"2526\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER ID\",\"shortName\":\"EMPOWERING CORPS\",\"billerName\":\"Empowering The Corps (ETC)\"},{\"category\":\"MNSD\",\"billerId\":\"209\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER INSURANCE NUMBER\",\"shortName\":\"GTA\",\"billerName\":\"Mansard Insurance Plc\"},{\"category\":\"ABC\",\"billerId\":\"417\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"ABCMFB\",\"billerName\":\"ABC MFB\"},{\"category\":\"ST\",\"billerId\":\"914\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"SLIM TRADER\",\"billerName\":\"SlimTrader Mobile Book-On-Hold\"},{\"category\":\"SHP\",\"billerId\":\"499\",\"customFieldName2\": ,\"customFieldName\":\"ORDER NUMBER\",\"shortName\":\"6KSHOP\",\"billerName\":\"6KShop\"},{\"category\":\"4SURE\",\"billerId\":\"310\",\"customFieldName2\": ,\"customFieldName\":\"IRM NUMBER\",\"shortName\":\"4SURE\",\"billerName\":\"4Sure payments\"},{\"category\":\"NGE\",\"billerId\":\"975\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"NGE\",\"billerName\":\"Napier Garden Estate\"},{\"category\":\"CRWB\",\"billerId\":\"884\",\"customFieldName2\": ,\"customFieldName\":\"METERNUMBER/ACCOUNT NUMBER\",\"shortName\":\"CRWB\",\"billerName\":\"Cross River Water Board\"},{\"category\":\"FUW\",\"billerId\":\"995\",\"customFieldName2\": ,\"customFieldName\":\"JAMB REGISTRATION NUMBER\",\"shortName\":\"FEDERAL UNIVERSITY WUKARI\",\"billerName\":\"Federal University Wukari\"},{\"category\":\"NISE01\",\"billerId\":\"1213\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"NISTRUCT\",\"billerName\":\"Nigerian Institution of Structural Engineers\"}],\"respcode\":\"00\",\"respdesc\":\"SUCCESS\"}";
	     //System.out.println("--"+response);
	    
	     } catch (JSONException e) {
	      e.printStackTrace();
	     }
	  
	  return response;
	 }
	
	
	public static String getCatagories()
	 {
	JSONObject request = new JSONObject();
	  ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
	  String response = "";
	  try {
	      request.put("reqtype", "QT_CATEGORIES");//BILLERS
	      request.put("channel", "MOBILE");
	      //response = ServiceCaller.route(request).toString();
	      //response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
	      response = ServiceRequestClient.serviceURL(request);
	      //response="{\"billersdata\":[{\"category\":\"1004\",\"billerId\":\"516\",\"customFieldName2\": ,\"customFieldName\":\"FLAT NUMBER E.G D1-104\",\"shortName\":\"1004ESTATE\",\"billerName\":\"1004 Estates Limited\"},{\"category\":\"LFG\",\"billerId\":\"2676\",\"customFieldName2\": ,\"customFieldName\":\"DEALER CODE - TONNAGE - SHIP TO CODE\",\"shortName\":\"LAFARGEPAYMENTS\",\"billerName\":\"Lafarge Africa Plc\"},{\"category\":\"ABC\",\"billerId\":\"985\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"ABC TRANSPORT\",\"billerName\":\"ABC Transport\"},{\"category\":\"LCC\",\"billerId\":\"217\",\"customFieldName2\": ,\"customFieldName\":\"LCC ACCOUNT NUMBER\",\"shortName\":\"LCC\",\"billerName\":\"Lekki Concession Company (Lekki - Epe Expressway)\"},{\"category\":\"LTC\",\"billerId\":\"158\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"LINK BRIDGE\",\"billerName\":\"Lekki Concession Company (Lekki - Ikoyi Bridge)\"},{\"category\":\"ACN\",\"billerId\":\"215\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING CONFIRMATION NUMBER\",\"shortName\":\"AERO\",\"billerName\":\"AERO Book-On-Hold\"},{\"category\":\"SWIFT\",\"billerId\":\"202\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER ID\",\"shortName\":\"INTERNET SERVICES\",\"billerName\":\"Swift 4G Subscription\"},{\"category\":\"MTNV\",\"billerId\":\"109\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"MTN Direct Top-up (Prepaid)\"},{\"category\":\"MTNHC\",\"billerId\":\"507\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER ACCOUNT NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"MTN ePayment (HYCONNECT)\"},{\"category\":\"AIRTP\",\"billerId\":\"617\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"Airtel Topup (Postpaid)\"},{\"category\":\"GLOV\",\"billerId\":\"913\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"GLO Quick Charge (Topup)\"},{\"category\":\"DSTV\",\"billerId\":\"104\",\"customFieldName2\": ,\"customFieldName\":\"SMART CARD NUMBER\",\"shortName\":\"CABLE\",\"billerName\":\"DSTV Subcription\"},{\"category\":\"MTN\",\"billerId\":\"903\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"MTN Direct Top-up (Postpaid)\"},{\"category\":\"AIRTEL\",\"billerId\":\"901\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"Airtel  MobileTopup (Prepaid)\"},{\"category\":\"ETIV\",\"billerId\":\"908\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTIME\",\"billerName\":\"Etisalat Recharge (E-Topup)\"},{\"category\":\"GOTV\",\"billerId\":\"459\",\"customFieldName2\": ,\"customFieldName\":\"Decoder Number (ICU)\r\n\",\"shortName\":\"CABLE\",\"billerName\":\"GoTV Subscription\r\n\"},{\"category\":\"AMBOH\",\"billerId\":\"915\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"AERO MOBILE\",\"billerName\":\"AERO Mobile Book-On-Hold\"},{\"category\":\"ARIK\",\"billerId\":\"667\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER REFERENCE E.G ( 3877XXXXXXXX)\",\"shortName\":\"ARIK AIR\",\"billerName\":\"Arik Air Book-On-Hold\"},{\"category\":\"DANAA\",\"billerId\":\"336\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"DANA AIR\",\"billerName\":\"Dana Air- Book On Hold\"},{\"category\":\"DSC\",\"billerId\":\"643\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING CONFIRMATION NUMBER\",\"shortName\":\"DISCOVERY AIR\",\"billerName\":\"Discovery Air Book-on-hold\"},{\"category\":\"HAKAIR\",\"billerId\":\"290\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"HAK AIR\",\"billerName\":\"Hak Air Book-On-Hold\"},{\"category\":\"MDV\",\"billerId\":\"281\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE NUMBER\",\"shortName\":\"MEDVIEW\",\"billerName\":\"Medview Airlines\"},{\"category\":\"SAAL\",\"billerId\":\"2712\",\"customFieldName2\": ,\"customFieldName\":\"PNR\",\"shortName\":\"SOUTH ARICAN AIRWAYS\",\"billerName\":\"South African Airlines\"},{\"category\":\"WKN\",\"billerId\":\"123\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID/BOOKING NUMBER\",\"shortName\":\"WAKANOW\",\"billerName\":\"Wakanow\"},{\"category\":\"POGN\",\"billerId\":\"2717\",\"customFieldName2\": ,\"customFieldName\":\"AUTHORITY TO PAY\",\"shortName\":\"PINNACLE OIL AND GAS\",\"billerName\":\"Pinnacle Oil and Gas\"},{\"category\":\"AIRT\",\"billerId\":\"102\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"AIRTEL POSTPAID\",\"billerName\":\"Airtel Bill Payment (Postpaid)\"},{\"category\":\"ETIPP\",\"billerId\":\"904\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"ETISALAT POSTPAID\",\"billerName\":\"Etisalat Postpaid Payments\"},{\"category\":\"MTNPP\",\"billerId\":\"506\",\"customFieldName2\": ,\"customFieldName\":\"MSISDN (ENTER AS 234XXXX)\",\"shortName\":\"MTN EPOSTPAID\",\"billerName\":\"MTN ePayment (POSTPAID)\"},{\"category\":\"ETIDP\",\"billerId\":\"917\",\"customFieldName2\": ,\"customFieldName\":\"DEALER NUMBER\",\"shortName\":\"ETISALAT DEALER\",\"billerName\":\"Etisalat Dealer Payments\"},{\"category\":\"FCE\",\"billerId\":\"998\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"FCE\",\"billerName\":\"Friends Colony Estates\"},{\"category\":\"BCE\",\"billerId\":\"979\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"BCE\",\"billerName\":\"Bourdillion Court Estate\"},{\"category\":\"CHINA\",\"billerId\":\"210\",\"customFieldName2\": ,\"customFieldName\":\"PICK-UP NUMBER\",\"shortName\":\"CHINESE EMBASSY\",\"billerName\":\"Chinese Embassy Visa Payments - LGS\"},{\"category\":\"COR\",\"billerId\":\"448\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"CORONA SCHOOLS\",\"billerName\":\"Corona Schools\"},{\"category\":\"LSP\",\"billerId\":\"272\",\"customFieldName2\": ,\"customFieldName\":\"REFERENCE NUMBER\",\"shortName\":\"LASPOTECH\",\"billerName\":\"Lagos State Polytechnic\"},{\"category\":\"DAAR\",\"billerId\":\"113\",\"customFieldName2\": ,\"customFieldName\":\"DECODER NUMBER\",\"shortName\":\"DAARSAT\",\"billerName\":\"DAARSAT Communications\"},{\"category\":\"ITV\",\"billerId\":\"308\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"INFINITYTV\",\"billerName\":\"Infinity TV Payments\"},{\"category\":\"IROKO\",\"billerId\":\"977\",\"customFieldName2\": ,\"customFieldName\":\"USER ID\",\"shortName\":\"IROKOTV\",\"billerName\":\"iROKOtv\"},{\"category\":\"MYTV\",\"billerId\":\"112\",\"customFieldName2\": ,\"customFieldName\":\"SMART CARD NUMBER\",\"shortName\":\"MYTV\",\"billerName\":\"MyTV Smart Payment\"},{\"category\":\"STIM\",\"billerId\":\"240\",\"customFieldName2\": ,\"customFieldName\":\"SMARTCARD NUMBER\",\"shortName\":\"STARTIMES\",\"billerName\":\"Startimes Payments\"},{\"category\":\"FPE\",\"billerId\":\"841\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"FEDERAL POLY EDE\",\"billerName\":\"Federal Polytechnic Ede\"},{\"category\":\"LCP\",\"billerId\":\"390\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"LAGOS CITY POLYTECHNIC\",\"billerName\":\"Lagos City Polytechnic\"},{\"category\":\"LASU\",\"billerId\":\"603\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"LASU\",\"billerName\":\"Lagos State University\"},{\"category\":\"RIS\",\"billerId\":\"449\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"REDEEMERS INTL SCHOOL\",\"billerName\":\"Redeemer's International School\"},{\"category\":\"SUN\",\"billerId\":\"412\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"SOUTHWESTERN\",\"billerName\":\"South Western University\"},{\"category\":\"FIRS\",\"billerId\":\"840\",\"customFieldName2\": ,\"customFieldName\":\"TIN\",\"shortName\":\"FIRS\",\"billerName\":\"FIRS\"},{\"category\":\"GLO3G\",\"billerId\":\"503\",\"customFieldName2\": ,\"customFieldName\":\"MOBILE PHONE NUMBER\",\"shortName\":\"GLO3G\",\"billerName\":\"Glo 3G Modem Plans\"},{\"category\":\"GLOHSI\",\"billerId\":\"504\",\"customFieldName2\": ,\"customFieldName\":\"MOBILE PHONE NUMBER\",\"shortName\":\"GLOHSI\",\"billerName\":\"Glo HSI Router Plans\"},{\"category\":\"MTLK\",\"billerId\":\"918\",\"customFieldName2\": ,\"customFieldName\":\"MOBILE NUMBER\",\"shortName\":\"MULTILINKS EVDO\",\"billerName\":\"Multilinks Internet e-PIN Voucher\"},{\"category\":\"SPEC\",\"billerId\":\"991\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT ID/USER ID\",\"shortName\":\"SPECTRANET\",\"billerName\":\"Spectranet Limited\"},{\"category\":\"MBTL\",\"billerId\":\"311\",\"customFieldName2\": ,\"customFieldName\":\"MAC ID\",\"shortName\":\"MOBITEL\",\"billerName\":\"Mobitel Payments\"},{\"category\":\"RGMFB\",\"billerId\":\"2474\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"RGMFB\",\"billerName\":\"Regent MFB\"},{\"category\":\"VDMFB\",\"billerId\":\"2452\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"VDMFB\",\"billerName\":\"Verdant MFB\"},{\"category\":\"DELTA\",\"billerId\":\"117\",\"customFieldName2\": ,\"customFieldName\":\"PAYEE ID\",\"shortName\":\"DELTA\",\"billerName\":\"Delta State Government\"},{\"category\":\"HLM\",\"billerId\":\"561\",\"customFieldName2\": ,\"customFieldName\":\"MATRIC NUMBER\",\"shortName\":\"HALL MARKUNI\",\"billerName\":\"Hallmark University\"},{\"category\":\"SSCOE\",\"billerId\":\"2678\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"SSCOE\",\"billerName\":\"Shehu Shagari College of Education\"},{\"category\":\"DELSP\",\"billerId\":\"666\",\"customFieldName2\": ,\"customFieldName\":\"APPLICANT ID/MATRIC NO.\",\"shortName\":\"DELTA STATE POLY\",\"billerName\":\"Delta State PolyTechnic\"},{\"category\":\"NIM\",\"billerId\":\"2506\",\"customFieldName2\": ,\"customFieldName\":\"MEMBERSHIP ID\",\"shortName\":\"NIM\",\"billerName\":\"Nigerian Institute of Management\"},{\"category\":\"JBM\",\"billerId\":\"391\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"JOBBERMAN\",\"billerName\":\"Jobberman\"},{\"category\":\"DBX\",\"billerId\":\"255\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"DOBOX\",\"billerName\":\"Dobox\"},{\"category\":\"PGTSL\",\"billerId\":\"963\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"99TAPS\",\"billerName\":\"99TAPS\"},{\"category\":\"3MAIN\",\"billerId\":\"230\",\"customFieldName2\": ,\"customFieldName\":\"MOBILE PHONE NUMBER\",\"shortName\":\"3RDMAIN\",\"billerName\":\"3rdMain\"},{\"category\":\"MFE\",\"billerId\":\"969\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"MFE\",\"billerName\":\"Milverton Flats Estate\"},{\"category\":\"NFE\",\"billerId\":\"961\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"NFE\",\"billerName\":\"Northern Foreshore Estate\"},{\"category\":\"EHC\",\"billerId\":\"1765\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"EHC\",\"billerName\":\"Executive Housing Co-Operative Society Limited\"},{\"category\":\"EDOWAS\",\"billerId\":\"201\",\"customFieldName2\": ,\"customFieldName\":\"HOUSE CODE\",\"shortName\":\"EDO WASTE\",\"billerName\":\"Edo Waste Management\"},{\"category\":\"NESG\",\"billerId\":\"256\",\"customFieldName2\": ,\"customFieldName\":\"PHONE NUMBER\",\"shortName\":\"NESG20\",\"billerName\":\"20th Nigerian Economic Summit\"},{\"category\":\"PLIGR\",\"billerId\":\"970\",\"customFieldName2\": ,\"customFieldName\":\"TIN -TAX IDENTIFICATION NUMBER\",\"shortName\":\"PLIGR\",\"billerName\":\"Plateau State IGR\"},{\"category\":\"KOGI\",\"billerId\":\"805\",\"customFieldName2\": ,\"customFieldName\":\"PAYEE ID\",\"shortName\":\"KOGI\",\"billerName\":\"Kogi State Government\"},{\"category\":\"IPNX\",\"billerId\":\"110\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER ID\",\"shortName\":\"IPNX\",\"billerName\":\"ipNX Subscription Payments\"},{\"category\":\"ETC\",\"billerId\":\"2526\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER ID\",\"shortName\":\"EMPOWERING CORPS\",\"billerName\":\"Empowering The Corps (ETC)\"},{\"category\":\"MNSD\",\"billerId\":\"209\",\"customFieldName2\": ,\"customFieldName\":\"CUSTOMER INSURANCE NUMBER\",\"shortName\":\"GTA\",\"billerName\":\"Mansard Insurance Plc\"},{\"category\":\"ABC\",\"billerId\":\"417\",\"customFieldName2\": ,\"customFieldName\":\"ACCOUNT NUMBER\",\"shortName\":\"ABCMFB\",\"billerName\":\"ABC MFB\"},{\"category\":\"ST\",\"billerId\":\"914\",\"customFieldName2\": ,\"customFieldName\":\"BOOKING REFERENCE\",\"shortName\":\"SLIM TRADER\",\"billerName\":\"SlimTrader Mobile Book-On-Hold\"},{\"category\":\"SHP\",\"billerId\":\"499\",\"customFieldName2\": ,\"customFieldName\":\"ORDER NUMBER\",\"shortName\":\"6KSHOP\",\"billerName\":\"6KShop\"},{\"category\":\"4SURE\",\"billerId\":\"310\",\"customFieldName2\": ,\"customFieldName\":\"IRM NUMBER\",\"shortName\":\"4SURE\",\"billerName\":\"4Sure payments\"},{\"category\":\"NGE\",\"billerId\":\"975\",\"customFieldName2\": ,\"customFieldName\":\"UNIQUE IDENTIFIER\",\"shortName\":\"NGE\",\"billerName\":\"Napier Garden Estate\"},{\"category\":\"CRWB\",\"billerId\":\"884\",\"customFieldName2\": ,\"customFieldName\":\"METERNUMBER/ACCOUNT NUMBER\",\"shortName\":\"CRWB\",\"billerName\":\"Cross River Water Board\"},{\"category\":\"FUW\",\"billerId\":\"995\",\"customFieldName2\": ,\"customFieldName\":\"JAMB REGISTRATION NUMBER\",\"shortName\":\"FEDERAL UNIVERSITY WUKARI\",\"billerName\":\"Federal University Wukari\"},{\"category\":\"NISE01\",\"billerId\":\"1213\",\"customFieldName2\": ,\"customFieldName\":\"TRANSACTION ID\",\"shortName\":\"NISTRUCT\",\"billerName\":\"Nigerian Institution of Structural Engineers\"}],\"respcode\":\"00\",\"respdesc\":\"SUCCESS\"}";
	    // System.out.println("--"+response);
	    
	     } catch (JSONException e) {
	      e.printStackTrace();
	     }
	  
	  return response;
	 }
	
	
	public static String getCustomerAccountDetail(String username) {
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			
			request = new JSONObject();
		    request.put("reqtype", "BAL");
		    request.put("username", username);
		    request.put("channel", "MOBILE");
			
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			// response="{\"respdesc\":\"SUCCESS\",\"BVN\":\"BVN123456789\",\"respcode\":\"00\",\"CUST_INFO\":[{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0039148152\",\"accountname\":\"AMUSAN TITILOPE D\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[{\"netbalance\":\"155,271.23\",\"noofWithdrawals\":\"\",\"accountcurrency\":\"NGN\",\"daue\":\"\",\"clearedBalance\":\"155,271.23\",\"closingBalance\":\"180,371.23\",\"hasCOT\":\"1\",\"unclearedBalance\":\"0.00\",\"withdrawals\":\"\",\"noofLodgements\":\"\",\"flgempacct\":\"N\",\"dateOpened\":\"2013-06-11\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"ACCOUNT OPEN REGULAR\",\"accountNo\":\"0039148152\",\"flgstaff\":\"Y\",\"availabletowithdraw\":\"155,271.23\",\"amountHold\":\"0.00\",\"openingBalance\":\"0\",\"odlimit\":\"\",\"hasIMAGE\":\"\",\"totalbalance\":\"155,271.23\",\"accountproduct\":\"CURRENT ACCOUNT - STAFF\",\"lodgements\":\"\"}],\"transferlimit\":\"500000\",\"accright\":\"transfer\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"},{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0051358634\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[],\"transferlimit\":\"0\",\"accright\":\"view\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"},{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0052342539\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[],\"transferlimit\":\"0\",\"accright\":\"view\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"}],\"BRCODE\":\"682\"}";
			//System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	public static String getCustomerMobileValidation(String mobileno) {
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			
			request = new JSONObject();
		    request.put("reqtype", "ACCBYMOB");
		    request.put("phoneNumber", mobileno);
			
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			// response="{\"respdesc\":\"SUCCESS\",\"BVN\":\"BVN123456789\",\"respcode\":\"00\",\"CUST_INFO\":[{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0039148152\",\"accountname\":\"AMUSAN TITILOPE D\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[{\"netbalance\":\"155,271.23\",\"noofWithdrawals\":\"\",\"accountcurrency\":\"NGN\",\"daue\":\"\",\"clearedBalance\":\"155,271.23\",\"closingBalance\":\"180,371.23\",\"hasCOT\":\"1\",\"unclearedBalance\":\"0.00\",\"withdrawals\":\"\",\"noofLodgements\":\"\",\"flgempacct\":\"N\",\"dateOpened\":\"2013-06-11\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"ACCOUNT OPEN REGULAR\",\"accountNo\":\"0039148152\",\"flgstaff\":\"Y\",\"availabletowithdraw\":\"155,271.23\",\"amountHold\":\"0.00\",\"openingBalance\":\"0\",\"odlimit\":\"\",\"hasIMAGE\":\"\",\"totalbalance\":\"155,271.23\",\"accountproduct\":\"CURRENT ACCOUNT - STAFF\",\"lodgements\":\"\"}],\"transferlimit\":\"500000\",\"accright\":\"transfer\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"},{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0051358634\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[],\"transferlimit\":\"0\",\"accright\":\"view\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"},{\"accountcurrency\":\"NGN\",\"customeremail\":\"damselpet2002@yahoo.com\",\"firstname\":\"TITILOPE\",\"mobilenumber\":\"2348136642310\",\"accountnumber\":\"0052342539\",\"accountname\":\"AMUSAN TITILOPE D.\",\"accountstatus\":\"Authorised\",\"custAcctInfo\":[],\"transferlimit\":\"0\",\"accright\":\"view\",\"username\":\"tdamusan\",\"lastname\":\"AMUSAN\"}],\"BRCODE\":\"682\"}";
			//System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static JSONObject  getBillersDetaisl()
	  {
		JSONObject request = new JSONObject();
	   JSONObject resp=null;
	   ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
	   try {
	    
	   /* request.put("channel", "MOBILE");*/
	    request.put("billerid", "2676");
	    /*request.put("userid", "CEVA" );*/
	    request.put("reqtype", "BILLER_DET" );
	    request.put("channel", "USSD");
	    //String response = ServiceCaller.route(request).toString();
	   // String response =ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
	    String response = ServiceRequestClient.serviceURL(request);
	    resp=new JSONObject(response);
	   } catch (JSONException e) {
	    e.printStackTrace();
	   }
	   return resp;
	  }
	
	public static String AddAccount(String accountNumber,String username,String channel) {
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			
			request = new JSONObject();
		    request.put("accountNumber", accountNumber);
		    request.put("username", username);
		    request.put("channel", channel);
		    request.put("reqtype", "ADD_ACT" );
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			//System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String ModifyAccount(String accountNumber,String username,String channel,String flag,String maker) {
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			
			request = new JSONObject();
		    request.put("accountNumber", accountNumber);
		    request.put("username", username);
		    request.put("channel", channel);
		    request.put("flag", flag);
		    request.put("maker", maker);
		    request.put("reqtype", "MOD_ACT" );
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			//System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	public static String ChangePassword(String newpassword,String userid,String channel) {
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			
			request = new JSONObject();
		    request.put("newpassword", newpassword);
		    request.put("userid", userid);
		    request.put("channel", channel);
		    request.put("reqtype", "CHANGE_PASSWORD" );
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			//System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String bvnInfo(String bvnno) {
		JSONObject request = new JSONObject();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			
			request = new JSONObject();
			request.put("reqtype", "BVN");
			request.put("bvn", bvnno);
			request.put("channel", "MOBILE");
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);
			response = ServiceRequestClient.serviceURL(request);
			//response ="{\"DateOfBirth\":\"29-MAY-79\",\"EnrollmentBank\":\"214\",\"MiddleName\":\"DAVID\",\"respcode\":\"00\",\"respdesc\":\"SUCCESS\",\"FirstName\":\"TOLUWASE\",\"PhoneNumber\":\"8038382068\",\"LastName\":\"ASHAOLU\"}";

			//{"respcode":"99","respdesc":"FAIL"}
			
			//System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String AccountOpen(
			String fname,String mname,String lname,String gender,String dateOfBirth,String mobno,String productCode,String initiatorID,
			String branch,String rmCode,String bvnno,String initialamount,String cevarefno,String reqtime) {
		JSONObject request = new JSONObject();
		String response = "";
		try {
			
			request = new JSONObject();
			request.put("reqtype", "ACC_OPEN_NEW");
			request.put("fname", fname);
			request.put("mname", mname);
			request.put("lname", lname);
			request.put("gender", gender);
			request.put("dateOfBirth", dateOfBirth ); //dateOfBirth "28101982"
			request.put("mobno", mobno);
			request.put("productCode", productCode);
			request.put("initiatorID", initiatorID);
			request.put("branch", branch);
			request.put("rmCode", rmCode);
			// request.put("verifierID", verifierID);
			request.put("channel", "WEB");
			request.put("otp", "");
			
			request.put("cevarefno", cevarefno);
			request.put("initialamount", initialamount);
			request.put("bvn", bvnno);
			request.put("fee", "");
			request.put("totalamount", initialamount);
			request.put("cevarefno", cevarefno);
			request.put("reqtime", reqtime);
			
			/*
			-----------------------------------
			
			
			*/
			
			System.out.println("Request [" + request + "]");
			//response = ServiceCaller.route(request).toString();
			//response = ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);

			response = ServiceRequestClient.serviceURL(request);
			System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String AccountOpenFundTransfer(String acctno,String initialAmount,String username,String cevarefno){
		JSONObject request = new JSONObject();
		String response = "";
		try {
			request = new JSONObject();
			request.put("reqtype", "INI_FUND_TRANS");
					request.put("fromAcccountNumber", "160680129" );
					request.put("toacccountNumber", acctno );
					request.put("amount", initialAmount );
					request.put("branchCode", "682" );
					request.put("username", username);
					request.put("channel", "WEB");
					request.put("description", "Fund Transfer");
					request.put("pin", "" );
					request.put("type", "NO-AUTH");
					request.put("authtype", "NO-AUTH");
					request.put("transdesc", "" );
					request.put("authvalue", "NO-AUTH");
					request.put("cevarefno", cevarefno );
			
			response = ServiceRequestClient.serviceURL(request);
			System.out.println("Response [" + response + "]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
		}
	
	
	public static String serviceURL(JSONObject request) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("auth");
		String response = "";
		try {
			//response = ServiceCaller.route(request).toString();
			response=ServiceRequestSender.postRequest(resourceBundle.getString("Service_config_url"), request);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	

}
