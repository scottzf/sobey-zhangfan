package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.CompanyDTO;
import com.sobey.generate.cmdbuild.DTOResult;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.WSResult;

/**
 * Instance
 * 
 * @author Administrator
 * 
 */
@Service
public class CmdbuildService {

	@Autowired
	private CmdbuildSoapService service;

	public WSResult createcompany(CompanyDTO companyDTO) {
		return service.createCompany(companyDTO);
	}

	public WSResult deleteCompany(Integer id) {
		return service.deleteCompany(id);
	}

	public WSResult updateCompany(Integer id, CompanyDTO companyDTO) {
		return service.updateCompany(id, companyDTO);
	}

	public WSResult getCompanyList(SearchParams searchParams) {
		return service.getCompanyList(searchParams);
	}

	public WSResult getCompanyPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		return service.getCompanyPagination(searchParams, pageNumber, pageSize);
	}

	public DTOResult findCompany(SearchParams searchParams) {
		return service.findCompanyByParams(searchParams);
	}

	public DTOResult findCompany(Integer id) {
		return service.findCompany(id);
	}

}
