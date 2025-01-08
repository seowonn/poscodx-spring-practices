package guestbook.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import guestbook.repository.template.JdbcContext;
import guestbook.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	private JdbcContext jdbcContext;
	
	public GuestbookRepository(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	public GuestbookVo findById(Long id) {
		return jdbcContext.queryForObject(
				"SELECT id, name, contents, "
				+ "DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s') AS regDate "
				+ "FROM guestbook "
				+ "WHERE id = ?", 
				new Object[] {id}, new BeanPropertyRowMapper<>(GuestbookVo.class));
	}

	public List<GuestbookVo> findAll() {
		return jdbcContext.query(
				"SELECT id, name, contents, DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s') AS regDate "
				+ "FROM guestbook"
				+ " ORDER BY reg_date DESC",
				
				// 자동으로 rs의 결과값을 GuestbookVo 속성에 매핑해줌
				new BeanPropertyRowMapper<>(GuestbookVo.class));
	}
	
	public int insert(GuestbookVo vo) {
		return jdbcContext.update(
				"NSERT INTO guestbook (name, password, contents, reg_date) VALUES(?, ?, ?, now())",
				vo.getName(), vo.getPassword(), vo.getContents());
	}

	public int deleteByIdAndPassword(Long id, String password) {
		return jdbcContext.update("DELETE FROM guestbook WHERE id=? AND password=?", 
				id, password);
	}
	
}
