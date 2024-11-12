<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>查看所有用户</title>
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
            max-width: 1200px;
            margin-top: 20px;
        }
        h2 {
            margin-bottom: 20px;
            color: #333333;
            text-align: center;
            font-size: 24px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 12px;
            border: 1px solid #dddddd;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
            font-size: 16px;
            margin-top: 20px;
            display: inline-block;
        }
        a:hover {
            text-decoration: underline;
        }
        .button-container {
            margin-bottom: 20px;
            text-align: right;
        }
        
        .add-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #2196F3;
            color: white;
            border-radius: 4px;
            text-decoration: none;
            transition: background-color 0.3s;
        }
        
        .add-button:hover {
            background-color: #1976D2;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>查看所有用户</h2>
    <div class="button-container">
        <a href="register.jsp" class="add-button">新增用户</a>
    </div>
    <table>
        <tr>
            <th>用户 ID</th>
            <th>用户名</th>
            <th>地区</th>
            <th>性别</th>
            <th>年龄</th>
            <th>体重 (KG)</th>
            <th>身高 (CM)</th>
            <th>操作</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.username}</td>
                <td>${user.region}</td>
                <td>${user.gender}</td>
                <td>${user.age}</td>
                <td>${user.weight}</td>
                <td>${user.height}</td>
                <td>
                    <a href="DeleteUserServlet?userId=${user.userId}">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="adminHome.jsp">返回管理员首页</a></p>
</div>
</body>
</html>
