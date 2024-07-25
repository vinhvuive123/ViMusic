<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manager Account</title>
        <link rel="icon" href="<c:url value='/resources/img/logo.ico' />">
        <link href="<c:url value='/resources/css/managerAcc.css' />" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Archivo+Narrow:400,700"/>

    </head>
    <body>
        <%@include file="TopBar.jsp" %>
        <!--<div class="header">Account manager</div>-->				
        <main>
            <div class="container">
                <div class="table-container ">
                    <div class="mb-2">
                        <h2 class="">Account manager</h2>
                        <small class="text-secondary"
                               >View all form tasks assigned to your group.</small
                        >
                    </div>
                    <table id="mytable" class="table align-middle mb-0 bg-white">
                        <thead class="bg-light">
                            <tr class="header-row">
                                <th>ID #</th>
                                <th>userName</th>
                                <th>fullname</th>
                                <th>email</th>
                                <th>dob</th>
                                <th>role</th>
                                <th>update</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listA}" var="a">
                                <tr>
                                    <td>${a.id}</td>
                                    <td>${a.useName}</td>
                                    <td>${a.fullName}</td>
                                    <td>${a.email}</td>
                                    <td>${a.dob}</td>
                                    <td>${roles[a.role]}</td>
                                    <td><a href="<%=request.getContextPath()%>/Admin/EditAcc?type=1&uId=${a.id}">update</a></td>
                                    <!--<td onclick=""><a href="/PRJ301_SE1810_SP24_2/Admin/managerAccount?type=2&uId=${a.id}">delete</a></td>-->
                                    <!--<td ><button onclick="deleteAcc(${a.id})">delete</button></td>-->
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

            </div>
            <!--			<div class="updateInfo">
                                            <form action="managerAccount" method="POST">
            
                                                    <table>
                                                            <tr><td>Id:</td><td>${acc1.id}</td></tr>
                                                            <tr><td>userName:</td></tr>
                                                            <tr><td>password:</td></tr>
                                                            <tr><td>Full Name:</td></tr>
                                                            <tr><td>email:</td></tr>
                                                            <tr>
                                                                    <td>role:</td>
                                                                    <td>
                                                                            <select name="role">
            <c:forEach items="${roles}" var="r" varStatus="check">
                    <option value="${r[check.index]}">${r}</option>
            </c:forEach>

    </select>
</td>
</tr>
</table>
</form>
</div>-->

        </main>
    </body>
</html>
