@import gg.jte.Content

@param String title
@param Content content

<!DOCTYPE html>
<html lang="en" data-theme="emerald">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://unpkg.com/htmx.org@2.0.3"></script>
    <link rel="icon" type="image/png" href="/imgs/favicon_0101.png">
    <link rel="stylesheet" href="https://unpkg.com/@material-tailwind/html@latest/styles/material-tailwind.css" />

    <link href="/css/styles.css" rel="stylesheet" type="text/css" />
    <link
            href="https://fonts.googleapis.com/icon?family=Material+Icons"
            rel="stylesheet"
    />
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"
            integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w=="
            crossorigin="anonymous"
    />
    <title>${title}</title>
</head>
<body class="font-sans">
    ${content}
    <script src="https://unpkg.com/@material-tailwind/html@latest/scripts/ripple.js"></script>
</body>
</html>