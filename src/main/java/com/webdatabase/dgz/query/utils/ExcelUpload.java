package com.webdatabase.dgz.query.utils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.webdatabase.dgz.model.License;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUpload {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	public static boolean hasExcelFormat(MultipartFile file) {
		return TYPE.equals(file.getContentType());
	}

	public static List<InsertEntityModel> excelToList(InputStream iStream, String entityName, Class<?> clazz) {

	      List<InsertEntityModel> entries = new ArrayList<>();
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheetAt(0);

	      int i = 0;
	      ArrayList<String> fieldNames = new ArrayList<>();
	      for (Row row : sheet) {
	    	  if(i == 0) {//Header
	    		  for (Cell cell : row) {
	    			  String fieldName = cell.getStringCellValue();
	    			  fieldNames.add(fieldName.trim());
	    			  System.out.println("field: " + fieldName);
	    	    	  
		          }
	    		  System.out.println("fieldNames.size() : " + fieldNames.size());
	    	  }
	    	  else {
	    		  System.out.println("Start data parsing...");
	    		  InsertEntityModel entry = new InsertEntityModel();
		    	  entry.setEntityName(entityName);
		    	  List<InsertEntityFieldModel> fList = new ArrayList<>();
		    	  
		          for (int j = 0; j < fieldNames.size(); j++) {
		              String fieldName = fieldNames.get(j);
		              Object fieldVal = null;
		              for(Field f : clazz.getDeclaredFields()) {
	            		  f.setAccessible(true);
		            	  MetaFieldName mt = f.getAnnotation(MetaFieldName.class);
	            		  //System.out.println(mt.label());
		            	  if(mt != null && mt.label().toLowerCase().trim().equals(fieldName.toLowerCase().trim())) {
		            		  
		            		  //System.out.println(fieldName + " - " + f.getType().getName());
		            		  
		            		  String valStr = row.getCell(j).getStringCellValue().trim();
		            		  if(valStr.isEmpty()) continue;
		            		  System.out.println(fieldName + ": " + valStr);
		            		  if(f.getType().getName().equals(long.class.getName())) {
									fieldVal = Long.parseLong(valStr);
								}
								else if (f.getType().getName().equals(int.class.getName())) {
									fieldVal = Integer.parseInt(valStr);
								}
								else if (f.getType().getName().equals(float.class.getName())) {
									fieldVal = Float.parseFloat(valStr);
								}
								else if (f.getType().getName().equals(double.class.getName())) {
									fieldVal = Double.parseDouble(valStr);
								}
								else if (f.getType().getName().equals(boolean.class.getName())) {
									fieldVal = valStr.equals("true");
								}
								else if (f.getType().getName().equals(String.class.getName())) {
									fieldVal = valStr;
								}
								else if (f.getType().getName().equals(Date.class.getName())) {
									SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
									try {
										fieldVal = sdf.parse(valStr);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								else {
									System.out.println("Поле " + fieldName + " имеет неизвстный тип: " + f.getType().getName());
								}
								InsertEntityFieldModel fm = new InsertEntityFieldModel();
								fm.setName(f.getName());
								fm.setVal(fieldVal);
								fList.add(fm);
		            		  break;
		            	  }
		              }
		          }
		          entry.setFields(fList.toArray(new InsertEntityFieldModel[0]));
		          entries.add(entry);
	    	  }
	    	  
	          i++;
	      }
	      System.out.println("Entries parsed: " + entries.size());
    	  //System.out.println(entries);
	      
	      workbook.close();
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    } catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return entries;
	  }

	private static final int license_secret_row = 0;
	private static final int license_secret_col = 7;
	private static final String license_secret_word = "license_s3cret";
	private static boolean isLicenseFile(Sheet sheet){
		Row r = sheet.getRow(license_secret_row);
		Cell c = r.getCell(license_secret_col);
		System.out.println(c.getStringCellValue());
		return c.getStringCellValue().equals(license_secret_word);
	}
	public static List<License> excelLicenseToList(InputStream iStream) {

		List<License> licenses = new ArrayList<>();
		try {
			Workbook workbook = new XSSFWorkbook(iStream);

			Sheet sheet = workbook.getSheetAt(0);

			if(!isLicenseFile(sheet)) {
				return licenses;
			}

			for (int i = 1; i < 1001; i++) {
				Row r = sheet.getRow(i);


			}

			workbook.close();
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return licenses;
	}
}
