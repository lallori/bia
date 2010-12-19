<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditContextVolumeForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>Context</b></legend>
			<div><form:textarea id="ccontext" path="ccontext" cssClass="txtarea"/><form:errors path="ccontext" cssClass="inputerrors"/></div>

			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" style="margin-left:300px" class="button"/>
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
	            $('#EditContextVolumeDiv').block({ message: $('#question') }); 
				return false;
			});
	        
			$('#no').click(function() { 
				$.unblockUI();$(".blockUI").fadeOut("slow");
	            return false; 
	        }); 
	        
			$('#yes').click(function() { 
				$.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
					$("#body_left").html(html);
	 			}});
				
				return false; 
	        }); 

			$("#EditContextVolumeForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
					$("#EditContextVolumeDiv").html(html);
				}});
				return false;
			});

	        $("#EditCorrespondentsVolume").removeAttr("href"); 
	        $("#EditDescriptionVolume").removeAttr("href"); 
			$("#EditDetailsVolume").removeAttr("href"); 
		});
	</script>	