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
<fmt:message key="general.edit" var="edit"/>
<fmt:message key="general.cancel" var="cancel"/>
<fmt:message key="general.dateFormat" var="dateFormat"/>

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

<fmt:message key="orders.rentalType" var="rentalType"/>
<fmt:message key="orders.startDate" var="startDate"/>
<fmt:message key="orders.endDate" var="endDate"/>
<fmt:message key="orders.confirmOrder" var="confirmOrder"/>

<html>
<head>
    <title>${requestScope.book.title} | ${appName}</title>
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
            <h1>${bookTitle}: ${requestScope.book.title}</h1>
            <p>${authors}:
                <c:forEach items="${requestScope.book.authorList}" var="author" varStatus="loop">
                    ${author.name}
                <c:if test="${!loop.last}">,</c:if>
                </c:forEach>
            <p>${genre}: ${requestScope.book.genre.name}</p>
            <p>${publisher}: ${requestScope.book.publisher.name}</p>
            <p>${publishmentYear}: ${requestScope.book.publishmentYear}</p>
            <c:if test="${sessionScope.user.role != 'READER'}">
                <p>${inStock}: ${requestScope.book.amount}</p>
            </c:if>
        </div>
        <div class="round-bordered-subject block-container">
            <h1>${rentalType}: ${requestScope.bookOrder.type}</h1>
            <p>${startDate}: <fmt:formatDate value="${requestScope.bookOrder.startDate}" pattern="${dateFormat}"/></p>
            <p>${endDate}: <fmt:formatDate value="${requestScope.bookOrder.endDate}" pattern="${dateFormat}"/></p>
        </div>
        <form class="buttons-container" method="post" action="controller?">
            <c:if test="${sessionScope.user.role == 'READER'}">
                <button type="submit" name="command"
                        value="booksPage" class="red">${cancel}</button>
                <input type="hidden" name="bookId" value="${requestScope.book.id}"/>
                <input type="hidden" name="startDate" value="${requestScope.bookOrder.startDate}"/>
                <input type="hidden" name="endDate" value="${requestScope.bookOrder.endDate}"/>
                <input type="hidden" name="rentalType" value="${requestScope.bookOrder.type}"/>
                <button type="submit" name="command"
                        value="order" class="green">${confirmOrder}</button>
            </c:if>
        </form>
    </div>
</section>
</body>
</html>