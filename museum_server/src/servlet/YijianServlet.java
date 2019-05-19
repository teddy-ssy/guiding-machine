package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.YijianDao;
import dao.impl.YijianDaoImpl;

public class YijianServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public YijianServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		YijianDao dao = new YijianDaoImpl();
		String yijian = request.getParameter("yijian");
		yijian= new String(yijian.getBytes("ISO8859-1"),"UTF-8");
		System.out.print(yijian);
		dao.insertYijian(yijian);
		out.print("лл");
		out.flush();
		out.close();
	}
	protected void doPost(HttpServletRequest request , HttpServletResponse response)throws IOException,ServletException{
		doGet(request, response);
	}
	public void destroy(){
		super.destroy();
	}
	public void init()throws ServletException{
		
	}
}
