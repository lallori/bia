<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${person.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateCreated}" /></div>
		<a id="vettingChronology" href="/DocSources/de/VettingChronology.html"></a>
		<a id="menuActions" href="/DocSources/de/peoplebase/ActionsMenu.html"></a>
		<a id="buttonPrint" title="Print this record" href="#"></a>
		<div id="buttonShareLink">
			<a href="#"><img src="/DocSources/images/1024/img_transparent.png"></a>
			<span>Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.</span>
		</div>
	</div>
		