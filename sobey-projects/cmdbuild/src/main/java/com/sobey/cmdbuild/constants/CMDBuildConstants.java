package com.sobey.cmdbuild.constants;

/**
 * CMDBuild 常量定义.
 * 
 * <pre>
 * "A" 活跃		最新数据
 * "U" 更新		数据更新前的数据
 * "N" 非活跃	逻辑删除的数据
 * 
 * <pre>
 * 
 * @author Administrator
 * 
 */
public class CMDBuildConstants {

	/**
	 * "A" 活跃
	 */
	public static final Character STATUS_ACTIVE = 'A';

	/**
	 * "U" 更新
	 */
	public static final Character STATUS_UPDATE = 'U';

	/**
	 * "N" 非活跃
	 */
	public static final Character STATUS_NON_ACTIVE = 'N';

}
