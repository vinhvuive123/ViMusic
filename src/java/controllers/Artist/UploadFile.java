package controllers.Artist;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;
import common.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import models.Account;
import org.apache.tika.metadata.Metadata;


@WebServlet("/UploadFileServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
	maxFileSize = 1024 * 1024 * 10, // 10 MB
	maxRequestSize = 1024 * 1024 * 50) // 50 MB
public class UploadFile extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try ( PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet UploadFile</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet UploadFile at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		HttpSession ses = request.getSession();
		Account a = (Account) ses.getAttribute("user_ses");
		if (a!=null && a.getRole()==2) {
			if (request.getParameter("cancle") != null) {
				request.getRequestDispatcher("/views/InputFile.jsp").forward(request, response);
			}
			request.getRequestDispatcher("/views/InputFile.jsp").forward(request, response);

		}else{
			request.getRequestDispatcher("/views/forbiden.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// sourcepath
		String sourPath = "D:\\HT\\java\\Assignment_HE173311\\web\\resources\\songs\\";
		// get file from jsp
		Part filePart = request.getPart("mp3File");
		// get name file upload
		String fileName = filePart.getSubmittedFileName();
		// writeFile and get annoument 
		String status = FileUtils.writeFile(filePart, sourPath);
		// load data
		if (request.getParameter("loadData") != null) {
			status = "LoadData " + status;
			Metadata dataSong = Mp3Utils.getMetadata(sourPath + fileName);
			HashMap<String, String> dataMaps = Mp3Utils.info(dataSong);

			String title, artist, album, genre;
			title = dataMaps.get("dc:title");
			artist = dataMaps.get("xmpDM:artist");
			album = dataMaps.get("xmpDM:album");
			genre = dataMaps.get("xmpDM:genre");
			request.setAttribute("title_d", title);
			request.setAttribute("artist_d", artist);
			request.setAttribute("album_d", album);
			request.setAttribute("genre_d", genre);
		}
		
		request.setAttribute("filePath", fileName);
		request.setAttribute("output", status);
		doGet(request, response);
	}
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//		throws ServletException, IOException {
//		// sourcepath
//		String sourPath = "D:\\Workspace\\FU_Fall2023\\PRJ301\\practice\\PRJ301_SE1761_FA23_2\\web\\resources\\songs\\DawnFM\\";
//		// get file from jsp
//		Part filePart = request.getPart("mp3File");
//		// get name file upload
//		String fileName = filePart.getSubmittedFileName();
//		// writeFile and get annoument 
//		String status = FileUtils.writeFile(filePart, sourPath);
//		// load data
//		String sql = "";
//		float duration;
//		if (request.getParameter("loadData") != null) {
//			status = "LoadData " + status;
//			Metadata dataSong = Mp3Utils.getMetadata(sourPath + fileName);
//			HashMap<String, String> dataMaps = Mp3Utils.info(dataSong);
//
//			String title, artist, album, genre;
//			title = dataMaps.get("dc:title");
//
//			artist = dataMaps.get("xmpDM:artist");
//			album = dataMaps.get("xmpDM:album");
//			genre = dataMaps.get("xmpDM:genre");
//			duration = Float.parseFloat(dataMaps.get("xmpDM:duration"));
//			String date;
//			request.setAttribute("title_d", title);
//			request.setAttribute("artist_d", artist);
//			request.setAttribute("album_d", album);
//			request.setAttribute("genre_d", genre);
//			int like = 0;
//			int listen = 0;
//			String description = "";
//			int Genre_id = 3;
//			int Ablum_id = 17;
//			String source = "/PRJ301_SE1761_FA23_2/resources/songs/";
//			sql += "( '" + title + "', " + (int) duration + ", " + like + ", " + "'" + source + album + "/" + fileName + "'" + ", "
//				+ listen + ", '" + description + "', " + Genre_id + ", " + Ablum_id + "),";
//		}
//		request.setAttribute("sql", sql);
//		request.setAttribute("filePath", fileName);
//		request.setAttribute("output", status);
//		doGet(request, response);
//	}

	private String getSubmittedFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private void loadData() {

	}

	//Returns a short description of the servlet.
	//@return a String containing servlet description
	@Override
	public String getServletInfo() {
		return "Short description";
	}

}
