<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowVettingChronologyDocumentURL" value="/de/peoplebase/ShowVettingChronologyDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="ShowMenuActionsDocumentURL" value="/de/peoplebase/ShowMenuActionsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="ShowShareLinkDocumentURL" value="/src/peoplebase/ShowShareLinkDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></div>
		<a id="vettingChronology" href="${ShowVettingChronologyDocumentURL}"></a>
		<a id="menuActions" href="${ShowMenuActionsDocumentURL}"></a>
		<a id="buttonPrint" title="Print this record" href="#"></a>
		<div id="buttonShareLink">
			<a href="${ShowShareLinkDocumentURL}"><img src="/DocSources/images/1024/img_transparent.png"></a>
			<span>Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.</span>
		</div>
	</div>
