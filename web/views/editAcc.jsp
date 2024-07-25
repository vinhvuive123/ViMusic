<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manager Account</title>
        <link rel="icon" href="<c:url value='/resources/img/logo.ico' />">
        <!--<link href="<c:url value='/resources/css/managerAcc.css' />" rel="stylesheet" type="text/css"/>-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Archivo+Narrow:400,700"/>
        <style>
            body{
                background-color: #f5f6f8 !important;
                font-family: "Archivo Narrow", sans-serif;
            }

            main{
                margin-left: 5rem;
                margin-top: 7rem;
                width: calc(100vw - 5rem);
                /*height: calc(100vh - 5rem);*/
                display: flex;
                justify-content: center;
                padding-bottom: 300px;
            }
            .tableEdit{
                width: 30vw;
            }
            tr{
                height: 30px;
            }
        </style>

    </head>
    <body>
        <%@include file="TopBar.jsp" %>
        <!--<div class="header">Account manager</div>-->				
        <main>
            <div class="">
                <form action="" method="POST">

<!--						<td>${a.id}</td>
                                                                        <td>${a.useName}</td>
                                                                        <td>${a.fullName}</td>
                                                                        <td>${a.email}</td>
                                                                        <td>${a.dob}</td>
                                                                        <td>${roles[a.role]}</td-->
                    <table class="tableEdit">
                        <tr><td>Id:</td><td>${a1.id}</td></tr>
                        <input type="hidden" name="uid" value="${a1.id}">
                        <tr><td>userName:</td><td><input type="text" name="name" value="${a1.useName}" required></td></tr>
                        <!--<tr><td>password:</td><td></td></tr>-->
                        <tr><td>Full Name:</td><td><input type="text" name="fullName" value="${a1.fullName}" required></td></tr>
                        <tr><td>email:</td><td><input type="email" name="email" value="${a1.email}" required></tr>
                        <tr>
                            <td>role:</td>
                            <td>
                                <select name="role">
                                    <c:forEach items="${roles}" var="r" varStatus="check">
                                        <option value="${check.index+1}" ${a1.role==(check.index+1)?"selected":""}>${r}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr><td> <input type="submit" value="Save"></td></tr>
                    </table>
                    <a href="managerAccount">Back to Account manager</a>
                </form>
                ${mess}
            </div>	

        </main>

    </body>
</html>