<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="UndeletePersonURL" value="/de/peoplebase/UndeletePerson.do">
		<c:param name="personId"   value="${command.personId}" />
	</c:url>
	<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
		<c:param name="personId"   value="${command.personId}" />
	</c:url>
	
	<div id="DeleteThisRecordDiv">
		<h1><fmt:message key=“people.showConfirmUndeletePerson.areYouSure”/></h1>
		
		<a id="yes" class="button_small" href="${UndeletePersonURL}"><fmt:message key=“people.showConfirmUndeletePerson.yes”/></a>
	
		<a id="no" class="button_small" href="#"><fmt:message key=“people.showConfirmUndeletePerson.no”/></a>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"POST", url: '${UndeletePersonURL}', async:false, success:function(html) {
					$j("#DeleteThisRecordDiv").html(html);
					$j("#body_left").load('${ShowPersonURL}');
				}});
				
				return false;
			});
		});
	</script>
