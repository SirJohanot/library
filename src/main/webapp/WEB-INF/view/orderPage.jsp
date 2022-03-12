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
<fmt:message key="general.dateFormat" var="dateFormat"/>

<fmt:message key="navigation.books" var="books"/>
<fmt:message key="navigation.addABook" var="addABook"/>
<fmt:message key="navigation.users" var="users"/>
<fmt:message key="navigation.orders" var="orders"/>
<fmt:message key="navigation.myOrders" var="myOrders"/>

<fmt:message key="orders.startDate" var="startDate"/>
<fmt:message key="orders.endDate" var="endDate"/>
<fmt:message key="orders.returnDate" var="returnDate"/>
<fmt:message key="orders.rentalType" var="rentalType"/>
<fmt:message key="orders.rentalState" var="rentalState"/>
<fmt:message key="orders.approveOrder" var="approveOrder"/>
<fmt:message key="orders.decline" var="declineOrder"/>
<fmt:message key="orders.collectOrder" var="collectOrder"/>
<fmt:message key="orders.returnOrder" var="returnOrder"/>

<fmt:message key="books.bookTitle" var="bookTitle"/>
<fmt:message key="books.authors" var="authors"/>
<fmt:message key="books.genre" var="genre"/>
<fmt:message key="books.publisher" var="publisher"/>
<fmt:message key="books.publishmentYear" var="publishmentYear"/>
<fmt:message key="books.inStock" var="inStock"/>

<fmt:message key="authorisation.loginLocale" var="login"/>
<fmt:message key="users.name" var="name"/>
<fmt:message key="users.surname" var="surname"/>
<fmt:message key="users.role" var="role"/>
<fmt:message key="users.blocked" var="blocked"/>

<html>
<head>
    <title>${requestScope.bookOrder.user.login} ${requestScope.bookOrder.book.title} | ${appName}</title>
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
    <ctg:navigation/>
    <div id="main-content-div">
        <div class="round-bordered-subject block-container">
            <h1>${startDate}: <fmt:formatDate value="${requestScope.bookOrder.startDate}" pattern="${dateFormat}"/></h1>
            <p>${endDate}: <fmt:formatDate value="${requestScope.bookOrder.endDate}" pattern="${dateFormat}"/></p>
            <p>${returnDate}: <fmt:formatDate value="${requestScope.bookOrder.returnDate}" pattern="${dateFormat}"/></p>
            <p>${rentalType}: ${requestScope.bookOrder.type}</p>
            <p>${rentalState}: ${requestScope.bookOrder.state}</p>
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
        <c:if test="${sessionScope.user.role != 'READER'}">
            <div class="round-bordered-subject block-container">
                <h1>${login}: ${requestScope.bookOrder.user.login}</h1>
                <p>${name}: ${requestScope.bookOrder.user.name}</p>
                <p>${surname}: ${requestScope.bookOrder.user.surname}</p>
                <p>${role}: ${requestScope.bookOrder.user.role}</p>
                <p>${blocked}: ${requestScope.bookOrder.user.blocked}</p>
            </div>
        </c:if>
        <form class="buttons-container" method="post" action="controller?">
            <input type="hidden" name="orderId" value="${requestScope.bookOrder.id}"/>
            <c:choose>
                <c:when test="${(sessionScope.user.role == 'LIBRARIAN') && (requestScope.bookOrder.state == 'ORDER_PLACED')}">
                    <button type="submit" name="command" value="declineOrder" class="red">${declineOrder}</button>
                    <button type="submit" name="command" value="approveOrder" class="green">${approveOrder}</button>
                </c:when>
                <c:when test="${(sessionScope.user.role == 'READER') && (sessionScope.user.id == requestScope.bookOrder.user.id)}">
                    <c:choose>
                        <c:when test="${requestScope.bookOrder.state == 'ORDER_APPROVED'}">
                            <button type="submit" name="command" value="collectOrder">${collectOrder}</button>
                        </c:when>
                        <c:when test="${requestScope.bookOrder.state == 'BOOK_COLLECTED'}">
                            <button type="submit" name="command" value="returnOrder">${returnOrder}</button>
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>
        </form>
    </div>
</section>
</body>
</html>