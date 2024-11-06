<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>创建运动计划</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            padding: 20px;
        }
        .container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        h2 {
            color: #333333;
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #666666;
        }
        input[type="date"], 
        input[type="number"],
        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .exercise-item {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 10px;
        }
        button {
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }
        button:hover {
            background-color: #45a049;
        }
        .add-exercise {
            background-color: #2196F3;
        }
        .add-exercise:hover {
            background-color: #1976D2;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>创建新运动计划</h2>
    <form action="CreateExercisePlanServlet" method="post" id="planForm">
        <div class="form-group">
            <label for="startDate">开始日期：</label>
            <input type="date" id="startDate" name="startDate" required>
        </div>
        <div class="form-group">
            <label for="endDate">结束日期：</label>
            <input type="date" id="endDate" name="endDate" required>
        </div>
        <div class="form-group">
            <label for="totalDuration">总目标时长（分钟）：</label>
            <input type="number" id="totalDuration" name="totalDuration" required>
        </div>
        
        <h3>运动项目</h3>
        <div id="exerciseItems">
            <div class="exercise-item">
                <div class="form-group">
                    <label>运动类型：</label>
                    <select name="activityType[]" required>
                        <c:forEach var="type" items="${activityTypes}">
                            <option value="${type.activityType}">${type.activityType}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>目标时长（分钟）：</label>
                    <input type="number" name="targetDuration[]" required>
                </div>
                <div class="form-group">
                    <label>每周频率：</label>
                    <input type="number" name="weeklyFrequency[]" required>
                </div>
            </div>
        </div>
        
        <button type="button" class="add-exercise" onclick="addExerciseItem()">添加运动项目</button>
        <button type="submit">保存计划</button>
    </form>
</div>

<script>
function addExerciseItem() {
    const container = document.getElementById('exerciseItems');
    const newItem = document.createElement('div');
    newItem.className = 'exercise-item';
    newItem.innerHTML = `
        <div class="form-group">
            <label>运动类型：</label>
            <select name="activityType[]" required>
                <c:forEach var="type" items="${activityTypes}">
                    <option value="${type.activityType}">${type.activityType}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>目标时长（分钟）：</label>
            <input type="number" name="targetDuration[]" required>
        </div>
        <div class="form-group">
            <label>每周频率：</label>
            <input type="number" name="weeklyFrequency[]" required>
        </div>
    `;
    container.appendChild(newItem);
}
</script>
</body>
</html> 