package com.webdatabase.dgz.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
  public void init();

  public void save(MultipartFile file, long supplierId);

  public Resource load(String filename, long supplierId);

  public void deleteAll();
  public void deleteOne(String fileName, long supplierId);

  public Stream<Path> loadAll(long supplierId);
}