<head>
    <link rel="stylesheet" type="text/css" href="/layout.css">
    <link rel="stylesheet" type="text/css" href="/article.css">
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

<script>
    window.onload = () => {
        let btn = document.getElementById("submit");
        btn.addEventListener("click", _ => {
            let textarea = document.getElementById("content");

            const data = {
                content: textarea.value
            }

            fetch("/article", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            }).then((response) => {
                response.text().then(data => {
                    if(data !== "FAILED") {
                        alert(data)
                    }
                })
            }).catch(e => {
                alert(e)
            });
        })
    }
</script>