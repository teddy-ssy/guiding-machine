package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.PostgreSQLutil;
import dao.YijianDao;

public class YijianDaoImpl implements YijianDao{

	@Override
	public void insertYijian(String yijian) {
		String sql = "insert into museumn_yijian  values('"+ yijian +"');";
		PostgreSQLutil util = new PostgreSQLutil();
		Connection conn = util.openConnection();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, yijian);
			pstmt.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			util.closeConn(conn);
		}
	}

}
