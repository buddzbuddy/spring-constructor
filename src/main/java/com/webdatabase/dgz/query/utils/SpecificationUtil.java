package com.webdatabase.dgz.query.utils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {
	public static <T> Specification<T> bySearchQuery(SearchQuery searchQuery, Class<T> clazz) {

		return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criterailBuilder) -> {

			List<Predicate> predicates = new ArrayList<>();
			// Add Predicates for tables to be joined
			List<JoinColumnProps> joinColumnProps = searchQuery.getJoinColumnProps();

			if (joinColumnProps != null && !joinColumnProps.isEmpty()) {
				for (JoinColumnProps joinColumnProp : joinColumnProps) {
					addJoinColumnProps(predicates, joinColumnProp, criterailBuilder, root);
				}
			}

			List<SearchFilter> searchFilters = searchQuery.getSearchFitler();

			if (searchFilters != null && !searchFilters.isEmpty()) {

				for (final SearchFilter searchFilter : searchFilters) {
					
					try {
						Field f = clazz.getDeclaredField(searchFilter.getProperty());
						f.setAccessible(true);
						if (f.getType().getName().equals(Date.class.getName())) {
							String dateSrc = searchFilter.getValue().toString();
							searchFilter.setValue(castToDate(dateSrc));
						}
						addPredicates(predicates, searchFilter, criterailBuilder, root);
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (predicates.isEmpty()) {
				return criterailBuilder.conjunction();
			}

			return criterailBuilder.and(predicates.toArray(new Predicate[0]));

		};
	}

    
	public static Date castToDate(String src) {
    	DateTimeFormatter formatter = 
    	        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
    	LocalDateTime date = LocalDateTime.parse(src, formatter);
    	return java.sql.Timestamp.valueOf(date);
    }
	private static <T> void addJoinColumnProps(List<Predicate> predicates, JoinColumnProps joinColumnProp,
			CriteriaBuilder criterailBuilder, Root<T> root) {

		Join joinParent = root.join(joinColumnProp.getJoinColumnName());
		
		SearchFilter searchFilter = joinColumnProp.getSearchFilter();

		if(searchFilter != null) {
			String property = searchFilter.getProperty();
			Path expression = joinParent.get(property);
			//joinParent = joinParent.on(criterailBuilder.equal(expression, searchFilter.getValue()));
			addPredicate(predicates, searchFilter, criterailBuilder, expression);	
			//predicates.add(criterailBuilder.equal(expression, searchFilter.getValue()));
		}

	}

	private static <T> void addPredicates(List<Predicate> predicates, SearchFilter searchFilter,
			CriteriaBuilder criterailBuilder, Root<T> root) {
		String property = searchFilter.getProperty();
		Path<?> expression = root.get(property);

		addPredicate(predicates, searchFilter, criterailBuilder, expression);

	}

	private static void addPredicate(List<Predicate> predicates, SearchFilter searchFilter,
			CriteriaBuilder criterailBuilder, Path expression) {
		switch (searchFilter.getOperator()) {
		case "=":
			predicates.add(criterailBuilder.equal(expression, searchFilter.getValue()));
			break;
		case "LIKE":
			predicates.add(criterailBuilder.like(expression, "%" + searchFilter.getValue() + "%"));
			break;
		case "IN":
			predicates.add(criterailBuilder.in(expression).value(searchFilter.getValue()));
			break;
		case ">":
			predicates.add(criterailBuilder.greaterThan(expression, (Comparable) searchFilter.getValue()));
			break;
		case "<":
			predicates.add(criterailBuilder.lessThan(expression, (Comparable) searchFilter.getValue()));
			break;
		case ">=":
			predicates.add(criterailBuilder.greaterThanOrEqualTo(expression, (Comparable) searchFilter.getValue()));
			break;
		case "<=":
			predicates.add(criterailBuilder.lessThanOrEqualTo(expression, (Comparable) searchFilter.getValue()));
			break;
		case "!":
			predicates.add(criterailBuilder.notEqual(expression, searchFilter.getValue()));
			break;
		case "IsNull":
			predicates.add(criterailBuilder.isNull(expression));
			break;
		case "NotNull":
			predicates.add(criterailBuilder.isNotNull(expression));
			break;
		default:
			System.out.println("Predicate is not matched");
			throw new IllegalArgumentException(searchFilter.getOperator() + " is not a valid predicate");
		}

	}
}
