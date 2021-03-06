<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
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
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditCorrespondentsOrPeopleDocumentForm" method="post" cssClass="edit">

		<fieldset>
		<legend><b><fmt:message key="docbase.editCorrespondentsOrPeople.title.correspondents"/></b></legend>
			<div class="listForm">
				<div class="row">
					<div class="col_l"><form:label id="senderPeopleDescriptionLabel" for="senderPeopleDescription" path="senderPeopleDescription" cssErrorClass="error"><fmt:message key="docbase.editCorrespondentsOrPeople.sender"/></form:label></div>
					<div class="col_l"><form:input id="senderPeopleDescriptionAutoCompleter" path="senderPeopleDescription" cssClass="input_25c" /></div>
					<div class="col_l">
						<form:label id="senderPeopleUnsureLabel" for="senderPeopleUnsure" path="senderPeopleUnsure"><fmt:message key="docbase.editCorrespondentsOrPeople.unsure"/></form:label>
						<form:checkbox id="senderPeopleUnsure" path="senderPeopleUnsure"/>
					</div>
					<div class="col_l">
						<!-- RR: personId = 198 equivalent to "To Be Entered" - 3905 equivalent to "Not Relevant in this Entry" - 9285 equivalent to "Person Name Lost, Not Indicated or Unidentifiable" -->
						<c:if test="${command.document.senderPeople.personId != 9285 && command.document.senderPeople.personId != 3905 && command.document.senderPeople.personId != 198}">
							<a title="Show this person record" id="personIcon" class="senderLinkPeople" href="${CompareSenderURL}"><input type="hidden" style="display:none;" class="tabId" value="peopleId${command.document.senderPeople.personId}" /></a>
						</c:if>
						<c:if test="${command.document.senderPeople.personId == 9285 || command.document.senderPeople.personId == 3905 || command.document.senderPeople.personId == 198}">
							<a title="Show this person record" id="personIcon" class="senderLinkPeople"></a>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="col_l"><form:label id="senderPlaceDescriptionLabel" for="senderPlaceDescription" path="senderPlaceDescription" cssErrorClass="error"><fmt:message key="docbase.editCorrespondentsOrPeople.location"/></form:label></div>
					<div class="col_l"><form:input id="senderPlaceDescriptionAutoCompleter" path="senderPlaceDescription" cssClass="input_25c" /></div>
					<div class="col_l">
						<form:label id="senderPlaceUnsureLabel" for="senderPlaceUnsure" path="senderPlaceUnsure"><fmt:message key="docbase.editCorrespondentsOrPeople.unsure"/></form:label>
						<form:checkbox id="senderPlaceUnsure" path="senderPlaceUnsure"/>
					</div>
					<div class="col_l">
						<!-- RR: placeAllId = 53384 equivalent to "Place Name Lost, Not Indicated or Unidentifable" - 54332 equivalent to "Not Relevant in this Entry" - 55627 equivalent to "To Be Entered" -->
						<c:if test="${command.document.senderPlace.placeAllId != 53384 && command.document.senderPlace.placeAllId != 55627 && command.document.senderPlace.placeAllId != 54332}">
							<a title="Show this place record" id="placeIcon" class="senderLinkPlace" href="${CompareFromURL}"></a>
						</c:if>
						<c:if test="${command.document.senderPlace.placeAllId == 53384 || command.document.senderPlace.placeAllId == 55627 || command.document.senderPlace.placeAllId == 54332}">
							<a title="Show this place record" id="placeIcon" class="senderLinkPlace"></a>
						</c:if>
					</div>
				</div>
			</div>
			
			<br />
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="docbase.editCorrespondentsOrPeopleDocument.help.sendernotes"></fmt:message>">?</a>
					<form:label id="senderNotesLabel" for="sendNotes" path="sendNotes"><fmt:message key="docbase.editCorrespondentsOrPeople.senderNotes"/></form:label>
				</div>
				<div class="row">
					<form:textarea path="sendNotes" id="senderNotes" class="txtarea" name="senderNotes"/>
				</div>
			</div>

			<hr />
			
			<div class="listForm">
				<div class="row">
					<div class="col_l"><form:label id="recipientPeopleDescriptionLabel" for="recipientPeopleDescription" path="recipientPeopleDescription" cssErrorClass="error"><fmt:message key="docbase.editCorrespondentsOrPeople.recipient"/></form:label></div>
					<div class="col_l"><form:input id="recipientPeopleDescriptionAutoCompleter" path="recipientPeopleDescription" cssClass="input_25c"/></div>
					<div class="col_l">
						<form:label id="recipientPeopleUnsureLabel" for="recipientPeopleUnsure" path="recipientPeopleUnsure"><fmt:message key="docbase.editCorrespondentsOrPeople.unsure"/></form:label>
						<form:checkbox id="recipientPeopleUnsure" path="recipientPeopleUnsure"/>
					</div>
					<div class="col_l">
						<c:if test="${command.document.recipientPeople.personId != 9285 && command.document.recipientPeople.personId != 3905 && command.document.recipientPeople.personId != 198}">
							<a title="Show this person record" id="personIcon" class="recipientLinkPeople" href="${CompareRecipientURL}"><input type="hidden" style="display:none;" class="tabId" value="peopleId${command.document.recipientPeople.personId}" /></a>
						</c:if>
						<c:if test="${command.document.recipientPeople.personId == 9285 || command.document.recipientPeople.personId == 3905 || command.document.recipientPeople.personId == 198}">
							<a title="Show this person record" id="personIcon" class="recipientLinkPeople"></a>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="col_l"><form:label id="recipientPlaceDescriptionLabel" for="recipientPlaceDescription" path="recipientPlaceDescription" cssErrorClass="error"><fmt:message key="docbase.editCorrespondentsOrPeople.location"/></form:label></div>
					<div class="col_l"><form:input id="recipientPlaceDescriptionAutoCompleter" path="recipientPlaceDescription" cssClass="input_25c" /></div>
					<div class="col_l">
						<form:label id="recipientPlaceUnsureLabel" for="recipientPlaceUnsure" path="recipientPlaceUnsure"><fmt:message key="docbase.editCorrespondentsOrPeople.unsure"/></form:label>
						<form:checkbox id="recipientPlaceUnsure" path="recipientPlaceUnsure"/>
					</div>
					<div class="col_l">
						<c:if test="${command.document.recipientPlace.placeAllId != 53384 && command.document.recipientPlace.placeAllId != 55627 && command.document.recipientPlace.placeAllId != 54332}">
							<a title="Show this place record" id="placeIcon" class="recipientLinkPlace" href="${CompareToURL}"></a>
						</c:if>
						<c:if test="${command.document.recipientPlace.placeAllId == 53384 || command.document.recipientPlace.placeAllId == 55627 || command.document.recipientPlace.placeAllId == 54332}">
							<a title="Show this place record" id="placeIcon" class="recipientLinkPlace"></a>
						</c:if>
					</div>
				</div>
			</div>
			
			<br />
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="docbase.editCorrespondentsOrPeopleDocument.help.recipientnotes"></fmt:message>">?</a>
					<form:label for="recipNotes" id="recipientNotesLabel" path="recipNotes"><fmt:message key="docbase.editCorrespondentsOrPeople.recipientNotes"/></form:label>
				</div>
				<div class="row"><form:textarea path="recipNotes" id="recipientNotes" name="recipientNotes" class="txtarea"/></div>
			</div>
			
			<form:hidden path="senderPeopleId"/>
			<form:hidden path="senderPlaceId"/>
			<form:hidden path="senderPlacePrefered"/>
			<form:hidden path="recipientPeopleId"/>
			<form:hidden path="recipientPlaceId"/>
			<form:hidden path="recipientPlacePrefered"/>	
			<input type="hidden" value="0" id="fromPersonForm" />

			<div>
				<input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
			</div>
			<input type="hidden" value="" id="modify" />			
		</fieldset>	
	</form:form>
	
	<div id="PeopleCorrespondentsDocumentDiv">
	<form:form id="PeopleCorrespondentsDocumentsForm" method="post" cssClass="edit">
		<fieldset>	
			<legend><b><fmt:message key="docbase.editCorrespondentsOrPeople.title.people"/></b></legend>
			<p><fmt:message key="docbase.editCorrespondentsOrPeople.subtitle.people"/>:</p>
			
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
			
			<div class="listForm">
				<div class="row">
					<div class="col_l"><input id="people_${currentPersonLinked.epLinkId}" name="people" class="input_35c_disabled" type="text" value="${currentPersonLinked.person.mapNameLf}" disabled="disabled"/></div>
					<div class="col_l"><a class="deleteIcon" title="Delete this entry" href="${DeletePersonDocumentURL}"></a></div>
					<div class="col_l"><a class="editValue" href="${EditPersonDocumentURL}" title="Edit this entry"></a></div>
					<div class="col_l">
						<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
							<c:param name="personId"   value="${currentPersonLinked.person.personId}" />
						</c:url>
						<a title="Show this person record" id="personIcon" href="${ComparePersonURL}" class="linkPeople"><input type="hidden" style="display:none;" class="tabId" value="peopleId${currentPersonLinked.person.personId}" /></a>
					</div>
				</div>
			</div>
			
			</c:if>
		</c:forEach>
			<br>			
			<div>
				<input id="closePeople" type="submit" value="Close" title="Do not save changes" class="closeForm button_small fl"/>
				<input id="AddNewValue" class="button_small fr" type="submit" value="Add" title="Add new Person" />
			</div>
		</fieldset>
	</form:form>
	</div>
		
	<div id="EditPersonDocumentDiv"></div>		
	<c:url var="searchSenderPeopleURL" value="/de/peoplebase/SearchSenderPeople.json">
		<c:param name="entryId"	value="${command.document.entryId}" />
	</c:url>
	<c:url var="searchSenderPlaceURL" value="/de/geobase/SearchSenderPlace.json"/>
	<c:url var="searchRecipientPeopleURL" value="/de/peoplebase/SearchRecipientPeople.json">
		<c:param name="entryId"	value="${command.document.entryId}" />
	</c:url>
	<c:url var="searchRecipientPlaceURL" value="/de/geobase/SearchRecipientPlace.json"/>
	
	<c:url var="ShowSenderPlaceDetailsURL" value="/de/geobase/ShowSenderPlaceDetails.json" />
	<c:url var="ShowRecipientPlaceDetailsURL" value="/de/geobase/ShowRecipientPlaceDetails.json" />

	<script type="text/javascript">
		$j(document).ready(function() {
			if($j("#fromPersonForm").val() == 0){
				$j.scrollTo("#EditCorrespondentsOrPeopleDocumentForm");
			}else{
				$j.scrollTo("#PeopleCorrespondentsDocumentsForm");
				$j("#fromPersonForm").val(0);
			}
			
	        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden'); 
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'hidden');
	        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');
	        
	        $j("#EditCorrespondentsOrPeopleDocumentForm :input").change(function(){
				$j("#modify").val(1); //set the hidden field if an element is modified
				return false;
			});
	        
	        var $senderPeopleAutoComplete = $j('#senderPeopleDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchSenderPeopleURL}',
			    loadingImageUrl:'${LoadingImageURL}',
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
			    		$j('.senderLinkPeople').find('.tabId').val('peopleId' + data);
				    }else{
				    	$j('.senderLinkPeople').removeAttr("href");
				    }
			    }			    
			  });
	        
	        //MD: Introduce this code for close the autocompleter on blur event
	        $j('#senderPeopleDescriptionAutoCompleter').blur(function(){
	        	$senderPeopleAutoComplete.killSuggestions();
	        	return false;
	        });
			
			$j('.senderLinkPeople').click(function(){
				if($j('#senderPeopleDescriptionAutoCompleter').val() == '')
					$j('.senderLinkPeople').attr("href", null);
			});

			
			$j('#senderPlaceDescriptionAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${searchSenderPlaceURL}',
			    minChars:3, 
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
			    		$j('.recipientLinkPeople').find('.tabId').val('peopleId' + data);
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
			    minChars:3, 
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
				if($j("#senderPeopleId, #recipientPeopleId").val() != "" && $j("#senderPeopleId").val() == $j("#recipientPeopleId").val()){
					$j('#EditCorrespondentsDocumentDiv').block({ message: $j('.questionSendRecip') });
					return false;
				}
				if($j("#senderPlacePrefered").val() == 'V' || $j("#recipientPlacePrefered").val() == 'V'){
					$j('#EditCorrespondentsDocumentDiv').block({ message: $j('.notPrincipal') });
					return false;
				}else{
					$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
		        	$j("#loadingDiv").css('visibility', 'visible');
	 				$j.ajax({ type:"POST", url:$j(this).closest('form').attr("action"), data:$j(this).closest('form').serialize(), async:false, success:function(html) { 
						$j("#body_left").html(html);
					}});
	 				return false;
				}
			});
			
			$j("#AddNewValue").click(function(){
				$j("#EditPersonDocumentDiv").load("${AddPersonURL}");
				return false;
			});

			$j(".deleteIcon").click(function() {
				var temp = $j(this);
				$j("#PeopleCorrespondentsDocumentDiv").block({ message: $j(".questionPerson")});
				
				$j('.personNo').click(function() {
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j(".questionPerson").hide();
					$j("#PeopleCorrespondentsDocumentDiv").append($j(".questionPerson"));
					$j(".blockUI").remove();
					$j("#EditCorrespondentsDocumentDiv").load('${EditCorrespondentsOrPeopleDocumentURL}');
					return false; 
				}); 

				$j('.personYes').click(function() { 
					$j.get(temp.attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditCorrespondentsDocumentDiv").load('${EditCorrespondentsOrPeopleDocumentURL}');
						return false;
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
				if($j("#modify").val() == 1){
					$j('#EditCorrespondentsDocumentDiv').block({ message: $j('#question'), 
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
			
			$j("#closePeople").die();
			$j('#closePeople').live('click', function(){
				if($j("#modify").val() == 1){
					$j('#EditCorrespondentsDocumentDiv').block({ message: $j('#question'), 
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
				}
					
				return false; 
			});

			$j('.senderLinkPeople').click(function() {
				var tabName = $j("#senderPeopleDescriptionAutoCompleter").val();
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j('.senderLinkPlace').click(function() {
				var tabName = $j("#senderPlaceDescriptionAutoCompleter").val();
					
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});
			
			$j('.recipientLinkPeople').click(function() {
				var tabName = $j("#recipientPeopleDescriptionAutoCompleter").val();
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j('.recipientLinkPlace').click(function() {
				var tabName = $j("#recipientPlaceDescriptionAutoCompleter").val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});

			$j('.linkPeople').click(function() {
				var tabName = $j(this).parent().parent();
				tabName = $j(tabName).find('.input_35c_disabled').val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
		});
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1><fmt:message key="docbase.editCorrespondentsOrPeopleDocument.discardChangesQuestion"/></h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<div id="questionPlace" class="notPrincipal" style="display:none; cursor: default">
		<h1><fmt:message key="docbase.editCorrespondentsOrPeopleDocument.thisNamePlace"/></h1>
		<input type="button" id="ok" value="Ok" />
	</div>
	
<div class="questionPerson" style="display:none; cursor: default"> 
		<h1><fmt:message key="docbase.editCorrespondentsOrPeopleDocument.deleteThisPersonEntry"/></h1> 
		<input type="button" class="personYes" value="Yes" /> 
		<input type="button" class="personNo" value="No" /> 
</div>

<div class="questionSendRecip" style="display:none; cursor: default">
	<h1><fmt:message key="docbase.editCorrespondentsOrPeopleDocument.theSenderAndRecipientSame"/></h1>
	<input type="button" class="sendRecipYes" value="Yes" />
	<input type="button" class="sendRecipNo" value="No" />
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		
		$j('.helpIcon').tooltip({ 
			track: true, 
			fade: 350 
		});
		
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
				$j("#body_left").html(html);
			}});
			return false;
		});

		$j('.sendRecipNo').click(function() {
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j(".questionSendRecip").hide();
			$j("#EditCorrespondentsDocumentDiv").append($j(".questionSendRecip"));
			$j(".blockUI").remove();
			//$j("#EditCorrespondentsDocumentDiv").load('${EditCorrespondentsOrPeopleDocumentURL}');
			return false; 
		}); 

		$j('.sendRecipYes').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j(".questionSendRecip").hide();
			$j("#EditCorrespondentsDocumentDiv").append($j(".questionSendRecip"));
			$j(".blockUI").remove();
			if($j("#senderPlacePrefered").val() == 'V' || $j("#recipientPlacePrefered").val() == 'V'){
				$j('#EditCorrespondentsDocumentDiv').block({ message: $j('.notPrincipal') });
				return false;
			}else{
				$j.ajax({ type:"POST", url:$j("#EditCorrespondentsOrPeopleDocumentForm").closest('form').attr("action"), data:$j("#EditCorrespondentsOrPeopleDocumentForm").closest('form').serialize(), async:false, success:function(html) { 
					$j("#body_left").html(html);
				}});
				return false;
			}
        });
     
	});
</script>