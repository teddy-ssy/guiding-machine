package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FavoriteDao;
import dao.impl.FavoriteDaoImpl;

public class DesMark extends HttpServlet {
	public DesMark() {
		super();
	}

	public void destory() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		FavoriteDao dao = new FavoriteDaoImpl();
		String ex_id = request.getParameter("ex_id");
		dao.des_mark(Integer.parseInt(ex_id));
		out.print("cancle ops");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {

	}
}
