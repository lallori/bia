<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditContextVolumeForm" method="post" cssClass="edit">
		<form:textarea id="extract" path="extract" cssClass="txtarea" rows="20" style="width: 96%; height: 96%;"/>
		<input id="saveExtract" type="image" src="<c:url value="/images/saveExtract.png"/>" alt="Save Extract"/>
		<input id="saveAndEditSynopsis" type="image" src="<c:url value="/images/saveAndEditSynopsis.png"/>" alt="Save and edit Synopsis"/>
		<form:hidden path="entryId"/>
	</form:form>
