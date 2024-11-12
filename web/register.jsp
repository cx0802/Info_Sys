<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>新增用户</title>
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
            padding: 20px 40px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
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
        label {
            text-align: left;
            margin-bottom: 5px;
            color: #666666;
        }
        input[type="text"], input[type="password"] {
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
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
        
        .cancel-button {
            width: 100%;
            padding: 10px;
            background-color: #f44336;
            border: none;
            border-radius: 4px;
            color: white;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
        }
        
        .cancel-button:hover {
            background-color: #d32f2f;
        }
        
        select, input[type="number"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0 20px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>新增用户</h2>
    <form action="RegisterServlet" method="post">
        <label for="username">用户名：</label>
        <input type="text" id="username" name="username" required>
        
        <label for="password">密码：</label>
        <input type="password" id="password" name="password" required>
        
        <label for="region">地区：</label>
        <input type="text" id="region" name="region">
        
        <label for="gender">性别：</label>
        <select id="gender" name="gender">
            <option value="男">男</option>
            <option value="女">女</option>
        </select>
        
        <label for="age">年龄：</label>
        <input type="number" id="age" name="age" min="1" max="120">
        
        <label for="weight">体重 (KG)：</label>
        <input type="number" id="weight" name="weight" step="0.1">
        
        <label for="height">身高 (CM)：</label>
        <input type="number" id="height" name="height">
        
        <div class="button-group">
            <input type="submit" value="添加用户">
            <a href="ViewAllUsersServlet" class="cancel-button">取消</a>
        </div>
    </form>
</div>
</body>
</html>
