package com.sobey.cmdbuild.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;
import com.sobey.core.persistence.SearchFilter.Operator;
import com.sobey.test.spring.SpringTransactionalTestCase;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DynamicSpecificationTest extends SpringTransactionalTestCase {

	@Autowired
	private RackDao RackDao;

	@Test
	public void fineRackByFilter() {
		// EQ
		SearchFilter filter = new SearchFilter("code", Operator.EQ, "管理员");
		List<Rack> Racks = RackDao
				.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(1, Racks.size());

		// LIKE
		filter = new SearchFilter("description", Operator.LIKE, "min");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(1, Racks.size());

		// GT
		filter = new SearchFilter("id", Operator.GT, "1");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(5, Racks.size());

		filter = new SearchFilter("id", Operator.GT, "6");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(0, Racks.size());

		// GTE
		filter = new SearchFilter("id", Operator.GTE, "1");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(6, Racks.size());

		filter = new SearchFilter("id", Operator.GTE, "6");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(1, Racks.size());

		// LT
		filter = new SearchFilter("id", Operator.LT, "6");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(5, Racks.size());

		filter = new SearchFilter("id", Operator.LT, "1");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(0, Racks.size());

		// LTE
		filter = new SearchFilter("id", Operator.LTE, "6");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(6, Racks.size());

		filter = new SearchFilter("id", Operator.LTE, "1");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Rack.class));
		assertEquals(1, Racks.size());

		// Empty filters, select all
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(new ArrayList<SearchFilter>(), Rack.class));
		assertEquals(6, Racks.size());

		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(null, Rack.class));
		assertEquals(6, Racks.size());

		// AND 2 Conditions
		SearchFilter filter1 = new SearchFilter("code", Operator.EQ, "管理员");
		SearchFilter filter2 = new SearchFilter("description", Operator.LIKE, "min");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter1, filter2), Rack.class));
		assertEquals(1, Racks.size());

		filter1 = new SearchFilter("code", Operator.EQ, "管理员");
		filter2 = new SearchFilter("description", Operator.LIKE, "Rack");
		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter1, filter2), Rack.class));
		assertEquals(0, Racks.size());

		// 2 conditions on same field
		filter1 = new SearchFilter("id", Operator.GTE, "1");
		filter2 = new SearchFilter("id", Operator.LTE, "6");

		Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter1, filter2), Rack.class));
		assertEquals(6, Racks.size());

		// // Nest Attribute
		// filter = new SearchFilter("team.id", Operator.EQ, "1");
		// Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter),
		// Rack.class));
		// assertEquals(6, Racks.size());
		//
		// filter = new SearchFilter("team.id", Operator.EQ, "10");
		// Racks = RackDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter),
		// Rack.class));
		// assertEquals(0, Racks.size());
	}
}
