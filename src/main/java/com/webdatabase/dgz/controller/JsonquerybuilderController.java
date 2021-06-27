package com.webdatabase.dgz.controller;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;

import com.webdatabase.dgz.model.GrantedSource;
import com.webdatabase.dgz.model.LocalGrantedSource;
import com.webdatabase.dgz.model.SourceType;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.query.utils.*;
import com.webdatabase.dgz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.service.QueryBuilderService;

@RestController
@RequestMapping("/data-api/query")
public class JsonquerybuilderController {

	//@Autowired
	//private AppConfig appConfig;

	@Autowired
	private SupplierRepository supplierRepo;
	@Autowired
	private LicenseTypeRepository licenseTypeRepo;
	@Autowired
	private LicenseRepository licenseRepo;
	@Autowired
	private DebtRepository debtRepo;
	@Autowired
	private OwnershipTypeRepository ownershipTypeRepo;
	@Autowired
	private IndustryRepository industryRepo;

	@Autowired
	private GrantedSourceRepository grantedSourceRepo;

	@Autowired
	private LocalGrantedSourceRepository localGrantedSourceRepo;

    @Autowired
    private QueryBuilderService queryApi;
    
    
    @PostMapping(path = "/exec",
    		consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryResult> exec(@RequestBody SearchQuery searchQuery)
    {
    	try {
    		Class<?> birdClass = Class.forName("com.webdatabase.dgz.model." + searchQuery.getRootName());
    		
    		List<?> list = queryApi.exec(birdClass, searchQuery);
    		
        	QueryResult res = new QueryResult(true, "", list.toArray(new Object[0]));
	    	return new ResponseEntity<QueryResult>(res, HttpStatus.OK);
		} catch (ClassNotFoundException e) {
	    	QueryResult res = new QueryResult(false, "Класс не найден", null);
			e.printStackTrace();
			return new ResponseEntity<QueryResult>(res, HttpStatus.BAD_REQUEST);
		}
    }
    
    @PostMapping(path = "/insert",
    		consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> insert(@RequestBody InsertEntityModel insert)
    {
    	try {
    		Class<?> birdClass = Class.forName("com.webdatabase.dgz.model." + insert.getEntityName());
    		 
			try {
				Constructor<?> constructor = birdClass.getConstructor();
	    	    Object t = constructor.newInstance();
	    		queryApi.addEntry(insert, t.getClass());
			} catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			QueryResult res = new QueryResult(true, "", null);
	    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (ClassNotFoundException e) {
	    	QueryResult res = new QueryResult(false, "Класс не найден", null);
			e.printStackTrace();
			return new ResponseEntity<Boolean>(true, HttpStatus.BAD_REQUEST);
		}
    }

    @PostMapping(path = "/update/{id}",
    		consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> insert(@PathVariable long id, @RequestBody InsertEntityModel insert)
    {
    	try {
    		Class<?> birdClass = Class.forName("com.webdatabase.dgz.model." + insert.getEntityName());
    		 
			try {
				Constructor<?> constructor = birdClass.getConstructor();
	    	    Object t = constructor.newInstance();
	    		queryApi.updateEntry(id, insert, t.getClass());
			} catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			QueryResult res = new QueryResult(true, "", null);
	    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (ClassNotFoundException e) {
	    	QueryResult res = new QueryResult(false, "Класс не найден", null);
			e.printStackTrace();
			return new ResponseEntity<Boolean>(true, HttpStatus.BAD_REQUEST);
		}
    }

    @GetMapping(path = "/delete/{deleteName}/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String deleteName, @PathVariable long id)
    {
    	queryApi.deleteEntry(deleteName, id);
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    @GetMapping(path = "/getMeta")
    public ResponseEntity<QueryResult> getMeta()
    {
    	QueryResult res = new QueryResult(true, "", queryApi.getMetaDescription());
    	return new ResponseEntity<QueryResult>(res, HttpStatus.OK);
    }
    
    @GetMapping(path = "/getMeta/{className}")
    public ResponseEntity<MyClassDesc> getMeta(@PathVariable String className)
    {
    	return new ResponseEntity<MyClassDesc>(queryApi.getClassDescription(className), HttpStatus.OK);
    }
    
    @PostMapping("/{className}/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable String className,
    		@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelUpload.hasExcelFormat(file)) {
          try {
        	  Class<?> clazz = queryApi.findClassByName(className);
        	  List<InsertEntityModel> m = ExcelUpload.excelToList(file.getInputStream(), className, clazz);
        	  for(InsertEntityModel mItem : m) {
        		  queryApi.addEntry(mItem, clazz);
        	  }
            message = "Файл успешно загружен: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
          } catch (Exception e) {
            message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
          }
        }

        message = "Загрузите файл в формате Excel!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


	@PostMapping("/upload/license")
	public ResponseEntity<ResponseMessage> uploadLicense(@RequestParam("file") MultipartFile file) {
		String message = "";
		ExcelUpload excelUpload = new ExcelUpload(supplierRepo, licenseTypeRepo, licenseRepo, debtRepo, ownershipTypeRepo, industryRepo);
		if (excelUpload.hasExcelFormat(file)) {
			try {
				ExcelUploadResultMessage resultMessage = excelUpload.excelLicenseToList(file.getInputStream());
				if(resultMessage.isResult()) {
					message = "Файл успешно загружен: " + file.getOriginalFilename();
				}
				else {
					message = resultMessage.getErrorMessage();
				}
				return ResponseEntity.ok(new ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Загрузите файл в формате Excel!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@Value("${spring.app-settings.license-template-path}")
	private String LICENSE_TMPL_PATH;
	@RequestMapping(path = "/download/license", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadLicense() throws IOException {
		System.out.println(LICENSE_TMPL_PATH);
		File file = new File(LICENSE_TMPL_PATH);
		HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=license-template.xlsx");
		Path path = Paths.get(LICENSE_TMPL_PATH);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}



	@Value("${spring.app-settings.debt-template-path}")
	private String DEBT_TMPL_PATH;
	@RequestMapping(path = "/download/debt", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadDebt() throws IOException {
		System.out.println(DEBT_TMPL_PATH);
		File file = new File(DEBT_TMPL_PATH);
		HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=debt-template.xlsx");
		Path path = Paths.get(DEBT_TMPL_PATH);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}
	@PostMapping("/upload/debt")
	public ResponseEntity<ResponseMessage> uploadDebt(@RequestParam("file") MultipartFile file) {
		String message = "";
		ExcelUpload excelUpload = new ExcelUpload(supplierRepo, licenseTypeRepo, licenseRepo, debtRepo, ownershipTypeRepo, industryRepo);
		if (excelUpload.hasExcelFormat(file)) {
			try {
				ExcelUploadResultMessage resultMessage = excelUpload.uploadDebts(file.getInputStream());
				if(resultMessage.isResult()) {
					message = "Файл успешно загружен: " + file.getOriginalFilename();
				}
				else {
					message = resultMessage.getErrorMessage();
				}
				return ResponseEntity.ok(new ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Загрузите файл в формате Excel!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@Value("${spring.app-settings.supplier-one-template-path}")
	private String SUPPLIER_ONE_TMPL_PATH;
	@RequestMapping(path = "/download/supplierone", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadSupplierOne() throws IOException {
		System.out.println(SUPPLIER_ONE_TMPL_PATH);
		File file = new File(SUPPLIER_ONE_TMPL_PATH);
		HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=supplier-one-template.xlsx");
		Path path = Paths.get(SUPPLIER_ONE_TMPL_PATH);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}
	@PostMapping("/upload/supplierone")
	public ResponseEntity<ResponseMessage> uploadSupplierOne(@RequestParam("file") MultipartFile file) {
		String message = "";
		ExcelUpload excelUpload = new ExcelUpload(supplierRepo, licenseTypeRepo, licenseRepo, debtRepo, ownershipTypeRepo, industryRepo);
		if (excelUpload.hasExcelFormat(file)) {
			try {
				ExcelUploadResultMessage resultMessage = excelUpload.uploadSupplierOnes(file.getInputStream());
				if(resultMessage.isResult()) {
					message = "Файл успешно загружен: " + file.getOriginalFilename();
				}
				else {
					message = resultMessage.getErrorMessage();
				}
				return ResponseEntity.ok(new ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Загрузите файл в формате Excel!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}



	@PostMapping(path = "/grant-source",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> grantSource(@RequestBody GrantedSource grantedSource) {

		if(grantedSource.getSourceType() != null) {
			grantedSourceRepo.save(grantedSource);
			return ResponseEntity.ok(new ResponseMessage("Настройки обновлены!"));
		}
		return ResponseEntity.ok(new ResponseMessage("Что-то пошло не так!"));
	}
	@PostMapping(path = "/deny-source",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> denySource(@RequestBody GrantedSource grantedSource) {

		if(grantedSource.getSourceType() != null) {
			for (GrantedSource source : grantedSourceRepo.findAll()) {
				if(source.getSourceType().equals(grantedSource.getSourceType())) {
					grantedSourceRepo.delete(source);
				}
			}
			return ResponseEntity.ok(new ResponseMessage("Настройки обновлены!"));
		}
		return ResponseEntity.ok(new ResponseMessage("Что-то пошло не так!"));
	}

	@GetMapping(path = "/privacy-source-types")
	public ResponseEntity<SourceType[]> getPrivacyTypes(){
		List<SourceType> sourceTypes = new ArrayList<>();
		sourceTypes.add(SourceType.LICENSE);
		sourceTypes.add(SourceType.DEBT);
		sourceTypes.add(SourceType.COMPLAINT);
		sourceTypes.add(SourceType.BUYER_SUPPLIER_REGISTRY);
		return new ResponseEntity<SourceType[]>(sourceTypes.toArray(new SourceType[0]), HttpStatus.OK);
	}

	@GetMapping(path = "/get-granted-sources/")
	public ResponseEntity<LocalGrantedSource[]> getLocalGrantedByInnAll(){
		return new ResponseEntity<>(localGrantedSourceRepo.findAll().toArray(new LocalGrantedSource[0]), HttpStatus.OK);
	}

	@GetMapping(path = "/get-granted-sources/{supplierInn}")
	public ResponseEntity<SourceType[]> getLocalGrantedByInn(@PathVariable String supplierInn){
		List<SourceType> sourceTypes = new ArrayList<>();
		Supplier s = getSupplierByInn(supplierInn);
		if(s != null) {
			List<LocalGrantedSource> localGrantedSources = localGrantedSourceRepo.findBySupplierId(s.getId());
			for (LocalGrantedSource l : localGrantedSources){
				sourceTypes.add(l.getSourceType());
			}
		}
		return new ResponseEntity<>(sourceTypes.toArray(new SourceType[0]), HttpStatus.OK);
	}

	@PostMapping(path = "/grant-source-local",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> grantSourceLocal(@RequestBody LocalGrantedSourceViewModel grantedSource) {

		if(grantedSource.getSourceType() != null) {
			Supplier s = getSupplierByInn(grantedSource.getSupplierInn());
			if(s != null) {
				LocalGrantedSource obj = new LocalGrantedSource();
				obj.setSourceType(grantedSource.getSourceType());
				obj.setSupplierId(s.getId());
				localGrantedSourceRepo.save(obj);
				return ResponseEntity.ok(new ResponseMessage("Настройки обновлены!"));
			}
			return ResponseEntity.ok(new ResponseMessage("Поставщик с ИНН \""+grantedSource.getSupplierInn()+"\" не найден в базе поставщиков!"));
		}
		return ResponseEntity.ok(new ResponseMessage("Тип источника пуст!"));
	}
	@PostMapping(path = "/deny-source-local",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> denySourceLocal(@RequestBody LocalGrantedSourceViewModel grantedSource) {

		if(grantedSource.getSourceType() != null) {
			Supplier s = getSupplierByInn(grantedSource.getSupplierInn());
			if(s == null) {
				return ResponseEntity.ok(new ResponseMessage("Поставщик с ИНН \""+grantedSource.getSupplierInn()+"\" не найден в базе поставщиков!"));
			}
			for (LocalGrantedSource source : localGrantedSourceRepo.findBySupplierIdAndSourceType(s.getId(), grantedSource.getSourceType())) {
				localGrantedSourceRepo.delete(source);
			}
			return ResponseEntity.ok(new ResponseMessage("Настройки обновлены!"));
		}
		return ResponseEntity.ok(new ResponseMessage("Тип источника пуст!"));
	}
	private Supplier getSupplierByInn(String inn) {
		Optional<Supplier> obj = supplierRepo.findByInn(inn);
		return obj.orElse(null);
	}

	

	@PostMapping(path = "/exec-sql",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object[]> getSQL(@RequestBody JsonSqlScript model){
		return new ResponseEntity<Object[]>(queryApi.executeSQL(model.getSqlScript()).toArray(), HttpStatus.OK);
	}

}

class JsonSqlScript {
	private String sqlScript;

	public String getSqlScript() {
		return sqlScript;
	}

	public void setSqlScript(String sqlScript) {
		this.sqlScript = sqlScript;
	}
}

class LocalGrantedSourceViewModel {
	private SourceType sourceType;
	private String supplierInn;

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public String getSupplierInn() {
		return supplierInn;
	}

	public void setSupplierInn(String supplierInn) {
		this.supplierInn = supplierInn;
	}
}

class QueryCondition {
	private String table;
	
private SearchCriteria[] params;

public SearchCriteria[] getParams() {
	return params;
}

public void setParams(SearchCriteria[] params) {
	this.params = params;
}

public String getTable() {
	return table;
}

public void setTable(String table) {
	this.table = table;
}
}
class QueryResult {
	private Boolean result;
	private String error;
	private Object[] data;
	
	public QueryResult() {}
	
	public QueryResult(Boolean result, String error, Object[] data) {
		this.result = result;
		this.error = error;
		this.data = data;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] result) {
		this.data = result;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}