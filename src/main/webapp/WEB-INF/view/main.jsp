<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html>
    <head>
        <title>Main | Library</title>
        <link rel="stylesheet" href="static/styles/style.css"/>
        <meta name="viewport" content="width=device-width">
    </head>
    <body>
        <header>
            <div class="container">
                <img src="static/resources/white_book_symbol.png">
                <h1>Library<h1>
                <h1 class="logout-button">
                    <form action="controller?command=log-out">
                        <button type="submit">
                            <img src="static/resources/logout_symbol.png">
                        </button>
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