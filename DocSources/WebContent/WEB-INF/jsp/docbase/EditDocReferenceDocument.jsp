<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
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
			
			<div id="inputerror" style="color: red; display:none"></div>
			<form:errors path="entryIdFrom" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="entryIdTo" cssClass="inputerrors" htmlEscape="false"/>
			
			<div>
				<input id="closeDocReference" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
			</div>
			<input type="hidden" value="" id="modifyDocReference" />
		</fieldset>	

		<form:hidden path="docReferenceId"/>
		<form:hidden id="entryIdFromHidden" path="entryIdFrom"/>
	</form:form>
	
	<div id="questionDocReference" style="display:none; cursor: default"> 
		<h1>Discard changes?</h1> 
		<input type="button" id="yesDocReference" value="Yes" /> 
		<input type="button" id="noDocReference" value="No" /> 
	</div>
	
	<script type="text/javascript"> 
	    $j(document).ready(function() { 
	    	$j("#EditDocReferenceDocumentForm :input").change(function(){
				$j("#modifyDocReference").val(1); <%-- //set the hidden field if an element is modified --%>
				$j("#autoreferenceError").css('style','display:none');
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

			$j("#EditDocReferenceDocumentForm").submit(function () {
				// show the loading div
				$j("#loadingDiv2").css('height', $j("#loadingDiv2").parent().height());
	        	$j("#loadingDiv2").css('visibility', 'visible');
	        	
	        	// Client validation
	        	var entryIdTo = $j("#entryIdTo").val();
	        	if (entryIdTo == $j("#entryIdFromHidden").val()) {
	        		// Error: autoreference
	        		$j("#loadingDiv2").css('visibility', 'hidden');
	        		$j("#inputerror").html('<fmt:message key="docbase.editDocReferenceDocument.error.autoreference"/>');
	        		$j("#inputerror").show();
	        	} else {
	        		var entered = false;
	        		$j('#DocReferenceDocumentDiv input[type="text"]').each(function() {
	        			if ($j(this).val() == ("#"+entryIdTo)) {
	        				entered = true;
	        			}
	        		});
	        		if (entered) {
	        			// Error: document reference already exists
	        			$j("#loadingDiv2").css('visibility', 'hidden');
	        			$j("#inputerror").html('<fmt:message key="docbase.editDocReferenceDocument.error.referenceExistent"/>');
	        			$j("#inputerror").show();
	        		} else {
	        			// All OK, form can be submitted
			        	$j.ajax(
			        		{ 
			        			type: "POST",
			        			url: $j(this).attr("action"),
			        			data: $j(this).serialize(),
			        			async: false, 
			        			success: function(html) {
			        				if ($j(html).find(".inputerrors").length > 0) {
										$j("#EditDocReferenceDocumentDiv").html(html);
									} else {
										$j("#modifyDocReference").val(1);
										$j("#EditDocReferenceDocumentDiv").empty();
										$j("#DocReferenceDocumentDiv").load('${EditDocReferencesDocumentURL}');
									}
								},
								error: function(html) {
									$j("#loadingDiv2").css('visibility', 'hidden');
									$j("#inputerror").html('<fmt:message key="docbase.editDocReferenceDocument.error.serverError"/>');
									$j("#inputerror").show();
								}
			        		}
			        	);
	        		}
	        	}
				return false;
			});
			
			$j('#entryIdTo').change(function() {
				$j("#inputerror").hide();
			});

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