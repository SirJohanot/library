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
<fmt:message key="books.deleteConfirmation" var="deleteConfirmation"/>
<fmt:message key="books.order" var="order"/>

<fmt:message key="orders.rentalType" var="rentalType"/>
<fmt:message key="orders.days" var="days"/>

<html>
<head>
    <title>${requestScope.book.title} | ${appName}</title>
    <link rel="stylesheet" href="static/styles/style.css"/>
    <meta name="viewport" content="width=device-width">
    <script src="static/javaScript/daysToggling.js"></script>
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
        <c:choose>
            <c:when test="${sessionScope.user.role == 'ADMIN'}">
                <form class="buttons-container" method="post" action="controller?">
                    <input type="hidden" name="bookId" value="${requestScope.book.id}"/>
                    <button type="submit" name="command" value="editBookPage">${edit}</button>
                    <button type="submit" name="command" value="deleteBook"
                            onclick="return confirm('${deleteConfirmation}')" class="red">${delete}</button>
                </form>
            </c:when>
            <c:when test="${sessionScope.user.role == 'READER'}">
                <form method="post" class="round-bordered-subject order-options-container"
                      action="controller?command=placeOrder">
                    <input type="hidden" name="bookId" value="${requestScope.book.id}"/>
                    <p>${rentalType}:</p>
                    <input id="outOfLibrary" type="radio" name="type" value="OUT_OF_LIBRARY"
                           onchange="setDaysRadiosDisabled(0)" checked="checked"/>
                    <label for="outOfLibrary"><fmt:message key="OUT_OF_LIBRARY"/></label>
                    <input id="toReadingHall" type="radio" name="type" value="TO_READING_HALL"
                           onchange="setDaysRadiosDisabled(1)"/>
                    <label for="toReadingHall"><fmt:message key="TO_READING_HALL"/></label>
                    <p>${days}:</p>
                    <input id="7" type="radio" name="days" value="7" checked="checked"/>
                    <label for="7">7</label>
                    <input id="14" type="radio" name="days" value="14"/>
                    <label for="14">14</label>
                    <input id="21" type="radio" name="days" value="21"/>
                    <label for="21">21</label>
                    <button type="submit">${order}</button>
                </form>
            </c:when>
        </c:choose>
    </div>
</section>
</body>
</html>