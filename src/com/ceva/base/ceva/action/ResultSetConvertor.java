package com.ceva.base.ceva.action;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResultSetConvertor {
	public static JSONArray convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            //JSONObject obj = new JSONObject();
            JSONArray obj=new JSONArray();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getString(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null){
                    columnValue = "null";
                }
                /*if (obj.has(columnName)){
                    columnName += "1";
                }*/
                obj.add(columnValue);
            }
            jsonArray.add(obj);
        }
        return jsonArray;
    }

	public static JSONObject convertResultSetToSelectBox(ResultSet resultSet) throws Exception {
		JSONObject obj = new JSONObject();
        while (resultSet.next()) {
        	obj.put(resultSet.getString(1), resultSet.getString(2));
        }
        return obj;
    }

	public static JSONArray convertResultSetToJsonObjectArray(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            //JSONArray obj=new JSONArray();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getString(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null){
                    columnValue = "null";
                }
                /*if (obj.has(columnName)){
                    columnName += "1";
                }*/
                obj.put(columnName, columnValue);
            }
            jsonArray.add(obj);
        }
        return jsonArray;
    }

    public static int converBooleanIntoInt(boolean bool){
        if (bool) return 1;
        else return 0;
    }

    public static int convertBooleanStringIntoInt(String bool){
        if (bool.equals("false")) return 0;
        else if (bool.equals("true")) return 1;
        else {
            throw new IllegalArgumentException("wrong value is passed to the method. Value is "+bool);
        }
    }

    public static double getDoubleOutOfString(String value, String format, Locale locale){
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat(format,otherSymbols);
        String formattedValue = f.format(Double.parseDouble(value));
        double number = Double.parseDouble(formattedValue);
        return  Math.round(number * 100.0) / 100.0;
    }
}
