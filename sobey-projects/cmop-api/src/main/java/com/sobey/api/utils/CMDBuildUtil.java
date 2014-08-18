package com.sobey.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.SearchParams.ParamsMap;
import com.sobey.generate.cmdbuild.SearchParams.ParamsMap.Entry;

/**
 * CMDBuild相关的一些帮助方法.
 * 
 * @author Administrator
 *
 */
public class CMDBuildUtil {

	/**
	 * 对放入HashMap中的查询字段和查询值,进行封装.<br>
	 * 
	 * 遍历HashMap后,将key和value 封装入CMDBuild webservice 生成的SearchParams对象.
	 * 
	 * 避免繁琐的、重复的SearchParams对象设值.
	 * 
	 * 
	 * @param map
	 * @return
	 */
	public static SearchParams wrapperSearchParams(HashMap<String, Object> map) {

		SearchParams searchParams = new SearchParams();
		List<Entry> entries = new ArrayList<Entry>();

		for (java.util.Map.Entry<String, Object> entry : map.entrySet()) {

			Entry searchEntry = new Entry();
			searchEntry.setKey(entry.getKey());
			searchEntry.setValue(entry.getValue());
			entries.add(searchEntry);
		}

		ParamsMap paramsMap = new ParamsMap();

		paramsMap.getEntry().addAll(entries);

		searchParams.setParamsMap(paramsMap);

		return searchParams;

	}

}
