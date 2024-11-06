<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- 添加 JSTL 格式化库 -->
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>我的运动计划</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
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
            width: 80%;
            max-width: 600px;
            text-align: center;
        }
        h2 {
            color: #333333;
        }
        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #dddddd;
            text-align: center;
        }
        th {
            background-color: #f0f0f0;
            color: #333333;
        }
        .no-plan {
            color: #666666;
            font-style: italic;
            margin-top: 20px;
        }
        .progress {
            font-size: 0.9em;
            color: #4CAF50;
        }
        .back-button {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .back-button:hover {
            background-color: #45a049;
        }
        .edit-button {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .edit-button:hover {
            background-color: #1976D2;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>我的运动计划</h2>
    <c:choose>
        <c:when test="${not empty exercisePlan}">
            <p><strong>开始日期:</strong> ${exercisePlan.startDate}</p>
            <p><strong>结束日期:</strong> ${exercisePlan.endDate}</p>
            <p><strong>总目标时间:</strong> ${exercisePlan.totalDuration} 分钟</p>

            <table>
                <thead>
                <tr>
                    <th>运动类型</th>
                    <th>目标时长 (分钟)</th>
                    <th>频率 (每周)</th>
                    <th>已完成时长</th>
                    <th>进度</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="detail" items="${exerciseDetails}">
                    <tr>
                        <td>${detail.activityType}</td>
                        <td>${detail.targetDuration}</td>
                        <td>${detail.weeklyFrequency} 次</td>
                        <td>${detail.completedDuration} 分钟</td>
                        <td class="progress">
                            <c:choose>
                                <c:when test="${detail.targetDuration > 0}">
                                    <fmt:formatNumber value="${(detail.completedDuration / detail.targetDuration) * 100}" pattern="0"/>%
                                </c:when>
                                <c:otherwise>
                                    0%
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="no-plan">目前没有有效的运动计划。</p>
            <button class="back-button" onclick="window.location.href='home.jsp'">返回主页</button>
        </c:otherwise>
    </c:choose>
    <button class="back-button" onclick="window.location.href='home.jsp'">返回主页</button>
    <c:if test="${not empty exercisePlan.planId}">
        <button class="edit-button" onclick="window.location.href='EditExercisePlanServlet?planId=${exercisePlan.planId}'">编辑计划</button>
    </c:if>
</div>
</body>
</html>
