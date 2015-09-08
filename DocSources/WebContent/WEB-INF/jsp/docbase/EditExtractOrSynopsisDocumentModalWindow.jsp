<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>

	<form:form id="EditExtractOrSynopsisDocumentFullscreenForm" method="post" cssClass="edit">

		<div style="float:left">
			<form:label for="docExtract" id="docExtractLabel" path="docExtract"><fmt:message key="docbase.editExtractOrSynopsisDocumentModal.transcription"/>:</form:label>
			<br />
			<form:textarea id="docExtract" path="docExtract" cssClass="txtarea_fullscreen"/>
		</div>

		<div style="float:right">
			<form:label for="synopsis" id="synopsisLabel" path="synopsis"><fmt:message key="docbase.editExtractOrSynopsisDocumentModal.synopsis"/>:</form:label>
			<br />
			<form:textarea id="synopsis" path="synopsis" cssClass="txtarea_fullscreen"/>
		</div>

		<br />
		<div id="closeSaveButtonsFullscreen">
			<input id="close" class="button_small fl" type="submit" value="Close" title="Do not save changes" />
			<input id="save" class="button_small fr" type="submit" value="Save" />
		</div>
		
		<form:hidden path="entryId"/>
		<form:hidden path="synExtrId"/>
		<input type="hidden" value="" id="modify" />
	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditExtractOrSynopsisDocumentFullscreenForm :input").change(function(){
				$j("#modify").val(1); //set the hidden field if an element is modified
				return false;
			});
			
	        $j('#close').click(function() {
	        	if($j("#modify").val() == 1){
					$j('#MB_frame').block({ message: $j('#question'),
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
					}); 
					return false;
	        	}else{
	        		Modalbox.hide();
	        		return false;
	        	}
			});

			$j("#EditExtractOrSynopsisDocumentFullscreenForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						if ($j(html).find(".inputerrors").length > 0){
							$j("#modalBox").html(html);
						} else {
							$j("#body_left").load('${ShowDocument}');
							Modalbox.hide(); 
						}
						return false;
					} 
				});
				return false;
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1><fmt:message key="docbase.editExtractOrSynopsisDocumentModalWindow.discardChangesQuestion"/></h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#MB_frame").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			//$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
				//$j("#body_left").html(html);
				Modalbox.hide();
				//return false;
			//}});
			
				
			return false; 
		}); 
     
	});
</script>