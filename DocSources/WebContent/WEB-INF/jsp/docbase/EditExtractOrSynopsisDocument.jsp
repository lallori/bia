<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditExtractOrSynopsisDocumentForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>EXTRACT/SYNOPSIS</b></legend>
			
			<div><form:label for="docExtract" id="docExtractLabel" path="docExtract" cssErrorClass="error">Extract:</form:label></div>
			<div><form:textarea id="docExtract" path="docExtract" class="txtarea_big" /></div>
			<div><form:label for="synopsis" path="synopsis" id="synopsisLabel" cssErrorClass="error">Synopsis:</form:label></div>
			<div><form:textarea id="synopsis" path="synopsis" class="txtarea_big" /></div>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>
			
			<form:hidden path="entryId"/>
			<form:hidden path="synExtrId"/>
		</fieldset>	
	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$(document).ready(function() {
	        $("#EditDetailsDocument").removeAttr("href"); 
	        $("#EditCorrespondentsOrPeopleDocument").removeAttr("href"); 
	        $("#EditFactCheckDocument").removeAttr("href");
	        $("#EditTopicsDocument").removeAttr("href");

	        $('#close').click(function() {
				$('#EditExtractOrSynopsisDocumentDiv').block({ message: $('#question') }); 
				return false;
			});
      
			$('#no').click(function() { 
				$.unblockUI();
				$(".blockUI").fadeOut("slow");
				$('#question').hide();
				$('#EditExtractOrSynopsisDocumentDiv').append($("#question"));
				$(".blockUI").remove();
	            return false; 
	        }); 
	        
			$('#yes').click(function() { 
				$.ajax({ url: '${ShowDocument}', cache: false, success:function(html) { 
					$("#body_left").html(html);
				}});
					
				return false; 
			}); 

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
