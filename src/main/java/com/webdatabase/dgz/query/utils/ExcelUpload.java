package com.webdatabase.dgz.query.utils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.webdatabase.dgz.model.*;
import com.webdatabase.dgz.repository.*;
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


	  private final SupplierRepository _supplierRepo;
	private final LicenseTypeRepository _licenseTypeRepo;
	private final OwnershipTypeRepository _ownershipTypeRepo;
	private final IndustryRepository _industryRepo;
	private final LicenseRepository _licenseRepo;
	private final DebtRepository _debtRepo;

	public ExcelUpload(SupplierRepository supplierRepository, LicenseTypeRepository licenseTypeRepository, LicenseRepository licenseRepository, DebtRepository debtRepository,
					   OwnershipTypeRepository ownershipTypeRepository, IndustryRepository industryRepository) {
		_supplierRepo = supplierRepository;
		_ownershipTypeRepo = ownershipTypeRepository;
		_industryRepo = industryRepository;
		_licenseTypeRepo = licenseTypeRepository;
		_licenseRepo = licenseRepository;
		_debtRepo = debtRepository;
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
	public ExcelUploadResultMessage excelLicenseToList(InputStream iStream) {
		ExcelUploadResultMessage response = new ExcelUploadResultMessage();
		List<License> licenses = new ArrayList<>();
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			Sheet sheet = workbook.getSheetAt(0);
			if(!isLicenseFile(sheet)) {
				response.setResult(false);
				response.setErrorMessage("Файл не идентифицирован! Вы используете сторонний файл!");
				return response;
			}

			List<License> licenseList = new ArrayList<>();
			for (int i = 1; i < 1001; i++) {
				Row r = sheet.getRow(i);
				try {
					String issuer = r.getCell(0).getStringCellValue();
					String no = r.getCell(1).getStringCellValue().replace("'", "");
					String issueDateStr = r.getCell(2).getStringCellValue();
					String supplierInn = r.getCell(3).getStringCellValue().replace("'", "");
					String licenseTypeName = r.getCell(4).getStringCellValue();
					String expiryDateStr = r.getCell(5).getStringCellValue();
					String additionalInfo = r.getCell(6).getStringCellValue();
					if(issuer.isEmpty() && no.isEmpty() && issueDateStr.isEmpty() &&
							supplierInn.isEmpty() && licenseTypeName.isEmpty() &&
							expiryDateStr.isEmpty() && additionalInfo.isEmpty()) {
						break;
					}
					if(issuer.isEmpty() || no.isEmpty() || issueDateStr.isEmpty() ||
							supplierInn.isEmpty() || licenseTypeName.isEmpty() ||
							expiryDateStr.isEmpty() || additionalInfo.isEmpty()) {
						response.setResult(false);
						response.setErrorMessage("Один из полей строки № " + (i+1) + " пуст!");
						return response;
					}
					Date issueDate = parseDate(issueDateStr);
					if(issueDate == null) {
						response.setResult(false);
						response.setErrorMessage("Значение поля \"Дата выдачи\" некорректный - строка № " + (i+1));
						return response;
					}
					Date expiryDate = parseDate(expiryDateStr);
					if(expiryDate == null) {
						response.setResult(false);
						response.setErrorMessage("Значение поля \"Срок окончания\" некорректный - строка № " + (i+1));
						return response;
					}
					Supplier supplier = getSupplierByInn(supplierInn);
					if(supplier == null) {
						response.setResult(false);
						response.setErrorMessage("Поставщик с ИНН \""+ supplierInn +"\" не найден в базе - строка № " + (i+1));
						return response;
					}

					LicenseType licenseType = getLicenseTypeByName(licenseTypeName);
					License license = new License();
					license.setLicenseTypeId(licenseType.getId());
					license.setSupplierId(supplier.getId());
					license.setIssuer(issuer);
					license.setNo(no);
					license.setIssueDate(issueDate);
					license.setExpiryDate(expiryDate);
					license.setAdditionalInfo(additionalInfo);

					licenseList.add(license);
				} catch (IllegalStateException e) {
					if(e.getMessage().equals("Cannot get a STRING value from a NUMERIC cell")){
						response.setResult(false);
						response.setErrorMessage("Одно из значений поля строки № " + (i+1) + " предоставлено как число. Просьба поменять на текстовый формат (или можно перед числом вставить символ ' - одинарная ковычка)");
						return response;
					}
				}
			}
			workbook.close();
			_licenseRepo.saveAll(licenseList);
			response.setResult(true);
			return response;

		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	private Supplier getSupplierByInn(String inn) {
		Optional<Supplier> obj = _supplierRepo.findByInn(inn);
		return obj.orElse(null);
	}
	private LicenseType getLicenseTypeByName(String name) {
		Optional<LicenseType> obj = _licenseTypeRepo.findByName(name);
		if(obj.isPresent()) {
			return obj.get();
		}
		else {
			LicenseType newObj = new LicenseType();
			newObj.setName(name);
			_licenseTypeRepo.save(newObj);
			return newObj;
		}
	}
	private OwnershipType getOwnershipTypeByName(String name) {
		Optional<OwnershipType> obj = _ownershipTypeRepo.findByName(name);
		if(obj.isPresent()) {
			return obj.get();
		}
		else {
			OwnershipType newObj = new OwnershipType();
			newObj.setName(name);
			_ownershipTypeRepo.save(newObj);
			return newObj;
		}
	}
	private Industry getIndustryByName(String name) {
		Optional<Industry> obj = _industryRepo.findByName(name);
		if(obj.isPresent()) {
			return obj.get();
		}
		else {
			Industry newObj = new Industry();
			newObj.setName(name);
			_industryRepo.save(newObj);
			return newObj;
		}
	}
	public static Date parseDate(final String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	private static final int debt_secret_row = 0;
	private static final int debt_secret_col = 2;
	private static final String debt_secret_word = "debt_s3cret";
	private static boolean isDebtFile(Sheet sheet){
		try {
			Row r = sheet.getRow(debt_secret_row);
			Cell c = r.getCell(debt_secret_col);
			System.out.println(c.getStringCellValue());
			return c.getStringCellValue().equals(debt_secret_word);
		} catch (IllegalStateException e) {
			return false;
		}
	}
	public ExcelUploadResultMessage uploadDebts(InputStream iStream) {
		ExcelUploadResultMessage response = new ExcelUploadResultMessage();
		List<Debt> debts = new ArrayList<>();
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			Sheet sheet = workbook.getSheetAt(0);
			if(!isDebtFile(sheet)) {
				response.setResult(false);
				response.setErrorMessage("Файл не идентифицирован! Вы используете сторонний файл!");
				return response;
			}

			List<Debt> debtList = new ArrayList<>();
			for (int i = 1; i < 1001; i++) {
				Row r = sheet.getRow(i);
				try {
					String supplierInn = r.getCell(0).getStringCellValue().replace("'", "");
					String hasStr = r.getCell(1).getStringCellValue();
					if(supplierInn.isEmpty()&& hasStr.isEmpty()) {
						break;
					}
					if(supplierInn.isEmpty() || hasStr.isEmpty()) {
						response.setResult(false);
						response.setErrorMessage("Один из полей строки № " + (i+1) + " пуст!");
						return response;
					}
					boolean has = false;
					if(!(hasStr.trim().equalsIgnoreCase("да") || hasStr.trim().equalsIgnoreCase("нет"))) {
						response.setResult(false);
						response.setErrorMessage("Значение наличия о задолженности не определен в строке № " + (i+1) + ". Допустимо только ДА или НЕТ");
						return response;
					}
					else {
						has = hasStr.trim().equalsIgnoreCase("да");
					}

					Supplier supplier = getSupplierByInn(supplierInn);
					if(supplier == null) {
						response.setResult(false);
						response.setErrorMessage("Поставщик с ИНН \""+ supplierInn +"\" не найден в базе - строка № " + (i+1));
						return response;
					}
					Debt debt = new Debt();
					debt.setSupplierId(supplier.getId());
					debt.setHas(has);

					debtList.add(debt);
				} catch (IllegalStateException e) {
					if(e.getMessage().equals("Cannot get a STRING value from a NUMERIC cell")){
						response.setResult(false);
						response.setErrorMessage("Одно из значений поля строки № " + (i+1) + " предоставлено как число. Просьба поменять на текстовый формат (или можно перед числом вставить символ ' - одинарная ковычка)");
						return response;
					}
				}
			}
			workbook.close();
			_debtRepo.saveAll(debtList);
			response.setResult(true);
			return response;

		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}


	private static final int supplier_one_secret_row = 0;
	private static final int supplier_one_secret_col = 9;
	private static final String supplier_one_secret_word = "supplier_one_s3cret";
	private static boolean isSupplierOneFile(Sheet sheet){
		try {
			Row r = sheet.getRow(supplier_one_secret_row);
			Cell c = r.getCell(supplier_one_secret_col);
			System.out.println(c.getStringCellValue());
			return c.getStringCellValue().equals(supplier_one_secret_word);
		} catch (IllegalStateException e) {
			return false;
		}
	}
	public ExcelUploadResultMessage uploadSupplierOnes(InputStream iStream) {
		ExcelUploadResultMessage response = new ExcelUploadResultMessage();
		List<Supplier> suppliers = new ArrayList<>();
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			Sheet sheet = workbook.getSheetAt(0);
			if(!isSupplierOneFile(sheet)) {
				response.setResult(false);
				response.setErrorMessage("Файл не идентифицирован! Вы используете сторонний файл!");
				return response;
			}

			List<Supplier> supplierList = new ArrayList<>();
			for (int i = 1; i < 1001; i++) {
				Row r = sheet.getRow(i);
				try {
					String supplierName = r.getCell(0).getStringCellValue();
					String supplierInn = r.getCell(1).getStringCellValue().replace("'", "");
					String ownershipTypeName = r.getCell(2).getStringCellValue();
					String industryName = r.getCell(3).getStringCellValue();
					String legalAddress = r.getCell(4).getStringCellValue();
					String factAddress = r.getCell(5).getStringCellValue();
					String isResidentStr = r.getCell(6).getStringCellValue();
					String isBlackStr = r.getCell(7).getStringCellValue();
					String telephone = r.getCell(8).getStringCellValue().replace("'", "");
					if(supplierInn.isEmpty()&& isResidentStr.isEmpty()) {
						break;
					}
					if(supplierName.isEmpty() || supplierInn.isEmpty() || telephone.isEmpty()
					|| ownershipTypeName.isEmpty() || industryName.isEmpty() || legalAddress.isEmpty()
					|| factAddress.isEmpty() || isResidentStr.isEmpty() || isBlackStr.isEmpty()) {
						response.setResult(false);
						response.setErrorMessage("Один из полей строки № " + (i+1) + " пуст!");
						return response;
					}
					boolean isResident = false;
					if(!(isResidentStr.trim().equalsIgnoreCase("да") || isResidentStr.trim().equalsIgnoreCase("нет"))) {
						response.setResult(false);
						response.setErrorMessage("Значение наличия о задолженности не определен в строке № " + (i+1) + ". Допустимо только ДА или НЕТ");
						return response;
					}
					else {
						isResident = isResidentStr.trim().equalsIgnoreCase("да");
					}
					boolean isBlack = false;
					if(!(isBlackStr.trim().equalsIgnoreCase("да") || isBlackStr.trim().equalsIgnoreCase("нет"))) {
						response.setResult(false);
						response.setErrorMessage("Значение наличия о задолженности не определен в строке № " + (i+1) + ". Допустимо только ДА или НЕТ");
						return response;
					}
					else {
						isBlack = isBlackStr.trim().equalsIgnoreCase("да");
					}

					Supplier supplier = getSupplierByInn(supplierInn);
					if(supplier != null) {
						response.setResult(false);
						response.setErrorMessage("Поставщик с ИНН \""+ supplierInn +"\" уже существует в базе - строка № " + (i+1));
						return response;
					}

					supplier = new Supplier();
					supplier.setName(supplierName);
					supplier.setInn(supplierInn);
					OwnershipType ownershipType = getOwnershipTypeByName(ownershipTypeName);
					Industry industry = getIndustryByName(industryName);
					supplier.setOwnershipTypeId(ownershipType.getId());
					supplier.setIndustryId(industry.getId());
					supplier.setLegalAddress(legalAddress);
					supplier.setFactAddress(factAddress);
					supplier.setTelephone(telephone);
					supplier.setIsBlack(isBlack);
					supplier.setIsResident(isResident);
					supplierList.add(supplier);
				} catch (IllegalStateException e) {
					if(e.getMessage().equals("Cannot get a STRING value from a NUMERIC cell")){
						response.setResult(false);
						response.setErrorMessage("Одно из значений поля строки № " + (i+1) + " предоставлено как число. Просьба поменять на текстовый формат (или можно перед числом вставить символ ' - одинарная ковычка)");
						return response;
					}
				}
			}
			workbook.close();
			_supplierRepo.saveAll(supplierList);
			response.setResult(true);
			return response;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
