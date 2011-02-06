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
		$j(document).ready(function() {
	        $j("#EditDetailsDocument").removeAttr("href"); 
	        $j("#EditCorrespondentsOrPeopleDocument").removeAttr("href"); 
	        $j("#EditExtractOrSynopsisDocument").removeAttr("href"); 
	        $j("#EditFactCheckDocument").removeAttr("href");
	        $j("#EditTopicsDocument").removeAttr("href");

	        $j('#close').click(function() {
				$j('#EditFactCheckDocumentDiv').block({ message: $j('#question') }); 
				return false;
			});
      
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j('#question').hide();
				$j('#EditFactCheckDocumentDiv').append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${ShowDocument}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
					
				return false; 
			}); 

			$j("#EditFactCheckDocumentForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						if(html.match(/inputerrors/g)){
							$j("#EditFactChecksDocumentDiv").html(html);
						} else {
							$j("#body_left").html(html);
						}
					} 
				});
				return false;
			});
		});
	</script>
