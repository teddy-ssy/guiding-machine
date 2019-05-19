package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.SearchDao;
import dao.impl.SearchDaoImpl;
import entity.Exhibit;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List exhibits= new ArrayList();

	public SearchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SearchDao dao = new SearchDaoImpl();
		String name = request.getParameter("name");
		name = new String(name.getBytes("ISO8859-1"),"UTF-8");
		exhibits = dao.search(name);
		int num = exhibits.size();
		out.print(Build(exhibits,num));
		out.flush();
		out.close();
	}

	private String Build(List exhibits,int num) {
		String userMsg = "";
		userMsg += "num=" + String.valueOf(num);
		for (int i = 0; i < num; i++) {
			Exhibit exhibit =(Exhibit)exhibits.get(i);
			userMsg += ";";
			userMsg += "name=" + exhibit.getname();
		}
		for (int i = 0; i < num; i++) {
			Exhibit exhibit =(Exhibit)exhibits.get(i);
			userMsg += ";";
			userMsg += "id=" + String.valueOf(exhibit.getex_id());
		}
		return userMsg;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destory() {
		super.destroy();
	}

	public void init() throws ServletException {
	}

}
