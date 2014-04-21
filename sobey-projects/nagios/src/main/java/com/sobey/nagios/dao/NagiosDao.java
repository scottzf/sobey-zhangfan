package com.sobey.nagios.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sobey.nagios.entity.NagiosResult;

/**
 * 对nagios数据库进行读取.
 * 
 * @author Administrator
 * 
 */
@Repository
public class NagiosDao {

	private JdbcTemplate jdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * 获得nagios数据库中的数据.
	 * 
	 * @param itemId
	 * @param ipaddress
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<NagiosResult> getNagiosResult(String itemId, String ipaddress, String startDate, String endDate) {
		String sql = "SELECT T2.name1,T2.name2,T1.start_time,T1.end_time,T1.output FROM nagios_servicechecks T1,nagios_objects T2"
				+ " WHERE T2.object_id = T1.service_object_id AND command_object_id = 0 AND T2.object_id = "
				+ itemId
				+ " AND T2.name1 = '" + ipaddress + "'";

		if (StringUtils.isNotBlank(startDate)) {
			sql += "AND T1.end_time >='" + startDate + "'";
		}

		if (StringUtils.isNotBlank(endDate)) {
			sql += "AND T1.end_time <='" + endDate + "'";
		}

		return jdbcTemplate.query(sql, new NagiosResultMapper());
	}

	private class NagiosResultMapper implements RowMapper<NagiosResult> {

		@Override
		public NagiosResult mapRow(ResultSet rs, int rowNum) throws SQLException {
			NagiosResult result = new NagiosResult();
			result.setIpaddress(rs.getString("name1"));
			result.setItem(rs.getString("name2"));
			result.setStartTime(rs.getTimestamp("start_time"));
			result.setEndTime(rs.getTimestamp("end_time"));
			result.setOutput(rs.getString("output"));
			return result;
		}
	}

}
