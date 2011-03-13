<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocument" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
		</c:url>
		<c:url var="AddPersonUrl" value="/de/docbase/EditPersonDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
			<c:param name="epLinkId"  value="0" />
		</c:url>
		<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditCorrespondentsOrPeopleDocumentForm" method="post" cssClass="edit">

		<fieldset>
		<legend><b>CORRESPONDENTS/PEOPLE </b></legend>
			<div>
				<form:label id="senderPeopleDescriptionLabel" for="senderPeopleDescription" path="senderPeopleDescription" cssErrorClass="error">Sender:</form:label>
				<form:input id="senderPeopleDescriptionAutoCompleter" path="senderPeopleDescription" cssClass="input_25c" />
				<form:label id="senderPeopleUnsureLabel" for="senderPeopleUnsure" path="senderPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="senderPeopleUnsure" path="senderPeopleUnsure" cssClass="checkboxPers2"/>
				<a title="Show this person record" id="personIcon" href="#"></a>
			</div>
			<div>	
				<form:label id="senderPlaceDescriptionLabel" for="senderPlaceDescription" path="senderPlaceDescription" cssErrorClass="error">From:</form:label>
				<form:input id="senderPlaceDescriptionAutoCompleter" path="senderPlaceDescription" cssClass="input_25c" />
				<form:label id="senderPlaceUnsureLabel" for="senderPlaceUnsure" path="senderPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="senderPlaceUnsure" path="senderPlaceUnsure" cssClass="checkboxPers2"/>
				<a title="Show this place record" id="placeIcon" href="#"></a>
			</div>

			<hr />
			
			<div>
				<form:label id="recipientPeopleDescriptionLabel" for="recipientPeopleDescription" path="recipientPeopleDescription" cssErrorClass="error">Recipient:</form:label>
				<form:input id="recipientPeopleDescriptionAutoCompleter" path="recipientPeopleDescription" cssClass="input_25c"/>
				<form:label id="recipientPeopleUnsureLabel" for="recipientPeopleUnsure" path="recipientPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPeopleUnsure" path="recipientPeopleUnsure" cssClass="checkboxPers2"/>
				<a title="Show this person record" id="personIcon" href="#"></a>
			</div>
			<div>
				<form:label id="recipientPlaceDescriptionLabel" for="recipientPlaceDescription" path="recipientPlaceDescription" cssErrorClass="error">From:</form:label>
				<form:input id="recipientPlaceDescriptionAutoCompleter" path="recipientPlaceDescription" cssClass="input_25c" />
				<form:label id="recipientPlaceUnsureLabel" for="recipientPlaceUnsure" path="recipientPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPlaceUnsure" path="recipientPlaceUnsure" cssClass="checkboxPers2"/>
				<a title="Show this place record" id="placeIcon" href="#"></a>
			</div>
			
			<form:hidden path="senderPeopleId"/>
			<form:hidden path="senderPlaceId"/>
			<form:hidden path="recipientPeopleId"/>
			<form:hidden path="recipientPlaceId"/>	

			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<input id="save" type="submit" value="" class="button"/>
			</div>			
		</fieldset>	
	</form:form>
	
	<form:form id="EditPeopleDocumentForm" method="post" cssClass="edit">
		<fieldset>	
		
			<div>
				<label for="people" id="peopleLabel">People:</label>
			</div>
		<c:forEach items="${command.document.epLink}" var="currentPerson">
			<c:url var="EditPersonDocumentUrl" value="/de/docbase/EditPersonDocument.do">
				<c:param name="entryId" value="${currentPerson.document.entryId}" />
				<c:param name="epLinkId" value="${currentPerson.epLinkId}" />
			</c:url>

			<c:url var="DeletePersonDocumentUrl" value="/de/docbase/DeletePersonDocument.do" >
				<c:param name="entryId" value="${currentPerson.document.entryId}" />
				<c:param name="epLinkId" value="${currentPerson.epLinkId}" />
			</c:url>

			<div>
				<input id="people_${currentPerson.epLinkId}" name="people" class="input_28c_disabled" type="text" value="${currentPerson.people.mapNameLf}" disabled="disabled"/>
				<a id="deleteIcon" title="Delete this entry" href="${DeletePersonDocumentUrl}"></a>
				<a class="editValue" href="${EditPersonDocumentUrl}">edit value</a>
				<a title="Show this person record" id="personIcon" href="#"></a>
			</div>
		</c:forEach>
			<br>			
			<div>
				<a id="close" href=""></a>
				<a id="AddNewValue" title="Add new person" href="${AddPersonUrl}"></a>
			</div>	
		</fieldset>
	</form:form>
		
	<div id="EditPersonDocumentDiv"></div>
			
	<c:url var="searchSenderPeopleUrl" value="/de/peoplebase/SearchSenderPeople.json"/>
	<c:url var="searchSenderPlaceUrl" value="/de/geobase/SearchSenderPlace.json"/>
	<c:url var="searchRecipientPeopleUrl" value="/de/peoplebase/SearchRecipientPeople.json"/>
	<c:url var="searchRecipientPlaceUrl" value="/de/geobase/SearchRecipientPlace.json"/>


	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden'); 
	        $j("#EditDocumentInManuscriptViewer").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');

			$j('#senderPeopleDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchSenderPeopleUrl}',
			    minChars:3, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#senderPeopleId').val(data); 
			    	}
			  });

			$j('#senderPlaceDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchSenderPlaceUrl}',
			    minChars:5, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:400,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#senderPlaceId').val(data); }
			  });
			
			$j('#recipientPeopleDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchRecipientPeopleUrl}',
			    minChars:3, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#recipientPeopleId').val(data); }
			  });

			$j('#recipientPlaceDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchRecipientPlaceUrl}',
			    minChars:5, 
			    delimiter: null, // regex or character
			    maxHeight:400,
			    width:400,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#recipientPlaceId').val(data); }
			  });

			$j("#save").click(function (){
	 			$j.ajax({ type:"POST", url:$j(this).closest('form').attr("action"), data:$j(this).closest('form').serialize(), async:false, success:function(html) { 
					$j("#EditCorrespondentsOrPeopleDocumentDiv").html(html);
				}});
	 			return false;
			});
			
			$j("#AddNewValue").click(function(){
				$j("#EditPersonDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			$j(".deleteValue").click(function() {
				$j.get($j(this).attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditCorrespondentsOrPeopleDocumentDiv").load('${EditCorrespondentsOrPeopleDocument}');
					}
		        });
				return false;
			});

			$j(".editValue").click(function() {
				$j("#EditPersonDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			$j('#close').click(function() {
				$j('#EditCorrespondentsOrPeopleDocumentDiv').block({ message: $j('#question') }); 
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
			$j.ajax({ url: '${ShowDocument}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>