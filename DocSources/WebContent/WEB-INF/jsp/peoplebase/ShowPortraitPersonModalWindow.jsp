<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowPortraitPersonURL" value="/src/peoplebase/ShowPortraitPerson.do">
		<c:param name="personId" value="${command.personId}" />
		<c:param name="time" value="${time}" />
	</c:url>
	<div>
		<img src="${ShowPortraitPersonURL}" width="111" height="145"/>
		<a id="close" href="#" title="Close Portrait window"><fmt:message key=“people.showPortraitPersonModalWindow.close”/></a>
	</div>

		
	<script type="text/javascript">
		$j(document).ready(function() {

			$j("#close").click(function (){
		        $j("#uploadPortraitWindow").dialog("close");
				
				return false;
			});
		});
	</script>