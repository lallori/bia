<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="EditExtractDocumentDiv">
		<form:form id="EditSynopsisDocumentForm" method="post" cssClass="edit">
			<form:textarea id="synopsis" path="synopsis" cssClass="txtarea" rows="20" style="width: 96%; height: 96%;"/>
			<div>
				<input id="saveSynopsis" type="image" src="<c:url value="/images/saveSynopsis.png" />" alt="Save Synopsis"/>
				<input id="saveSynopsisExit" type="image" src="<c:url value="/images/saveSynopsisExit.png" />" alt="Save Synopsis and Exit"/>
			</div>
			<form:hidden path="entryId"/>
		</form:form>
	</div>