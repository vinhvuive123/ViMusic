/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAL.LoginDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Account;
import common.*;

/**
 *
 * @author admin
 */
public class forgetPass extends HttpServlet {

    private LoginDAO d;

    public void init() {
        d = new LoginDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet forgetPass</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet forgetPass at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      request.getRequestDispatcher("/views/forgotPass.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String user = request.getParameter("user_r");
		String email= request.getParameter("email_r");
		init();
		if (d.getAccountfromEmails(user, email) != null) {
			Account c = d.getAccountfromEmails(user, email);
			HttpSession ses = request.getSession();
			ses.setAttribute("user_ses", c);

			String rPass= TextUtils.initRandomPass(10);
			String subject = "Reset password from Vimusic";
			String content = "<div>Hello "+c.getFullName()+",</div>"
				+ "<p>userName: "+"<strong>"+c.getUseName()+"</strong>"+"</p>"
				+ "<p>Your new password here: "+"<strong>"+rPass+"</strong>"+" </p>"
				+ "<p>please go back to website to check new password</p>";
			MailUtils.sendMail(content, c.getEmail(),subject);
			// encode
			d.updatePass(rPass, user);
			request.setAttribute("mess", "sended to " + email);
			
		} else {
			request.setAttribute("error", "Wrong username or email");
		}
		doGet(request, response);
	}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
