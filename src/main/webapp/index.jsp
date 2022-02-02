<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html>
    <head>
        <title>Log In | Library</title>
        <link rel="stylesheet" href="static/styles/style.css"/>
    </head>
    <body>
        <form class="centered form-container" method="post" action="controller?command=login">
           <!---<label class="centered login-row" for="login">login</label>--->
            <input class="centered login-row" type="text" name="login" placeholder="Login"/>
            <!---<label class="centered login-row" for="password">password</label>--->
            <input class="centered login-row" type="password" name="password" placeholder="Password"/>
            <input class="centered login-row" type="submit"/>
        </form>
        <div class="centered" style="color:red";>${errorMessage}</div>
    </body>
</html>
