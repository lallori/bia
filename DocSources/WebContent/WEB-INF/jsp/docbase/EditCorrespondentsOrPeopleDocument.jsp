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
	</security:authorize>

	<form:form id="EditCorrespondentsOrPeopleDocumentForm" method="post" cssClass="edit">

		<fieldset>
		<legend><b>CORRESPONDENTS </b></legend>
			<div>
				<form:label id="senderPeopleDescriptionLabel" for="senderPeopleDescription" path="senderPeopleDescription" cssErrorClass="error">Sender</form:label>
				<form:input id="senderPeopleDescriptionAutoCompleter" path="senderPeopleDescription" cssClass="input_25c" />
				<form:label id="senderPeopleUnsureLabel" for="senderPeopleUnsure" path="senderPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="senderPeopleUnsure" path="senderPeopleUnsure" cssClass="checkboxPers2"/>
				<a title="Show this person record" id="personIcon" class="senderLinkPeople" href="${CompareSenderURL}"></a>
			</div>
			<div>	
				<form:label id="senderPlaceDescriptionLabel" for="senderPlaceDescription" path="senderPlaceDescription" cssErrorClass="error">From</form:label>
				<form:input id="senderPlaceDescriptionAutoCompleter" path="senderPlaceDescription" cssClass="input_25c" />
				<form:label id="senderPlaceUnsureLabel" for="senderPlaceUnsure" path="senderPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="senderPlaceUnsure" path="senderPlaceUnsure" cssClass="checkboxPers2"/>
				<a title="Show this place record" id="placeIcon" href="#"></a>
			</div>

			<hr />
			
			<div>
				<form:label id="recipientPeopleDescriptionLabel" for="recipientPeopleDescription" path="recipientPeopleDescription" cssErrorClass="error">Recipient</form:label>
				<form:input id="recipientPeopleDescriptionAutoCompleter" path="recipientPeopleDescription" cssClass="input_25c"/>
				<form:label id="recipientPeopleUnsureLabel" for="recipientPeopleUnsure" path="recipientPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPeopleUnsure" path="recipientPeopleUnsure" cssClass="checkboxPers2"/>
				<a title="Show this person record" id="personIcon" class="linkPeople" href="${CompareRecipientURL}"></a>
			</div>
			<div>
				<form:label id="recipientPlaceDescriptionLabel" for="recipientPlaceDescription" path="recipientPlaceDescription" cssErrorClass="error">From</form:label>
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
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>			
		</fieldset>	
	</form:form>
	
	<form:form id="PeopleCorrespondentsDocumentsForm" method="post" cssClass="edit">
		<fieldset>	
			<legend><b>PEOPLE</b></legend>
			<br />
			<div>
				Individuals and corporate bodies indicated in the document extract:
			</div>
			<br />
		<c:forEach items="${command.document.epLink}" var="currentPersonLinked">
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
		</c:forEach>
			<br>			
			<div>
				<a id="AddNewValue" title="Add new person" href="${AddPersonURL}">Add</a>
			</div>
			<img src="/DocSources/images/1024/img_transparent.png">
			<br>
			<br>
		</fieldset>
	</form:form>
		
	<div id="EditPersonDocumentDiv"></div>
			
	<c:url var="searchSenderPeopleURL" value="/de/peoplebase/SearchSenderPeople.json"/>
	<c:url var="searchSenderPlaceURL" value="/de/geobase/SearchSenderPlace.json"/>
	<c:url var="searchRecipientPeopleURL" value="/de/peoplebase/SearchRecipientPeople.json"/>
	<c:url var="searchRecipientPlaceURL" value="/de/geobase/SearchRecipientPlace.json"/>


	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden'); 
	        $j("#EditDocumentInManuscriptViewer").css('visibility', 'hidden');
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
				    var oldSender = $j('#senderPeopleId').val(); 
			    	$j('#senderPeopleId').val(data);
			    	var link = $j('.senderLinkPeople').attr("href");
			    	if(oldSender == ""){
				    	link += data;
			    	}else{			    	
			    		link = link.replace(oldSender, data);
			    	} 
			    	$j('.senderLinkPeople').attr("href", link);
			    }			    
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
			    onSelect: function(value, data){ $j('#senderPlaceId').val(data); }
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
			    onSelect: function(value, data){ $j('#recipientPeopleId').val(data); }
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
			    onSelect: function(value, data){ $j('#recipientPlaceId').val(data); }
			  });

			$j("#save").click(function (){
	 			$j.ajax({ type:"POST", url:$j(this).closest('form').attr("action"), data:$j(this).closest('form').serialize(), async:false, success:function(html) { 
					$j("#EditCorrespondentsDocumentDiv").html(html);
				}});
	 			return false;
			});
			
			$j("#AddNewValue").click(function(){
				$j("#EditPersonDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			$j(".deleteIcon").click(function() {
				$j.get($j(this).attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditCorrespondentsDocumentDiv").load('${EditCorrespondentsDocument}');
					}
		        });
				return false;
			});

			$j(".editValue").click(function() {
				$j("#EditPersonDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			$j('#close').click(function() {
				$j('#EditCorrespondentsDocumentDiv').block({ message: $j('#question') }); 
				return false;
			});

			$j('.senderLinkPeople').click(function() {
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j("#senderPeopleDescriptionAutoCompleter").val() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});

			$j('.linkPeople').click(function() {
				var nome = $j(this).parent();
				nome = $j(nome).find('.input_28c_disabled');
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(nome).val() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
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
     
	});
</script>