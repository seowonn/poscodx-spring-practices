package guestbook.repository.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JdbcContext {
	
	// 여기도 DataSource를 주입해야 함
	private DataSource dataSource;
	
	public JdbcContext(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public <E> List<E> query(String sql, RowMapper<E> rowMapper) {
		return queryWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makeStatement(Connection connection) throws SQLException{
				return connection.prepareStatement(sql);
			}
		}, rowMapper);
	}
	
	public <E> E queryForObject(String sql, Object[] paramters, RowMapper<E> rowMapper) {
		return queryForObjectWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makeStatement(Connection connection) throws SQLException{
				PreparedStatement pstmt = connection.prepareStatement(sql);
				for (int i = 0; i < paramters.length; i++) {
					pstmt.setObject(i + 1, paramters[i]);
				}
				return pstmt;
			}
		}, rowMapper);
	}

	private <E> List<E> queryWithStatementStrategy(StatementStrategy statementStrategy, RowMapper<E> rowMapper) throws RuntimeException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			pstmt = statementStrategy.makeStatement(conn);
			rs = pstmt.executeQuery();
			
			List<E> result = new ArrayList<>();
			while(rs.next()) {
				E e = rowMapper.mapRow(rs, rs.getRow());
				result.add(e);
			}
			
			return result;
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {				
					DataSourceUtils.releaseConnection(conn, dataSource);
				}
			} catch (SQLException ignore){
				
			}
		}
	}
	
	private <E> E queryForObjectWithStatementStrategy(StatementStrategy statementStrategy, RowMapper<E> rowMapper) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			pstmt = statementStrategy.makeStatement(conn);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rowMapper.mapRow(rs, rs.getRow());
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {				
					DataSourceUtils.releaseConnection(conn, dataSource);
				}
			} catch (SQLException ignore){
				
			}
		}
		
		return null;
	}

	public int update(String sql, Object... parameters) { // 마지막 파라미터로는 가변 (..) 변수를 넣을 수 있다.
		return updateWithStatementStrategy(new StatementStrategy() {
			
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
	public int updateWithStatementStrategy(StatementStrategy statementStrategy) throws RuntimeException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		// 기존 dataSource.getConnection() 과정을 위임. 생략함.
		// 아님 다시 dataSource 주입을 통해 getConnection 사용
		try {
			conn = DataSourceUtils.getConnection(dataSource);
			pstmt = statementStrategy.makeStatement(conn);
			// 기존 pstmt.setString, setLong 등을 하나의 Object[] parameters로 받아서 for문으로 매핑시켜줬다. 
			/* 이 바인딩 코드는 ~ 필요에 의해 위로 배치됨
			for (int i = 0; i < parameters.length; i++) {
				pstmt.setObject(i + 1, parameters[i]);
			}
			*/
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {				
					DataSourceUtils.releaseConnection(conn, dataSource);
				}
			} catch (SQLException ignore){
				
			}
		}

	}

}
