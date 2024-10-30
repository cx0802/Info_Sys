<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>管理员首页</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f0f0f0;
      display: flex;
      flex-direction: column;
      align-items: center;
      height: 100vh;
      margin: 0;
    }
    .container {
      background-color: #ffffff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      width: 80%;
      max-width: 400px;
      margin-top: 20px;
    }
    h2 {
      margin-bottom: 20px;
      color: #333333;
      text-align: center;
      font-size: 24px;
    }
    .actions {
      margin-top: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    .actions a {
      margin: 10px 0;
      color: #4CAF50;
      text-decoration: none;
      font-size: 16px;
      display: block;
      text-align: center;
      width: 100%;
      padding: 10px;
      background-color: #f9f9f9;
      border-radius: 4px;
    }
    .actions a:hover {
      text-decoration: underline;
      background-color: #e9e9e9;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>管理员首页</h2>
  <div class="actions">
    <a href="ViewAllUsersServlet">查看所有用户</a>
    <a href="ViewAllActivitiesServlet">查看所有活动</a>
    <a href="setWeather.jsp">设置天气信息</a>
    <a href="setCalories.jsp">设置燃烧卡路里</a>
    <a href="index.jsp">返回首页</a>
  </div>
</div>
</body>
</html>
