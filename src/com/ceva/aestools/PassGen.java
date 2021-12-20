package com.ceva.aestools;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.ceva.base.service.utils.dao.CommonServiceDao;

public class PassGen {
	
	
	private String str;
    private int randInt;
    private StringBuilder sb;
    private List<Integer> l;

    public PassGen() {
        this.l = new ArrayList<>();
        this.sb = new StringBuilder();

        buildPassword();
    }

    private void buildPassword() {

        //Add ASCII numbers of characters commonly acceptable in passwords
        for (int i = 33; i < 127; i++) {
            l.add(i);
        }

        //Remove characters /, \, and " as they're not commonly accepted
        l.remove(new Integer(34));
        l.remove(new Integer(47));
        l.remove(new Integer(92));

        /*Randomise over the ASCII numbers and append respective character
          values into a StringBuilder*/
        for (int i = 0; i < 10; i++) {
            randInt = l.get(new SecureRandom().nextInt(91));
            sb.append((char) randInt);
        }

        str = sb.toString();
    }

    public String generatePassword() {
        return str;
    }
    
    public String getGenPass(){
    	String out="";
    	
    	//begin method with return string and you may or may not give input
    	char[] values1 = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    	    char[] values2 = {'!','@','&','$','#','%','*'};
    	    char[] values3 = {'1','2','3','4','5','6','7','8','9','0'};
    	    String out1="";
    	    String out2="";
    	    String out3="";
    	 
    	         for (int i=0;i<6;i++)
    	            {
    	             int idx=new SecureRandom().nextInt(values1.length);
    	            out1+= values1[idx];
    	            }
    	 
    	    for (int i=0;i<3;i++)
    	            {
    	            int idx=new SecureRandom().nextInt(values3.length);
    	             out2+= values3[idx];
    	            }
    	 
    	    for (int i=0;i<1;i++)
    	            {
    	            int idx=new SecureRandom().nextInt(values2.length);
    	             out3+= values2[idx];
    	            }
    	 
    	    out= out1.concat(out3).concat(out2);
    	    return out;
    	 
    	//end of method returning String 'OUT' as a random password
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//PassGen pg=new PassGen();
		
		//System.out.println(pg.getGenPass());
		System.out.println(CommonServiceDao.getGenPass());
	}
	
	

}
