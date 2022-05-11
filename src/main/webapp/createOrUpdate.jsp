<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create</title>
    </head>
    <body>

        <form method="post" action="${context}/contact" enctype="multipart/form-data">
            <input type="hidden" name="_method" value="${method}">
            <input type="hidden" name="id" value="${contact.id}">

            <c:if test="${method == 'update'}">
                <img src="data:image/png;base64,${contact.avatarEncoded}" width="70px"><br/>
            </c:if>

            <label for="avatar">Avatar </label> <input type="file" id="avatar" name="avatar"><br/><br/>

            <label for="firstname">First Name </label><input id="firstname" name="firstname" value="${contact.firstName}"><br/><br/>

            <label for="lastname">Last Name </label><input id="lastname" name="lastname" value="${contact.lastName}"><br/><br/>

            <label for="phone">Phone Number </label><input id="phone" name="phone" value="${contact.phone}"><br/><br/>

            <label for="email">Email </label><input id="email" name="email" value="${contact.email}"><br/><br/>

            <label>Select Gender: </label>
            <input type="radio" id="male" name="gender" value="MALE" ${contact.gender == "MALE" ? 'checked="cheked"' : ''}/><label for="male">Male</label>&nbsp;
            <input type="radio" id="female" name="gender" value="FEMALE" ${contact.gender =="FEMALE" ? 'checked="cheked"' : ''}/><label for="female">Female</label>
            <br/><br/>

            <label>Select SocialNetwork: </label>
            <input type="checkbox" name="socialNetworks" id="FACEBOOK" value="FACEBOOK" ${fn:contains(contact.socialNetworks, "FACEBOOK") ? 'checked="checked"' : ''} /><label for="FACEBOOK">Facebook</label>&nbsp;
            <input type="checkbox" name="socialNetworks" id="INSTAGRAM" value="INSTAGRAM" ${fn:contains(contact.socialNetworks, "INSTAGRAM") ? 'checked="cheked"' : ''} /><label for="INSTAGRAM">Instagram</label>&nbsp;
            <input type="checkbox" name="socialNetworks" id="TELEGRAM" value="TELEGRAM" ${fn:contains(contact.socialNetworks, "TELEGRAM") ? 'checked="cheked"' : ''}/><label for="TELEGRAM">Telegram</label>&nbsp;
            <input type="checkbox" name="socialNetworks" id="VIBER" value="VIBER" ${fn:contains(contact.socialNetworks, "VIBER") ? 'checked="cheked"' : ''}/><label for="VIBER">Viber</label>&nbsp;
            <input type="checkbox" name="socialNetworks" id="TWITTER" value="TWITTER" ${fn:contains(contact.socialNetworks, "TWITTER") ? 'cheked="cheked"' : ''}/><label for="TWITTER">Twitter</label>
            <br/><br/>


            <label>MaritalStatus:</label>
            <input type="radio" name="maritalStatus" id="MARRIED" value="MARRIED" ${contact.maritalStatus == "MARRIED" ? 'checked="cheked"' : ''}/><label for="MARRIED">Married</label>&nbsp;
            <input type="radio" name="maritalStatus" id="SINGLE" value="SINGLE" ${contact.maritalStatus =="SINGLE" ? 'checked="cheked"' : ''}/><label for="SINGLE">Single</label>
            <br/><br/>


            <input type="submit" value="Save">
        </form>

    </body>
</html>