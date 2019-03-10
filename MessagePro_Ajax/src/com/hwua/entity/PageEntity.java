package com.hwua.entity;

import java.util.List;

/**
 * 一个PageEntity就代表一页
 * 
 * @author Administrator
 *
 */
public class PageEntity {
	private int pageNo;// 当前页
	private int nextPage;// 下一页
	private int prePage;// 上一页
	private Long totalRecords;// 总记录数
	private int pageSize;// 当前页显示的记录数
	private int totalPages;// 总页数
	private List<Message> msgList = null;// 每页显示的记录集合

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 得到下一页的页码
	 * 
	 * @return
	 */
	public int getNextPage() {
		if (pageNo >= getTotalPages()) {
			nextPage = getTotalPages();
		} else {
			nextPage = pageNo + 1;
		}
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * 得到上一页
	 * 
	 * @return
	 */
	public int getPrePage() {
		if (pageNo <= 1) {
			prePage = 1;
		} else {
			prePage = pageNo - 1;
		}
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 得到总页数
	 * 
	 * @return
	 */
	public int getTotalPages() {
		totalPages = (int) (totalRecords / pageSize);
		if (totalRecords % pageSize != 0) {
			totalPages += 1;
		}
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<Message> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<Message> msgList) {
		this.msgList = msgList;
	}

}
