package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PicDao;
import dao.impl.PicDaoImpl;

public class PicServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PicServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		PicDao dao = new PicDaoImpl();
		String ex_id = request.getParameter("ex_id");
		ex_id = new String(ex_id.getBytes("ISO8859-1"), "UTF-8");
		int e_id = Integer.parseInt(ex_id);
		String picstr = dao.getpics(e_id);
		out.print(picstr);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		super.destroy();
	}

	public void init() throws ServletException {

	}
}
