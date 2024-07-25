/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import common.EnCodingUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Account;

/**
 *
 * @author admin
 */

public class LoginDAO {

    private Connection con;
    private String status;

    public LoginDAO() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Account getLogin(String user, String pass) {
        String ePass = EnCodingUtils.encoding(pass);
        String sql = "select * from Accounts_HE173311 where UserName=? and Password=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, ePass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account a = new Account(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password"), rs.getString("FullName"), rs.getString("Email"), rs.getString("Img"), rs.getBoolean("Male"), rs.getDate("Dob"), rs.getInt("Role"));
                return a;
            } else {
                status = "Erorr enter account";
            }
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();

        }
        return null;
    }

    public void updateAccount3(Account a) {
        String sql = "UPDATE [dbo].[Accounts_HE173311] SET [UserName]=?, [FullName] =? , [Email] =? ,[Male] =? ,[Dob] =? , [Role] =? WHERE Id=?";
        java.sql.Date sqlDate=null;
        if(a.getDob() != null){
             sqlDate = new java.sql.Date(a.getDob().getTime());
        }
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, a.getUseName());
            ps.setString(2, a.getFullName());
            ps.setString(3, a.getEmail());
            ps.setBoolean(4, a.isGender());
            ps.setDate(5, sqlDate);
            ps.setInt(6, a.getRole());
            ps.setInt(7, a.getId());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();

        }
    }

    public Account getLogin2(String user, String pass) {
        String sql = "select * from Accounts_HE173311 where UserName=? and Password=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account a = new Account(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password"), rs.getString("FullName"), rs.getString("Email"), rs.getString("Img"), rs.getBoolean("Male"), rs.getDate("Dob"), rs.getInt("Role"));
                return a;
            } else {
                status = "Erorr enter account";
            }
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();

        }
        return null;
    }

    public List<Account> getListAcc() {
        List<Account> listA = new ArrayList<>();
        String sql = "select * from Accounts_HE173311 order by Role";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password"), rs.getString("FullName"), rs.getString("Email"), rs.getString("Img"), rs.getBoolean("Male"), rs.getDate("Dob"), rs.getInt("Role"));
                listA.add(a);
            }
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();
        }
        return listA;
    }

    public boolean checkAccount(String user) {
        String sql = "select UserName from Accounts_HE173311 where UserName=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                return true;
            }

        } catch (Exception e) {
            status = "Erorr connection at checkAccount  " + e.getMessage();
        }
        return false;
    }


    /*
	
INSERT INTO [dbo].[Accounts_HE173311]([UserName],[Password],[FullName],[Email],[Img],[Male],[Dob],[Role])
VALUES('','','','',NULL, 0,NULL, 1)
     */
    public void addAccount(String user, String pass, String email) {
        String ePass = EnCodingUtils.encoding(pass);
        String sql = "INSERT INTO [dbo].[Accounts_HE173311]([UserName],[Password],[FullName],[Email],[Img],[Male],[Dob],[Role]) VALUES(?,?,?,?,NULL, 0,NULL, 1)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, ePass);
            ps.setString(3, user);
            ps.setString(4, email);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();

        }
    }
//	public void updateAccount(){
//	}

    public void updateAccount(String fullname, String email, boolean gender, Date date, String userName) {
        String sql = "UPDATE [dbo].[Accounts_HE173311] SET [FullName] =? , [Email] =? ,[Male] =? ,[Dob] =? WHERE UserName =?";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fullname);
            ps.setString(2, email);
            ps.setBoolean(3, gender);
            ps.setDate(4, sqlDate);
            ps.setString(5, userName);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();

        }
    }

    public void updateAccount2(String fullname, String email, boolean gender, Date date, String userName) {
        String sql = "UPDATE [dbo].[Accounts_HE173311] SET [FullName] =? , [Email] =? ,[Male] =? ,[Dob] =? WHERE UserName =?";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fullname);
            ps.setString(2, email);
            ps.setBoolean(3, gender);
            ps.setDate(4, sqlDate);
            ps.setString(5, userName);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();

        }
    }

    public void deleteAcc(String Id) {

        String sql = "DELETE FROM [dbo].[Accounts_HE173311] WHERE Id =?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Id);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();
        }
    }

    public void updatePass(String nPass, String uname) {

        String ePass = EnCodingUtils.encoding(nPass);

        String sql = "UPDATE [dbo].[Accounts_HE173311] SET Password=? WHERE UserName =?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ePass);
            ps.setString(2, uname);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();
        }
    }

    public Account getAccountfromEmails(String user, String email) {
        String sql = "select * from Accounts_HE173311 where UserName=? and Email=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account a = new Account(rs.getInt("Id"), rs.getString("UserName"), rs.getString("Password"), rs.getString("FullName"), rs.getString("Email"), rs.getString("Img"), rs.getBoolean("Male"), rs.getDate("Dob"), rs.getInt("Role"));
                return a;
            } else {
                status = "Erorr enter account";
            }
        } catch (Exception e) {
            status = "Erorr connection " + e.getMessage();

        }
        return null;
    }
}
