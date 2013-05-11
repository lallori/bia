<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="YesDeleteThisRecordDiv">
		<form:form>
		<form:errors path="entryId" cssClass="inputerrors" htmlEscape="false"/>
		</form:form>
			
		<input id="close" class="button_small" type="submit" title="Accept and close window" value="OK"/>
	</div>
	
	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide(); return false;
			});
		});
	</script>