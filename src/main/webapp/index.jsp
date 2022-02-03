<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html>
    <head>
        <title>Log In | Library</title>
        <link rel="stylesheet" href="static/styles/style.css"/>
    </head>
    <body>
        <form class="centered login-form" method="post" action="controller?command=login">
           <!---<label class="centered login-form-row" for="login">login</label>--->
            <input class="centered login-form-row" type="text" name="login" placeholder="Login"/>
            <!---<label class="centered login-form-row" for="password">password</label>--->
            <input class="centered login-form-row" type="password" name="password" placeholder="Password"/>
            <input class="centered login-form-row" style="margin-bottom:0px;" type="submit"/>
            <div class="error-message">${errorMessage}</div>
        </form>
    </body>
</html>
