package com.ceva.base.reports.statement;


import static org.thymeleaf.templatemode.TemplateMode.HTML;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import com.ceva.base.reports.statement.model.Accountinfostmnt;
import com.ceva.base.reports.statement.model.Accountstatementdetails;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;


public class StatementReportGenaration {


	private static final String UTF_8 = "UTF-8";
	public static final String STATEMENT_TEMPL_NAME="wallet_statement";
	private static String STATEMENT_NAME = "wallet_account_statement.pdf";

	private static ResourceBundle rb = ResourceBundle.getBundle("configurations");

	public static void main(String[] args) {

		try {

			//Accountinfostmnt obj = testData();

			String mobileno = "8152190597" , type = "Agent" , fromdate= "01/01/2021" , todate= "30/09/2021" ;
			todate= "20/02/2021" ;

			Accountinfostmnt statementObj = new Accountinfostmnt();

			StatementReportDao.getStatementData( mobileno, type, fromdate, todate , statementObj);

			System.out.println("Accountinfostmnt object |" + statementObj.toString() );

			genarateStatement(statementObj , mobileno );

			System.out.println("After Genarating pdf");


		} catch (Exception e) {
			System.out.println("Error while converting HTML to PDF " + e.getMessage());
			e.printStackTrace();
		}

	}

	private static Accountinfostmnt testData() 
	{

		Accountinfostmnt data = new Accountinfostmnt();

		data.setCusname("cevaaaaa");
		data.setAccountno("20138903456");
		data.setBranchname("SBI");
		data.setAccountype("Savings");
		data.setCurrency("NIG");

		data.setPrintdate("2020-01-08");
		data.setStartdate("2020-01-08");
		data.setEnddate("2020-01-08");

		data.setName("cevastatment");
		data.setAddress("xxxxxx");
		data.setTotaldebit("333333333");
		data.setTotalcredit("333332222");
		data.setClosingbalance("2233");



		Accountstatementdetails acountinfodetails = new Accountstatementdetails();
		acountinfodetails.setPosdate("22-07-2021");
		acountinfodetails.setTransactiontype("Fund Transfer Other Banks");
		acountinfodetails.setTransactiondetails("MOBILE/Transfer:Pay\n" + 
				"ref:10129785018749447207USA/23480657\n" + 
				"31492 to 0057537273");
		acountinfodetails.setReferencenumber("000182107220807350002\n" + 
				"57572190");
		acountinfodetails.setValuedate("22-07-2021");
		acountinfodetails.setCredit("9990.00");
		acountinfodetails.setDebit("8888.00");
		acountinfodetails.setBalance("91725.00");

		List<Accountstatementdetails> list = new ArrayList<Accountstatementdetails>();
		list.add(acountinfodetails);

		data.setList(list);

		return data;

	}

	public static void genarateStatement( Accountinfostmnt data , String mobileno ) {

		try {

			String pathx = rb.getString("OUT_PATH");

			ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

			templateResolver.setPrefix("/statementfiles/"); 
			templateResolver.setSuffix(".html");
			templateResolver.setTemplateMode(HTML);
			templateResolver.setCharacterEncoding(UTF_8);

			TemplateEngine templateEngine = new TemplateEngine();
			templateEngine.setTemplateResolver(templateResolver);

			Context context = new Context();
			context.setVariable( "data", data );
			context.setVariable( "postings", data.getList() );
			// System.out.println("Weclome1 |" + templateEngine.getConfiguration());
			String renderedHtmlContent = templateEngine.process ( STATEMENT_TEMPL_NAME , context);

			String outputPdf = pathx+STATEMENT_TEMPL_NAME+"_"+ mobileno +".pdf";

			//create well formed HTML
			org.w3c.dom.Document doc = createWellFormedHtml(renderedHtmlContent);
			System.out.println("Starting conversion to PDF...");
			xhtmlToPdf(doc, outputPdf);


		} catch (IOException e) {
			System.out.println("Error while converting HTML to PDF " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Creating well formed document
	private static org.w3c.dom.Document createWellFormedHtml(String inputHTML) throws IOException {
		Document document = Jsoup.parse(inputHTML, "UTF-8");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		System.out.println("HTML parsing done...");
		return new W3CDom().fromJsoup(document);
	}

	private static void xhtmlToPdf(org.w3c.dom.Document doc, String outputPdf) throws IOException {

		String htmlSourcePath = rb.getString("HTMLTEMPSOURCE");
		System.out.println("htmlSourcePath | "+ htmlSourcePath );

		// base URI to resolve future resources 
		String baseUri = FileSystems.getDefault()
				.getPath(htmlSourcePath)
				.toUri()
				.toString();
		OutputStream os = new FileOutputStream(outputPdf);
		PdfRendererBuilder builder = new PdfRendererBuilder();


		PDFTextStripper stripper = new PDFTextStripper();
		stripper.setStartPage(1);
		stripper.setEndPage(Integer.MAX_VALUE);

		builder.withUri(outputPdf);
		builder.useDefaultPageSize(400, 800, PdfRendererBuilder.PageSizeUnits.MM);

		builder.toStream(os);
		// add external font
		// builder.useFont(new File(getClass().getClassLoader().getResource("Code39.ttf").getFile()), "PRISTINA");
		builder.withW3cDocument(doc, baseUri);

		builder.run();

		System.out.println("PDF creation completed"); 
		os.close();
	}


}
