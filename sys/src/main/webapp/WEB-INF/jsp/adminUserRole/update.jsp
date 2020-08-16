<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/jsp/header.jsp" %>

<title>分配角色</title>
</head>
<body>
<div class="pd-20">
    <form action="updateAdminUserRole.do" method="post">
        <input type="hidden" name="adminUserId" value="${adminUserId }" />
        <table class="table table-border table-bordered table-bg table-hover" >

		<!-- 此处要修改的其它用户信息-->
            <thead>
                <tr>
                    <th>选中</th>
                    <th>角色名称</th>
                    <th>角色描述</th>
                </tr>
            </thead>
            <tbody>
                    
            <!-- 此处写角色选中。-->
            <c:forEach items="${roleList}" var="role">
                <tr>
                    <td>
                        <input type="checkbox" name="roleId" value="${role.id} "
                        <c:forEach items="${adminUserRoleList}" var="adminUserRole">
                               <c:if test="${role.id eq adminUserRole.roleId}">checked</c:if>
                        </c:forEach>
                        >
                    </td>

                    <td>
                        ${role.name}
                    </td>
                    <td>

                        ${role.description}
                    </td>

                </tr>
            </c:forEach>


                    
            </tbody>
        </table>
        <br>
        <input class="btn btn-primary radius" type="submit" value="分配角色" />
        <input class="btn btn-default radius" type="button" value="关闭" onclick="location.href='list.do'" style="margin-left: 30px;" />
    </form>
</div>
</body>
</html>