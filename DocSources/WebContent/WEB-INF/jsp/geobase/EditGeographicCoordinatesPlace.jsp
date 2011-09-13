<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditGeographicCoordinatesPlaceURL" value="/de/geobase/EditGeographicCoordinatesPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
		
		<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<form:form id="EditGeoCoorPlaceForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>GEOGRAPHIC COORDINATES</b></legend>
		
		<div>
			<label for="latitudeGeo" id="latitudeGeoLabel">Latitude</label>
			<input id="latitudeGeo" name="latitudeGeo" class="input_10c" type="text" value=""/>
			<label for="longitudeGeo" id="longitudeGeoLabel">Longitude</label>
			<input id="longitudeGeo" name="longitudeGeo" class="input_10c" type="text" value=""/>
		</div>
		
		<div>
			<input id="close" type="submit" value="Close" title="Do not save changes"/>
			<a href="#" id="AddNewValue" title="Add new Name">Add</a>
		</div>
		
		</fieldset>
	</form:form>
	

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'hidden');
			$j("#EditNamesOrNameVariantsPlace").css('visibility', 'hidden'); 
			$j("#EditExtLinkPlace").css('visibility', 'hidden');

			$j("#EditGeoCoorPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditGeoCoorPlaceDiv").load($j(this).attr("href"));
				return false;
			});

			$j('#close').click(function(e) {
				$j('#EditGeoCoorPlaceForm').block({ message: $j('#question') }); 
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
				$j("#EditGeoCoorPlaceForm").append($j("#question"));
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
