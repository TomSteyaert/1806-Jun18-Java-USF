package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.models.Users;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[LOG] - in LogoutServlet.doGet()");
		
		if(req.getSession(false) == null) {
			resp.sendRedirect("index.html");
		}
		
		HttpSession session = req.getSession(false);
		Users sessionUser = (Users) session.getAttribute("user");
		session.removeAttribute("user");
		session.invalidate();
		
		System.out.println("[LOG] -  Session invalidated");
		
		resp.sendRedirect("/Project1/");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[LOG] - in LogoutServlet.doGet()");
		
		if(req.getSession(false) == null) {
			resp.sendRedirect("index.html");
		}
		
		HttpSession session = req.getSession(false);
		session.invalidate();
		
		System.out.println("[LOG] -  Session invalidated");
		
		resp.sendRedirect("index.html");
	}

}
