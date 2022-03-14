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

<html>
<head>
    <title>${orders} | ${appName}</title>
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
        <form method="post" action="controller?command=searchOrders" class="search-field">
            <input type="text" name="searchValue" placeholder="${search}"/>
        </form>
        <form method="post" action="controller?command=viewOrder">
            <c:forEach items="${requestScope.orderList}" var="order">
                <button type="submit" name="orderId" value="${order.id}" class="block-container round-bordered-subject">
                    <h1>${order.book.title} | ${order.user.login}</h1>
                    <div class="block-parameters">
                        <p>${startDate}: <fmt:formatDate value="${order.startDate}" pattern="${dateFormat}"/></p>
                        <p>${endDate}: <fmt:formatDate value="${order.endDate}" pattern="${dateFormat}"/></p>
                        <p>${returnDate}: <fmt:formatDate value="${order.returnDate}" pattern="${dateFormat}"/></p>
                        <p>${rentalType}: ${order.type}</p>
                        <p>${rentalState}: ${order.state}</p>
                    </div>
                </button>
            </c:forEach>
        </form>
        <ctg:pagination command="searchOrders&searchValue=${requestScope.searchValue}"/>
    </div>
</section>
</body>
</html>