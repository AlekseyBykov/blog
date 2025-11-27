<#ftl output_format="HTML">

<#macro content>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title!"abykov.dev"}</title>
    <link rel="stylesheet" href="/styles.css">
</head>
<body>

<div class="app-layout">

    <aside class="sidebar">
        <#include "sidebar.ftl">
    </aside>

    <main class="main-content">
        <#nested>
    </main>

</div>

</body>
</html>

</#macro>
