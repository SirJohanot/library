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
<fmt:message key="mainPage.title" var="title"/>
<fmt:message key="mainPage.greetingsMessage" var="greetingsMessage"/>
<fmt:message key="navigation.books" var="books"/>
<fmt:message key="navigation.addABook" var="addABook"/>
<fmt:message key="navigation.users" var="users"/>
<fmt:message key="navigation.orders" var="orders"/>
<fmt:message key="navigation.myOrders" var="myOrders"/>

<html>
<head>
    <title>${title}</title>
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
        <form method="get" action="controller?">
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
    <div class="container round-bordered-subject main-page-message">
        <h1>${greetingsMessage}, ${sessionScope.user.login}</h1>
    </div>
</section>
</body>
</html>