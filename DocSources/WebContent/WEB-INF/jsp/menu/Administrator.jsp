<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
			
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			<li><a href="<c:url value="/admin/ShowAdministrationModule.do" />" id="administrationModule"><fmt:message key="menu.administrator.button"/></a></li>
			
			<script type="text/javascript">
			$j(document).ready(function(){
				$j("#administrationModule").click(function(){
					Modalbox.show($j(this).attr("href"), {title: "<fmt:message key="menu.administrator.open"/>", width: 270, height: 320});
					return false;
				}						
				);
			});
			</script>
		</security:authorize>			