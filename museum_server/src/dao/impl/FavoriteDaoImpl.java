package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.PostgreSQLutil;
import dao.FavoriteDao;

public class FavoriteDaoImpl implements FavoriteDao {
	public void mark(int id) {
		String sql = "update museum_exhibit " + "set favorites = favorites +1"
				+ "where ex_id = ?";
		PostgreSQLutil util = new PostgreSQLutil();
		Connection conn = util.openConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
	}

	public void des_mark(int id) {
		String sql = "update museum_exhibit" + "set favorites = favorites - 1"
				+ "where ex_id = ?";
		PostgreSQLutil util = new PostgreSQLutil();
		Connection conn = util.openConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConn(conn);
		}
	}

}
