<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html>
    <head>
        <title>Log In | Library</title>
        <link rel="stylesheet" href="static/styles/style.css"/>
        <meta name="viewport" content="width=device-width">
    </head>
    <body>
        <header>
            <div class="container">
                <img src="static/resources/white_book_symbol.png">
                <h1>Library<h1>
            </div>
        </header>
        <section>
            <form class="login-form" method="post" action="controller?command=login">
                <input type="text" name="login" placeholder="Login"/>
                <input type="password" name="password" placeholder="Password"/>
                <input type="submit" value="Sign in"/>
                <div class="error-message">${errorMessage}</div>
            </form>
        </section>
    </body>
</html>