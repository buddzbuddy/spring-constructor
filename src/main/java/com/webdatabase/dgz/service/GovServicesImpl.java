package com.webdatabase.dgz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.webdatabase.dgz.model.MsecDetail;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.model.SupplierMember;
import com.webdatabase.dgz.repository.Msec_detailRepository;
import com.webdatabase.dgz.repository.SupplierRepository;

@Service
public class GovServicesImpl implements GovServices {
	@Autowired
	private SupplierRepository _supplierRepo;
	@Autowired
	private Msec_detailRepository _msec_detailRepository;
	@Override
	public int initMsecAll(List<Supplier> listSuppliers) {
		int successCount = 0;
		if(listSuppliers == null) {
			listSuppliers = _supplierRepo.findAll();
		}
		
		for (Supplier supplier : listSuppliers){
			for(SupplierMember supplierMember : supplier.getSupplierMembers()) {
				JSONObject reqMsecData;
				try {
					reqMsecData = requestMsecData(supplierMember.getPin());
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
						_msec_detailRepository.save(msecDetail);
						successCount++;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return successCount;
	}
	private JSONObject requestMsecData(String pin) throws JSONException {

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
}
