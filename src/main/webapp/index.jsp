<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${sessionScope.locale == null}">
    <c:set var="en_US" value="${locale}" scope="session" />
</c:if>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent"/>
<fmt:message key="general.appname" var="appname" />
<fmt:message key="general.englishcode" var="en" />
<fmt:message key="general.russiancode" var="ru" />
<fmt:message key="general.belarusiancode" var="bel" />
<fmt:message key="authorisation.title" var="title" />
<fmt:message key="authorisation.loginlocale" var="loginLocale" />
<fmt:message key="authorisation.passwordlocale" var="passwordLocale" />
<fmt:message key="authorisation.signinlocale" var="signInLocale" />
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
                <h1>${appname}<h1>
                <h1 class="right-header-buttons">
                    <form method="post" action="controller?command=language-change" id="language-change">
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
        <section>
            <form class="login-form" method="post" action="controller?command=login">
                <input type="text" name="login" placeholder=${loginLocale} />
                <input type="password" name="password" placeholder=${passwordLocale} />
                <input type="submit" value=${signInLocale} />
                <div class="error-message">${errorMessage}</div>
            </form>
        </section>
    </body>
</html>