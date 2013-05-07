<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditExtractOrSynopsisDocumentURL" value="/de/docbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${command.entryIdFrom}" />
		</c:url>
		
		<c:url var="EditDocReferenceDocumentURL" value="/de/docbase/EditDocReferenceDocument.do">
			<c:param name="entryIdFrom" value="${command.entryIdFrom}" />
		</c:url>
		
		<c:url var="EditDocReferencesDocumentURL" value="/de/docbase/EditDocReferencesDocument.do">
			<c:param name="entryId" value="${command.entryIdFrom}" />
		</c:url>
	</security:authorize>
	<br>
<%-- Loading div when saving the form  --%>
<div id="loadingDiv2"></div>
	<form:form id="EditDocReferenceDocumentForm" cssClass="edit">
		<fieldset>
			<legend>
			<c:if test="${empty command.entryIdTo}"> 
				<b><fmt:message key="docbase.editDocReferenceDocument.addNewDocument"/></b>
			</c:if>
			<c:if test="${command.entryIdTo > 0}">
				<b><fmt:message key="docbase.editDocReferenceDocument.editDocument"/></b>
			</c:if> 
			</legend>
			<div>
				<form:label id="entryIdToLabel" for="entryIdTo" path="entryIdTo" cssErrorClass="error"><fmt:message key="docbase.editDocReferenceDocument.docIdToLink"/>:</form:label>
				<form:input id="entryIdTo" path="entryIdTo" cssClass="input_5c" />
			</div>
			<div>
				<input id="closeDocReference" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
			</div>
			<input type="hidden" value="" id="modifyDocReference" />
		</fieldset>	

		<form:hidden path="docReferenceId"/>
		<form:hidden path="entryIdFrom"/>
	</form:form>

	<script type="text/javascript"> 
	    $j(document).ready(function() { 
	    	//$j.scrollTo("#EditPersonDocumentForm");
	    	
	    	$j("#EditDocReferenceDocumentForm :input").change(function(){
				$j("#modifyDocReference").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
	    	
	    	$j('#closeDocReference').click(function() {
				if($j("#modifyDocReference").val() == 1){
					$j('#EditDocReferenceDocumentDiv').block({ message: $j('#questionDocReference'),
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
					$j("#EditDocReferenceDocumentDiv").html('');				
					
					return false;
				}
			});

			$j("#EditDocReferenceDocumentForm").submit(function (){
				$j("#loadingDiv2").css('height', $j("#loadingDiv2").parent().height());
	        	$j("#loadingDiv2").css('visibility', 'visible');
	        	//If the section of correspondents is modified (BETA)
// 				if($j("#modify").val() == 1){
// 					if($j("#senderPeopleId, #recipientPeopleId").val() != "" && $j("#senderPeopleId").val() == $j("#recipientPeopleId").val()){
// 						$j('#EditCorrespondentsDocumentDiv').block({ message: $j('.questionSendRecip') });
// 						return false;
// 					}
// 					if($j("#senderPlacePrefered").val() == 'V' || $j("#recipientPlacePrefered").val() == 'V'){
// 						$j('#EditCorrespondentsDocumentDiv').block({ message: $j('.notPrincipal') });
// 						return false;
// 					}else{
// 						$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
// 			        	$j("#loadingDiv").css('visibility', 'visible');
// 		 				$j.ajax({ type:"POST", url:'${EditCorrespondentsOrPeopleDocumentURL}', data:$j("#EditCorrespondentsOrPeopleDocumentForm").closest('form').serialize(), async:false, success:function(html) { 
// 								$j("#body_left").html(html);
// 						}});
// 		 				return false;
// 					}
// 				}
	        	$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#fromPersonForm").val(1);
// 					$j("#EditCorrespondentsDocumentDiv").load('${EditCorrespondentsOrPeopleDocumentURL}');
					$j("#EditDocReferenceDocumentDiv").empty();
					$j("#DocReferenceDocumentDiv").load('${EditDocReferencesDocumentURL}');
				}});
				return false;
			});
		});					  
	</script>

<div id="questionDocReference" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yesDocReference" value="Yes" /> 
	<input type="button" id="noDocReference" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#noDocReference').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditDocReferenceDocumentDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yesDocReference').click(function() { 
			$j("#EditDocReferenceDocumentDiv").html('');				
				
			return false; 
		}); 
     
	});
</script>