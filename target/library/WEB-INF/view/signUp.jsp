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

<fmt:message key="authorisation.loginLocale" var="loginLocale"/>
<fmt:message key="authorisation.passwordLocale" var="passwordLocale"/>
<fmt:message key="authorisation.signInLocale" var="signInLocale"/>

<fmt:message key="userCreation.signUp" var="signUp"/>
<fmt:message key="userCreation.confirmPassword" var="confirmPassword"/>

<fmt:message key="users.name" var="name"/>
<fmt:message key="users.surname" var="surname"/>

<html>
<head>
    <title>${signUp} | ${appName}</title>
    <link rel="stylesheet" href="static/styles/style.css"/>
    <meta name="viewport" content="width=device-width">
</head>
<body>
<header>
    <div class="container">
        <img src="static/resources/white_book_symbol.png" alt="Book symbol">
        <h1>${appName}</h1>
        <h1 class="right-header-buttons">
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
    <form class="login-form round-bordered-subject" method="post" action="controller?command=signUp" autocomplete="on">
        <input type="text" name="login" placeholder="${loginLocale}" required="required"/>
        <input type="password" name="password" placeholder="${passwordLocale}" required="required"/>
        <input type="password" name="confirmedPassword" placeholder="${confirmPassword}" required="required"/>
        <input type="text" name="name" placeholder="${name}" required="required"/>
        <input type="text" name="surname" placeholder="${surname}" required="required"/>
        <input type="submit" value="${signUp}"/>
        <div class="error-message">${requestScope.errorMessage}</div>
    </form>
</section>
</body>
</html>