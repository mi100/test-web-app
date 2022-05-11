<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contacts</title>
    </head>
    <body>

        <table>
            <thead>
                <tr>
                    <th><a href="/test-web-app/contact/create">➕</a>️</th>
                    <th>Avatar</th>
                    <th>Name</th>
                    <th>Phone Number</th>
                    <th>Email</th>
                    <th>Gender</th>
                    <th>MaritalStatus</th>
                    <th>SocialNetwork</th>
                    <th colspan="2"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="contact" items="${contacts}">
                    <tr>
                        <td></td>
                        <td><img src="data:image/png;base64,${contact.avatarEncoded}" width="70px"></td>
                        <td>${contact.firstName} ${contact.lastName}</td>
                        <td>${contact.phone}</td>
                        <td>${contact.email}</td>
                        <td>${contact.gender}</td>
                        <td>${contact.maritalStatus}</td>
                        <td>
                        <c:forEach var="socialNetwork" items="${contact.socialNetworks}" varStatus="loop">
                            ${socialNetwork}
                            <c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                        </td>
                        <td><a href="/test-web-app/contact/edit?id=${contact.id}">🖊</a>️</td>
                        <td class="delete-icon" data-id="${contact.id}">❌</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    <script>
        document.addEventListener('DOMContentLoaded', function(){
            document.querySelectorAll('.delete-icon').forEach(el => el.onclick = function(e){
                var el = e.target;
                var id = el.getAttribute("data-id");
                fetch('/test-web-app/contact?id=' + id, {
                    'method': 'delete'
                }).then(r => location.reload());
            });
        });
    </script>
    </body>
</html>
