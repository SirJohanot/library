<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="customTags" prefix="ctg" %>

<c:if test="${sessionScope.locale == null}">
    <c:set var="locale" value="en_US" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pageContent"/>

<fmt:message key="general.appName" var="appName"/>
<fmt:message key="general.englishCode" var="en"/>
<fmt:message key="general.russianCode" var="ru"/>
<fmt:message key="general.belarusianCode" var="bel"/>
<fmt:message key="general.search" var="search"/>
<fmt:message key="general.cancel" var="cancel"/>
<fmt:message key="general.commitChanges" var="commitChanges"/>

<fmt:message key="navigation.books" var="books"/>
<fmt:message key="navigation.addABook" var="addABook"/>
<fmt:message key="navigation.users" var="users"/>
<fmt:message key="navigation.orders" var="orders"/>
<fmt:message key="navigation.myOrders" var="myOrders"/>

<fmt:message key="authorisation.loginLocale" var="login"/>
<fmt:message key="users.name" var="name"/>
<fmt:message key="users.surname" var="surname"/>
<fmt:message key="users.role" var="role"/>
<fmt:message key="users.blocked" var="blocked"/>
<fmt:message key="users.block" var="block"/>

<html>
<head>
    <title>${requestScope.targetUser.login} | ${appName}</title>
    <link rel="stylesheet" href="static/styles/style.css"/>
    <meta name="viewport" content="width=device-width">
</head>
<body>
<header>
    <div class="container">
        <img src="static/resources/white_book_symbol.png" alt="Book symbol">
        <h1>${appName}</h1>
        <h1 class="right-header-buttons">
            <form method="post" action="controller?command=signOut" class="sign-out-button">
                <button type="submit">
                    <img src="static/resources/sign_out_symbol.png" alt="Sign out symbol">
                </button>
            </form>
            <form method="post" action="controller?command=languageChange" id="language-change">
                <button type="button">
                    <img src="static/resources/white_globe_symbol.png" alt="Globe symbol">
                </button>
                <div class="dropdown-content">
                    <button type="submit" name="locale" value="en_US">${en}</button>
                    <button type="submit" name="locale" value="ru_RU">${ru}</button>
                    <button type="submit" name="locale" value="bel_BEL">${bel}</button>
                </div>
            </form>
        </h1>
    </div>
</header>
<ctg:navigation/>
<section id="main-content">
    <div id="main-content-centered-element">
        <form id="userChanges" class="round-bordered-subject block-container" method="post"
              action="controller?command=editUser">
            <input type="hidden" name="userId" value="${requestScope.targetUser.id}"/>
            <h1>${login}: ${requestScope.targetUser.login}</h1>
            <label for="name">${name}:</label>
            <input id="name" name="name" type="text"
                   value="${requestScope.targetUser.name}"
                   required="required"/>
            <label for="surname">${surname}:</label>
            <input id="surname" name="surname" type="text" value="${requestScope.targetUser.surname}"
                   required="required"/>
            <label for="role">${role}:</label>
            <select id="role" name="role" required="required">
                <option value="READER"
                        <c:if test="${requestScope.targetUser.role == 'READER'}">selected="selected"</c:if> >
                    <fmt:message key="READER"/>
                </option>
                <option value="LIBRARIAN"
                        <c:if test="${requestScope.targetUser.role == 'LIBRARIAN'}">selected="selected"</c:if>>
                    <fmt:message key="LIBRARIAN"/>
                </option>
            </select>
            <div class="error-message">${requestScope.errorMessage}</div>
        </form>
        <form method="post" action="controller?command=usersPage" class="buttons-container">
            <button type="submit" class="red">${cancel}</button>
            <button type="submit" form="userChanges" class="green">${commitChanges}</button>
        </form>
    </div>
</section>
</body>
</html>