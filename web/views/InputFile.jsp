
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Input file</title>
        <link href="<c:url value='/resources/css/bar.css' />" rel="stylesheet" type="text/css"/>
        <link href="<c:url value='/resources/css/inputFile.css' />" rel="stylesheet" type="text/css"/>
        <style>
            .formMp3{
                margin-top:5rem	;
            }
        </style>
    </head>
    <body>
        <%@include file="TopBar.jsp" %>
        <main class="">
            <div class="formMp3">
                <h3>${sql}</h3>
                <form action="<%=request.getContextPath()%>" method="post" enctype="multipart/form-data">
                    <p> ${output}</p>
                    <label for="mp3File">Choose Mp3 file: </label>

                    <% if(request.getAttribute("filePath") != null){%>

                    <input type="text" value="${filePath}" name="nameFile" readonly/>
                    <%}%>	
                    ${status2}

                    <br/>
                    <input type="file" accept=".mp3" id="mp3File" name="mp3File"  multiple>

                    <input type="submit" name="loadData" value="Load data">

                    <br/>
                    <%
//				String title, artist, album, genre;	
//				Date rdate;
//				String duration;
//				 if(request.getAttribute("filePath") != null){
//				HashMap<String, String> dataMaps= (HashMap<String, String>) request.getAttribute("dataMaps");
//				title = dataMaps.get("dc:title");
//				artist = dataMaps.get("xmpDM:artist");
//				album = dataMaps.get("xmpDM:album");
//				genre = dataMaps.get("xmpDM:genre");
//				}
                    %>
                    <table>
                        <tr>
                            <td>title</td>
                            <td><input type="text" name="title" value='${title_d}'/></td>
                        </tr>
                        <tr>
                            <td>artist</td>
                            <td><input type="text" name="artist" value="${artist_d}"/></td>
                        </tr>
                        <tr>
                            <td>album</td>
                            <td><input type="text" name="album" value="${album_d}"/></td>

                        </tr>
                        <tr>
                            <td>genre</td>
                            <td><input type="text" name="genre" value="${genre_d}"/></td>
                        </tr>
                        <tr>
                            <td>Release Date</td>
                            <td><input type="Date" name="rdate" value=""/></td>
                        </tr>
                        <tr>
                            <td>duration: </td>
                            <td></td>
                        </tr>

                    </table>
                    <input type="submit" name="upload" value="Upload MP3">
<!--                </form>
                <form action="<%=request.getContextPath()%>/CancleFile">
                    <input type="text" value="${filePath}" name="nameFile" readonly/>
                    <input type="submit" name="cancle" value="Cancle">
                </form>-->
</form>
                <form action="<%=request.getContextPath()%>/Home"> <!-- Updated action to redirect to home -->
                    <input type="submit" name="cancel" value="Cancel">
                </form>
            </div>
        </main>

        <%@include file="musicBar.jsp" %>
    </body>
</html>
