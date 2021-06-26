package com.webdatabase.dgz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.print.attribute.standard.Fidelity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webdatabase.dgz.model.*;
import com.webdatabase.dgz.repository.BuyerRepository;
import com.webdatabase.dgz.repository.ComplaintRepository;
import com.webdatabase.dgz.repository.LocalGrantedSourceRepository;
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
import com.webdatabase.dgz.service.GovServices;
import com.webdatabase.dgz.service.QueryBuilderService;
import com.webdatabase.dgz.util.SOAPClientSAAJ;

@RestController
@RequestMapping("/data-api/supplier-requests")
public class SupplierDetailsController {
	@Autowired
	private SupplierRepository supplierRepo;
	@Autowired
	private BuyerRepository buyerRepo;
	
	@Autowired
	private ComplaintRepository complaintRepo;
	
	@Autowired
	private SupplierMemberRepository supplierMemberRepo;

	@Autowired
	private Msec_detailRepository msec_detailRepository;
	
	@Autowired
	private GovServices _govServices;



	@Autowired
	private LocalGrantedSourceRepository localGrantedSourceRepo;

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



		List<LocalGrantedSource> localGrantedSourceList = localGrantedSourceRepo.findAll();
    	if(customQuery.getSpec1() != null) {//наличие лицензии
    		spec1 s1 = customQuery.getSpec1();
    		int licenseTypeId = s1.getLicenseTypeId();
    		
    		List<Supplier> newList = new ArrayList<Supplier>();
    		for(Supplier supplier : list) {
				if(hasLicenseConstraint(localGrantedSourceList, supplier)) continue;//Local grant deny rules
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
				if(hasLicenseConstraint(localGrantedSourceList, supplier)) continue;//Local grant deny rules
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
				if(hasDebtConstraint(localGrantedSourceList, supplier)) continue;//Local grant deny rules
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
				{
					List<Complaint> complaints = complaintRepo.findBySupplierId(supplier.getId());
					for(Complaint a : complaints) {
	    				if(a.getCreatedAt().before(ld)
	    					&&
	    					a.getCreatedAt().after(fd)) {
	    					newList.add(supplier);
	    					break;
	    				}
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

	private boolean hasLicenseConstraint(List<LocalGrantedSource> localGrantedSourceList, Supplier supplier) {
		for (LocalGrantedSource lgs : localGrantedSourceList) {
			if(lgs.getSupplierId() == supplier.getId()) {
				return lgs.getSourceType().equals(SourceType.LICENSE);
			}
		}
		return false;
	}
	private boolean hasDebtConstraint(List<LocalGrantedSource> localGrantedSourceList, Supplier supplier) {
		for (LocalGrantedSource lgs : localGrantedSourceList) {
			if(lgs.getSupplierId() == supplier.getId()) {
				return lgs.getSourceType().equals(SourceType.DEBT);
			}
		}
		return false;
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
	
	@GetMapping(path = "/isConnectedToSupplier/{iin}")
    public ResponseEntity<?> isConnectedToSupplier(@PathVariable String iin)
    {
		Optional<Supplier> sop = supplierRepo.findByInn(iin);
		boolean res = sop.isPresent() && sop.get().getKeycloakUserId() != null && !sop.get().getKeycloakUserId().isBlank();
		return new ResponseEntity<>(res, HttpStatus.OK);
    }

	@GetMapping(path = "/isConnectedToBuyer/{iin}")
    public ResponseEntity<?> isConnectedToBuyer(@PathVariable String iin)
    {
		Optional<Buyer> sop = buyerRepo.findByInn(iin);
		boolean res = sop.isPresent() && sop.get().getKeycloakUserId() != null && !sop.get().getKeycloakUserId().isBlank();
		return new ResponseEntity<>(res, HttpStatus.OK);
    }
	
	@PostMapping(path = "/connectToSupplier",
	consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> connectToSupplier(@RequestBody connectToModel model)
    {
		Optional<Supplier> sop = supplierRepo.findByInn(model.getIin());
		
		if(sop.isPresent()) {
			Supplier s = sop.get();
			s.setKeycloakUserId(model.getKeycloakUserId());
			supplierRepo.save(s);
		}
		else {
			Supplier s = new Supplier();
			s.setKeycloakUserId(model.getKeycloakUserId());
			s.setInn(model.getIin());
			s.setName(model.getOrgName());
			//DEFAULTS
			s.setOwnershipTypeId(1);
			s.setIndustryId(1);
			
			
			supplierRepo.save(s);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
    }
	
	@PostMapping(path = "/connectToBuyer",
	consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> connectToBuyer(@RequestBody connectToModel model)
    {
		Optional<Buyer> sop = buyerRepo.findByInn(model.getIin());
		
		if(sop.isPresent()) {
			Buyer s = sop.get();
			s.setKeycloakUserId(model.getKeycloakUserId());
			buyerRepo.save(s);
		}
		else {
			Buyer s = new Buyer();
			s.setKeycloakUserId(model.getKeycloakUserId());
			s.setInn(model.getIin());
			s.setName(model.getOrgName());
			buyerRepo.save(s);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
    }
	
	@PostMapping(path = "/saveInitialSupplier",
	consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveInitialSupplier(@RequestBody saveInitialSupplierModel model)
    {
		Optional<Supplier> sop = supplierRepo.findById(model.getSupplierId());
		
		if(sop.isPresent()) {
			Supplier s = sop.get();
			s.setFactAddress(model.getFactAddress());
			s.setLegalAddress(model.getLegalAddress());
			s.setOwnershipTypeId(model.getOwnershipTypeId());
			s.setIndustryId(model.getIndustryId());
			s.setTelephone(model.getTelephone());
			s.setBankName(model.getBankName());
			s.setBankAccount(model.getBankAccount());
			s.setBic(model.getBic());
			s.setResident(model.getResident());
			s.setHasInit(true);
			supplierRepo.save(s);
			
			if(model.getManagerPin() != null && !model.getManagerPin().isBlank()
					&& model.getManagerLastname() != null && !model.getManagerLastname().isBlank()
							&& model.getManagerFirstname() != null && !model.getManagerFirstname().isBlank()
									&& model.getManagerMiddlename() != null && !model.getManagerMiddlename().isBlank()
					) {
				SupplierMember sm = new SupplierMember();
				sm.setSupplierId(s.getId());
				sm.setName(model.getManagerFirstname());
				sm.setSurname(model.getManagerLastname());
				sm.setPatronymic(model.getManagerMiddlename());
				sm.setPin(model.getManagerPin());
				
				long managerId = 2;
				sm.setMemberTypeId(managerId);
				
				supplierMemberRepo.save(sm);	
			}
			
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
    }
	
	
	@GetMapping(path = "/initMsecDataAll",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> initMsecDataAll() throws JSONException {
		List<Supplier> listSuppliers = supplierRepo.findAll();
		int successCount = _govServices.initMsecAll(listSuppliers);
		JSONObject response = new JSONObject();
		response.put("message", "Данные МСЭК успешно обновлены у " + successCount + " участников");
		response.put("scannedCount", listSuppliers.size());
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/testAddressApi",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> testAddressApi() throws JSONException {
		SOAPClientSAAJ.execute();
		
		
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping(path = "/getSupplierByUserId/{keycloakUserId}")
    public ResponseEntity<?> getSupplierByUserId(@PathVariable String keycloakUserId)
    {
		Optional<Supplier> sop = supplierRepo.findByKeycloakUserId(keycloakUserId);
		if (sop.isPresent()) {
			return new ResponseEntity<>(sop.get(), HttpStatus.OK);			
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
    }
	
	@GetMapping(path = "/getBuyerByUserId/{keycloakUserId}")
    public ResponseEntity<?> getBuyerByUserId(@PathVariable String keycloakUserId)
    {
		Optional<Buyer> sop = buyerRepo.findByKeycloakUserId(keycloakUserId);
		if (sop.isPresent()) {
			return new ResponseEntity<>(sop.get(), HttpStatus.OK);			
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
    }
	

	@PostMapping(path = "/saveInitialBuyer",
	consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveInitialBuyer(@RequestBody saveInitialBuyerModel model)
    {
		Optional<Buyer> sop = buyerRepo.findById(model.getBuyerId());
		
		if(sop.isPresent()) {
			Buyer s = sop.get();
			s.setFactAddress(model.getFactAddress());
			s.setLegalAddress(model.getLegalAddress());
			s.setTelephone(model.getTelephone());
			s.setHasInit(true);
			buyerRepo.save(s);
			
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
    }
	

	
	@GetMapping(path = "/getComplaintsBySupplierId/{supplierId}")
    public ResponseEntity<Complaint[]> getComplaintsBySupplierId(@PathVariable long supplierId)
    {
		List<Complaint> sop = complaintRepo.findBySupplierId(supplierId);
		return new ResponseEntity<Complaint[]>(sop.toArray(new Complaint[0]), HttpStatus.OK);
    }

	@GetMapping(path = "/getComplaintsByBuyerId/{buyerId}")
    public ResponseEntity<Complaint[]> getComplaintsByBuyerId(@PathVariable long buyerId)
    {
		List<Complaint> sop = complaintRepo.findByBuyerId(buyerId);
		System.out.println("1111111111");
		return new ResponseEntity<Complaint[]>(sop.toArray(new Complaint[0]), HttpStatus.OK);
    }
	

}

class connectToModel {
	private String iin;
	private String keycloakUserId;
	private String orgName;
	public String getIin() {
		return iin;
	}
	public void setIin(String iin) {
		this.iin = iin;
	}
	public String getKeycloakUserId() {
		return keycloakUserId;
	}
	public void setKeycloakUserId(String keycloakUserId) {
		this.keycloakUserId = keycloakUserId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}

class saveInitialSupplierModel {
	private long supplierId;
	private String factAddress;
	private String legalAddress;
	private long ownershipTypeId;
	private long industryId;
	private String telephone;
	private String bankName;
	private String bankAccount;
	private String bic;
	private boolean resident;
	private String managerPin;
	private String managerLastname;
	private String managerFirstname;
	private String managerMiddlename;
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public String getFactAddress() {
		return factAddress;
	}
	public void setFactAddress(String factAddress) {
		this.factAddress = factAddress;
	}
	public String getLegalAddress() {
		return legalAddress;
	}
	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}
	public long getOwnershipTypeId() {
		return ownershipTypeId;
	}
	public void setOwnershipTypeId(long ownershipTypeId) {
		this.ownershipTypeId = ownershipTypeId;
	}
	public long getIndustryId() {
		return industryId;
	}
	public void setIndustryId(long industryId) {
		this.industryId = industryId;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public boolean getResident() {
		return resident;
	}
	public void setResident(boolean resident) {
		this.resident = resident;
	}
	public String getManagerPin() {
		return managerPin;
	}
	public void setManagerPin(String managerPin) {
		this.managerPin = managerPin;
	}
	public String getManagerLastname() {
		return managerLastname;
	}
	public void setManagerLastname(String managerLastname) {
		this.managerLastname = managerLastname;
	}
	public String getManagerFirstname() {
		return managerFirstname;
	}
	public void setManagerFirstname(String managerFirstname) {
		this.managerFirstname = managerFirstname;
	}
	public String getManagerMiddlename() {
		return managerMiddlename;
	}
	public void setManagerMiddlename(String managerMiddlename) {
		this.managerMiddlename = managerMiddlename;
	}
}

class saveInitialBuyerModel {
	private long buyerId;
	private String factAddress;
	private String legalAddress;
	private String telephone;
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public String getFactAddress() {
		return factAddress;
	}
	public void setFactAddress(String factAddress) {
		this.factAddress = factAddress;
	}
	public String getLegalAddress() {
		return legalAddress;
	}
	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
