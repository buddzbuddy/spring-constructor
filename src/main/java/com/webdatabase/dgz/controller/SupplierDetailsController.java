package com.webdatabase.dgz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.repository.SupplierRepository;

@RestController
@RequestMapping("/data-api/supplier-details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplierDetailsController {
	@Autowired
	private SupplierRepository supplierRepo;
	
	@GetMapping(path = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable long id)
    {
		Supplier s = supplierRepo.findById(id).get();
		System.out.println(s.getOwnershipType().getName());
    	return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
