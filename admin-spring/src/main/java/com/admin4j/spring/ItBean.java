package com.admin4j.spring;

/**
 * @author Young
 */
public class ItBean {

	private AdminBean adminBean;

	public void setAdminBean(AdminBean adminBean) {
		this.adminBean = adminBean;
	}

	/**
	 * 构造函数
	 */
	public ItBean(){
		System.out.println("ItBean 构造器...");
	}
}
