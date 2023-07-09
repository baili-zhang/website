<head>
    <link rel="stylesheet" type="text/css" href="/static/layout.css">
</head>

<article>
    <h1>创建文章</h1>
    <hr>
    <div>
        <label>
            <input id="keywords" type="text" placeholder="请输入关键字..." />
        </label>
        <div id="content" contenteditable="true"></div>
        <button id="submit">提交</button>
    </div>
</article>

<style>
    h1 {
        font-weight: normal;
        padding: 60px 0 20px;
    }

    hr {
        margin: 0 0 20px;
        border: none;
        border-bottom: #c0c0c0 1px solid;
    }

    input {
        width: 300px;
        height: 38px;
        padding: 0.5rem 0.75rem;
        margin-bottom: 20px;
        border: none;
        outline: none;
        background-color: #f5f5f5;
        color: black;
        font-size: 16px;
    }

    #content {
        width: 100%;
        height: 400px;
        outline: none;
        font-size: 16px;
        background-color: #f5f5f5;
        padding: 0.75rem 0.75rem;
        overflow: auto;
    }

    button {
        width: 160px;
        height: 38px;
        margin-top: 30px;
        border: none;
        background-color: #404040;
        color: #f0f0f0;
    }

    button:hover {
        background-color: #202020;
        cursor: pointer;
    }
</style>


<script src="/static/fetch.js"></script>
<script>
    window.onload = () => {
        const btn = document.getElementById("submit")
        btn.addEventListener("click", async _ => {
            const keywordsInput = document.getElementById("keywords")
            const contentDiv = document.getElementById("content")

            const data = {
                keywords: keywordsInput.value,
                content: contentDiv.innerText
            }

            const result = await post("article", data)
            if(!result.success) {
                alert("创建文件失败")
                return
            }

            window.location = "/article/" + result.id
        })
    }
</script>