<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html>
    <head>
        <title>Log In | Library</title>
        <link rel="stylesheet" href="static/styles/style.css"/>
        <meta name="viewport" content="width=device-width">
    </head>
    <body>
        <header>
            <div class="page-title">
                <img src="static/resources/white_book_symbol.png" class="app-symbol">
                <h1>Library<h1>
            </div>
        </header>
        <main>
            <form class="login-form" method="post" action="controller?command=login">
                <input type="text" class="login-form-input" name="login" placeholder="Login"/>
                <input type="password" class="login-form-input" name="password" placeholder="Password"/>
                <input class="login-form-submit" type="submit" value="Sign in"/>
                <div class="error-message">${errorMessage}</div>
            </form>
        </main>
    </body>
</html>