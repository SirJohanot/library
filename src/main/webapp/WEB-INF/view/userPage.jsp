<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
<fmt:message key="general.edit" var="edit"/>
<fmt:message key="authorisation.loginLocale" var="login"/>
<fmt:message key="users.name" var="name"/>
<fmt:message key="users.surname" var="surname"/>
<fmt:message key="users.role" var="role"/>
<fmt:message key="users.blocked" var="blocked"/>
<fmt:message key="users.block" var="block"/>
<fmt:message key="books.orderToReadingHall" var="orderToReadingHall"/>
<fmt:message key="books.orderOnSubscription" var="orderOnSubscription"/>
<fmt:message key="navigation.books" var="books"/>
<fmt:message key="navigation.addABook" var="addABook"/>
<fmt:message key="navigation.users" var="users"/>
<fmt:message key="navigation.orders" var="orders"/>
<fmt:message key="navigation.myOrders" var="myOrders"/>

<html>
<head>
    <title>${requestScope.user.login} | ${appName}</title>
    <link rel="stylesheet" href="static/styles/style.css"/>
    <meta name="viewport" content="width=device-width">
</head>
<body>
<header>
    <div class="container">
        <img src="static/resources/white_book_symbol.png">
        <h1>${appName}</h1>
        <h1 class="right-header-buttons">
            <form method="post" action="controller?command=signOut" class="sign-out-button">
                <button type="submit">
                    <img src="static/resources/sign_out_symbol.png">
                </button>
            </form>
            <form method="post" action="controller?command=languageChange" id="language-change">
                <button type="button">
                    <img src="static/resources/white_globe_symbol.png">
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
<section id="main-content">
    <nav>
        <form method="post" action="controller?">
            <button type="submit" name="command" value="booksPage">${books}</button>
            <c:choose>
                <c:when test="${sessionScope.user.role == 'ADMIN'}">
                    <button type="submit" name="command" value="addABookPage">${addABook}</button>
                    <button type="submit" name="command" value="usersPage">${users}</button>
                </c:when>
                <c:when test="${sessionScope.user.role == 'LIBRARIAN'}">
                    <button type="submit" name="command" value="globalOrdersPage">${orders}</button>
                </c:when>
                <c:when test="${sessionScope.user.role == 'READER'}">
                    <button type="submit" name="command" value="userOrdersPage">${myOrders}</button>
                </c:when>
            </c:choose>
        </form>
    </nav>
    <div>
        <div class="round-bordered-subject block-container">
            <h1>${login}: ${requestScope.user.login}</h1>
            <p>${name}: ${requestScope.user.name}</p>
            <p>${surname}: ${requestScope.user.surname}</p>
            <p>${role}: ${requestScope.user.role}</p>
            <p>${blocked}: ${requestScope.user.blocked}</p>
        </div>
        <form class="buttons-container" method="post"
              action="controller?userId=${sessionScope.user.id}">
            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <button type="submit" name="command" value="editUserPage">${edit}</button>
                <button type="submit" name="command" value="blockUser" class="red">${block}</button>
            </c:if>
        </form>
    </div>
</section>
</body>
</html>