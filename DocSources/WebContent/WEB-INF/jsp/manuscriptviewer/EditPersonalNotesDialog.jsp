<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditPersonalNotesForm" method="post" cssClass="edit">
		<form:textarea id="personalNotes" path="personalNotes" cssClass="txtarea" rows="20" style="width: 96%; height: 96%;"/>
		<input id="save" type="image" src="<c:url value="/images/saveExtract.png"/>" alt="Save Extract"/>
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditPersonalNotesForm").submit(function (){
				$j.ajax({ type:"POST", url:$j("#EditPersonalNotesForm").attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						$j("#EditPersonalNotesDiv").html(html);
						personalNotesChanged=false;
					} 
				});
				return false;
			});
			$j("#extract").change(function(){
				personalNotesChanged=true;
			});
		});
	</script>

