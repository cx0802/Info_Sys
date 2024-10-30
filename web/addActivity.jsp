<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>添加运动活动</title>
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
        }
        form {
            display: flex;
            flex-direction: column;
        }
        select, input[type="date"], input[type="time"], input[type="number"] {
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
    </style>
</head>
<body>
<div class="container">
    <h2>添加运动活动</h2>
    <form action="AddActivityServlet" method="post">
        <label for="activityType">运动类型:</label>
        <select name="activityType" id="activityType" required>
            <c:forEach var="type" items="${activityTypes}">
                <option value="${type}">${type}</option>
            </c:forEach>
        </select>
        <label for="date">日期:</label>
        <input type="date" name="date" required><br>
        <label for="startTime">开始时间:</label>
        <input type="time" name="startTime" required><br>
        <label for="duration">持续时间 (分钟):</label>
        <input type="number" name="duration" required><br>
        <input type="submit" value="添加">
    </form>
</div>
</body>
</html>
