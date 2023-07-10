<head>
    <meta name="keywords" content="${article.keywords}">
    <link rel="stylesheet" type="text/css" href="/static/layout.css">
    <title>Bailizhang.com</title>
</head>

<div id="side-content">

</div>

<article>
    ${article.content}

    <#if isHomePage == false>
        <p id="button-wrapper"><button onclick="submit('${article.id}')">设置为首页文章</button></p>
    </#if>
</article>

<style>
    #side-content {
        position: fixed;
        top: 20%;
        left: 48px;
        border: 1px solid #d0d0d0;
        display: flex;
        flex-direction: column;
        padding: 1rem 2rem 1rem 0;
        background-color: white;
        overflow: auto;
        max-height: 50%;
    }

    #side-content a {
        color: #606060;
        font-size: 14px;
        padding: 0.5rem 2rem;
    }

    article {
        padding: 30px 0;
    }

    h1 {
        padding: 1.5rem 0 2rem;
        text-align: center;
    }

    button {
        width: 160px;
        height: 38px;
        margin: 30px auto 0;
        border: none;
        background-color: #404040;
        color: #f0f0f0;
    }

    button:hover {
        background-color: #202020;
        cursor: pointer;
    }

    #button-wrapper {
        text-align: center;
    }
</style>

<style>
    @media screen and (prefers-color-scheme: dark){
        html {
            background-color: #1b1b1b;
            color: #c6c6c6;
        }

        body, #side-content {
            background-color: #343434;
            border: none;
        }

        #side-content a {
            color: #c6c6c6;
        }
    }
</style>

<script src="/static/fetch.js"></script>
<script>
    window.onload = _ => {
        const sideContent = document.getElementById("side-content")

        const h2 = document.getElementsByTagName("h2")
        let index = 1;

        for (const item of h2) {
            const h2Title = index + ". " + item.innerText
            item.id = h2Title

            const link = document.createElement("a")
            link.text = h2Title
            link.href = "#" + h2Title

            sideContent.appendChild(link)

            index ++
        }
    }

    async function submit(id) {
        const data = {
            articleId: id
        }

        const result = await post("/websiteConfig/homeArticleId", data)
        if(!result.success) {
            alert("设置成主页文章失败")
            return
        }

        window.location = "/"
    }
</script>
