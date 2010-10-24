<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!-- <pre>
Hello! I'm <sec:authentication property="principal.username"/>

I land on DocSource home page!


    <a href="<c:url value="/user/UpdateUser.do"/>">Update profile</a>
    <a href="<c:url value="/user/UpdateUserPassword.do"/>">Update user password</a>
    <sec:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
    	<a href="<c:url value="/user/SearchUser.do"/>">Search Users</a>
    	<a href="<c:url value="/user/AdvancedSearchUser.do"/>">Advanced Search Users</a>
    </sec:authorize>
    <a href="<c:url value="/user/DeleteUser.do"/>">Unregister from service</a>
    <a href="<c:url value="/LogoutUser.do"/>">Exit</a>
</pre>
 -->