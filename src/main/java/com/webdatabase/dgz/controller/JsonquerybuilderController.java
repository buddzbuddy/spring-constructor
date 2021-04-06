package com.webdatabase.dgz.controller;


import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webdatabase.dgz.query.utils.MyClassDesc;
import com.webdatabase.dgz.query.utils.SearchCriteria;
import com.webdatabase.dgz.query.utils.SearchQuery;
import com.webdatabase.dgz.service.QueryBuilderService;

@RestController
@RequestMapping("/data-api/query")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JsonquerybuilderController {

	
    @Autowired
    private QueryBuilderService queryApi;
    
    @PostMapping(path = "/execute",
    		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<QueryResult> execute(@RequestBody QueryCondition condition)
    {
    	List<SearchCriteria> params = null;
    	if(condition.getParams() != null && condition.getParams().length > 0) {
    		params = Arrays.asList(condition.getParams());
    	}
    	
    	try {
			Class<?> birdClass = Class.forName("com.webdatabase.dgz.model." + condition.getTable());
			List<?> results = queryApi.execute(birdClass, params);
	    	QueryResult res = new QueryResult(true, "", results.toArray());
	    	return new ResponseEntity<QueryResult>(res, HttpStatus.OK);
		} catch (ClassNotFoundException e) {
	    	QueryResult res = new QueryResult(false, "Класс не найден", null);
			e.printStackTrace();
			return new ResponseEntity<QueryResult>(res, HttpStatus.BAD_REQUEST);
		}
    }
    
    @PostMapping(path = "/exec",
    		consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueryResult> exec(@RequestBody SearchQuery searchQuery)
    {
    	try {
    		Class<?> birdClass = Class.forName("com.webdatabase.dgz.model." + searchQuery.getRootName());
        	QueryResult res = new QueryResult(true, "", queryApi.test(birdClass, searchQuery).toArray(new Object[0]));
	    	return new ResponseEntity<QueryResult>(res, HttpStatus.OK);
		} catch (ClassNotFoundException e) {
	    	QueryResult res = new QueryResult(false, "Класс не найден", null);
			e.printStackTrace();
			return new ResponseEntity<QueryResult>(res, HttpStatus.BAD_REQUEST);
		}
    }
    
    @GetMapping(path = "/getMeta")
    public ResponseEntity<QueryResult> getMeta()
    {
    	QueryResult res = new QueryResult(true, "", queryApi.getMetaDescription());
    	return new ResponseEntity<QueryResult>(res, HttpStatus.OK);
    }
    
    @GetMapping(path = "/getMeta/{className}")
    public ResponseEntity<MyClassDesc> getMeta(@PathVariable String className)
    {
    	return new ResponseEntity<MyClassDesc>(queryApi.getClassDescription(className), HttpStatus.OK);
    }
}

class QueryCondition {
	private String table;
	
private SearchCriteria[] params;

public SearchCriteria[] getParams() {
	return params;
}

public void setParams(SearchCriteria[] params) {
	this.params = params;
}

public String getTable() {
	return table;
}

public void setTable(String table) {
	this.table = table;
}
}
class QueryResult {
	private Boolean result;
	private String error;
	private Object[] data;
	
	public QueryResult() {}
	
	public QueryResult(Boolean result, String error, Object[] data) {
		this.result = result;
		this.error = error;
		this.data = data;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] result) {
		this.data = result;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}