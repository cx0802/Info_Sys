<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>分享活动</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333333;
            text-align: center;
            margin-bottom: 30px;
        }
        .activity-list {
            display: grid;
            gap: 20px;
        }
        .activity-card {
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 15px;
            position: relative;
            transition: transform 0.2s;
        }
        .activity-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .activity-info {
            margin-bottom: 15px;
        }
        .activity-info p {
            margin: 5px 0;
            color: #666;
        }
        .activity-type {
            font-weight: bold;
            color: #4CAF50;
        }
        .share-form {
            display: none;
            margin-top: 10px;
        }
        .share-form.active {
            display: block;
        }
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 10px;
            resize: vertical;
            min-height: 60px;
        }
        .button-group {
            display: flex;
            gap: 10px;
        }
        button {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .share-button {
            background-color: #4CAF50;
            color: white;
        }
        .share-button:hover {
            background-color: #45a049;
        }
        .cancel-button {
            background-color: #f5f5f5;
            color: #666;
        }
        .cancel-button:hover {
            background-color: #e0e0e0;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #4CAF50;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>选择要分享的活动</h2>
    <div class="activity-list">
        <c:forEach var="activity" items="${activities}">
            <div class="activity-card">
                <div class="activity-info">
                    <p class="activity-type">${activity.activityType}</p>
                    <p>日期：<fmt:formatDate value="${activity.date}" pattern="yyyy-MM-dd"/></p>
                    <p>时间：${activity.startTime}</p>
                    <p>持续时间：${activity.durationMinutes} 分钟</p>
                </div>
                <button class="share-button" onclick="showShareForm(${activity.activityId})">分享这个活动</button>
                <div id="shareForm${activity.activityId}" class="share-form">
                    <form action="ShareActivityServlet" method="post">
                        <input type="hidden" name="activityId" value="${activity.activityId}">
                        <textarea name="shareText" placeholder="说点什么..."></textarea>
                        <div class="button-group">
                            <button type="submit" class="share-button">确认分享</button>
                            <button type="button" class="cancel-button" onclick="hideShareForm(${activity.activityId})">取消</button>
                        </div>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
    <a href="home.jsp" class="back-link">返回主页</a>
</div>

<script>
function showShareForm(activityId) {
    document.querySelectorAll('.share-form').forEach(form => form.classList.remove('active'));
    document.getElementById('shareForm' + activityId).classList.add('active');
}

function hideShareForm(activityId) {
    document.getElementById('shareForm' + activityId).classList.remove('active');
}
</script>
</body>
</html>
