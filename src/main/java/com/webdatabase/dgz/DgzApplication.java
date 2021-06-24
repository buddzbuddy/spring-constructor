package com.webdatabase.dgz;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.webdatabase.dgz.service.FilesStorageService;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.webdatabase.dgz.*")
public class DgzApplication implements CommandLineRunner {
	@Resource
	FilesStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(DgzApplication.class, args);
	}

	@Override
	  public void run(String... arg) throws Exception {
	    //storageService.deleteAll();
	    storageService.init();
	  }
}

