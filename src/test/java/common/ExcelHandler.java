package common;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class ExcelHandler{

	
	public HSSFSheet sheet;
	private String excelFileName;
	private HSSFWorkbook wb;
	private String sheetName;
	private int rowno,colno;
	private HSSFRow row;
	private HSSFCell cell;
	
	//To get rows count
	public long getCSVRowsCount(String fileName)
	{
		long number=0;
		try {
			Reader reader=new BufferedReader(new FileReader(new File(fileName)));
			 long cells = 0;
		        int lines = 0;
		        int c;
		        boolean qouted = false;
		        while ((c = reader.read()) != -1) {
		            if (c == '"') {
		                 qouted = !qouted;
		            }
		            if (!qouted) {
		                if (c == '\n') {
		                    lines++;
		                    cells++;
		                }
		                if (c == ',') {
		                    cells++;
		                }
		            }
		        }
		        System.out.printf("lines : %d\n cells %d\n cols: %d\n", lines, cells, cells / lines);
		        reader.close();
		        number=lines;
//			number= Files.readAllLines(Paths.get(fileName)).stream().map(line -> line.split(",")[0]).count();
//			return number;
		}catch(Exception e)
		{
			
		}
		try
	 	 {
			//sleep required
			Thread.sleep(500);
	 	 	 File file = new File(fileName);
	 	 	 if (file.exists())
	 	 	 {
	 	 		 //delete the file after verification
	 	 	 	 file.delete();
	 	 	 }
	 	 	 else
	 	 	 {
	 	 	 	 System.out.println("Path is not present");
	 	 	 }
	 	 }
	 	 catch (Exception e)
	 	 {
	 	 }
		return number;
	}
	
	//To get record count
	public int getRecordsCount(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	    byte[] c = new byte[1024];
	    int count = 0;
	    int readChars = 0;
	    boolean empty = true;
	    while ((readChars = is.read(c)) != -1) {
	        empty = false;
	        for (int i = 0; i < readChars; ++i) {
	            if (c[i] == '\n') {
	                ++count;
	            }
	        }
	    }
	    return (count == 0 && !empty) ? 1 : count;
	    } finally {
	    is.close();
	   }
	}
	
	/* Get the newest file for a specific extension */
	public File getTheNewestFile(String filePath, String ext) {
		try {
			Thread.sleep(2000);
			}
			catch (Exception e) {
			}
	    File theNewestFile = null;
	    File dir = new File(filePath);
	    FileFilter fileFilter = new WildcardFileFilter("*." + ext);
	    File[] files = dir.listFiles(fileFilter);

	    if (files.length > 0) {
	        /** The newest file comes first **/
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	        theNewestFile = files[0];
	    }

	    return theNewestFile;
	}
	
	/**
	 * used to read data from an excel sheet
	 * @param filename 
	 * @param sheetname 
	 * @return a 2 dimensional string read from the excel sheet
	 * @throws IOException
	 */
	public String[][] readArrXLSFile(String filename, String sheetname) throws IOException {
		HSSFRow row;
		HSSFCell cell;
		String[][] value = null;
		@SuppressWarnings("unused")
		double[][] nums = null;

			FileInputStream inputStream = new FileInputStream(filename);
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			
			HSSFSheet sheet = workbook.getSheet(sheetname);
				int rows = sheet.getPhysicalNumberOfRows();
				value = new String[rows][];

				for (int r = 0; r < rows; r++) {
					row = sheet.getRow(r); // bring row
					if (row != null) {
						// get number of cell from row
						int cells = sheet.getRow(r).getPhysicalNumberOfCells();
//						LOGGER.info(cells);
						value[r] = new String[cells];
						for (int c = 0; c < cells; c++) {
							cell = row.getCell(c);
							nums = new double[rows][cells];
//							System.out.print(r+" "+c+" ");

							if (cell != null) {

								switch (cell.getCellType()) {

								case HSSFCell.CELL_TYPE_FORMULA:
									value[r][c] = cell.getCellFormula();
									break;

								case HSSFCell.CELL_TYPE_NUMERIC:
									value[r][c] = ""
											+ cell.getNumericCellValue();
									break;

								case HSSFCell.CELL_TYPE_STRING:
									value[r][c] = ""
											+ cell.getStringCellValue();
									break;

								case HSSFCell.CELL_TYPE_BLANK:
//									value[r][c] = "[BLANK]";
									break;

								case HSSFCell.CELL_TYPE_ERROR:
									value[r][c] = ""+cell.getErrorCellValue();
									break;
								default:
								}
//								System.out.print(value[r][c]+"\t");

							} else {
//								System.out.print("[null]\t");
							}
						} // for(c)
//						System.out.print("\n");
					}
				} // for(r)
//			}
//			LOGGER.info(value.length);
				workbook.close();
				inputStream.close();
			return value;
	}

	
	public void writeXLSFile() throws IOException {
		
		writeXLSOpen();
		writeXLSCFHeadings();
		writeXLSClose();
		
	}
	
	/**
	 * used to print the entire control mapping (as is)
	 * @param file - name of the file to be created
	 * @param data - data to be put into file
	 * @throws IOException
	 */
	public void writeXLSFile(String file,String[][] data) throws IOException {
		
		writeXLSOpen(file);
		writeXLSCFHeadings();
		writeArr2XLS(data);
		writeXLSClose();
		
	}
	
	/**
	 * To make control mappings form json for profiler
	 * @param file - Name of the file (Please provide with .xls extention)
	 * @param sheetName - name of the sheet to be created and printed in
	 * @param data - a 2D array to be printed to xlfile.
	 * @throws IOException - While saving file data.
	 */
	public void writeXLSFile(String file,String sheetName,String[][] data) throws IOException {
		
		writeXLSOpen(file,sheetName);
		writeArr2XLS(data);
		writeXLSClose();
		
	}

	/**
	 * To make control mappings form json for profiler
	 * @param file - Name of the file (Please provide with .xls extention)
	 * @param sheetName - name of the sheet to be created and printed in
	 * @param data - a 2D array to be printed to xlfile.
	 * @throws IOException - While saving file data.
	 */
	public void writeXLSFile(String file,String sheetName,ArrayList<String[]> data) throws IOException {
		
		writeXLSOpen(file,sheetName);
		writeArr2XLS(data);
		writeXLSClose();
		
	}
	
	/**
	 * used to initialize to print the control mapping
	 * @param file - name of the file to be created
	 * @param data - data to be put into file
	 * @throws IOException
	 */
	public void writeCtrlMapXLSFileinit(String file,String[][] data) throws IOException {
		
		writeXLSOpen(file);
		writeXLSCFHeadings();
		
	}
	
	



	/**
	 * used to open or make an excel file for writing
	 */
	public void writeXLSOpen() {
		excelFileName = "Data1.xls";//name of excel file

		sheetName = "sheet1";//name of sheet

		wb = new HSSFWorkbook();
		sheet = wb.createSheet(sheetName) ;		
	}

	/**
	 * used to open or make an excel file for writing
	 * @param file - takes the name of the file to be opend or created
	 */
	public void writeXLSOpen(String file) {
		//LOGGER.info("[INFO]File name is "+file+".xls");
		excelFileName = file+".xls";//name of excel file

		sheetName = "sheet1";//name of sheet

		wb = new HSSFWorkbook();
		//LOGGER.info("[INFO]Sheet Name is "+sheetName);
		sheet = wb.createSheet(sheetName) ;		
	}

	/**
	 * used to open or make an excel file for writing
	 * @param file - takes the name of the file to be opend or created
	 * @param sheetname 
	 */
	public void writeXLSOpen(String file, String sheetname) {
		//LOGGER.info("[INFO]File name is "+file+".xls");
		excelFileName = file;//name of excel file

		sheetName = sheetname;//name of sheet

		wb = new HSSFWorkbook();
		//LOGGER.info("[INFO]Sheet Name is "+sheetName);
		sheet = wb.createSheet(sheetName) ;		
	}

	/**
	 * used to make heading row of controls mapping file
	 */
	public void writeXLSCFHeadings() {
//		int r = 0;
		row = sheet.createRow(rowno++);
		row.createCell(0).setCellValue("GWT");		
		row.createCell(1).setCellValue("Control Name");		
		row.createCell(2).setCellValue("Control Type");		
		row.createCell(3).setCellValue("Control Identification Type");		
		row.createCell(4).setCellValue("Control Id");		
		row.createCell(5).setCellValue("Parent Control");		
		row.createCell(6).setCellValue("PageName");		
		row.createCell(7).setCellValue("Action");		
		row.createCell(8).setCellValue("ActionMethods");		
		
	}
	
	/**
	 * used to print the contents into an controls mapping file
	 */
/*	public static void writeXLSCFContents() {
		String[] gwt = {"Given i entered Username as \'<UserName>\'","And i entered Password as \'<Password>\'","When i select Login","Then Home page shall be \'<displayed>\'"},
		controlname = {"User Name","Password","Login","Home"},
		controltype = {"TextBox","TextBox","Button","Page"},
		controlidentificationtype = {"ID","ID","Text","Title"},
		controlid = {"txtUserName","txtPassword","Login","Home"},
		parrentcontrol={};
		
		String[][] data = {{"Given i entered Username as \'<UserName>\'","And i entered Password as \'<Password>\'","When i select Login","Then Home page shall be \'<displayed>\'"},
		{"User Name","Password","Login","Home"},
		{"TextBox","TextBox","Button","Page"},
		{"ID","ID","Text","Title"},
		{"txtUserName","txtPassword","Login","Home"},
		{"","","",""}};

		//iterating r number of rows
		for (int r=1;r <data[0].length+1 ; r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			for (int c=0;c < data.length; c++ )
			{
				HSSFCell cell = row.createCell(c);
				cell.setCellValue(data[c][r-1]);
			}
		}

	}*/
	
	/**
	 * used to print a 2d array into an excel sheet
	 * @param data - a 2d string array
	 */
	public void writeArr2XLS(String[][] data) {

		//iterating r number of rows
		for (int r=0;r <data.length ; r++ )
		{
//			LOGGER.info("row = "+r);
			HSSFRow row = sheet.createRow(r);
//			LOGGER.info(data[r]);
	
			if(data[r]!= null){
			//iterating c number of columns
			for (int c=0;c < data[r].length; c++ )
			{
//				LOGGER.info("col = "+c);
				HSSFCell cell = row.createCell(c);
				cell.setCellValue(data[r][c]);
			}
//			LOGGER.info("row added");
			}
		}
	}
	

	/**
	 * used to print a 2d array into an excel sheet
	 * @param data - a 2d string array
	 */
	public void writeArr2XLS(ArrayList<String[]> data) {

		//iterating r number of rows
		for (int r=0;r <data.size() ; r++ )
		{
			//			LOGGER.info("row = "+r);
			HSSFRow row = sheet.createRow(r);
			//			LOGGER.info(data[r]);

			if(data.get(r)!= null){
				//iterating c number of columns
				for (int c=0;c < data.get(r).length; c++ )
				{
					//				LOGGER.info("col = "+c);
					HSSFCell cell = row.createCell(c);
					cell.setCellValue(data.get(r)[c]);
				}
				//			LOGGER.info("row added");
			}
		}
	}

	/**
	 * used to print a 1d array into an excel sheet
	 * @param data - a 1d string array
	 */
/*	public static void writeArr2XLS(String[][] data) {

		//iterating r number of rows
		for (int r=0;r <data.length ; r++ )
		{
			LOGGER.info("row = "+r);
			HSSFRow row = sheet.createRow(r);
			LOGGER.info(data[r]);
	
			if(data[r]!= null){
			//iterating c number of columns
			for (int c=0;c < data[r].length; c++ )
			{
				LOGGER.info("col = "+c);
				HSSFCell cell = row.createCell(c);
				cell.setCellValue(data[r][c]);
			}
			LOGGER.info("row added");
			}
		}
	}*/

	/**
	 * used to closed the excel file
	 * @throws IOException
	 */
	public void writeXLSClose() throws IOException {
		if(excelFileName!=null) {
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		if(wb!=null)
			wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		}
		if(wb!=null)
			wb.close();
		//LOGGER.info("Excel File Created "+excelFileName);

	}

	public void nextRow(String[] strings) {
		// TODO Auto-generated method stub
		HSSFRow row = sheet.createRow(rowno++);
		int col = 0;
		for (String string : strings) {
			row.createCell(col++).setCellValue(string);
		}
	}


	public void emptyRow() {
		// TODO Auto-generated method stub
//		HSSFRow row = sheet.createRow(rowno++);
		rowno++;

	}

	/**
	 * Makes a new row and inserts states name in the 1'st column of the data
	 * excel sheet
	 * 
	 * @param name
	 *            - name of the state to be inserted in the 1'st column of the
	 *            excel sheet
	 */
	public void newRow(String name) {
		// TODO Auto-generated method stub
		row = sheet.createRow(rowno++);
		colno = 0;
		cell = row.createCell(colno);
		cell.setCellValue(name);

	}

	public String readdata(String filename)
	{
		ArrayList<String> ar = new ArrayList<String>();  
		String value=null;
		try {   
		 File csvFile = new File(filename);  
		         BufferedReader br = new BufferedReader(new FileReader(csvFile));  
		         String line = "";  
		         StringTokenizer st = null;  
		         int lineNumber = 0;   
		         int tokenNumber = 0;  
		         while ((line = br.readLine()) != null) {  
		           lineNumber++;  
		           //use comma as token separator  
		           st = new StringTokenizer(line, ",");  
		           while (st.hasMoreTokens()) {  
		             tokenNumber++;  
		              String sd=st.nextToken() + "  ";  
		                
		             if(sd!=null){  
		             ar.add(sd);  
		                 System.err.println(sd);  
		             }  
		               
		           }  
		           tokenNumber = 0;  
		         } 
		         br.close();
		        
		         value=ar.toString();
		}catch(Exception e)
		{
			value=ar.toString();
		}
		try
	 	 {
			Thread.sleep(500);
	 	 	 File file = new File(filename);
	 	 	 if (file.exists())
	 	 	 {
	 	 	 	 file.delete();
	 	 	 }
	 	 	 else
	 	 	 {
	 	 	 	 System.out.println("Path is not existing");
	 	 	 }
	 	 }
	 	 catch (Exception e)
	 	 {
	 	 }
		return value;
	}

	public void newCell(String value) {
		// TODO Auto-generated method stub
		cell = row.createCell(++colno);
		cell.setCellValue(value);

	}


	public void inNewSheet(String sheetName, String[][] strings) {
		// TODO Auto-generated method stub
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		int r=0,c=0;
		for (String[] strings2 : strings) {
//			LOGGER.info("row = "+r);
			HSSFRow row = sheet.createRow(r++);
//			LOGGER.info(strings2);

			c = 0;
			if(strings2 != null){
				for (String string : strings2) {
//					LOGGER.info("col = "+c);
					HSSFCell cell = row.createCell(c++);
					cell.setCellValue(string);
				}
//			LOGGER.info("row added");
			}
		}
	}
	
	/**
	 * Appends new elements in an existing sheet from the last row
	 * @param file - existing fine name without .xls extention
	 * @param sheetName - sheet name to which the new data should be appended from the last row
	 * @param strings - a 2 d array of the data to be appended to the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void appendInSheet(String file, String sheetName, String[][] strings) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
//		wb.setActiveSheet(0);
//		wb.setActiveSheet(wb.getSheetIndex(sheetName));
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file+".xls"));
		HSSFSheet sheet = wb.getSheet(sheetName);
		//LOGGER.info("[INFO]Appending on Sheet "+sheet.getSheetName());
		
		int r=sheet.getPhysicalNumberOfRows(),c=0;
		for (String[] strings2 : strings) {
//			LOGGER.info("row = "+r);
			HSSFRow row = sheet.createRow(r++);
//			LOGGER.info(strings2);
			
			c = 0;
			if(strings2 != null){
				for (String string : strings2) {
//					LOGGER.info("col = "+c);
					HSSFCell cell = row.createCell(c++);
					cell.setCellValue(string);
				}
//			LOGGER.info("row added");
			}
		}

		if(excelFileName!=null) {
			FileOutputStream fileOut = new FileOutputStream(excelFileName);
			
			//write this workbook to an Outputstream.
			if(wb!=null)
				wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}
		if(wb!=null)
			wb.close();
		//LOGGER.info("Excel File Updated "+excelFileName);
	}
	   public static void convertHtmlToXml(File htmlFile, File xmlOutputFile) {
		   // Check if file exists and has read permissions
		   try {
	        if (htmlFile.exists() && htmlFile.canRead()) {
	        // Parse the HTML file using JSoup
	        org.jsoup.nodes.Document htmlDoc = Jsoup.parse(htmlFile, "UTF-8");
	        
	        // Create a new XML document
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document xmlDoc = builder.newDocument();

	        // Create the root element for XML
	        Element rootElement = xmlDoc.createElement("htmlContent");
	        xmlDoc.appendChild(rootElement);

	        // Extract all elements from the HTML document
	        Elements allElements = htmlDoc.getAllElements();
	        
	        // Iterate through each element and convert it to XML
	        for (org.jsoup.nodes.Element element : allElements) {
	            // Create a new XML element for each HTML element
	            Element xmlElement = xmlDoc.createElement(element.tagName());

	            // Add the element's attributes to the XML element
	            for (org.jsoup.nodes.Attribute attr : element.attributes()) {
	                xmlElement.setAttribute(attr.getKey(), attr.getValue());
	            }

	            // Add the element's text content as a child element
	            xmlElement.appendChild(xmlDoc.createTextNode(element.text()));

	            // Append the XML element to the root
	            rootElement.appendChild(xmlElement);
	        }
	        try {
	        // Write the XML content to the output file
	        writeXmlToFile(xmlDoc, xmlOutputFile);
	        System.out.println("Conversion complete!");
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        }
		   }
	      catch (Exception e) {
	            e.printStackTrace();
	    }
	        
	   }
	    private static void writeXmlToFile(Document xmlDoc, File outputFile) throws IOException, TransformerException {
	        // Save the XML document to a file
	        try (FileWriter writer = new FileWriter(outputFile)) {
	            // Create a TransformerFactory and Transformer to write the XML
	            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
	            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
	            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

	            // Write the document to the output file
	            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(xmlDoc);
	            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(writer);
	            transformer.transform(source, result);
	        }
	    }

	    
	 }