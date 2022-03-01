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
<fmt:message key="authorisation.loginLocale" var="login"/>
<fmt:message key="users.name" var="name"/>
<fmt:message key="users.surname" var="surname"/>
<fmt:message key="users.role" var="role"/>
<fmt:message key="users.blocked" var="blocked"/>
<fmt:message key="books.bookTitle" var="bookTitle"/>
<fmt:message key="books.authors" var="authors"/>
<fmt:message key="books.genre" var="genre"/>
<fmt:message key="books.publisher" var="publisher"/>
<fmt:message key="books.publishmentYear" var="publishmentYear"/>
<fmt:message key="books.inStock" var="inStock"/>
<fmt:message key="orders.startDate" var="startDate"/>
<fmt:message key="orders.endDate" var="endDate"/>
<fmt:message key="orders.returnDate" var="returnDate"/>
<fmt:message key="orders.rentalType" var="rentalType"/>
<fmt:message key="orders.rentalState" var="rentalState"/>
<fmt:message key="navigation.books" var="books"/>
<fmt:message key="navigation.addABook" var="addABook"/>
<fmt:message key="navigation.users" var="users"/>
<fmt:message key="navigation.orders" var="orders"/>
<fmt:message key="navigation.myOrders" var="myOrders"/>

<html>
<head>
    <title>${requestScope.bookOrder.user.login} ${requestScope.bookOrder.book.title} | ${appName}</title>
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
            <h1>${startDate}: ${requestScope.bookOrder.user.login}</h1>
            <p>${endDate}: ${requestScope.bookOrder.user.name}</p>
            <p>${returnDate}: ${requestScope.bookOrder.user.surname}</p>
            <p>${rentalType}: ${requestScope.bookOrder.user.role}</p>
            <p>${rentalState}: ${requestScope.bookOrder.user.blocked}</p>
        </div>
        <div class="round-bordered-subject block-container">
            <h1>${login}: ${requestScope.bookOrder.user.login}</h1>
            <p>${name}: ${requestScope.bookOrder.user.name}</p>
            <p>${surname}: ${requestScope.bookOrder.user.surname}</p>
            <p>${role}: ${requestScope.bookOrder.user.role}</p>
            <p>${blocked}: ${requestScope.bookOrder.user.blocked}</p>
        </div>
        <div class="round-bordered-subject block-container">
            <h1>${bookTitle}: ${requestScope.bookOrder.book.title}</h1>
            <p>${authors}:
                <c:forEach items="${requestScope.bookOrder.book.authorList}" var="author" varStatus="loop">
                    ${author.name}
                <c:if test="${!loop.last}">,</c:if>
                </c:forEach>
            <p>${genre}: ${requestScope.bookOrder.book.genre.name}</p>
            <p>${publisher}: ${requestScope.bookOrder.book.publisher.name}</p>
            <p>${publishmentYear}: ${requestScope.bookOrder.book.publishmentYear}</p>
            <c:if test="${sessionScope.user.role != 'READER'}">
                <p>${inStock}: ${requestScope.bookOrder.book.amount}</p>
            </c:if>
        </div>
        <form class="buttons-container" method="post"
              action="controller?bookId=${requestScope.bookOrder.book.id}">
            <c:choose>
                <c:when test="${sessionScope.user.role == 'ADMIN'}">
                    <button type="submit" name="command" value="editBookPage">${edit}</button>
                    <button type="submit" name="command" value="deleteBook" class="red">${delete}</button>
                </c:when>
                <c:when test="${sessionScope.user.role == 'READER'}">
                    <button type="submit" name="command"
                            value="orderToReadingHallPage">${orderToReadingHall}</button>
                    <div>
                        <h1>${orderOnSubscription}:</h1>
                        <form class="buttons-container" method="post"
                              action="controller?command=orderOnSubscriptionPage&bookId=${requestScope.bookOrder.book.id}">
                            <button type="submit" name="days" value="7">7 ${days}</button>
                            <button type="submit" name="days" value="14">14 ${days}</button>
                            <button type="submit" name="days" value="21">21 ${days}</button>
                        </form>
                    </div>
                </c:when>
            </c:choose>
        </form>
    </div>
</section>
</body>
</html>