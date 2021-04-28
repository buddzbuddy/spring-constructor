package com.webdatabase.dgz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webdatabase.dgz.model.Debt;
import com.webdatabase.dgz.model.License;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.query.utils.SearchQuery;
import com.webdatabase.dgz.query.utils.SpecificationUtil;
import com.webdatabase.dgz.repository.SupplierRepository;
import com.webdatabase.dgz.service.QueryBuilderService;

@RestController
@RequestMapping("/data-api/supplier-requests")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplierDetailsController {
	@Autowired
	private SupplierRepository supplierRepo;
    @Autowired
    private QueryBuilderService queryApi;
    
    @PostMapping(path = "/exec",
    		consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryResult> exec(@RequestBody CustomQueryModel customQuery
    		)
    {
    	List<Supplier> list = queryApi.exec(Supplier.class, customQuery.getSearchQuery());
		
    	if(customQuery.getSpec1() != null) {//наличие лицензии
    		spec1 s1 = customQuery.getSpec1();
    		int licenseTypeId = s1.getLicenseTypeId();
    		
    		List<Supplier> newList = new ArrayList<Supplier>();
    		
    		for(Supplier supplier : list) {
    			for(License l : supplier.getLicenses()) {
    				if(l.getLicenseTypeId() == licenseTypeId) {
    					newList.add(supplier);
    					break;
    				}
    			}
    		}
    		list = newList;
    	}
    	if(customQuery.getSpec2() != null) {//просроченные лицензии
    		spec2 s2 = customQuery.getSpec2();
    		Date fd = SpecificationUtil.castToDate(s2.getDateFrom());
			Date ld = SpecificationUtil.castToDate(s2.getDateTo());
			List<Supplier> newList = new ArrayList<>();
			for(Supplier supplier : list) {
    			for(License l : supplier.getLicenses()) {
    				if(l.getExpiryDate().before(ld)
    					&&
    					l.getExpiryDate().after(fd)) {
    					newList.add(supplier);
    					break;
    				}
    			}
    		}
    		list = newList;
    	}
    	

    	if(customQuery.getSpec3() != null) {//наличие задолженности за период
    		spec2 specObj = customQuery.getSpec3();
    		Date fd = SpecificationUtil.castToDate(specObj.getDateFrom());
			Date ld = SpecificationUtil.castToDate(specObj.getDateTo());
			List<Supplier> newList = new ArrayList<>();
			for(Supplier supplier : list) {
    			for(Debt l : supplier.getDebts()) {
    				if(l.getCreatedAt().before(ld)
    					&&
    					l.getCreatedAt().after(fd)
    					&& l.isHas()) {
    					newList.add(supplier);
    					break;
    				}
    			}
    		}
    		list = newList;
    	}
    	

    	if(customQuery.getSpec4() != null) {//наличие задолженности за период
    		spec2 specObj = customQuery.getSpec4();
    		Date fd = SpecificationUtil.castToDate(specObj.getDateFrom());
			Date ld = SpecificationUtil.castToDate(specObj.getDateTo());
			List<Supplier> newList = new ArrayList<>();
			/*for(Supplier supplier : list) {
    			for(License l : supplier.getLicenses()) {
    				if(l.getExpiryDate().before(ld)
    					&&
    					l.getExpiryDate().after(fd)) {
    					newList.add(supplier);
    					break;
    				}
    			}
    		}*/
    		list = newList;
    	}
    	if(customQuery.getSpec5() != null) {//наличие задолженности за период
    		spec2 specObj = customQuery.getSpec5();
    		Date fd = SpecificationUtil.castToDate(specObj.getDateFrom());
			Date ld = SpecificationUtil.castToDate(specObj.getDateTo());
			List<Supplier> newList = new ArrayList<>();
			/*for(Supplier supplier : list) {
    			for(License l : supplier.getLicenses()) {
    				if(l.getExpiryDate().before(ld)
    					&&
    					l.getExpiryDate().after(fd)) {
    					newList.add(supplier);
    					break;
    				}
    			}
    		}*/
    		list = newList;
    	}
    	
    	QueryResult res = new QueryResult(true, "", list.toArray(new Supplier[0]));
    	return new ResponseEntity<QueryResult>(res, HttpStatus.OK);
    }
    
	@GetMapping(path = "/getDetails/{id}")
    public ResponseEntity<?> get(@PathVariable long id)
    {
		Supplier s = supplierRepo.findById(id).get();
		System.out.println(s.getOwnershipType().getName());
    	return new ResponseEntity<>(s, HttpStatus.OK);
    }
	
	@GetMapping(path = "/initMsecData/{id}")
    public ResponseEntity<?> initMsecData(@PathVariable long id)
    {
    	return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
class CustomQueryModel{
	private SearchQuery searchQuery;
	
	private spec1 spec1;
	private spec2 spec2;
	private spec2 spec3;
	private spec2 spec4;
	private spec2 spec5;

	public SearchQuery getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(SearchQuery searchQuery) {
		this.searchQuery = searchQuery;
	}

	public spec1 getSpec1() {
		return spec1;
	}

	public void setSpec1(spec1 spec1) {
		this.spec1 = spec1;
	}

	public spec2 getSpec2() {
		return spec2;
	}

	public void setSpec2(spec2 spec2) {
		this.spec2 = spec2;
	}

	public spec2 getSpec3() {
		return spec3;
	}

	public void setSpec3(spec2 spec3) {
		this.spec3 = spec3;
	}

	public spec2 getSpec4() {
		return spec4;
	}

	public void setSpec4(spec2 spec4) {
		this.spec4 = spec4;
	}

	public spec2 getSpec5() {
		return spec5;
	}

	public void setSpec5(spec2 spec5) {
		this.spec5 = spec5;
	}
	
	
}
class spec1 { //наличие лицензии
	private int licenseTypeId;

	public int getLicenseTypeId() {
		return licenseTypeId;
	}

	public void setLicenseTypeId(int licenseTypeId) {
		this.licenseTypeId = licenseTypeId;
	}
}
class spec2 {//наличие просроченных лицензий
	private String dateFrom;
	private String dateTo;
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
}