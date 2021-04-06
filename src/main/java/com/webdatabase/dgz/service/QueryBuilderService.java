package com.webdatabase.dgz.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.webdatabase.dgz.ClassFinder;
import com.webdatabase.dgz.query.utils.QueryCriteriaConsumer;
import com.webdatabase.dgz.query.utils.FieldDesc;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;
import com.webdatabase.dgz.query.utils.MyClassDesc;
import com.webdatabase.dgz.query.utils.SearchCriteria;
import com.webdatabase.dgz.query.utils.SearchQuery;
import com.webdatabase.dgz.query.utils.SpecificationUtil;


@Service
@Transactional
public class QueryBuilderService {
	@PersistenceContext
	private EntityManager entityManager;

	public <T> List<T> execute(Class<T> clazz, List<SearchCriteria> params) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<?> r = query.from(clazz);

        if(params != null && params.size() > 0) {
            Predicate predicate = builder.conjunction();
        	QueryCriteriaConsumer searchConsumer =
        			new QueryCriteriaConsumer(predicate, builder, r);
	        params.stream().forEach(searchConsumer);
	        predicate = searchConsumer.getPredicate();
	        query.where(predicate);	
        }
        
        List<T> result = entityManager.createQuery(query).getResultList();
        return result;
	}
	
	public <T> CriteriaQuery<T> CreateQuery(Class<T> clazz, List<SearchCriteria> params) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<?> r = query.from(clazz);

        if(params != null && params.size() > 0) {
            Predicate predicate = builder.conjunction();
        	QueryCriteriaConsumer searchConsumer =
        			new QueryCriteriaConsumer(predicate, builder, r);
	        params.stream().forEach(searchConsumer);
	        predicate = searchConsumer.getPredicate();
	        query.where(predicate);
        }
        return query;
	}
	
	public <T> List<T> test(Class<T> clazz, SearchQuery searchQuery){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> r = query.from(clazz);
        Specification<T> s = SpecificationUtil.bySearchQuery(searchQuery, clazz);
        query.where(s.toPredicate(r, query, builder));
        TypedQuery<T> q = entityManager.createQuery(query);
        //System.out.println(q.unwrap(Query.class).getQueryString());
        List<T> result = q.getResultList();
        return result;
	}
	
	
	public static <T> Specification<T> joinTest2(Class<?> input, String onField) {
	    return new Specification<T>() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            Join<T,Class<?>> userProd = root.join(onField);
	            //Join<FollowingRelationship,Product> prodRelation = userProd.join("ownedRelationships");
	            return cb.equal(userProd.get("id"), input);
	        }
	    };
	}

	public MyClassDesc[] getMetaDescription() {
		List<Class<?>> classes = ClassFinder.find("com.webdatabase.dgz.model");
		
		
		ArrayList<MyClassDesc> descModel = new ArrayList<MyClassDesc> ();
		
		for(Class<?> c : classes) {
			IsMetaClass mc = c.getAnnotation(IsMetaClass.class);
			if(mc == null) {
				continue;
			}
			MyClassDesc cm = new MyClassDesc();
			
			cm.setClassName(c.getSimpleName());
			cm.setClassLabel(mc.label());
			
			ArrayList<FieldDesc> fList = new ArrayList<FieldDesc>();
			for(Field f : c.getDeclaredFields()) {
				FieldDesc fd = new FieldDesc();
				fd.setName(f.getName());
				fd.setDataType(f.getType().getSimpleName());
				MetaFieldName fName = f.getAnnotation(MetaFieldName.class);
				if(fName != null) {
					fd.setLabel(fName.label());
					if(!fName.selectClassName().isEmpty()) {
						fd.setDictionaryClassName(fName.selectClassName());
						fd.setDictionaryFieldName(fName.selectClassFieldName());
					}
				}
				fList.add(fd);
			}
			cm.setPropList(fList);
			
			descModel.add(cm);
		}
		MyClassDesc[] a = new MyClassDesc[descModel.size()];
		return descModel.toArray(a);
	}
	public MyClassDesc getClassDescription(String className) {
		List<Class<?>> classes = ClassFinder.find("com.webdatabase.dgz.model");
		
		
		for(Class<?> c : classes) {
			IsMetaClass mc = c.getAnnotation(IsMetaClass.class);
			if(mc == null) {
				continue;
			}
			if(!c.getSimpleName().equals(className)) {
				continue;
			}
			MyClassDesc cm = new MyClassDesc();
			
			cm.setClassName(c.getSimpleName());
			cm.setClassLabel(mc.label());
			
			ArrayList<FieldDesc> fList = new ArrayList<FieldDesc>();
			for(Field f : c.getDeclaredFields()) {
				FieldDesc fd = new FieldDesc();
				fd.setName(f.getName());
				fd.setDataType(f.getType().getSimpleName());
				MetaFieldName fName = f.getAnnotation(MetaFieldName.class);
				if(fName != null) {
					fd.setLabel(fName.label());
					if(!fName.selectClassName().isEmpty()) {
						fd.setDictionaryClassName(fName.selectClassName());
						fd.setDictionaryFieldName(fName.selectClassFieldName());
					}
				}
				fList.add(fd);
			}
			cm.setPropList(fList);
			return cm;
		}
		return null;
	}
	
}