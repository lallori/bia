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
					<a class="deleteValue" id="deleteValue" href="${DeletePersonDocumentUrl}"><img src="<c:url value="/images/button_cancel_form13.gif"/>" alt="Cancel value" title="Delete this entry"/></a>
					<a class="editValue" id="editValue" href="${EditPersonDocumentUrl}">edit value</a>
				</div>
			</c:forEach>

			<div>
				<a id="AddPersonDocument" href="${AddPersonUrl}">Add new Person</a>
			</div>	
		</fieldset>
	</form:form>
		
	<div id="EditPersonDocumentDiv"></div>
			
	<c:url var="searchSenderPeopleUrl" value="/de/peoplebase/SearchSenderPeople.json"/>
	<c:url var="searchSenderPlaceUrl" value="/de/geobase/SearchSenderPlace.json"/>
	<c:url var="searchRecipientPeopleUrl" value="/de/peoplebase/SearchRecipientPeople.json"/>
	<c:url var="searchRecipientPlaceUrl" value="/de/geobase/SearchRecipientPlace.json"/>


	<script type="text/javascript">
		$(document).ready(function() {
			$('#close').click(function() {
				$('#EditCorrespondentsOrPeopleDocumentDiv').block({ message: $('#question') }); 
				return false;
			});
      
			$('#no').click(function() { 
				$.unblockUI();$(".blockUI").fadeOut("slow");
				return false; 
			}); 
	        
			$('#yes').click(function() { 
				$.ajax({ url: '${ShowDocument}', cache: false, success:function(html) { 
					$("#body_left").html(html);
				}});
					
				return false; 
			}); 

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
			    minChars:5, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:400,
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
			    minChars:5, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:400,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#recipientPlaceId').val(data); }
			  });

			$('#close').click(function() {
	            $('#EditCorrespondentsOrPeopleDocumentDiv').block({ message: $('#question') }); 
				return false;
			});
	        
			$('#no').click(function() { 
				$.unblockUI();$(".blockUI").fadeOut("slow");
	            return false; 
	        }); 
	        
			$('#yes').click(function() { 
				$.ajax({ url: '${ShowDocument}', cache: false, success:function(html) { 
					$("#body_left").html(html);
	 			}});
				
				return false; 
	        }); 

			$("#EditCorrespondentsOrPeopleDocumentForm").submit(function (){
	 			$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
					$("#EditCorrespondentsOrPeopleDocumentDiv").html(html);
				}});
			});

			$("#AddPersonDocument").click(function(){$("#EditPersonDocumentDiv").load($(this).attr("href"));return false;});

			$(".deleteValue").click(function() {
				$.get(this.href, function(data) {
					if(data.match(/KO/g)){
			            var resp = $('<div></div>').append(data); // wrap response
					} else {
						$("#EditCorrespondentsOrPeopleDocumentDiv").load('${EditCorrespondentsOrPeopleDocument}');
					}
		        });
				return false;
			});

			$(".editValue").click(function() {
				$("#EditPersonDocumentDiv").load($(this).attr("href"));return false;
			});

		});
	</script>
