package com.webdatabase.dgz.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
  public void init();

  public void save(MultipartFile file, long supplierId, String packageTypeName, long packageItemId);

  public Resource load(String filename, long supplierId, String packageTypeName, long packageItemId);

  public void deleteAll();
  public void deleteOne(String fileName, long supplierId, String packageTypeName, long packageItemId);

  public Stream<Path> loadAll(long supplierId, String packageTypeName, long packageItemId);
}