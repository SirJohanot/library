<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                    <form action="controller?command=log-out" class="logout-button">
                        <button type="submit">
                            <img src="static/resources/logout_symbol.png">
                        </button>
                    </form>
                    <form action="controller?command=language-change" id="language-change">
                        <button type="button">
                            <img src="static/resources/white_globe_symbol.png">
                        </button>
                        <div class="dropdown-content">
                            <button type="submit" form="language-change" name="language" value="en">${en}</button>
                            <button type="submit" form="language-change" name="language" value="ru">${ru}</button>
                            <button type="submit" form="language-change" name="language" value="by">${by}</button>
                        </div>
                    </form>
                </h1>
            </div>
        </header>
        <section id="main-content">
            <nav>
                <ul>
                    <li><button>Option 1</button></li>
                    <li><button>Option 2</button></li>
                    <li><button>Option 3</button></li>
                </ul>
            </nav>
            <p>WELCOME, ${sessionScope.user}</p>
        </section>
    </body>
</html>