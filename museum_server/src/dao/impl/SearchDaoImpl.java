package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.PostgreSQLutil;
import dao.SearchDao;
import entity.Exhibit;

public class SearchDaoImpl implements SearchDao {


	@Override
	public List search(String name) {
		String sql = "select name , ex_id "+"from museum_exhibit"+" where name like '%"+name+"%'";
		PostgreSQLutil util=new PostgreSQLutil();
		Connection conn= util.openConnection();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			List exhibits = new ArrayList();
			while(rs.next()){
				Exhibit exhibit = new Exhibit();
				exhibit.setname(rs.getString("name"));
				exhibit.setex_id(rs.getInt("ex_id"));
				exhibits.add(exhibit);
			}
			return exhibits;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			util.closeConn(conn);
		}
		return null;
	}
}
