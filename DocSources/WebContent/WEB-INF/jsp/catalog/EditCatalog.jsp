<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_DIGITIZATION_COORDINATORS, DIGITIZATION_TECHNICIANS">
		<c:url var="ShowCatalogURL" value="/src/docbase/ShowCatalog.do">
			<c:param name="entryId"   value="${command.entryId}" />
		</c:url>
	</security:authorize>


<div>
</div>