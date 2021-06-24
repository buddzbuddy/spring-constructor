package com.webdatabase.dgz.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  private final Path root = Paths.get("uploads");

  @Override
  public void init() {
    try {
    	if(Files.notExists(root)) {
    		Files.createDirectory(root);	
    	}
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(MultipartFile file, long supplierId) {
    try {
    	Path subRoot = Paths.get("uploads/"+supplierId);
    	if(Files.notExists(subRoot)) {
    		Files.createDirectory(subRoot);	
    	}
    	long t = System.currentTimeMillis();
      Files.copy(file.getInputStream(), subRoot.resolve(t + "-" + file.getOriginalFilename()));
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  @Override
  public Resource load(String filename, long supplierId) {
    try {
    	Path subRoot = Paths.get("uploads/"+supplierId);
    	if(Files.notExists(subRoot)) {
    		Files.createDirectory(subRoot);	
    	}
      Path file = subRoot.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new RuntimeException("Error on creating subFolder: " + e.getMessage());
	}
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll(long supplierId) {
    try {
    	Path subRoot = Paths.get("uploads/"+supplierId);
    	if(Files.notExists(subRoot)) {
    		Files.createDirectory(subRoot);	
    	}
      return Files.walk(subRoot, 1).filter(path -> !path.equals(subRoot)).map(subRoot::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

}