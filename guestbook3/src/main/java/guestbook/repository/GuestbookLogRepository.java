package guestbook.repository;

import org.springframework.stereotype.Repository;

import guestbook.repository.template.JdbcContext;

@Repository
public class GuestbookLogRepository {
	
	private JdbcContext jdbcContext;
	
	public GuestbookLogRepository(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	public int insert() {
		return jdbcContext.update("INSERT INTO guestbook_log VALUES(current_date(), 1)");
	}
	
	public int update() {
		return jdbcContext.update(
				"UPDATE guestbook_log SET count = count + 1 WHERE date = current_date()");
	}
	
	public int update(String regDate) {
		return jdbcContext.update(
				"UPDATE guestbook_log SET count = count - 1 WHERE date_format(date, '%Y-%m-%d') = ?", 
				regDate);
	}
	
}
