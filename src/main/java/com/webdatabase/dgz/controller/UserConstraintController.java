package com.webdatabase.dgz.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webdatabase.dgz.model.UserConstraint;
import com.webdatabase.dgz.model.UserConstraintRole;
import com.webdatabase.dgz.repository.UserConstraintRepository;
import com.webdatabase.dgz.repository.UserConstraintRoleRepository;

@RestController
@RequestMapping("data-api/user-constraint")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserConstraintController {
	
	@Autowired
	private UserConstraintRepository userConstraintRepository;
	
	@Autowired
	private UserConstraintRoleRepository userConstraintRoleRepository;
	
	
	@PostMapping(path = "/create-user-constraint-role", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserConstraintRole> createUserRole(@RequestBody UserConstraintRole userConstraintRole){
		try {
			return new ResponseEntity<>(userConstraintRoleRepository.save(userConstraintRole), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(path = "/get-user-constraint-role/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserConstraintRole(@PathVariable long id){
		return userConstraintRoleRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping(path = "/get-user-constraint-role-all", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<List<UserConstraintRole>> getUserConstraintRoleAll(){
		try {
			List<UserConstraintRole> listUserConstraintRoles  = userConstraintRoleRepository.findAll();
			if(listUserConstraintRoles == null || listUserConstraintRoles.isEmpty()) {
				return new ResponseEntity<>(listUserConstraintRoles, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(listUserConstraintRoles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/get-user-constraint-role/update",consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserConstraintRole> updateUserConstraintRole(@RequestBody UserConstraintRole userConstraintRole){
		try {
			return new ResponseEntity<>(userConstraintRoleRepository.save(userConstraintRole), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/get-user-constraint-role/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteUserConstraintRole(@PathVariable long id){
		try {
			Optional<UserConstraintRole> userConstraintOptional = userConstraintRoleRepository.findById(id);
			if(userConstraintOptional.isPresent()) {
				userConstraintRoleRepository.delete(userConstraintOptional.get());
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	
	
	
	
	
	@PostMapping(path = "/create-user-constraint", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserConstraint> createUserConstraint(@RequestBody UserConstraint userConstraint){
		try {
			return new ResponseEntity<>(userConstraintRepository.save(userConstraint), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(path = "/get-user-constraint/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserConstraint(@PathVariable long id){
		return userConstraintRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping(path = "/get-user-constraint-all", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<List<UserConstraint>> getUserConstraintAll(){
		try {
			List<UserConstraint> listUserConstraints  = userConstraintRepository.findAll();
			if(listUserConstraints == null || listUserConstraints.isEmpty()) {
				return new ResponseEntity<>(listUserConstraints, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(listUserConstraints, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping(path = "/get-user-constraint/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteUserConstraint(@PathVariable long id){
		try {
			Optional<UserConstraint> userConstraintOptional = userConstraintRepository.findById(id);
			if(userConstraintOptional.isPresent()) {
				userConstraintRepository.delete(userConstraintOptional.get());
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
}