<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeletePersonURL" value="/de/peoplebase/DeletePerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	<c:url var="UndeletePersonURL" value="/de/peoplebase/UndeletePerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
		
	<div id="ActionsMenuDiv">
		<c:if test="${!person.logicalDelete}">
			<h1>Are you sure you want to delete this person record?</h1>		
			<a id="deletePeopleBase" href="${DeletePersonURL}">Delete this person record</a>
		</c:if>	
		<c:if test="${person.logicalDelete}">
			<h1>Are you sure you want to undelete this person record?</h1>
			<a id="undeletePeopleBase" href="${UndeletePersonURL}">Undelete this person record</a>
		</c:if>	
		<br>
		<br>				
		<input id="close" class="button_small" type="submit" title="Close Actions Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#deletePeopleBase").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "DELETE THIS PERSON RECORD", width: 450, height: 150});
				return false;
			});
			$j("#undeletePeopleBase").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "UNDELETE THIS PERSON RECORD", width: 450, height: 150});
				return false;
			});
		});
	</script>
