<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocumentURL" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId" value="${command.entryId}" />
		</c:url>
		
		<c:url var="EditPeopleDocumentURL" value="/de/docbase/EditPeopleDocument.do">
			<c:param name="entryId" value="${command.entryId}" />
		</c:url>
	</security:authorize>
	<br>
<%-- Loading div when saving the form  --%>
<div id="loadingDiv2"></div>
	<form:form id="EditPersonDocumentForm" cssClass="edit">
		<fieldset>
			<legend>
			<c:if test="${empty command.personId}"> 
				<b>ADD NEW PERSON</b>
			</c:if>
			<c:if test="${command.personId > 0}">
				<b>EDIT PERSON</b>
			</c:if> 
			</legend>
			<div>
				<form:label id="personDescriptionLabel" for="personDescription" path="personDescription" cssErrorClass="error">Name</form:label>
				<form:input id="personDescriptionAutoCompleter" path="personDescription" cssClass="input_25c" />
			</div>
			<div>
				<form:label id="assignUnsureLabel" for="assignUnsure" path="assignUnsure" cssErrorClass="error">Unsure?</form:label>
				<form:checkbox id="assignUnsure" path="assignUnsure" cssClass="checkboxPers2"/>
				<form:label id="portraitLabel" for="portrait" path="portrait" cssErrorClass="error">Portrait</form:label>
				<form:checkbox id="portrait" path="portrait" cssClass="checkboxPers2"/>
			</div>
			<div>
				<input id="closePerson" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>
			<input type="hidden" value="" id="modifyPerson" />
		</fieldset>	

		<form:hidden path="epLinkId"/>
		<form:hidden path="personId"/>
		<form:hidden path="entryId"/>
	</form:form>

	<c:url var="searchPersonLinkableToDocumentURL" value="/de/docbase/SearchPersonLinkableToDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>

	<script type="text/javascript"> 
	    $j(document).ready(function() { 
	    	//$j.scrollTo("#EditPersonDocumentForm");
	    	
	    	$j("#EditPersonDocumentForm :input").change(function(){
				$j("#modifyPerson").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
	    	
	    	var peopleDescription = $j('#personDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchPersonLinkableToDocumentURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#personId').val(data); }
			  });

			$j('#closePerson').click(function() {
				peopleDescription.killSuggestions();
				if($j("#modifyPerson").val() == 1){
					$j('#EditPersonDocumentDiv').block({ message: $j('#questionPerson'),
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
					$j("#EditPersonDocumentDiv").html('');				
					
					return false;
				}
			});

			$j("#EditPersonDocumentForm").submit(function (){
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
					$j("#EditPersonDocumentDiv").empty();
					$j("#PeopleCorrespondentsDocumentDiv").load('${EditPeopleDocumentURL}');
				}});
				return false;
			});
		});					  
	</script>

<div id="questionPerson" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yesPerson" value="Yes" /> 
	<input type="button" id="noPerson" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#noPerson').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditPersonDocumentDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yesPerson').click(function() { 
			$j("#EditPersonDocumentDiv").html('');				
				
			return false; 
		}); 
     
	});
</script>