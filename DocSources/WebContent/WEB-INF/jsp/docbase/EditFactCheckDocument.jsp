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
			<form:hidden path="vetId"/>
		</fieldset>	
	</form:form>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
	        $j("#EditCorrespondentsDocument").css('visibility', 'hidden'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden');
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');

	        $j('#close').click(function() {
				$j('#EditFactCheckDocumentDiv').block({ message: $j('#question') }); 
				return false;
			});
      
			$j("#EditFactCheckDocumentForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						$j("#EditFactCheckDocumentDiv").html(html);
					} 
				});
				return false;
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1>discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditFactCheckDocumentDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>