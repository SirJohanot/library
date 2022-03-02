<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
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
<fmt:message key="general.cancel" var="cancel"/>
<fmt:message key="general.add" var="add"/>
<fmt:message key="general.edit" var="edit"/>

<fmt:message key="navigation.books" var="books"/>
<fmt:message key="navigation.addABook" var="addABook"/>
<fmt:message key="navigation.users" var="users"/>
<fmt:message key="navigation.orders" var="orders"/>
<fmt:message key="navigation.myOrders" var="myOrders"/>

<fmt:message key="books.bookTitle" var="bookTitle"/>
<fmt:message key="books.authors" var="authors"/>
<fmt:message key="books.genre" var="genre"/>
<fmt:message key="books.publisher" var="publisher"/>
<fmt:message key="books.publishmentYear" var="publishmentYear"/>
<fmt:message key="books.inStock" var="inStock"/>
<fmt:message key="books.delete" var="delete"/>
<fmt:message key="books.orderToReadingHall" var="orderToReadingHall"/>
<fmt:message key="books.orderOnSubscription" var="orderOnSubscription"/>

<html>
<head>
    <title>${addABook} | ${appName}</title>
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
        <form id="bookChanges" class="round-bordered-subject block-container" method="post"
              action="controller?command=saveBook&userId=${sessionScope.user.id}">
            <label for="title">${bookTitle}:</label>
            <input id="title" name="title" type="text" required="required"/>
            <label for="authors">${authors}:</label>
            <input id="authors" name="authors" type="text" required="required"/>
            <label for="genre">${genre}:</label>
            <input id="genre" name="genre" type="text" required="required"/>
            <label for="publisher">${publisher}:</label>
            <input id="publisher" name="publisher" type="text" required="required"/>
            <label for="publishmentYear">${publishmentYear}:</label>
            <input id="publishmentYear" name="publishmentYear" type="number" min="1000" max="2022" step="1"
                   required="required"/>
            <label for="amount">${inStock}:</label>
            <input id="amount" name="amount" type="number" min="0" step="1" required="required"/>
        </form>
        <form method="post" action="controller?command=booksPage" class="buttons-container">
            <button type="submit" class="red">${cancel}</button>
            <button type="submit" form="bookChanges" class="green">${add}</button>
        </form>
    </div>
</section>
</body>
</html>