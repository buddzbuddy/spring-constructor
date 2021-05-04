package com.webdatabase.dgz.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.webdatabase.dgz.ClassFinder;
import com.webdatabase.dgz.query.utils.QueryCriteriaConsumer;
import com.webdatabase.dgz.query.utils.FieldDesc;
import com.webdatabase.dgz.query.utils.InsertEntityFieldModel;
import com.webdatabase.dgz.query.utils.InsertEntityModel;
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

	public <T> List<T> exec(Class<T> clazz, SearchQuery searchQuery){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> r = query.from(clazz);
        Specification<T> s = SpecificationUtil.bySearchQuery(searchQuery, clazz);
        List<Order> o = new ArrayList<>();
        query.where(s.toPredicate(r, query, builder));
        
        
        int pageSize = searchQuery.getPageSize();//10
        int pageNumber = searchQuery.getPageNumber();//2
        
        if(pageSize == 0) {
        	pageSize = 100;
        }
        if(pageNumber == 0) {
        	pageNumber = 1;
        }
        
        int skipSize = pageSize * (pageNumber - 1);//10
        
        
        
        //int qTotal = entityManager.createQuery(query).getMaxResults();
        //System.out.println("Total rows: " + qTotal);
        TypedQuery<T> q = entityManager.createQuery(query)/*
        		.setMaxResults(pageSize)
        		.setFirstResult(skipSize + pageNumber)*/;
        List<T> result = q.getResultList();
        return result;
	}
	
	@Transactional
	public void addEntry(InsertEntityModel insertModel, Class<?> clazz) {
		List<String> names = new ArrayList<>();
		List<String> symbols = new ArrayList<>();
		List<Object> vals = new ArrayList<>();
		Table et = clazz.getAnnotation(Table.class);
		if(et == null) {
			System.out.println("Entity не найден у класса " + clazz.getName());
		}
		else
		for(InsertEntityFieldModel item : insertModel.getFields()) {
			
			try {
				Field f = clazz.getDeclaredField(item.getName());
				if(f.getName().equals("id")) continue;
				
				f.setAccessible(true);
				if(f.getType().getName().equals(long.class.getName())) {
					vals.add(((Number) item.getVal()).longValue());
				}
				else if (f.getType().getName().equals(int.class.getName())) {
					vals.add((int)item.getVal());
				}
				else if (f.getType().getName().equals(float.class.getName())) {
					vals.add((float)item.getVal());
				}
				else if (f.getType().getName().equals(double.class.getName())) {
					vals.add((double)item.getVal());
				}
				else if (f.getType().getName().equals(boolean.class.getName())) {
					vals.add((boolean)item.getVal());
				}
				else if (f.getType().getName().equals(String.class.getName())) {
					vals.add((String)item.getVal());
				}
				else if (f.getType().getName().equals(Date.class.getName())) {
					vals.add((Date)item.getVal());
				}
				else {
					System.out.println("Поле " + item.getName() + " имеет неизвстный тип: " + f.getType().getName());
				}
				Column cAnnot = f.getAnnotation(Column.class);
				if(cAnnot != null) {
					names.add(cAnnot.name());
					symbols.add("?");
				}
				else {
					System.out.println("Поле " + item.getName() + " не имеет аннотации Column");
				}
					
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(item.getName());
				e.printStackTrace();
			}
			
			
		}
		String queryString = "INSERT INTO " + et.name() + " ("+ String.join(", ", names) +") VALUES (" + String.join(",", symbols) + ")";
		Query q = entityManager.createNativeQuery(queryString);
	
		System.out.println(queryString);
		
		for(int i = 0; i < names.size(); i++) {
			q = q.setParameter(i + 1, vals.get(i));
		}
		
		System.out.println("insert-result" + q.executeUpdate());
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
			cm.setOrderNo(mc.orderNo());
			
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
					fList.add(fd);
				}
			}
			cm.setPropList(fList);
			
			descModel.add(cm);
		}
		

		Collections.sort(descModel, new Comparator<MyClassDesc>() {
			  @Override
			  public int compare(MyClassDesc c1, MyClassDesc c2) {
			    return c1.getOrderNo().compareTo(c2.getOrderNo());
			  }
			});
		
		MyClassDesc[] a = new MyClassDesc[descModel.size()];
		return descModel.toArray(a);
	}
	public MyClassDesc getClassDescription(String className) {
		
		Class<?> c = findClassByName(className);
		if(c == null) {
			return null;
		}
		MyClassDesc cm = new MyClassDesc();
		IsMetaClass mc = c.getAnnotation(IsMetaClass.class);
		if(mc == null) {
			return null;
		}
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
				fList.add(fd);
			}
		}
		cm.setPropList(fList);
		return cm;
	}
	public Class<?> findClassByName(String name){
		List<Class<?>> classes = ClassFinder.find("com.webdatabase.dgz.model");
		for(Class<?> c : classes) {
			IsMetaClass mc = c.getAnnotation(IsMetaClass.class);
			if(mc == null) {
				continue;
			}
			if(c.getSimpleName().equals(name)) {
				return c;
			}
		}
		return null;
	}
}