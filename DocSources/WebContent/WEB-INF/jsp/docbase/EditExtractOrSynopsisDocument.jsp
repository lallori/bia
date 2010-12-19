<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditExtractOrSynopsisDocumentForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>EXTRACT/SYNOPSIS</b></legend>
			
			<div><form:label for="extract" id="extractLabel" path="extract" cssErrorClass="error">Extract:</label></div>
			<div><form:textarea id="extract" path="extract" class="txtarea_big" /></div>
			<div><form:label for="synopsis" id="synopsisLabel" cssErrorClass="error">Synopsis:</label></div>
			<div><form:textarea id="synopsis" path="synopsis" class="txtarea_big" /></div>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>
			
			<form:hidden path="entryId"/>
		</fieldset>	
	</form>

	</form:form>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#EditExtractOrSynopsisDocumentForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
						if(html.match(/inputerrors/g)){
							$("#EditExtractOrSynopsisDocumentDiv").html(html);
						} else {
							$("#body_left").html(html);
						}
					} 
				});
				return false;
			});
		});
	</script>
