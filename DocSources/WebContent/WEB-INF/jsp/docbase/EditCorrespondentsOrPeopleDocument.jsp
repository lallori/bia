<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocumentURL" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
		</c:url>
		<c:url var="AddPersonURL" value="/de/docbase/EditPersonDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
			<c:param name="epLinkId"  value="0" />
		</c:url>
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
		</c:url>
		
		<c:url var="CompareSenderURL" value="/src/peoplebase/ComparePerson.do">
			<c:param name="personId"   value="${command.document.senderPeople.personId}" />
		</c:url>
		
		<c:url var="CompareRecipientURL" value="/src/peoplebase/ComparePerson.do">
			<c:param name="personId"   value="${command.document.recipientPeople.personId}" />
		</c:url>
		
		<c:url var="CompareFromURL" value="/src/geobase/ComparePlace.do">
			<c:param name="placeAllId" value="${command.document.senderPlace.placeAllId}" />
		</c:url>
	
		<c:url var="CompareToURL" value="/src/geobase/ComparePlace.do">
			<c:param name="placeAllId" value="${command.document.recipientPlace.placeAllId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditCorrespondentsOrPeopleDocumentForm" method="post" cssClass="edit">

		<fieldset>
		<legend><b>CORRESPONDENTS </b></legend>
			<div>
				<form:label id="senderPeopleDescriptionLabel" for="senderPeopleDescription" path="senderPeopleDescription" cssErrorClass="error">Sender</form:label>
				<form:input id="senderPeopleDescriptionAutoCompleter" path="senderPeopleDescription" cssClass="input_25c" />
				<form:label id="senderPeopleUnsureLabel" for="senderPeopleUnsure" path="senderPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="senderPeopleUnsure" path="senderPeopleUnsure" cssClass="checkboxPers2"/>
				<c:if test="${command.document.senderPeople.personId != 9285 && command.document.senderPeople.personId != 3905 && command.document.senderPeople.personId != 198}">
					<a title="Show this person record" id="personIcon" class="senderLinkPeople" href="${CompareSenderURL}"></a>
				</c:if>
				<c:if test="${command.document.senderPeople.personId == 9285 || command.document.senderPeople.personId == 3905 || command.document.senderPeople.personId == 198 || command.document.senderPeople.personId == null}">
					<a title="Show this person record" id="personIcon" class="senderLinkPeople"></a>
				</c:if>
			</div>
			<div>	
				<form:label id="senderPlaceDescriptionLabel" for="senderPlaceDescription" path="senderPlaceDescription" cssErrorClass="error">From</form:label>
				<form:input id="senderPlaceDescriptionAutoCompleter" path="senderPlaceDescription" cssClass="input_25c" />
				<form:label id="senderPlaceUnsureLabel" for="senderPlaceUnsure" path="senderPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="senderPlaceUnsure" path="senderPlaceUnsure" cssClass="checkboxPers2"/>
				<c:if test="${command.document.senderPlace.placeAllId != 53384 && command.document.senderPlace.placeAllId != 55627 && command.document.senderPlace.placeAllId != 54332}">
					<a title="Show this place record" id="placeIcon" class="senderLinkPlace" href="${CompareFromURL}"></a>
				</c:if>
				<c:if test="${command.document.senderPlace.placeAllId == 53384 || command.document.senderPlace.placeAllId == 55627 || command.document.senderPlace.placeAllId == 54332 || command.document.senderPlace.placeAllId == 0}">
					<a title="Show this place record" id="placeIcon" class="senderLinkPlace"></a>
				</c:if>
			</div>

			<hr />
			
			<div>
				<form:label id="recipientPeopleDescriptionLabel" for="recipientPeopleDescription" path="recipientPeopleDescription" cssErrorClass="error">Recipient</form:label>
				<form:input id="recipientPeopleDescriptionAutoCompleter" path="recipientPeopleDescription" cssClass="input_25c"/>
				<form:label id="recipientPeopleUnsureLabel" for="recipientPeopleUnsure" path="recipientPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPeopleUnsure" path="recipientPeopleUnsure" cssClass="checkboxPers2"/>
				<c:if test="${command.document.recipientPeople.personId != 9285 && command.document.recipientPeople.personId != 3905 && command.document.recipientPeople.personId != 198}">
					<a title="Show this person record" id="personIcon" class="recipientLinkPeople" href="${CompareRecipientURL}"></a>
				</c:if>
				<c:if test="${command.document.recipientPeople.personId == 9285 || command.document.recipientPeople.personId == 3905 || command.document.recipientPeople.personId == 198 || command.document.recipientPeople.personId == 0}">
					<a title="Show this person record" id="personIcon" class="recipientLinkPeople"></a>
				</c:if>
			</div>
			<div>
				<form:label id="recipientPlaceDescriptionLabel" for="recipientPlaceDescription" path="recipientPlaceDescription" cssErrorClass="error">From</form:label>
				<form:input id="recipientPlaceDescriptionAutoCompleter" path="recipientPlaceDescription" cssClass="input_25c" />
				<form:label id="recipientPlaceUnsureLabel" for="recipientPlaceUnsure" path="recipientPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPlaceUnsure" path="recipientPlaceUnsure" cssClass="checkboxPers2"/>
				<c:if test="${command.document.recipientPlace.placeAllId != 53384 && command.document.recipientPlace.placeAllId != 55627 && command.document.recipientPlace.placeAllId != 54332}">
					<a title="Show this place record" id="placeIcon" class="recipientLinkPlace" href="${CompareToURL}"></a>
				</c:if>
				<c:if test="${command.document.recipientPlace.placeAllId == 53384 || command.document.recipientPlace.placeAllId == 55627 || command.document.recipientPlace.placeAllId == 54332 || command.document.recipientPlace.placeAllId == 0}">
					<a title="Show this place record" id="placeIcon" class="recipientLinkPlace"></a>
				</c:if>
			</div>
			
			<form:hidden path="senderPeopleId"/>
			<form:hidden path="senderPlaceId"/>
			<form:hidden path="senderPlacePrefered"/>
			<form:hidden path="recipientPeopleId"/>
			<form:hidden path="recipientPlaceId"/>
			<form:hidden path="recipientPlacePrefered"/>	

			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>			
		</fieldset>	
	</form:form>
	
	<div id="PeopleCorrespondentsDocumentDiv">
	<form:form id="PeopleCorrespondentsDocumentsForm" method="post" cssClass="edit">
		<fieldset>	
			<legend><b>PEOPLE</b></legend>
			<br />
			<div>
				Individuals and corporate bodies indicated in the document extract:
			</div>
			<br />
		<c:forEach items="${command.document.epLink}" var="currentPersonLinked">
			<c:if test="${currentPersonLinked.docRole!= 'S' && currentPersonLinked.docRole != 'R'}">
			<c:url var="EditPersonDocumentURL" value="/de/docbase/EditPersonDocument.do">
				<c:param name="entryId" value="${currentPersonLinked.document.entryId}" />
				<c:param name="epLinkId" value="${currentPersonLinked.epLinkId}" />
			</c:url>

			<c:url var="DeletePersonDocumentURL" value="/de/docbase/DeletePersonDocument.do" >
				<c:param name="entryId" value="${currentPersonLinked.document.entryId}" />
				<c:param name="epLinkId" value="${currentPersonLinked.epLinkId}" />
			</c:url>

			<div>
				<input id="people_${currentPersonLinked.epLinkId}" name="people" class="input_28c_disabled" type="text" value="${currentPersonLinked.person.mapNameLf}" disabled="disabled"/>
				<a class="deleteIcon" title="Delete this entry" href="${DeletePersonDocumentURL}"></a>
				<a class="editValue" href="${EditPersonDocumentURL}">edit value</a>
				<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentPersonLinked.person.personId}" />
					</c:url>
				<a title="Show this person record" id="personIcon" href="${ComparePersonURL}" class="linkPeople"></a>
			</div>
			</c:if>
		</c:forEach>
			<br>			
			<div>
				<input id="close" type="submit" value="Close" title="Do not save changes" class="closeForm"/>
				<a id="AddNewValue" title="Add new person" href="${AddPersonURL}">Add</a>
			</div>
		</fieldset>
	</form:form>
	</div>
		
	<div id="EditPersonDocumentDiv"></div>
			
	<c:url var="searchSenderPeopleURL" value="/de/peoplebase/SearchSenderPeople.json"/>
	<c:url var="searchSenderPlaceURL" value="/de/geobase/SearchSenderPlace.json"/>
	<c:url var="searchRecipientPeopleURL" value="/de/peoplebase/SearchRecipientPeople.json"/>
	<c:url var="searchRecipientPlaceURL" value="/de/geobase/SearchRecipientPlace.json"/>
	
	<c:url var="ShowSenderPlaceDetailsURL" value="/de/geobase/ShowSenderPlaceDetails.json" />
	<c:url var="ShowRecipientPlaceDetailsURL" value="/de/geobase/ShowRecipientPlaceDetails.json" />


	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden'); 
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');

			$j('#senderPeopleDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchSenderPeopleURL}',
			    minChars:3, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){
				    //the following code is for "refresh" the link to view the tab of sender people 
				   	$j('#senderPeopleId').val(data);
				    if(data != 9285 && data != 3905){
			    		var link = '<c:url value="/src/peoplebase/ComparePerson.do?personId=" />';
			    		link += data;
			    		$j('.senderLinkPeople').attr("href", link);
				    }else{
				    	$j('.senderLinkPeople').removeAttr("href");
				    }
			    }			    
			  });
			
			$j('.senderLinkPeople').click(function(){
				if($j('#senderPeopleDescriptionAutoCompleter').val() == '')
					$j('.senderLinkPeople').attr("href", null);
			});

			
			$j('#senderPlaceDescriptionAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${searchSenderPlaceURL}',
			    minChars:5, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:400,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#senderPlaceId').val(data);
			    	if(data != 53384 && data != 55627 && data != 54332){
			    		var link = '<c:url value="/src/geobase/ComparePlace.do?placeAllId=" />';
			    		link += data;
			    		$j('.senderLinkPlace').attr("href", link);
				    }else{
				    	$j('.senderLinkPlace').removeAttr("href");
				    }
			    	$j.get("${ShowSenderPlaceDetailsURL}", { placeAllId: "" + data }, function(data) {
			    		$j('#senderPlacePrefered').val(data.prefFlag);
			    	});
			    }
			  });
			
			$j('.senderLinkPlace').click(function(){
				if($j('#senderPlaceDescriptionAutoCompleter').val() == '')
					$j('.senderLinkPlace').attr("href", null);
			});
			
			$j('#recipientPeopleDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchRecipientPeopleURL}',
			    minChars:3, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#recipientPeopleId').val(data);
			    	if(data != 9285 && data != 3905){
			    		var link = '<c:url value="/src/peoplebase/ComparePerson.do?personId=" />';
			    		link += data;
			    		$j('.recipientLinkPeople').attr("href", link);
				    }else{
				    	$j('.recipientLinkPeople').removeAttr("href");
				    }
			    }
			  });
			
			$j('.recipientLinkPeople').click(function(){
				if($j('#recipientPeopleDescriptionAutoCompleter').val() == '')
					$j('.recipientLinkPeople').attr('href', null);
			});

			$j('#recipientPlaceDescriptionAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${searchRecipientPlaceURL}',
			    minChars:5, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:400,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#recipientPlaceId').val(data);
			    	if(data != 53384 && data != 55627 && data != 54332){
			    		var link = '<c:url value="/src/geobase/ComparePlace.do?placeAllId=" />';
			    		link += data;
			    		$j('.recipientLinkPlace').attr("href", link);
				    }else{
				    	$j('.recipientLinkPlace').removeAttr("href");
				    }
			    	$j.get("${ShowRecipientPlaceDetailsURL}", { placeAllId: "" + data }, function(data) {
			    		$j('#recipientPlacePrefered').val(data.prefFlag);
			    	});
			    	}
			  });
			
			$j('.recipientLinkPlace').click(function(){
				if($j('#recipientPlaceDescriptionAutoCompleter').val() == '')
					$j('.recipientLinkPlace').attr('href', null);
			});

			$j("#EditCorrespondentsOrPeopleDocumentForm").submit(function (){
				if($j("#senderPlacePrefered").val() == 'V' || $j("#recipientPlacePrefered").val() == 'V'){
					$j('#EditCorrespondentsDocumentDiv').block({ message: $j('.notPrincipal') });
					return false;
				}else{
	 			$j.ajax({ type:"POST", url:$j(this).closest('form').attr("action"), data:$j(this).closest('form').serialize(), async:false, success:function(html) { 
					$j("#EditCorrespondentsDocumentDiv").html(html);
				}});
	 			return false;
				}
			});
			
			$j("#AddNewValue").click(function(){
				$j("#EditPersonDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			$j(".deleteIcon").click(function() {
				var temp = $j(this);
				$j("#PeopleCorrespondentsDocumentDiv").block({ message: $j("#questionPerson")});
				
				$j('#personNo').click(function() {
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j("#questionPerson").hide();
					$j("#PeopleCorrespondentsDocumentDiv").append($j("#questionPerson"));
					$j(".blockUI").remove();
					return false; 
				}); 

				$j('#personYes').click(function() { 
					$j.get(temp.attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditCorrespondentsDocumentDiv").load('${EditCorrespondentsOrPeopleDocumentURL}');
					}
					return false;
		        });
				});
				return false;
			});

			$j(".editValue").click(function() {
				$j("#EditPersonDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			$j('#close').click(function() {
				$j('.autocomplete').remove();
				$j('#EditCorrespondentsDocumentDiv').block({ message: $j('#question') }); 
				return false;
			});
			
			$j('.closeForm').click(function(){
				$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
					
				return false; 
			});

			$j('.senderLinkPeople').click(function() {
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j("#senderPeopleDescriptionAutoCompleter").val() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});
			
			$j('.senderLinkPlace').click(function() {
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j("#senderPlaceDescriptionAutoCompleter").val() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});
			
			$j('.recipientLinkPeople').click(function() {
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j("#recipientPeopleDescriptionAutoCompleter").val() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});
			
			$j('.recipientLinkPlace').click(function() {
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j("#recipientPlaceDescriptionAutoCompleter").val() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});

			$j('.linkPeople').click(function() {
				var tabName = $j(this).parent();
				tabName = $j(tabName).find('.input_28c_disabled');
				
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1>discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<div id="questionPlace" class="notPrincipal" style="display:none; cursor: default">
		<h1>This name place is classified as a Variant Name and will be adjusted to its Preferred Name</h1>
		<input type="button" id="ok" value="Ok" />
	</div>
	
<div id="questionPerson" style="display:none; cursor: default"> 
		<h1>Delete this Person entry?</h1> 
		<input type="button" id="personYes" value="Yes" /> 
		<input type="button" id="personNo" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditCorrespondentsDocumentDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
		
		$j("#ok").click(function(){
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j(".notPrincipal").hide();
			$j("#EditCorrespondentsDocumentDiv").append($j(".notPrincipal"));
			$j(".blockUI").remove();
			$j.ajax({ type:"POST", url:$j("#EditCorrespondentsOrPeopleDocumentForm").closest('form').attr("action"), data:$j("#EditCorrespondentsOrPeopleDocumentForm").closest('form').serialize(), async:false, success:function(html) { 
				$j("#EditCorrespondentsDocumentDiv").html(html);
			}});
			return false;
		});
     
	});
</script>