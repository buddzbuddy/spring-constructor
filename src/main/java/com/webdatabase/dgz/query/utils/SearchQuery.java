package com.webdatabase.dgz.query.utils;

import java.util.List;

public class SearchQuery {
	private String rootName;
	private List<SearchFilter> searchFitler;

	private int pageNumber;
	private int pageSize;

	private SortOrder sortOrder;

	private List<JoinColumnProps> joinColumnProps;

	public List<SearchFilter> getSearchFitler() {
		return searchFitler;
	}

	public void setSearchFitler(List<SearchFilter> searchFitler) {
		this.searchFitler = searchFitler;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<JoinColumnProps> getJoinColumnProps() {
		return joinColumnProps;
	}

	public void setJoinColumnProps(List<JoinColumnProps> joinColumnProps) {
		this.joinColumnProps = joinColumnProps;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
}
