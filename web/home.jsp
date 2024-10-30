<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <style>
        @font-face {
            font-family: "xiangjiaolaiwan";
            src: url("xiangjiaolaiwan.ttf");
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            font-family: "xiangjiaolaiwan", serif;
            font-weight: lighter;
            color: #333333;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin: 10px 0;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
            font-size: 16px;
        }
        a:hover {
            text-decoration: underline;
        }
        p {
            margin-top: 20px;
        }
        .logout {
            display: block;
            margin-top: 20px;
            padding: 10px;
            background-color: #f44336;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .logout:hover {
            background-color: #e53935;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>欢迎, ${username}</h2>
    <ul>
        <li><a href="AddActivityServlet">添加运动活动</a></li>
        <li><a href="deleteActivity.jsp">删除运动活动</a></li>
        <li><a href="searchActivity.jsp">搜索运动活动</a></li>
        <li><a href="totalDuration.jsp">计算总运动时间</a></li>
        <li><a href="leaderboard.jsp">排行榜</a></li>
        <li><a href="setProfile.jsp">设置个人资料</a></li>
        <li><a href="editProfile.jsp">编辑个人资料</a></li>
        <li><a href="editAccount.jsp">编辑账户</a></li>
        <li><a href="shareActivity.jsp">分享活动</a></li>

    </ul>
    <a href="LogoutServlet" class="logout">登出</a>
</div>
</body>
</html>

