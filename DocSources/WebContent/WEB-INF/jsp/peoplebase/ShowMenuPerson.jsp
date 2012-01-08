<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowVettingChronologyPersonURL" value="/de/peoplebase/ShowVettingChronologyPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		<c:url var="ShowMenuActionsPersonURL" value="/de/peoplebase/ShowMenuActionsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		<c:url var="SharePersonURL" value="/src/peoplebase/SharePerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		<c:url var="PrintPersonURL" value="/src/peoplebase/PrintPerson.do">
			<c:param name="personId" value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${person.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateCreated}" /></div>
		<a id="vettingHistory" href="${ShowVettingChronologyPersonURL}">Vetting History</a>
		<a id="menuActions" href="${ShowMenuActionsPersonURL}">Actions</a>
		<a id="buttonPrint" title="Print this record" href="${PrintPersonURL}"></a>
		<a id="buttonPDF" href="#" title="Save this record as PDF"></a>
		<a id="buttonShareLink" href="${SharePersonURL}" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.">Share/Link</a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j("#buttonShareLink").click(function() {
			window.open($j(this).attr("href"),'SHARE PERSON','width=510,height=700,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
		});
	
		$j('#buttonShareLink').tooltip({track: true, fade: 350 });
		
		$j("#buttonPrint").click(function() {	
			window.open($j(this).attr("href"),'PRINT PERSON','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
		});

	});
	</script>
		