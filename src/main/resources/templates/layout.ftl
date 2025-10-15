<#ftl output_format="HTML">

<#macro content>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>${title!"Блог"}</title>
        <style>
            body {
                font-family: system-ui, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f8f9fa;
                color: #222;
            }

            header, footer {
                background: #343a40;
                color: white;
                padding: 1rem 2rem;
            }

            header a, footer a {
                color: #fff;
                text-decoration: none;
            }

            main {
                max-width: 800px;
                margin: 2rem auto;
                background: white;
                padding: 2rem;
                border-radius: 8px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            }

            h1, h2, h3 {
                color: #333;
            }

            a {
                color: #5c68e8;
                text-decoration: none;
            }

            a:hover {
                text-decoration: underline;
            }

            .meta {
                color: #777;
                font-size: 0.9rem;
                margin-bottom: 0.5rem;
            }

            .post-card {
                margin-bottom: 2rem;
                border-bottom: 1px solid #ddd;
                padding-bottom: 1rem;
            }
        </style>
    </head>
    <body>
        <header>
            <h1><a href="/">Мой блог</a></h1>
        </header>

        <main>
            <#nested>
        </main>

        <footer>
            <p>© ${.now?string("yyyy")} Блог на Spring Boot + FreeMarker</p>
        </footer>
    </body>
</html>
</#macro>
