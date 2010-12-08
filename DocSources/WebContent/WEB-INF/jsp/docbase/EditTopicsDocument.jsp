<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditTopicsDocumentForm" method="post" cssClass="edit">
	</form:form>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#EditTopicsDocumentForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
						if(html.match(/inputerrors/g)){
							$("#EditTopicsDocumentDiv").html(html);
						} else {
							$("#body_left").html(html);
						}
					} 
				});
				return false;
			});
		});
	</script>
