<link rel="stylesheet" type="text/css" href="/static/copyright.css">

<div id="main">
    <h1>500 Internal Server Error</h1>
    <p>内部服务出错！</p>
    <p id="content-text">>>> ${className}</p>
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
        padding: 50px;
    }

    h1 {
        font-weight: normal;
    }

    #content-text {
        margin-bottom: 150px;
        font-size: 14px;
        color: #404040;
    }
</style>