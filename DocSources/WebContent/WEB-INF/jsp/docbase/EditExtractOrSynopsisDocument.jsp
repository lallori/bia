<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.entryId}" />
		</c:url>
	</security:authorize>
<%-- Loading div when saving the form --%>
<div id="loadingDiv"></div>
	<form:form id="EditExtractOrSynopsisDocumentForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>EXTRACT/SYNOPSIS</b></legend>
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="Text goes here">?</a>
					<form:label for="docExtract" id="docExtractLabel" path="docExtract" cssErrorClass="error">Extract</form:label>
				</div>
				<div class="row">
					<form:textarea id="docExtract" path="docExtract" class="txtarea_big" />
				</div>
			
				<br />
			
				<div class="row">
					<a class="helpIcon" title="Text goes here">?</a>
					<form:label for="synopsis" path="synopsis" id="synopsisLabel" cssErrorClass="error">Synopsis</form:label>
				</div>
				<div class="row">
					<form:textarea id="synopsis" path="synopsis" class="txtarea_big" />
				</div>
			</div>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>
			
			<form:hidden path="entryId"/>
			<form:hidden path="synExtrId"/>
			<input type="hidden" value="" id="modify" />
		</fieldset>	
	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditExtractOrSynopsisDocumentForm");
			
	        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
	        $j("#EditCorrespondentsDocument").css('visibility', 'hidden'); 
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'hidden');
	        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');
	        
	        $j("#EditExtractOrSynopsisDocumentForm :input").change(function(){
				$j("#modify").val(1); //set the hidden field if an element is modified
				return false;
			});
	        
	        $j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });

	        $j('#close').click(function() {
	        	if($j("#modify").val() == 1){
					$j('#EditExtractOrSynopsisDocumentDiv').block({ message: $j('#question'),
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
	        		$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
	    				$j("#body_left").html(html);
	    			}});
	        		
	        		return false;
	        	}
			});

			$j("#EditExtractOrSynopsisDocumentForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						//$j("#EditExtractOrSynopsisDocumentDiv").html(html);
						$j("#body_left").load('${ShowDocumentURL}');
					} 
				});
				return false;
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditExtractOrSynopsisDocumentDiv").append($j("#question"));
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