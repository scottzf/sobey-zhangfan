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
import com.sobey.nagios.entity.NagiosStreamResult;

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
	 * @param itemName
	 * @param ipaddress
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<NagiosResult> getNagiosResult(String itemName, String ipaddress, String startDate, String endDate) {

		String sql = "SELECT T2.name1,T2.name2,T1.start_time,T1.end_time,T1.output FROM nagios_servicechecks T1,nagios_objects T2"
				+ " WHERE T2.object_id = T1.service_object_id AND command_object_id = 0 AND T2.name2 = '"
				+ itemName
				+ "' AND T2.name1 = '" + ipaddress + "'";

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

	public List<NagiosStreamResult> getNagiosStreamResult() {

		String sql = "SELECT T2.name1,T2.name2,T1.last_check,T1.last_state_change,T1.current_check_attempt,T1.max_check_attempts,T1.output "
				+ "FROM nagios_servicestatus T1,nagios_objects T2"
				+ " WHERE T2.object_id = T1.service_object_id AND T1.output = 'streamer: no' AND T1.current_check_attempt = '5' AND T1.max_check_attempts = '5'";

		return jdbcTemplate.query(sql, new NagiosStreamResultMapper());
	}

	private class NagiosStreamResultMapper implements RowMapper<NagiosStreamResult> {

		@Override
		public NagiosStreamResult mapRow(ResultSet rs, int rowNum) throws SQLException {

			NagiosStreamResult result = new NagiosStreamResult();
			result.setHostName(rs.getString("name1"));
			result.setServiceName(rs.getString("name2"));
			result.setLastCheck(rs.getTimestamp("last_check"));
			result.setLastStateChange(rs.getTimestamp("last_state_change"));
			result.setCurrentCheckAttempt(rs.getString("current_check_attempt"));
			result.setMaxCheckAttempts(rs.getString("max_check_attempts"));
			result.setOutput(rs.getString("output"));
			return result;
		}
	}

}
