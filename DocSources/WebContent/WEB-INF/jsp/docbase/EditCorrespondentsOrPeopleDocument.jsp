<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<!-- The class assigned to href is the default of jqModal -->
	
	<form:form id="EditCorrespondentsDocumentForm" method="post" cssClass="edit">

		<fieldset>
		<legend><b>CORRESPONDENTS/PEOPLE </b></legend>
			<div>
				<form:label id="senderPeopleDescriptionLabel" for="senderPeopleDescription" path="senderPeopleDescription" cssErrorClass="error">Sender:</form:label>
				<form:input id="senderPeopleDescriptionAutoCompleter" path="senderPeopleDescription" cssClass="input_25c" />
				<form:label id="senderPeopleUnsureLabel" for="senderPeopleUnsure" path="senderPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="senderPeopleUnsure" path="senderPeopleUnsure" cssClass="checkboxPers2"/>
			</div>
			<div>	
				<form:label id="senderPlaceDescriptionLabel" for="senderPlaceDescription" path="senderPlaceDescription" cssErrorClass="error">From:</form:label>
				<form:input id="senderPlaceDescriptionAutoCompleter" path="senderPlaceDescription" cssClass="input_25c" />
				<form:label id="senderPlaceUnsureLabel" for="senderPlaceUnsure" path="senderPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="senderPlaceUnsure" path="senderPlaceUnsure" cssClass="checkboxPers2"/>
			</div>

			<hr />
			
			<div>
				<form:label id="recipientPeopleDescriptionLabel" for="recipientPeopleDescription" path="recipientPeopleDescription" cssErrorClass="error">Recipient:</form:label>
				<form:input id="recipientPeopleDescriptionAutoCompleter" path="recipientPeopleDescription" cssClass="input_25c"/>
				<form:label id="recipientPeopleUnsureLabel" for="recipientPeopleUnsure" path="recipientPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPeopleUnsure" path="recipientPeopleUnsure" cssClass="checkboxPers2"/>
			</div>
			<div>
				<form:label id="recipientPlaceDescriptionLabel" for="recipientPlaceDescription" path="recipientPlaceDescription" cssErrorClass="error">From:</form:label>
				<form:input id="recipientPlaceDescriptionAutoCompleter" path="recipientPlaceDescription" cssClass="input_25c" />
				<form:label id="recipientPlaceUnsureLabel" for="recipientPlaceUnsure" path="recipientPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPlaceUnsure" path="recipientPlaceUnsure" cssClass="checkboxPers2"/>
			</div>
			
			<form:hidden path="senderPeopleId"/>
			<form:hidden path="senderPlaceId"/>
			<form:hidden path="recipientPeopleId"/>
			<form:hidden path="recipientPlaceId"/>	

			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>			
		</fieldset>	
	</form:form>
	
	<form:form id="EditCorrespondentsDocumentForm" method="post" cssClass="edit">
		<fieldset>	
		
			<div>
				<label for="people" id="peopleLabel">People:</label>
			</div>
			<c:forEach items="${command.epLink}" var="currentPeople">
				<c:url var="EditPeopleDocument" value="/de/docbase/EditPeopleDocument.do">
					<c:param name="entryId" value="${currentPeople.document.entryId}" />
					<c:param name="epLinkId" value="${currentPeople.epLinkId}" />
				</c:url>
	
				<c:url var="DeletePeopleDocument" value="/de/docbase/DeletePeopleDocument.do" >
					<c:param name="entryId" value="${currentPeople.document.entryId}" />
					<c:param name="epLinkId" value="${currentPeople.epLinkId}" />
				</c:url>
	
				<div>
					<input id="people_${currentPeople.epLinkId}" name="people" class="input_28c_disabled" type="text" value="${currentPeople.people.mapNameLf}" disabled="disabled"/>
					<a id="deleteValue" href="${DeletePeopleDocument}"><img src="<c:url value="/images/button_cancel_form13.gif"/>" alt="Cancel value" title="Delete this entry"/></a>
					<a id="editValue" href="${EditPeopleDocument}">edit value</a>
				</div>
			</c:forEach>

			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<a id="AddPersonCorrespondentsDocument" href="/DocSources/de/docbase/AddPersonCorrespondentsDocument.html">Add new Person</a>
			</div>	
		</fieldset>
	</form:form>
		
		<div id="AddPersonCorrespondentsDocumentDiv"></div>
			
		<script type="text/javascript">
			$(document).ready(function() {
				$("#AddPersonCorrespondentsDocument").click(function(){$("#AddPersonCorrespondentsDocumentDiv").load($(this).attr("href"));return false;});
				$("#editValue").click(function(){$("#EditCorrespondentsDocumentDiv").load($(this).attr("href"));return false;});
			});
		</script>	

	<c:url var="searchSenderPeopleUrl" value="/de/peoplebase/SearchSenderPeople.json"/>
	<c:url var="searchSenderPlaceUrl" value="/de/geobase/SearchSenderPlace.json"/>
	<c:url var="searchRecipientPeopleUrl" value="/de/peoplebase/SearchRecipientPeople.json"/>
	<c:url var="searchRecipientPlaceUrl" value="/de/geobase/SearchRecipientPlace.json"/>


	<script type="text/javascript">
		$(document).ready(function() {
			var senderPeople = $('#senderPeopleDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchSenderPeopleUrl}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#senderPeopleId').val(data); }
			  });

			var senderPlace = $('#senderPlaceDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchSenderPlaceUrl}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#senderPlaceId').val(data); }
			  });
			
			var recipientPeople = $('#recipientPeopleDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchRecipientPeopleUrl}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#recipientPeopleId').val(data); }
			  });

			var recipientPlace = $('#recipientPlaceDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchRecipientPlaceUrl}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#recipientPlaceId').val(data); }
			  });

			$("#EditDetailsVolume").submit(function (){
				$.post($(this).attr("action"), $(this).serialize(), function() {
					// In questa function si definisce la sostituzione del div dove visualizzare il risultato
					// questa function rappresenta 
					alert('done!');
				});
			});
		});
	</script>


<script type="text/javascript"> 
    $(document).ready(function() { 
		$('#close').click(function() { 
            $('#EditDetailsVolumeDiv').block({ 
                message: '<h1>Discard changes and close window?</h1>', 
                css: { border: '3px solid #a00' } 
            }); 
        }); 
	});					  
</script>
