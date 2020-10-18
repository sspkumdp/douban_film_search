package com.pku.bean;
import java.util.List;
public class SearchResult<T> {
	int count;
	List<T> list;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
