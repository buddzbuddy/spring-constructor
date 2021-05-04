package com.webdatabase.dgz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.print.attribute.standard.Fidelity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webdatabase.dgz.model.*;
import com.webdatabase.dgz.repository.Msec_detailRepository;
import com.webdatabase.dgz.repository.SupplierMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;
import org.json.JSONException;
import org.json.JSONObject;

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
	private Msec_detailRepository msec_detailRepository;

    @Autowired
    private QueryBuilderService queryApi;
	private Object object;
    
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
					assert l.getExpiryDate() != null;
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
    	

    	if(customQuery.getSpec4() != null) {//наличие уголовных дел
    		spec2 specObj = customQuery.getSpec4();
    		Date fd = SpecificationUtil.castToDate(specObj.getDateFrom());
			Date ld = SpecificationUtil.castToDate(specObj.getDateTo());
			List<Supplier> newList = new ArrayList<>();
			for(Supplier supplier : list) {
    			for(CriminalCase c : supplier.getCriminalCases()) {
    				if(c.getCreatedAt().before(ld)
    					&&
    					c.getCreatedAt().after(fd)) {
    					newList.add(supplier);
    					break;
    				}
    			}
    		}
    		list = newList;
    	}
    	if(customQuery.getSpec5() != null) {//наличие судебных тяжб
    		spec2 specObj = customQuery.getSpec5();
    		Date fd = SpecificationUtil.castToDate(specObj.getDateFrom());
			Date ld = SpecificationUtil.castToDate(specObj.getDateTo());
			List<Supplier> newList = new ArrayList<>();
			for(Supplier supplier : list) {
    			for(Litigation l : supplier.getLitigations()) {
    				if(l.getCreatedAt().before(ld)
    					&&
    					l.getCreatedAt().after(fd)) {
    					newList.add(supplier);
    					break;
    				}
    			}
    		}
    		list = newList;
    	}
    	
    	if(customQuery.getSpec6() != null) {//наличие жалобы
    		spec2 specObj = customQuery.getSpec6();
    		Date fd = specObj.getDateFrom() != null ? SpecificationUtil.castToDate(specObj.getDateFrom()) : null;
			Date ld = specObj.getDateTo() != null ? SpecificationUtil.castToDate(specObj.getDateTo()) : null;
			List<Supplier> newList = new ArrayList<>();
			for(Supplier supplier : list) {
				if(fd != null && ld != null)
    			for(Appeal a : supplier.getAppeals()) {
    				if(a.getCreatedAt().before(ld)
    					&&
    					a.getCreatedAt().after(fd)) {
    					newList.add(supplier);
    					break;
    				}
    			}
				else {
					if (supplier.getAppeals() != null && supplier.getAppeals().size() > 0) {
						newList.add(supplier);
					}
				}
    		}
    		list = newList;
    	}
    	
    	QueryResult res = new QueryResult(true, "", list.toArray(new Supplier[0]));
    	return new ResponseEntity<QueryResult>(res, HttpStatus.OK);
    }
    
	@GetMapping(path = "/getDetails/{id}")
    public ResponseEntity<?> get(@PathVariable long id)
    {
		Optional<Supplier> sop = supplierRepo.findById(id);
		assert sop.isPresent();
		Supplier s = sop.get();
		System.out.println(s.getOwnershipType().getName());
    	return new ResponseEntity<>(s, HttpStatus.OK);
    }

	
	@GetMapping(path = "/initMsecData/{id}")
    public ResponseEntity<?> initMsecData(@PathVariable long id)
    {
		Optional<Supplier> sop = supplierRepo.findById(id);
		assert sop.isPresent();
		Supplier supplier = sop.get();

		int successCount = 0;
		for(SupplierMember supplierMember : supplier.getSupplierMembers()) {
			JSONObject reqMsecData = requestMsecData(supplierMember.getPin());
			if(reqMsecData.getString("StatusCode").equals("SUCCESS")){
				//TODO: Save to DB
				MsecDetail msecDetail = new MsecDetail();
				msecDetail.setDisabilityGroup(reqMsecData.getString("DisabilityGroup"));
				msecDetail.setExaminationDate(parseDate(reqMsecData.getString("ExaminationDate")));
				msecDetail.setExaminationType(reqMsecData.getString("ExaminationType"));
				msecDetail.setFromDate(parseDate(reqMsecData.getString("From")));
				msecDetail.setToDate(parseDate(reqMsecData.getString("To")));
				msecDetail.setOrganizationName(reqMsecData.getString("OrganizationName"));
				msecDetail.setReExamination(reqMsecData.getString("ReExamination"));
				msecDetail.setSupplierMemberId(supplierMember.getId());
				msec_detailRepository.save(msecDetail);
				successCount++;
			}
		}

		return new ResponseEntity<>("Данные МСЭК успешно обновлены у " + successCount + " участников", HttpStatus.OK);
    }
	
	@GetMapping(path = "/initMsecDataAll")
	public ResponseEntity<?> initMsecDataAll(Pageable pageable) {
		List<Supplier> listSuppliers = supplierRepo.findAll();
		assert !listSuppliers.isEmpty();
		int successCount = 0;
		for (Supplier supplier : listSuppliers){
			for(SupplierMember supplierMember : supplier.getSupplierMembers()) {
				JSONObject reqMsecData = requestMsecData(supplierMember.getPin());
				if(reqMsecData.getString("StatusCode").equals("SUCCESS")) {
					MsecDetail msecDetail = new MsecDetail();
					msecDetail.setDisabilityGroup(reqMsecData.getString("DisabilityGroup"));
					msecDetail.setExaminationDate(parseDate(reqMsecData.getString("ExaminationDate")));
					msecDetail.setExaminationType(reqMsecData.getString("ExaminationType"));
					msecDetail.setFromDate(parseDate(reqMsecData.getString("From")));
					msecDetail.setToDate(parseDate(reqMsecData.getString("To")));
					msecDetail.setOrganizationName(reqMsecData.getString("OrganizationName"));
					msecDetail.setReExamination(reqMsecData.getString("ReExamination"));
					msecDetail.setSupplierMemberId(supplierMember.getId());
					msec_detailRepository.save(msecDetail);
					successCount++;
				}
			}
		}
		
		return new ResponseEntity<>("Данные МСЭК успешно обновлены у " + successCount + " участников", HttpStatus.OK);
	}
	
	public static Date parseDate(final String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    private JSONObject requestMsecData(String pin) {

		//setting up the request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();

		JSONObject msecReq = new JSONObject();
		msecReq.put("clientId", "ebb7570a-5357-4afa-8430-19843b34dfd7");

		JSONObject item_1 = new JSONObject();
		JSONObject item_1_1 = new JSONObject();
		JSONObject item_1_1_1 = new JSONObject();
		item_1_1_1.put("PIN", pin);
		item_1_1.put("request", item_1_1_1);
		item_1.put("MSECDetails", item_1_1);
		msecReq.put("request", item_1);

		HttpEntity<String> request = new HttpEntity<String>(msecReq.toString(), headers);
		String msecResultStr = restTemplate.postForObject("http://195.38.189.101:8088/ServiceConstructor/SoapClient/SendRequest2", request, String.class);
		JSONObject res = new JSONObject(msecResultStr);
		JSONObject res_item_1 = res.getJSONObject("response");
		JSONObject res_item_1_1 = res_item_1.getJSONObject("MSECDetailsResponse");
		JSONObject res_item_1_1_2 = res_item_1_1.getJSONObject("response");
		return res_item_1_1_2;
	}
}
class CustomQueryModel{
	private SearchQuery searchQuery;
	
	private spec1 spec1;
	private spec2 spec2;
	private spec2 spec3;
	private spec2 spec4;
	private spec2 spec5;
	private spec2 spec6;

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

	public spec2 getSpec6() {
		return spec6;
	}

	public void setSpec6(spec2 spec6) {
		this.spec6 = spec6;
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