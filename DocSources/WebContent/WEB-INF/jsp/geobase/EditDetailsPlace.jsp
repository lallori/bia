<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPlaceURL" value="/de/peoplebase/EditDetailsPlace.do">
			<c:param name="placeAllId"   value="${command.placeAllId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="placeAllId"   value="${command.placeAllId}" />
		</c:url>
	</security:authorize>
	<br>
	<div>
		<form:form id="EditDetailsPlaceForm" cssClass="edit" method="post">
			<fieldset>
				<legend><b>PLACE DETAILS</b></legend>
				<div id="placeIdDiv">
					<label id="placeIDLabel" for="placeID">Place ID: </label> <i>Fills in automatically</i>
				</div>
				
				<div>
					<form:label for="geogKeyLabel" path="geogKey" cssErrorClass="error"><b>Geog Key:</b></form:label>
					<form:input path="geogKey" cssClass="input_14c" />
					<form:label for="tgnTermIdLabel" path="tgnTermId" cssErrorClass="error"><b>TGN_TermID</b></form:label>
					<form:input path="tgnTermId" cssClass="input_14c" />
				</div>
				
				<hr>
				
				<div>
					<b>Place name</b><br>
					<form:label for="nameNoAccentsLabel" path="nameNoAccents" cssErrorClass="error"><b>No accents</b></form:label>
					<form:input path="nameNoAccents" cssClass="input_14c" />
				</div>
				
				<div>
					<form:label for="nameWithAccentsLabel" path="nameWithAccents" cssErrorClass="error"><b>No accents</b></form:label>
					<form:input path="nameWithAccents" cssClass="input_14c" />
				</div>
				
				<hr>
				
				<div>
					<form:label for="placeTypeLabel" path="placeType" cssErrorClass="error"><b>Place Type</b></form:label>
					<form:select path="placeType" cssClass="selectform_short" items="${placeTypes}"/>
				
					<form:label for="placeParentLabel" path="placeParent" cssErrorClass="error"><b>Place Parent</b></form:label>
					<form:input path="placeParent" maxlength="2" cssClass="input_10c" />
				</div>
				
				<br>
				
				<div>
					<form:label for="placeNotesLabel" path="placeNotes" cssErrorClass="error"><b>Place Notes</b></form:label>
				</div>
				<div>
					<form:textarea path="placeNotes" class="txtarea"/>
				</div>
					
				<div>
					<input type="submit" title="Do not save changes" value="" id="close">
					<input type="submit" value="" id="save">
				</div>
			</fieldset>	

			<form:hidden path="placeAllId"/>
		</form:form>
	</div>

	<c:url var="searchBornPlaceURL" value="/de/peoplebase/SearchBornPlacePerson.json"/>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditParentsPerson").css('visibility', 'hidden');
			$j("#EditChildrenPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
	        
			$j('#close').click(function(e) {
				$j('#EditDetailsPersonDiv').block({ message: $j('#question') }); 
	            return false;
			});
			
			var bornPlaceDescription = $j('#bornPlaceDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchBornPlaceURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#bornPlaceId').val(data); }
			  });

			$j("#EditDetailsPersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0){
						$j("#EditDetailsPersonDiv").html(html);
					} else {
				<c:choose> 
					<c:when test="${command.personId == 0}"> 
						$j("#body_left").html(html);
					</c:when> 
					<c:otherwise> 
						$j("#EditDetailsPersonDiv").html(html);
					</c:otherwise> 
				</c:choose> 
					}
				}});
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
				$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
	
				return false; 
			}); 
	     
		});
	</script>