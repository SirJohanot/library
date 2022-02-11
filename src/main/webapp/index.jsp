<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html>
    <head>
        <title>Log In | Library</title>
        <link rel="stylesheet" href="static/styles/style.css"/>
    </head>
    <body>
        <form class="login-form" method="post" action="controller?command=login">
           <!---<label class="login-form-input" for="login">login</label>--->
            <input type="text" class="login-form-input" style="color:white;" name="login" placeholder="Login"/>
            <!---<label class="login-form-input" for="password">password</label>--->
            <input type="password" class="login-form-input" style="color:white;" name="password" placeholder="Password"/>
            <input class="login-form-submit" style="margin-bottom:0px;" type="submit"/>
            <div class="error-message">${errorMessage}</div>
        </form>
    </body>
</html>
