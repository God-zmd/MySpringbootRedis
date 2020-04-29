<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/27
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<body>
<div>
<table border="1" cellspacing="0">
    <tr>
        <th>field</th>
        <th>学号</th>
        <th>姓名</th>
        <th>出生日期</th>
        <th>描述</th>
        <th>平均分</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${List}" var="list">
        <tr>
            <td>${list.field}</td>
            <td>${list.stuId}</td>
            <td>${list.stuName}</td>
            <td>${list.birthday}</td>
            <td>${list.description}</td>
            <td>${list.avgscore}</td>
            <td><button><a href="/redisDelServlet?field=${list.field}">删除</a></button>
                 <button><a href="/add.jsp">添加</a></button>
                <button>
                    <a href="/edit.jsp?field=${list.field}&stuId=${list.stuId}&stuName=${list.stuName}&birthday=${list.birthday}&description=${list.description}&avgscore=${list.avgscore}">修改</a>
                </button>
            </td>
        </tr>
    </c:forEach>


</table>
</div>

<!-- 上一页 按钮 -->
    <c:choose>
    <c:when test="${nowPage != 1}">
    <a href="/myRedisServlet?nowPage=${nowPage-1}">
        <input type="button" name="lastPage" value="上一页" />
    </a>
    </c:when>
        <c:otherwise>
            <input type="button" id="lastPage" name="lastPage" value="上一页" /><!-- 为了要那个灰掉的button -->
        </c:otherwise>
    </c:choose>

  <%--当前页--%>
   当前页：${nowPage}

<!-- 下一页 按钮 -->
<c:choose>
    <c:when test="${nowPage != totalPage}">
        <a href="/myRedisServlet?nowPage=${nowPage+1}">
            <input type="button" name="nextPage" value="下一页" />
        </a>
    </c:when>
    <c:otherwise>
        <input type="button" id="nextPage" name="nextPage" value="下一页" /><!-- 为了要那个灰掉的button -->
    </c:otherwise>
</c:choose>

 <%--总页数--%>
   总页数：${totalPage}&nbsp;&nbsp;

    <%--页数列表--%>
  页数列表： <c:forEach items="${pageList}" var="item">
                <a href="/myRedisServlet?nowPage=${item}">${item}</a>
    </c:forEach>

<script>
     $("#lastPage").click(function () {
         alert("已经是第一页！")
     })

    $("#nextPage").click(function () {
        alert("已经是最后一页！")
    })
</script>

</body>
</html>
