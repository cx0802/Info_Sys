<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>名人堂</title>
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
            width: 600px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
            color: #333333;
        }
        form {
            margin-bottom: 20px;
        }
        input[type="text"], input[type="date"], select {
            width: calc(100% - 22px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #cccccc;
            border-radius: 4px;
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
        }
        input[type="submit"]:hover {
            background-color: #45a049;
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
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>排名榜</h2>
    <form action="LeaderboardServlet" method="get">
        地区：<input type="text" name="region"><br>
        性别：<input type="text" name="gender"><br>
        年龄范围：<select name="ageRange">
        <option value="">--选择年龄--</option>
        <option value="0-18">0-18</option>
        <option value="19-30">19-30</option>
        <option value="31-50">31-50</option>
        <option value="51+">51+</option>
    </select><br>
        开始日期：<input type="date" name="startDate"><br>
        结束日期：<input type="date" name="endDate"><br>
        活动类型：<input type="text" name="activityType"><br>
        <input type="submit" value="搜索">
    </form>
    <table>
        <tr>
            <th>用户名</th>
            <th>总运动时间</th>
            <th>地区</th>
            <th>性别</th>
            <th>年龄</th>
            <th>体重 (KG)</th>
            <th>身高 (CM)</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.username}</td>
                <td>${user.totalDuration}</td>
                <td>${user.region}</td>
                <td>${user.gender}</td>
                <td>${user.age}</td>
                <td>${user.weight}</td>
                <td>${user.height}</td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="home.jsp">返回主页</a></p>
</div>
</body>
</html>
