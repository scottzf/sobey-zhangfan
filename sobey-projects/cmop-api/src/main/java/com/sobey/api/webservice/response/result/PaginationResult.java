package com.sobey.api.webservice.response.result;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.api.constans.WsConstants;

/**
 * 某个对象返回的通用分页PaginationResult.
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@XmlType(name = "PaginationResult", namespace = WsConstants.NS)
public class PaginationResult<T> extends WSResult {

	// -- PaginationResult基本属性 --//

	/**
	 * 对象集合
	 */
	private List<T> getContent;

	/**
	 * 当前页面的数量，最小值为1
	 */
	private int getNumber;

	/**
	 * 当前页面上的元素的数量
	 */
	private int getNumberOfElements;

	/**
	 * 每页大小
	 */
	private int getSize;

	/**
	 * 元素的总量
	 */
	private int getTotalElements;

	/**
	 * 总页数
	 */
	private int getTotalPages;

	/**
	 * 是否有下一页
	 */
	private boolean hasNextPage;

	/**
	 * 是否有上一页
	 */
	private boolean hasPreviousPage;

	/**
	 * 是否首页
	 */
	private boolean isFirstPage;

	/**
	 * 是否末页
	 */
	private boolean isLastPage;

	public PaginationResult() {
		super();
	}

	public PaginationResult(int getNumber, int getSize, int getTotalPages, int getNumberOfElements,
			int getTotalElements, boolean hasPreviousPage, boolean isFirstPage, boolean hasNextPage,
			boolean isLastPage, List<T> getContent) {
		super();
		this.getNumber = getNumber;
		this.getSize = getSize;
		this.getTotalPages = getTotalPages;
		this.getNumberOfElements = getNumberOfElements;
		this.getTotalElements = getTotalElements;
		this.hasPreviousPage = hasPreviousPage;
		this.isFirstPage = isFirstPage;
		this.hasNextPage = hasNextPage;
		this.isLastPage = isLastPage;
		this.getContent = getContent;
	}

	public List<T> getGetContent() {
		return getContent;
	}

	public int getGetNumber() {
		return getNumber;
	}

	public int getGetNumberOfElements() {
		return getNumberOfElements;
	}

	public int getGetSize() {
		return getSize;
	}

	public int getGetTotalElements() {
		return getTotalElements;
	}

	public int getGetTotalPages() {
		return getTotalPages;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public void setGetContent(List<T> getContent) {
		this.getContent = getContent;
	}

	public void setGetNumber(int getNumber) {
		this.getNumber = getNumber;
	}

	public void setGetNumberOfElements(int getNumberOfElements) {
		this.getNumberOfElements = getNumberOfElements;
	}

	public void setGetSize(int getSize) {
		this.getSize = getSize;
	}

	public void setGetTotalElements(int getTotalElements) {
		this.getTotalElements = getTotalElements;
	}

	public void setGetTotalPages(int getTotalPages) {
		this.getTotalPages = getTotalPages;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
