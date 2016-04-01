package com.estsoft.guestbook.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.guestbook.dao.GuestBookDao;
import com.estsoft.guestbook.vo.GuestBookVo;

@WebServlet("/gb")
public class GuestBookServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//post방식의 한글 데이터 처리
		request.setCharacterEncoding("UTF-8"); 
		
		//요청분석
		String actionName = request.getParameter("a");
		
		if ( "add".equals( actionName ))
		{
			request.setCharacterEncoding("utf-8");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String message = request.getParameter("content");
			
			GuestBookVo vo = new GuestBookVo();
			GuestBookDao dao = new GuestBookDao( new MySQLWebDBConnection() );
			
			vo.setName(name);
			vo.setPassword(password);
			vo.setMessage(message);
			
			dao.insert(vo);
			
			response.sendRedirect("gb"); //상대경로 , 절대경로 = /guestbook2/gb/
		}
		else if ( "deleteform".equals( actionName ))
		{
			request.setCharacterEncoding("utf-8"); // 한글때문에 써야함.
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);
		}
		else if ( "delete".equals( actionName ))
		{
			request.setCharacterEncoding("utf-8"); // 한글때문에 써야함.
			
			Long no = Long.parseLong(request.getParameter("no"));
			String password = request.getParameter("password");

			GuestBookDao dao = new GuestBookDao( new MySQLWebDBConnection() );

			dao.delete(no, password);
			
			response.sendRedirect("/guestbook2/gb"); //절대경로
		}
		else // default action 또는 사용자가 장난치는 경우
		{
			// default action ( list, index )
			GuestBookDao dao = new GuestBookDao( new MySQLWebDBConnection() );
			List<GuestBookVo> list = dao.getList();
			
			// 포워딩전에 JSP로 보낼 데이터를 request범위(scope)에 저장한다.
			request.setAttribute("list", list); // (저장되는 이름, 저장되는 객체) 이 이름으로 이 객체를 저장한다.
			
			//forwarding (다른말로는 -> request 확장, request dispatcher)
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);
		}
	}

}
