<head>
    <link rel="stylesheet" type="text/css" href="/static/layout.css">
    <link rel="stylesheet" type="text/css" href="/static/article.css">
</head>

<article>
    <h1>创建文章</h1>
    <div>
        <div>
            <label for="content">内容: </label>
            <textarea id="content"></textarea>
        </div>
        <button id="submit">提交</button>
    </div>
</article>


<script src="/static/fetch.js"></script>
<script>
    window.onload = () => {
        const btn = document.getElementById("submit");
        btn.addEventListener("click", async _ => {
            const textarea = document.getElementById("content");

            const data = {
                content: textarea.value
            }

            const result = await post("article", data);
            alert(result);
        })
    }
</script>