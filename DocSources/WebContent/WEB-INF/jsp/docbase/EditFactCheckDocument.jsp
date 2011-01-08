<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditFactCheckDocumentForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>FACT CHECK</b></legend>
				
			<div><form:textarea id="addLRes" path="addLRes" class="txtarea" /></div>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>

			<form:hidden path="entryId"/>
		</fieldset>	
	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#close').click(function() {
				$('#EditFactCheckDocumentDiv').block({ message: $('#question') }); 
				return false;
			});
      
			$('#no').click(function() { 
				$.unblockUI();$(".blockUI").fadeOut("slow");
				return false; 
			}); 
	        
			$('#yes').click(function() { 
				$.ajax({ url: '${ShowDocument}', cache: false, success:function(html) { 
					$("#body_left").html(html);
				}});
					
				return false; 
			}); 

			$("#EditFactCheckDocumentForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
						if(html.match(/inputerrors/g)){
							$("#EditFactChecksDocumentDiv").html(html);
						} else {
							$("#body_left").html(html);
						}
					} 
				});
				return false;
			});
		});
	</script>
