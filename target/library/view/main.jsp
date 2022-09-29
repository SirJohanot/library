<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<html>
    <head>
        <title>Main | Library</title>
        <link rel="stylesheet" href="view/styles/style.css"/>
        <meta name="viewport" content="width=device-width">
    </head>
    <body>
        <header>
            <div class="page-title">
                <img src="resources/white_book_symbol.png" class="app-symbol">
                <h1>Library<h1>
                <button class="logout-button">
                    <img src="resources/logout_symbol.png">
                </button>
            </div>
        </header>
        <div class="main-content">
            <nav>
                <ul>
                    <li><button>Option 1</button></li>
                    <li><button>Option 2</button></li>
                    <li><button>Option 3</button></li>
                </ul>
            </nav>
            <main>
                WELCOME, ${sessionScope.user}
            </main>
        </div>
    </body>
</html>