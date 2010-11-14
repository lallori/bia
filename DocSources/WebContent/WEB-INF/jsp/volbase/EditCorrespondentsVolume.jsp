<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<link rel="stylesheet" href="<c:url value="/styles/style_editform.css" />" type="text/css" media="screen, projection">
	<form:form id="EditCorrespondentsVolumeForm" method="post">
		<fieldset>
			<legend><b>Volume CORRESPONDENTS</b></legend>
			<div>				
				<form:label id="recipsLabel" for="recips" path="recips" cssErrorClass="error"><i>From:</i></form:label>
				<form:textarea id="recips" path="recips" cssClass="txtarea"/><form:errors path="recips" cssClass="inputerrors"/>
			</div>
			<div>				
				<form:label id="sendersLabel" for="senders" path="senders" cssErrorClass="error"><i>To:</i></form:label>
				<form:textarea id="senders" path="senders" cssClass="txtarea"/><form:errors path="senders" cssClass="inputerrors"/>
			</div>
			<div style="margin-top:5px">
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" /><input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
			</div>
			<form:hidden path="summaryId"/>
		</fieldset>
	</form:form>

	<c:url var="ShowVolume" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>

	<script type="text/javascript">
		$(document).ready(function() {
	        $('#close').click(function() {
	        	$.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
					$("#body_left").html(html);
	 			}}); 
				return false;
			});

			$("#EditCorrespondentsVolumeForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
					if(html.match(/inputerrors/g)){
						$("#EditCorrespondentsVolumeDiv").html(html);
					} else {
						$("#body_left").html(html);
					}
				} });
				return false;
			});
		});
	</script>