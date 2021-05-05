package com.webdatabase.dgz.controller;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.webdatabase.dgz.query.utils.*;
import com.webdatabase.dgz.repository.LicenseRepository;
import com.webdatabase.dgz.repository.LicenseTypeRepository;
import com.webdatabase.dgz.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JsonquerybuilderController {

	@Autowired
	private SupplierRepository supplierRepo;
	@Autowired
	private LicenseTypeRepository licenseTypeRepo;
	@Autowired
	private LicenseRepository licenseRepo;
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
		ExcelUpload excelUpload = new ExcelUpload(supplierRepo, licenseTypeRepo, licenseRepo);
		if (excelUpload.hasExcelFormat(file)) {
			try {
				ExcelUploadResultMessage resultMessage = excelUpload.excelLicenseToList(file.getInputStream());
				if(resultMessage.isResult()) {
					message = "Файл успешно загружен: " + file.getOriginalFilename();
				}
				else {
					message = resultMessage.getErrorMessage();
				}
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Загрузите файл в формате Excel!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
	private static final String LICENSE_TMPL_PATH = "F:\\license-template.xlsx";
	@RequestMapping(path = "/download/license", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(String param) throws IOException {
		File file = new File(LICENSE_TMPL_PATH);
		HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=шаблон-загрузки-лицензий.xlsx");
		Path path = Paths.get(LICENSE_TMPL_PATH);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
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