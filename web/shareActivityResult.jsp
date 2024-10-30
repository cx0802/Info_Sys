<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>分享活动结果</title>
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
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            color: #333333;
            font-size: 28px;
        }
        .info {
            margin: 20px 0;
            font-size: 18px;
        }
        .info p {
            margin: 10px 0;
            color: #666666;
        }
        .highlight {
            color: #4CAF50;
            font-weight: bold;
        }
        a {
            color: #ffffff;
            text-decoration: none;
            font-size: 16px;
            display: inline-block;
            margin-top: 20px;
            padding: 12px 24px;
            background-color: #4CAF50;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>活动分享</h2>
    <div class="info">
        <p>活动类型: <span class="highlight">${activityType}</span></p>
        <p>持续时间: <span class="highlight">${duration} 分钟</span></p>
        <p>日期: <span class="highlight">${date}</span></p>
        <p>开始时间: <span class="highlight">${startTime}</span></p>
        <p>温度: <span class="highlight">${temperature} 摄氏度</span></p>
        <p>天气: <span class="highlight">${weatherCondition}</span></p>
        <p>总消耗卡路里: <span class="highlight">${totalCaloriesBurned} 千卡</span></p>
    </div>
    <a href="home.jsp">返回主页</a>
</div>
</body>
</html>
