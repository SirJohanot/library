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

<fmt:message key="navigation.books" var="books"/>
<fmt:message key="navigation.addABook" var="addABook"/>
<fmt:message key="navigation.users" var="users"/>
<fmt:message key="navigation.orders" var="orders"/>
<fmt:message key="navigation.myOrders" var="myOrders"/>

<fmt:message key="books.authors" var="authors"/>
<fmt:message key="books.genre" var="genre"/>
<fmt:message key="books.publisher" var="publisher"/>
<fmt:message key="books.publishmentYear" var="publishmentYear"/>

<html>
<head>
    <title>${books} | ${appName}</title>
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
        <form method="post" action="controller?command=searchBooks" class="search-field">
            <input type="text" name="searchValue" placeholder="${search}"/>
        </form>
        <form method="post" action="controller?command=viewBook">
            <c:forEach items="${requestScope.bookList}" var="book">
                <c:if test="${sessionScope.user.role != 'READER' || book.amount > 0}">
                    <button type="submit" name="bookId" value="${book.id}"
                            class="block-container round-bordered-subject">
                        <h1>${book.title}</h1>
                        <div class="block-parameters">
                            <p>${authors}: <c:forEach items="${book.authorList}" var="author" varStatus="loop">
                                ${author.name}
                                <c:if test="${!loop.last}">,</c:if>
                            </c:forEach>
                            </p>
                            <p>${genre}: ${book.genre.name}</p>
                            <p>${publisher}: ${book.publisher.name}</p>
                            <p>${publishmentYear}: ${book.publishmentYear}</p>
                        </div>
                    </button>
                </c:if>
            </c:forEach>
        </form>
        <ctg:pagination command="searchBooks&searchValue=${requestScope.searchValue}"/>
    </div>
</section>
</body>
</html>