<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPlaceURL" value="/de/geobase/EditDetailsPlace.do">
			<c:param name="placeAllId"   value="${command.placeAllId}" />
		</c:url>
		<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
			<c:param name="placeAllId"   value="${command.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div>
		<form:form id="EditDetailsTgnPlaceForm" cssClass="edit" method="post" action="${EditDetailsPlaceURL}">
			<fieldset>
				<legend><b>PLACE DETAILS</b></legend>
				<div id="placeIdDiv">
					<label id="placeIDLabel" for="placeAllId">Place ID: </label> <i>Fills in automatically</i>
				</div>
				
				<div>
					<form:label for="geogKeyLabel" path="geogKey" cssErrorClass="error" id="geogKeyLabel">Geog Key</form:label>
					<form:input path="geogKey" cssClass="input_14c" />
					<c:if test="${command.geoIdEncoding == 'TGN_GEOKEY' || command.geogKey >= 1000000}">
						<form:label for="tgnTermIdLabel" path="placeNameId" cssErrorClass="error" id="tgnTermIDLabel">TGN_TermID</form:label>
						<form:input path="placeNameId" cssClass="input_14c" />
					</c:if>
				</div>
				
				<hr />
				
				<div>
					<b>Place name</b><br />
					<form:label for="nameNoAccentsLabel" path="placeName" cssErrorClass="error" id="nameNoAccentsLabel">No accents</form:label>
					<form:input path="placeName" cssClass="input_28c" id="nameNoAccents" />
				</div>
				
				<div>
					<form:label for="nameWithAccentsLabel" path="termAccent" cssErrorClass="error" id="nameWithAccentsLabel">With accents if required</form:label>
					<form:input path="termAccent" cssClass="input_25c" id="nameWithAccents"/>
				</div>
				
				<hr />
				
				<div>
					<form:label for="placeTypeLabel" path="plType" cssErrorClass="error" id="placeTypeLabel">Place Type</form:label>
					<form:select path="plType" cssClass="selectform_Llong" items="${placeTypes}"/>
				
					<form:label for="placeParentLabel" path="plParent" cssErrorClass="error" id="placeParentLabel">Place Parent</form:label>
					<form:input id="placeParentAutoCompleter" path="plParent" cssClass="input_10c"/>
				</div>
				
				<br />
				
				<div>
					<form:label for="placeNotesLabel" path="placesMemo" cssErrorClass="error" id="placeNotesLabel">Place Notes</form:label>
				</div>
				<div>
					<form:textarea path="placesMemo" cssClass="txtarea" id="placeNotes"/>
				</div>
					
				<div>
					<input type="submit" title="Do not save changes" value="Close" id="close">
					<input type="submit" value="Save" id="save">
				</div>
			</fieldset>	

			<form:hidden path="placeAllId"/>
			<form:hidden path="parentPlaceAllId" />
		</form:form>
	</div>
	
	<c:url var="searchPlaceParentURL" value="/de/geobase/SearchPlaceParent.json" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditNamePlace").css('visibility', 'hidden');
	        $j("#EditGeoCoorPlace").css('visibility', 'hidden'); 
			$j("#EditExtLinkPlace").css('visibility', 'hidden');
			
			var placeParent = $j('#placeParentAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${searchPlaceParentURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#parentPlaceAllId').val(data); }
			  });

			$j("#EditDetailsTgnPlaceForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0){
						$j("#geoDiv").html(html);
					} else {
				<c:choose> 
					<c:when test="${command.placeAllId == 0}"> 
						$j("#body_left").html(html);
					</c:when> 
					<c:otherwise> 
						$j("#geoDiv").html(html);
					</c:otherwise> 
				</c:choose> 
					}
				}});
				return false;
			});
			 
	        
			$j('#close').click(function(e) {
				$j('#EditDetailsTgnPlaceForm').block({ message: $j('#question') }); 
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
				$j("#EditDetailsTgnPlaceForm").append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${ShowPlaceURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
	
				return false; 
			}); 
	     
		});
	</script>