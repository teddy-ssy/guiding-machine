package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import util.PostgreSQLutil;
import dao.DetialDao;

public class DetialDaoImpl implements DetialDao {

	@Override
	public String getMusic_addr(int ex_id) {
		String sql = "select music_addr " + "from museum_exhibit "
				+ "where ex_id = ?";
		PostgreSQLutil util = new PostgreSQLutil();
		Connection conn = util.openConnection();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ex_id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				String str = rs.getString(1);
				return str;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			util.closeConn(conn);
		}
		return null;
	}

	@Override
	public String getText(int ex_id) {
		String sql = "select text "+"from museum_exhibit "+"where ex_id = ?";
		PostgreSQLutil util = new PostgreSQLutil();
		Connection conn =util.openConnection();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ex_id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				String text = rs.getString(1);
				return text;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			util.closeConn(conn);
		}
		return null;
	}
	
}
