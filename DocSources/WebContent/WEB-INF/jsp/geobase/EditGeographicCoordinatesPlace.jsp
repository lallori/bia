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
		
		<c:url var="ShowGoogleMapsGeoCoorPlaceURL" value="/src/geobase/ShowGoogleMapsGeoCoorPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
			<c:param name="placeGeographicCoordinatesId" value="${command.placeGeographicCoordinatesId}" />
		</c:url>
	</security:authorize>
	
<%-- Loading div when saving the form --%>
<div id="loadingDiv"></div>
	<form:form id="EditGeoCoorPlaceForm" method="post" cssClass="edit" action="${EditGeographicCoordinatesPlaceURL}">
		<fieldset>
		<legend><b>GEOGRAPHIC COORDINATES</b></legend>
		<div class="listForm">
			<div class="row">
				<div class="col_l"><label for="latitudeGeo" id="latitudeGeoLabel">Latitude</label></div>
				<div class="col_l">
					<form:input id="latDegrees" path="degreeLatitude" class="input_2c" type="text" value="" maxlength="3"/> °
		            <form:input id="latMinutes" path="minuteLatitude" class="input_2c" type="text" value="" maxlength="3"/> '
		            <form:input id="latSeconds" path="secondLatitude" class="input_2c" type="text" value="" maxlength="3"/> ''
		            <form:input id="latDirection" path="directionLatitude" class="input_1c" type="text" value="" maxlength="1"/>
				</div>
			</div>
			<div class="row">
				<div class="col_l"><label for="longitudeGeo" id="longitudeGeoLabel">Longitude</label></div>
				<div class="col_l">
					<form:input id="lonDegrees" path="degreeLongitude" class="input_2c" type="text" value="" maxlength="3"/> °
		            <form:input id="lonMinutes" path="minuteLongitude" class="input_2c" type="text" value="" maxlength="3"/> '
		            <form:input id="lonSeconds" path="secondLongitude" class="input_2c" type="text" value="" maxlength="3"/> ''
		            <form:input id="lonDirection" path="directionLongitude" class="input_1c" type="text" value="" maxlength="1"/>
				</div>
			</div>
		</div>
		<br />
		<div class="listForm">
			<div class="row">
				<div class="col_l"><b>Finds Geo Coordinates on:</b></div>
			</div>
			<br />
			<div class="row">
				<div class="col_l">
					<a class="helpIcon" title="Text goes here">?</a>
					<a href="http://www.getty.edu/research/tools/vocabularies/tgn/" target="_blank" id="gettyTgn">Getty TGN</a>
				</div>
				<div class="col_l">
					<a class="helpIcon" title="Text goes here">?</a>
					<a href="${ShowGoogleMapsGeoCoorPlaceURL}" target="_blank" id="googleMaps">Google Maps</a>
				</div>
			</div>
		</div>
		
		<form:errors path="degreeLatitude" cssClass="inputerrors" htmlEscape="false"/>
		<form:errors path="minuteLatitude" cssClass="inputerrors" htmlEscape="false"/>
		<form:errors path="secondLatitude" cssClass="inputerrors" htmlEscape="false"/>
		<form:errors path="directionLatitude" cssClass="inputerrors" htmlEscape="false"/>
		<form:errors path="degreeLongitude" cssClass="inputerrors" htmlEscape="false"/>
		<form:errors path="minuteLongitude" cssClass="inputerrors" htmlEscape="false"/>
		<form:errors path="secondLongitude" cssClass="inputerrors" htmlEscape="false"/>
		<form:errors path="directionLongitude" cssClass="inputerrors" htmlEscape="false"/>
		
		<div>
			<input id="close" type="submit" value="Close" title="Do not save changes"/>
			<input type="submit" value="Save" id="save">
		</div>
		<input type="hidden" value="" id="modify" />
		</fieldset>
	</form:form>
	

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditGeoCoorPlaceForm");
			
			$j("#EditDetailsPlace").css('visibility', 'hidden');
			$j("#EditNamesOrNameVariantsPlace").css('visibility', 'hidden'); 
			$j("#EditExtLinkPlace").css('visibility', 'hidden');
			
			$j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });
			
			$j('#close').click(function() {
	        	if($j("#modify").val() == 1){
					$j('#EditDetailsPlaceForm').block({ message: $j('#question'),
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

			$j("#EditGeoCoorPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditGeoCoorPlaceDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j("#EditGeoCoorPlaceForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0){
						$j("#EditGeoCoorPlaceDiv").html(html);
					} else {
				<c:choose> 
					<c:when test="${command.placeAllId == 0}"> 
						$j("#body_left").html(html);
					</c:when> 
					<c:otherwise> 
						$j("#body_left").html(html);
					</c:otherwise> 
				</c:choose> 
					}
				}});
				return false;
			});
		});
		
		$j("#gettyTgn").open({width: 960, height: 350, scrollbars: "yes"});
		$j("#googleMaps").open({width: screen.width, height: screen.height, scrollbars: "yes"});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1>Discard changes?</h1> 
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
