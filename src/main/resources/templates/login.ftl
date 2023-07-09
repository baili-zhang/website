<div>
    <label>
        <input id="username" type="text" placeholder="用户名" />
    </label>
    <label>
        <input id="password" type="password" placeholder="密码" />
    </label>
    <input id="submit" type="button" value="登录" />
</div>

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