package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DetialDao;
import dao.impl.DetialDaoImpl;

public class MusicServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public MusicServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		DetialDao dao = new DetialDaoImpl();
		int ex_id =Integer.parseInt(request.getParameter("ex_id")) ;
		String str = dao.getMusic_addr(ex_id);
		out.print(str);
		out.flush();
		out.close();
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		doGet(request,response);
	}
	public void init()throws ServletException{
		
	}
}
