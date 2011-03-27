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

	<form:form id="EditExtractOrSynopsisDocumentForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>EXTRACT/SYNOPSIS</b></legend>
			
			<div><form:label for="docExtract" id="docExtractLabel" path="docExtract" cssErrorClass="error">Extract:</form:label></div>
			<div><form:textarea id="docExtract" path="docExtract" class="txtarea_big" /></div>
			<div><form:label for="synopsis" path="synopsis" id="synopsisLabel" cssErrorClass="error">Synopsis:</form:label></div>
			<div><form:textarea id="synopsis" path="synopsis" class="txtarea_big" /></div>
			
			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<input id="save" type="submit" value="" class="button"/>
			</div>
			
			<form:hidden path="entryId"/>
			<form:hidden path="synExtrId"/>
		</fieldset>	
	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'hidden'); 
	        $j("#EditDocumentInManuscriptViewer").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');

	        $j('#close').click(function() {
				$j('#EditExtractOrSynopsisDocumentDiv').block({ message: $j('#question') }); 
				return false;
			});

			$j("#EditExtractOrSynopsisDocumentForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						$j("#EditExtractOrSynopsisDocumentDiv").html(html);
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