<link rel="stylesheet" type="text/css" href="/static/copyright.css">

<div id="main">
    <h1>Welcome</h1>
    <label>
        <input id="username" type="text" placeholder="用户名" />
    </label>
    <label>
        <input id="password" type="password" placeholder="密码" />
    </label>
    <input id="submit" type="button" value="登录" />
    <p id="copyright">Copyright © 2023 <a href="/">bailizhang.com</a></p>
</div>

<style>
    * {
        box-sizing: border-box;
    }

    body {
        position: relative;
    }

    #main {
        width: 600px;
        height: 400px;
        border: 1px solid #d0d0d0;
        box-shadow: 0 0 0 1px rgba(0,0,0,.05), 0 2px 4px 1px rgba(0,0,0,.09);
        margin: auto;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    h1 {
        font-size: 30px;
        font-weight: lighter;
        margin: 16px 0 30px;
    }

    input {
        width: 300px;
        height: 38px;
        padding: 0.5rem 0.75rem;
        margin-bottom: 20px;
        align-items: flex-start;
        border: none;
        outline: none;
    }

    #username, #password {
        background-color: #f5f5f5;
        color: black;
    }

    #username:hover, #password:hover {
        border: #303030 2px solid;
        background-color: #ffffff;
        color: #303030;
    }

    #submit {
        margin-bottom: 60px;
        background-color: #303030;
        color: #f0f0f0;
    }

    #submit:hover {
        border: #303030 2px solid;
        background-color: #ffffff;
        color: #303030;
        cursor: pointer;
    }

</style>

<script src="/static/stringutils.js"></script>
<script src="/static/fetch.js"></script>
<script>
    window.onload = _ => {
        const submit = document.getElementById("submit")
        submit.addEventListener("click", async _ => {
            const usernameInput = document.getElementById("username")
            const passwordInput = document.getElementById("password")

            const data = {
                username: usernameInput.value,
                password: passwordInput.value
            }

            if(isBlank(data.username) || isBlank(data.password)) {
                alert("用户名或密码不能为空")
                return
            }

            const result = await post("login", data)
            alert(JSON.stringify(result))
        })
    }
</script>