package controllers.Admin;

import DAL.LoginDAO;
import java.io.IOException;
import java.util.List;
import models.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditAcc extends HttpServlet {

    private LoginDAO loginDAO;

    @Override
    public void init() {
        loginDAO = new LoginDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("type") != null) {
            int type = Integer.parseInt(request.getParameter("type"));
            int uId = Integer.parseInt(request.getParameter("uId"));
            List<Account> listA = loginDAO.getListAcc();
            Account accountToEdit = null;
            for (Account account : listA) {
                if (account.getId() == uId) {
                    accountToEdit = account;
                    break;
                }
            }
            request.setAttribute("a1", accountToEdit);
            String[] roles = {"user", "artist"};
            request.setAttribute("roles", roles);
            if (type == 1) { // update
                request.getRequestDispatcher("/views/editAcc.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("uid"));
        String name = request.getParameter("name");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        int role = Integer.parseInt(request.getParameter("role"));

        List<Account> listAcc = loginDAO.getListAcc();
        Account accountToUpdate = null;

        for (Account account : listAcc) {
            if (account.getId() == id) {
                accountToUpdate = account;
                break;
            }
        }

        if (accountToUpdate != null) {
            accountToUpdate.setUseName(name);
            accountToUpdate.setFullName(fullName);
            accountToUpdate.setEmail(email);
            accountToUpdate.setRole(role);

            loginDAO.updateAccount3(accountToUpdate);
        }

        // Redirect to admin/managerAccount after saving
        response.sendRedirect(request.getContextPath() + "/Admin/managerAccount");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
