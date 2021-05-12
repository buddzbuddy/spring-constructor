package com.webdatabase.dgz.service;

import java.util.List;

import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.repository.Msec_detailRepository;
import com.webdatabase.dgz.repository.SupplierRepository;

public interface GovServices {
	int initMsecAll(List<Supplier> listSuppliers);
}
