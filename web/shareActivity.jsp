<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>分享活动</title>
  <style>
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
      color: #333333;
      font-size: 24px;
    }
    form {
      display: flex;
      flex-direction: column;
    }
    input[type="text"] {
      width: 100%;
      padding: 10px;
      margin: 10px 0 20px;
      border: 1px solid #cccccc;
      border-radius: 4px;
      box-sizing: border-box;
    }
    input[type="submit"] {
      width: 100%;
      padding: 10px;
      background-color: #4CAF50;
      border: none;
      border-radius: 4px;
      color: white;
      font-size: 16px;
      cursor: pointer;
      transition: background-color 0.3s;
    }
    input[type="submit"]:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>分享活动</h2>
  <form action="ShareActivityServlet" method="post">
    <input type="text" name="activityId" placeholder="输入活动 ID" required>
    <input type="submit" value="分享">
  </form>
</div>
</body>
</html>
