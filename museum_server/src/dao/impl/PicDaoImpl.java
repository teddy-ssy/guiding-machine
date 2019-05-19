package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.PostgreSQLutil;
import dao.PicDao;
import entity.Exhibit;

public class PicDaoImpl implements PicDao{

	@Override
	public String getpics(int e_id) {
		String sql = "select pic_addr "+"from museum_pic "+"where e_id =?";
		PostgreSQLutil util = new PostgreSQLutil();
		Connection conn = util.openConnection();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,e_id);
			ResultSet rs = pstmt.executeQuery();
			List pics = new ArrayList<>();
			while(rs.next()){
				Exhibit exhibit = new Exhibit();
				exhibit.setcover_addr(rs.getString("pic_addr"));
				pics.add(exhibit);
			}
			int num = pics.size();
			String picstr="";
			picstr +="num="+String.valueOf(num);
			for(int i=0;i<num;i++){
				Exhibit exhibit =(Exhibit)pics.get(i);
				picstr += ";";
				picstr += "pic_addr=" + exhibit.getcover_addr();
			}
			if(picstr == null){
				picstr = "wu";
			}
			
			return picstr;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			util.closeConn(conn);
		}
		return null;
	}

}
