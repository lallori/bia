<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamesOrNameVariantsPlaceURL" value="/de/geobase/EditNamesOrNameVariantsPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
			<c:param name="geogKey" value="${command.geogKey}" />
		</c:url>
		
		<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
		
		<c:url var="AddNamePlaceURL" value="/de/geobase/EditNameOrNameVariantPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
			<c:param name="geogKey" value="${command.geogKey}" />
			<c:param name="currentPlaceAllId" value="0" />
		</c:url>
	</security:authorize>
	
	<form:form id="EditNamesOrNameVariantsPlaceForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>NAME or NAMES VARIANTS</b></legend>
		<c:forEach items="${placeNames}" var="currentName">
			<c:url var="EditNameOrNameVariantPlaceURL" value="/de/geobase/EditNameOrNameVariantPlace.do">
				<c:param name="placeAllId" value="${command.placeAllId}" />
				<c:param name="geogKey" value="${command.geogKey}" />
				<c:param name="currentPlaceAllId" value="${currentName.placeAllId}" />
			</c:url>
			
			<c:url var="DeleteNameOrNameVariantsPlaceURL" value="/de/geobase/DeleteNameOrNameVariantPlace.do">
				<c:param name="placeAllId" value="${currentName.placeAllId}" />
			</c:url>
			
			<div>
				<input id="name_${currentName.placeAllId}" name="name_${currentName.placeAllId}" class="input_35c_disabled" type="text" value="${currentName.placeName}" disabled="disabled" />
				<c:if test="${currentName.prefFlag != 'P' && currentName.placeAllId != command.placeAllId}">
					<a class="deleteIcon" title="Delete this entry" href="${DeleteNameOrNameVariantsPlaceURL}"></a>
				</c:if>
				<a class="editValue" href="${EditNameOrNameVariantPlaceURL}">edit value</a>
			</div>
		</c:forEach>
	
		<div>
			<input id="close" type="submit" value="Close" title="Do not save changes"/>
			<a href="${AddNamePlaceURL}" id="AddNewValue" title="Add new Name">Add</a>
		</div>
		
		</fieldset>
		
		<div id="EditNameVariantPlaceDiv"></div>
	</form:form>
	

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditNamesOrNameVariantsPlaceForm");
			
			$j("#EditDetailsPlace").css('visibility', 'hidden');
			$j("#EditGeoCoorPlace").css('visibility', 'hidden'); 
			$j("#EditExtLinkPlace").css('visibility', 'hidden');

			$j("#EditNamesOrNameVariantsPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditNameVariantPlaceDiv").load($j(this).attr("href"));
				return false;
			});

			$j('#close').click(function(e) {
				$j.ajax({ url: '${ShowPlaceURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
	            return false;
			});
			
			$j(".deleteIcon").click(function() {
				var temp = $j(this);
				$j("#EditNamePlaceDiv").block({ message: $j(".question")});

				$j('.no').click(function() {
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j(".question").hide();
					$j("#EditNamePlaceDiv").append($j(".question"));
					$j(".blockUI").remove();
					$j("#EditNamePlaceDiv").load('${EditNamesOrNameVariantsPlaceURL}');
					return false; 
				}); 

				$j('.yes').click(function() { 
					$j.get(temp.attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditNamePlaceDiv").load('${EditNamesOrNameVariantsPlaceURL}');
						return false;
					}
					
					return false; 
				}); 	
									     
				});
				return false;
			});
			
			$j(".editValue").click(function() {
				$j("#EditNameVariantPlaceDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j("#AddNewValue").click(function(){
				$j("#EditNameVariantPlaceDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
	
	<div class="question" style="display:none; cursor: default"> 
		<h1>Delete this Variant Place entry?</h1> 
		<input type="button" class="yes" value="Yes" /> 
		<input type="button" class="no" value="No" /> 
	</div>
