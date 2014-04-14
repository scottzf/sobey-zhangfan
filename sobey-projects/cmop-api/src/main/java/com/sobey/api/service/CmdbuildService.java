package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.CompanyDTO;
import com.sobey.generate.cmdbuild.DTOListResult;
import com.sobey.generate.cmdbuild.DTOResult;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.SearchParams;
import com.sobey.generate.cmdbuild.ServerDTO;
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

	public DTOListResult getCompanyList(SearchParams searchParams) {
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

	public DTOListResult getEcsList(SearchParams searchParams) {
		return service.getEcsList(searchParams);
	}

	public WSResult createEcsDTO(EcsDTO ecsDTO) {
		return service.createEcs(ecsDTO);
	}

	public WSResult deleteEcs(Integer id) {
		return service.deleteEcs(id);
	}

	public WSResult updateEcs(Integer id, EcsDTO ecsDTO) {
		return service.updateEcs(id, ecsDTO);
	}

	public WSResult getEcsPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		return service.getEcsPagination(searchParams, pageNumber, pageSize);
	}

	public DTOResult findEcs(SearchParams searchParams) {
		return service.findEcsByParams(searchParams);
	}

	public DTOResult findEcs(Integer id) {
		return service.findEcs(id);
	}

	public DTOListResult getServerList(SearchParams searchParams) {
		return service.getServerList(searchParams);
	}

	public WSResult createServer(ServerDTO serverDTO) {
		return service.createServer(serverDTO);
	}

	public WSResult deleteServer(Integer id) {
		return service.deleteServer(id);
	}

	public WSResult updateServer(Integer id, ServerDTO serverDTO) {
		return service.updateServer(id, serverDTO);
	}

	public WSResult getServerPagination(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
		return service.getServerPagination(searchParams, pageNumber, pageSize);
	}

	public DTOResult findServer(SearchParams searchParams) {
		return service.findServerByParams(searchParams);
	}

	public DTOResult findServer(Integer id) {
		return service.findServer(id);
	}

	public DTOResult findIpaddress(Integer id) {
		return service.findIpaddress(id);
	}

}
