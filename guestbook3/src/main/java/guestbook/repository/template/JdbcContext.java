package guestbook.repository.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

public class JdbcContext {
	
	// 여기도 DataSource를 주입해야 함
	private DataSource dataSource;
	
	public JdbcContext(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public <E> List<E> queryForList(String sql, RowMapper<E> rowMapper) {
		return queryForListWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makeStatement(Connection connection) throws SQLException{
				return connection.prepareStatement(sql);
			}
		}, rowMapper);
	}
	
	private <E> List<E> queryForListWithStatementStrategy(StatementStrategy statementStrategy, RowMapper<E> rowMapper) throws RuntimeException {
		List<E> result = new ArrayList<>();
		
		try(
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = statementStrategy.makeStatement(conn);
			ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				E e = rowMapper.mapRow(rs, rs.getRow());
				result.add(e);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public int executeUpdate(String sql, Object[] parameters) {
		return executeUpdateWithStatementStrategy(new StatementStrategy() {
			
			// ~ 이유로 String sql은 입력값으로 받아올 필요가 없음을 알 수 있게 됨..
			@Override
			public PreparedStatement makeStatement(Connection connection) throws SQLException{
				PreparedStatement pstmt = connection.prepareStatement(sql);
				// * 여기로 이동
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i + 1, parameters[i]);
				}
				return pstmt;
			}
		});
	}
	
	//  여기에 strategy를 써야 함 .왜? CalculateContext 참고
	public int executeUpdateWithStatementStrategy(StatementStrategy statementStrategy) throws RuntimeException {
		int count = 0;

		// 기존 dataSource.getConnection() 과정을 위임. 생략함.
		// 아님 다시 dataSource 주입을 통해 getConnection 사용
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = statementStrategy.makeStatement(conn);
		) {
			// 기존 pstmt.setString, setLong 등을 하나의 Object[] parameters로 받아서 for문으로 매핑시켜줬다. 
			/* 이 바인딩 코드는 ~ 필요에 의해 위로 배치됨
			for (int i = 0; i < parameters.length; i++) {
				pstmt.setObject(i + 1, parameters[i]);
			}
			*/
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return count;
	}

}
