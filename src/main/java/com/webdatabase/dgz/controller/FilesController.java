package com.webdatabase.dgz.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.FileInfo;
import com.webdatabase.dgz.service.FilesStorageService;

@RestController
@RequestMapping("/data-api/files")
public class FilesController {
	@Autowired
	  FilesStorageService storageService;

	  @PostMapping("/{supplierId}/upload")
	  public ResponseEntity<ResponseMessage> uploadFile(@PathVariable long supplierId, @RequestParam("file") MultipartFile file) {
	    String message = "";
	    try {
	      storageService.save(file, supplierId);

	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	  }

	  @GetMapping("/{supplierId}/list")
	  public ResponseEntity<List<FileInfo>> getListFiles(@PathVariable long supplierId) {
	    List<FileInfo> fileInfos = storageService.loadAll(supplierId).map(path -> {
	      String filename = path.getFileName().toString();
	      String url = MvcUriComponentsBuilder
	          .fromMethodName(FilesController.class, "getFile", supplierId, path.getFileName().toString()).build().toString();

	      return new FileInfo(filename, url);
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	  }

	  @GetMapping("/{supplierId}/download/{filename:.+}")
	  @ResponseBody
	  public ResponseEntity<Resource> getFile(@PathVariable long supplierId, @PathVariable String filename) {
	    Resource file = storageService.load(filename, supplierId);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }
}
