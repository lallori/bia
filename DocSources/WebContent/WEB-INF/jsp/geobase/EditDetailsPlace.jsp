<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPlaceURL" value="/de/geobase/EditDetailsPlace.do">
			<c:param name="placeAllId"   value="${command.placeAllId}" />
		</c:url>
		<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
			<c:param name="placeAllId"   value="${command.placeAllId}" />
			<c:param name="plSource" 	 value="${command.plSource}" />
		</c:url>
		<c:url var="ShowHierarchyPlaceURL" value="/de/geobase/ShowHierarchyPlace.do">
			<c:param name="placeAllId"	 value="${command.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div>
	
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
		<form:form id="EditDetailsTgnPlaceForm" cssClass="edit" method="post" action="${EditDetailsPlaceURL}">
			<fieldset>
				<legend><b>PLACE DETAILS</b></legend>
				<div class="listForm">
					<div class="row">
						<div class="col_l"><a class="helpIcon" title="<fmt:message key="geobase.details.edit.placeid"></fmt:message>">?</a><label id="placeIDLabel" for="placeAllId">Place ID: </label></div>
						<div class="col_l"><i>Fills in automatically</i></div>
					</div>
				
					<div class="row">
						<div class="col_l">
							<a class="helpIcon" title="<fmt:message key="geobase.details.edit.geogkey"></fmt:message>">?</a>
							<form:label for="geogKeyLabel" path="geogKey" cssErrorClass="error" id="geogKeyLabel">Geog Key</form:label>
						</div>
						<c:if test="${command.plSource != 'TGN'}">
							<div class="col_l"><form:input path="geogKey" cssClass="input_35c_disabled" disabled="disabled"/></div>
						</c:if>
						
						<c:if test="${command.plSource == 'TGN' || command.geogKey >= 1000000}">
							<div class="col_l"><form:input path="geogKey" cssClass="input_10c" /></div>
							<div class="col_r"><form:label for="tgnTermIdLabel" path="placeNameId" cssErrorClass="error" id="tgnTermIDLabel">TGN_TermID</form:label></div>
							<div class="col_r"><form:input path="placeNameId" cssClass="input_10c" /></div>
						</c:if>
					</div>
				</div>
				
				<hr />
				
				<div class="listForm">
					<div class="row">
						<div class="col_l"><b>Place name</b></div>
					</div>
					<div class="row">
						<div class="col_r">
							<a class="helpIcon" title="<fmt:message key="geobase.details.edit.placenamenoaccents"></fmt:message>">?</a>
							<form:label for="nameNoAccentsLabel" path="placeName" cssErrorClass="error" id="nameNoAccentsLabel">No accents</form:label>
						</div>
						<div class="col_r"><form:input path="placeName" cssClass="input_25c" id="nameNoAccents" /></div>
					</div>
					<div class="row">
						<div class="col_r">
							<a class="helpIcon" title="<fmt:message key="geobase.details.edit.placenamewithaccents"></fmt:message>">?</a>
							<form:label for="nameWithAccentsLabel" path="termAccent" cssErrorClass="error" id="nameWithAccentsLabel">With accents if required</form:label>
						</div>
						<div class="col_r"><form:input path="termAccent" cssClass="input_25c" id="nameWithAccents"/></div>
					</div>
				</div>
				
				<hr />
				
				<div class="listForm">
					<div class="row">
						<div class="col_l"><a class="helpIcon" title="<fmt:message key="geobase.details.edit.placetype"></fmt:message>">?</a><form:label for="placeTypeLabel" path="plType" cssErrorClass="error" id="placeTypeLabel">Place Type</form:label></div>
						<div class="col_l"><form:select path="plType" cssClass="selectform_XXXlong" items="${placeTypes}"/></div>
					</div>
					<div class="row">
						<div class="col_l"><a class="helpIcon" title="<fmt:message key="geobase.details.edit.placenparents"></fmt:message>">?</a><form:label for="placeParentLabel" path="plParent" cssErrorClass="error" id="placeParentLabel">Place Parent</form:label></div>
						<div class="col_l"><form:input id="placeParentAutoCompleter" path="plParent" cssClass="input_30c"/></div>
					</div>
				</div>
				
				<div class="listForm">
					<div class="row"><a class="helpIcon" title="<fmt:message key="geobase.details.edit.placenotes"></fmt:message>">?</a><form:label for="placeNotesLabel" path="placesMemo" cssErrorClass="error" id="placeNotesLabel">Place Notes</form:label></div>
					<div class="row"><form:textarea path="placesMemo" cssClass="txtarea" id="placeNotes"/></div>
				</div>
				
				<form:errors path="geogKey" cssClass="inputerrors" htmlEscape="false" />
				<form:errors path="parentPlaceAllId" cssClass="inputerrors" htmlEscape="false" />

				<div>
					<input type="submit" title="Do not save changes" value="Close" id="close" class="button_small fl" />
					<input type="submit" value="Save" id="save" class="button_small fr" />
				</div>
				<input type="hidden" value="" id="modify" />
			</fieldset>	

			<form:hidden path="placeAllId"/>
			<form:hidden path="parentPlaceAllId" />
<%-- 			<form:hidden path="geogKey"/> --%>
			<form:hidden path="plSource"/>
			
		</form:form>
	</div>
	
	<c:url var="searchPlaceParentURL" value="/de/geobase/SearchPlaceParent.json" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditDetailsTgnPlaceForm");
			
			$j("#EditNamePlace").css('visibility', 'hidden');
	        $j("#EditGeoCoorPlace").css('visibility', 'hidden'); 
			$j("#EditExtLinkPlace").css('visibility', 'hidden');
			
			$j("#EditDetailsTgnPlaceForm :input").change(function(){
				$j("#modify").val(1);
				return false;
			})
			
			$j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });
			
			$j('#close').click(function() {
	        	if($j("#modify").val() == 1){
					$j('#EditDetailsTgnPlaceForm').block({ message: $j('#question'),
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
	        		$j.ajax({ url: '${ShowPlaceURL}', cache: false, success:function(html) { 
	    				$j("#body_left").html(html);
	    			}});
	    				
	    			return false; 
	        	}	        		
			});
			
			
			$j('.input_35c_disabled').attr('disabled', 'disabled');
			
			var placeParent = $j('#placeParentAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${searchPlaceParentURL}',
			    loadingImageUrl:'${LoadingImageURL}',
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
						$j("#EditDetailsPlaceDiv").html(html);
					} else {
				<c:choose> 
					<c:when test="${command.placeAllId == 0}"> 
						$j("#body_left").html(html);
					</c:when> 
					<c:otherwise> 
						$j("#body_left").html(html);
						$j("#EditHierarchyPlaceDiv").load("${ShowHierarchyPlaceURL}");
					</c:otherwise> 
				</c:choose> 
					}
				}});
				return false;
			});
			 
	        
// 			$j('#close').click(function(e) {
// 				$j('.autocomplete').remove();
// 				$j('#EditDetailsTgnPlaceForm').block({ message: $j('#question'),
// 					css: { 
// 						border: 'none', 
// 						padding: '5px',
// 						boxShadow: '1px 1px 10px #666',
// 						'-webkit-box-shadow': '1px 1px 10px #666'
// 						} ,
// 						overlayCSS: { backgroundColor: '#999' }	
// 				}); 
// 	            return false;
// 			});			
		});
	</script>


	<div id="question" style="display:none; cursor: default"> 
		<h1>Discard changes?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
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