<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeletePersonURL" value="/de/peoplebase/DeletePerson.do">
		<c:param name="personId"   value="${command.personId}" />
	</c:url>
	<c:url var="ShowMenuActionsPersonURL" value="/de/peoplebase/ShowMenuActionsPerson.do">
		<c:param name="personId"   value="${command.personId}" />
	</c:url>
	
	<div id="DeleteThisRecordDiv">
		<h1>Are you sure you want to delete this record?</h1>
		
		<a id="yes" href="${DeletePersonURL}">YES</a>
	
		<a id="no" href="${ShowMenuActionsPersonURL}">NO</a>
			
		<input id="close" type="submit" title="Close Actions Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "PERSON ACTIONS MENU", width: 750, height: 150});
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"POST", url:$j(this).attr("href"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#DeleteThisRecordDiv").load(html);
				}})
				return false;
			});
		});
	</script>