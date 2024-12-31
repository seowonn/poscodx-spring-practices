package guestbook.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import guestbook.repository.template.JdbcContext;
import guestbook.repository.template.StatementStrategy;
import guestbook.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	private JdbcContext jdbcContext;
	
	public GuestbookRepository(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}

	public List<GuestbookVo> findAll() {
		return jdbcContext.queryForList("SELECT id, name, contents, DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s') AS formatted_date"
				+ " FROM guestbook"
				+ " ORDER BY reg_date DESC",
				new RowMapper<GuestbookVo>() {
					@Override
					public GuestbookVo mapRow(ResultSet rs, int rowNum) throws SQLException {
						GuestbookVo vo = new GuestbookVo();
						vo.setId(rs.getLong(1));
						vo.setName(rs.getString(2));
						vo.setContents(rs.getString(3));
						vo.setRegDate(rs.getString(4));
						
						return vo;
					}
				});
	}
	
	public int insert(GuestbookVo vo) {
		return jdbcContext.executeUpdate(
				"insert into guestbook (name, password, contents, reg_date) values(?, ?, ?, now())",
				new Object[] {vo.getName(), vo.getPassword(), vo.getContents()});
	}

	public int deleteByIdAndPassword(Long id, String password) {
		return jdbcContext.executeUpdate("delete from guestbook where id=? and password=?", 
				new Object[] {id, password});
	}
}
