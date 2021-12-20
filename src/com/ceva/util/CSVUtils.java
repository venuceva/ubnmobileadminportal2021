package com.ceva.util;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;



public class CSVUtils {
	
	static Logger logger=Logger.getLogger(CSVUtils.class);

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static void main(String[] args) throws Exception {

        String csvFile = "C:\\Users\\UBIC\\Desktop\\BulkOPClaims_Updated.csv";

        Scanner scanner = new Scanner(new File(csvFile));
        int i=1;
        while (scanner.hasNext()) {
        	String ldata = scanner.nextLine();
        	if(ldata !=null && ldata.trim().length()>0)
        	{
        		 List<String> line = parseLine(ldata);
                 logger.debug(i+++"-->Country [Amt= " + line.get(1) + ", code= " + line.get(0) + " , name=" + line.get(2) + "]");
        	}
           
        }
        scanner.close();

    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();
//logger.debug(cvsLine);
        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }
//logger.debug("customQuote :"+customQuote);
        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }
       // logger.debug("separators :"+separators);
        StringBuffer curVal = new StringBuffer();
       // logger.debug("curVal:"+curVal);
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();
      //  logger.debug("chars:"+Arrays.toString(chars));
        for (char ch : chars) {

            if (inQuotes) {
            	//logger.debug("in quoutes");
                startCollectChar = true;
                if (ch == customQuote) {
                //	logger.debug("ch == customQuote");
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {
                //	logger.debug("else ch == customQuote");

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                    	//logger.debug(" ch verf 1");
                        if (!doubleQuotesInColumn) {
                        	//logger.debug(" not doubleQuotesInColumn");
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                           // logger.debug("not double curVal:"+curVal);
                        }
                    } else {
                        curVal.append(ch);
                       // logger.debug(" else ch verf 1 curVal:"+curVal+" "+ch);
                    }

                }
            } else {
            	//logger.debug("else in quoutes:"+ch);
                if (ch == customQuote) {
                	//logger.debug("else in quoutes ch == customQuote");
                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                   // logger.debug(" check 1 :"+chars[0]+" "+customQuote);
                    if (chars[0] != '"' && customQuote == '\"') {
                    	//logger.debug(" check 2 :"+chars[0]+" "+customQuote+" "+curVal);
                        //curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                    	//logger.debug("check 3:"+curVal);
                        curVal.append('"');
                    }

                } else if (ch == separators) {
                	//logger.debug(" check 4 sep :"+curVal);
                    result.add(curVal.toString());
                    //logger.debug("1 result:"+result);
                    curVal = new StringBuffer();
                    //logger.debug("23 curVal:"+curVal);
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                	//logger.debug("fin else :"+curVal+" "+ch);
                    curVal.append(ch);
                }
            }

        }
       //logger.debug(curVal.toString());

        result.add(curVal.toString());
        //logger.debug("value of result:"+result);
        return result;
    }

}