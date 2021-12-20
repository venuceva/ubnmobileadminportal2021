package com.ceva.util;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.charts.*;
/* Line Chart Example in Apache POI */
public class LineChart
{
  public static void main(String[] args)
    throws Exception
  {
                        /* Create a Workbook object that will hold the final chart */
                       XSSFWorkbook my_workbook = new XSSFWorkbook();
                        /* Create a worksheet object for the line chart. This worksheet will contain the chart */
                       XSSFSheet my_worksheet = my_workbook.createSheet("LineChart_Example");
                       
                       /* Let us now create some test data for the chart */
                       /* Later we can see how to get this test data from a CSV File or SQL Table */
                       /* We use a 4 Row chart input with 5 columns each */
                       for (int rowIndex = 0; rowIndex < 4; rowIndex++)
               {
                       /* Add a row that contains the chart data */
                       XSSFRow my_row = my_worksheet.createRow((short)rowIndex);
                       for (int colIndex = 0; colIndex < 5; colIndex++)
               {
                       /* Define column values for the row that is created */
                       XSSFCell cell = my_row.createCell((short)colIndex);
                       cell.setCellValue(colIndex * (rowIndex + 1));
               }
               }               
                       /* At the end of this step, we have a worksheet with test data, that we want to write into a chart */
                       /* Create a drawing canvas on the worksheet */
                       XSSFDrawing xlsx_drawing = my_worksheet.createDrawingPatriarch();
                       /* Define anchor points in the worksheet to position the chart */
                       XSSFClientAnchor anchor = xlsx_drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);
                       /* Create the chart object based on the anchor point */
                       XSSFChart my_line_chart = xlsx_drawing.createChart(anchor);
                       /* Define legends for the line chart and set the position of the legend */
                       XSSFChartLegend legend = my_line_chart.getOrCreateLegend();
                       legend.setPosition(LegendPosition.BOTTOM);     
                       /* Create data for the chart */
                       XSSFScatterChartData data = my_line_chart.getChartDataFactory().createScatterChartData();     
                       /* Define chart AXIS */
                       ChartAxis bottomAxis = my_line_chart.getChartAxisFactory().createValueAxis(AxisPosition.BOTTOM);
                       ValueAxis leftAxis = my_line_chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
                       leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);     
                       /* Define Data sources for the chart */
                       /* Set the right cell range that contain values for the chart */
                       /* Pass the worksheet and cell range address as inputs */
                       /* Cell Range Address is defined as First row, last row, first column, last column */
                       ChartDataSource<Number> xs = DataSources.fromNumericCellRange(my_worksheet, new CellRangeAddress(0, 0, 0, 4));
                       ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(my_worksheet, new CellRangeAddress(1, 1, 0, 4));
                       ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(my_worksheet, new CellRangeAddress(2, 2, 0, 4));
                       ChartDataSource<Number> ys3 = DataSources.fromNumericCellRange(my_worksheet, new CellRangeAddress(3, 3, 0, 4));
                       /* Add chart data sources as data to the chart */
                       data.addSerie(xs, ys1);
                       data.addSerie(xs, ys2);
                       data.addSerie(xs, ys3);
                       /* Plot the chart with the inputs from data and chart axis */
                       my_line_chart.plot(data, new ChartAxis[] { bottomAxis, leftAxis });
                       /* Finally define FileOutputStream and write chart information */               
                       FileOutputStream fileOut = new FileOutputStream("F:\\xlsx-line-chart.xlsx");
                       my_workbook.write(fileOut);
                       fileOut.close();
  }
}
