<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ContextPathURL" value="/"/>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.png"/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/MainContent.css" />" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/Template.css" />" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/jquery-ui.css" />" />
        
        
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAHbLtT8No8RIf-7pzQOcnfOeWP0TPYHG4&sensor=false"></script>
        <script type="text/javascript" src="<c:url value="/scripts/jquery.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js" />"></script>
        
        
        <script type="text/javascript">
			function calcdeg(){
			// deg
			var signlat = 1;
			var signlon = 1;
			var latAbs=0;
			var lonAbs=0;           
			if(document.getElementById("lat").value < 0)  { signlat = -1; }
			latAbs = Math.abs( Math.round(document.getElementById("lat").value * 1000000.));
			if(latAbs > (90 * 1000000)) { alert(' Degrees Latitude must be in the range of -90. to 90. '); document.getElementById("lat_deg").value = '';  latAbs=0; }         
			if(document.getElementById("lng").value < 0)  { signlon = -1; }
			lonAbs = Math.abs(Math.round(document.getElementById("lng").value * 1000000.));
			if(lonAbs > (180 * 1000000)) {  alert(' Degrees Longitude must be in the range of -180 to 180. '); document.getElementById("long_deg").value='';  lonAbs=0; }
			document.getElementById("lat_deg").value = ((Math.floor(latAbs / 1000000) * signlat) + '\u00B0 ' + Math.floor(  ((latAbs/1000000) - Math.floor(latAbs/1000000)) * 60)  + '\' ' +  ( 	Math.floor(((((latAbs/1000000) - Math.floor(latAbs/1000000)) * 60) - Math.floor(((latAbs/1000000) - Math.floor(latAbs/1000000)) * 60)) * 100000) *60/100000 ).toFixed(0) + '\"'  );
			document.getElementById("long_deg").value = ((Math.floor(lonAbs / 1000000) * signlon) + '\u00B0 ' + Math.floor(  ((lonAbs/1000000) - Math.floor(lonAbs/1000000)) * 60)  + '\' ' +  ( Math.floor(((((lonAbs/1000000) - Math.floor(lonAbs/1000000)) * 60) - Math.floor(((lonAbs/1000000) - Math.floor(lonAbs/1000000)) * 60)) * 100000) *60/100000 ).toFixed(0) + '\"'  );
			//MAP
			//Latitude direction and degree
			if(signlat < 0){
				$j("#directionLatitude").val("S");
			}else{
				$j("#directionLatitude").val("N");
			}
			$j("#degreeLatitude").val((Math.floor(latAbs / 1000000)));
			$j("#minuteLatitude").val(Math.floor(  ((latAbs/1000000) - Math.floor(latAbs/1000000)) * 60));
			$j("#secondLatitude").val(( 	Math.floor(((((latAbs/1000000) - Math.floor(latAbs/1000000)) * 60) - Math.floor(((latAbs/1000000) - Math.floor(latAbs/1000000)) * 60)) * 100000) *60/100000 ).toFixed(0));
			
			//Longitude direction and degree
			if(signlon < 0){
				$j("#directionLongitude").val("W");
			}else{
				$j("#directionLongitude").val("E");
			}
			$j("#degreeLongitude").val((Math.floor(lonAbs / 1000000)));
			$j("#minuteLongitude").val(Math.floor(  ((lonAbs/1000000) - Math.floor(lonAbs/1000000)) * 60));
			$j("#secondLongitude").val(( Math.floor(((((lonAbs/1000000) - Math.floor(lonAbs/1000000)) * 60) - Math.floor(((lonAbs/1000000) - Math.floor(lonAbs/1000000)) * 60)) * 100000) *60/100000 ).toFixed(0));
			
			
			}
        </script>
        
        <script type="text/javascript">
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
			$j.ajaxSetup ({
			// Disable caching of AJAX responses */
			cache: false
			});
			});
        </script>
        
        <script type='text/javascript'>    
			//Useful links:
			// http://code.google.com/apis/maps/documentation/javascript/reference.html#Marker
			// http://code.google.com/apis/maps/documentation/javascript/services.html#Geocoding
			// http://jqueryui.com/demos/autocomplete/#remote-with-cache
			
			var geocoder;
			var map;
			var marker;
			
			function initialize(){
			//MAP
			var latlng = new google.maps.LatLng('${latitude}','${longitude}');
			var options = {
			zoom: 16,
			center: latlng,
			mapTypeId: google.maps.MapTypeId.ROADMAP
			};
			
			map = new google.maps.Map(document.getElementById("map_canvas"), options);
			
			//GEOCODER
			geocoder = new google.maps.Geocoder();
			
			marker = new google.maps.Marker({
			map: map,
			draggable: true,
			position: latlng
			});
				
			}
			
			$j(document).ready(function() { 
			
			initialize();
				  
			$j(function() {
			$j("#address").autocomplete({
			//This bit uses the geocoder to fetch address values
			source: function(request, response) {
			geocoder.geocode( {'address': request.term }, function(results, status) {
			response($j.map(results, function(item) {
			return {
			  label:  item.formatted_address,
			  value: item.formatted_address,
			  latitude: item.geometry.location.lat().toFixed(4),
			  longitude: item.geometry.location.lng().toFixed(4)
			}
			}));
			})
			},
			//This bit is executed upon selection of an address
			select: function(event, ui) {
			$j("#lat").val(ui.item.latitude);
			$j("#lng").val(ui.item.longitude);
			var location = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
			marker.setPosition(location);
			map.setCenter(location);
			calcdeg();
			}
			});
			});
			
			//Add listener to marker for reverse geocoding
			google.maps.event.addListener(marker, 'drag', function() {
			geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
			if (results[0]) {
			$j('#address').val(results[0].formatted_address);
			$j('#lat').val(marker.getPosition().lat().toFixed(4));
			$j('#lng').val(marker.getPosition().lng().toFixed(4));
			}
			}
			});
			calcdeg();
			});
			
			//MAP
			
			
			var delay = (function(){
				  var timer = 0;
				  return function(callback, ms){
				    clearTimeout (timer);
				    timer = setTimeout(callback, ms);
				  };
				})();
			
			delay(function(){
				geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
					if (status == google.maps.GeocoderStatus.OK) {
						$j('#lat').val(marker.getPosition().lat().toFixed(4));
						$j('#lng').val(marker.getPosition().lng().toFixed(4));
						calcdeg();
					}
				});
				
				$j("#address").val('${place.placeName}, ${place.plParent}');
				$j("#address").autocomplete("search", '${place.placeName}, ${place.plParent}');
				}, 1000 );
			
			if (status == google.maps.GeocoderStatus.OK) {
				$j('#lat').val(marker.getPosition().lat().toFixed(4));
				$j('#lng').val(marker.getPosition().lng().toFixed(4));
				calcdeg();
			}

			$j("#latlongForm").submit(function(){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0){
					}else{
						var contextPath = "${ContextPathURL}";
						var urlToShowPlace = contextPath + "src/geobase/ShowPlace.do?placeAllId=${command.placeAllId}";
						window.opener.$j("#body_left").load(urlToShowPlace);
						window.blur();
						window.opener.focus();
						window.close();
						return false;
					}
				}});
					
			});
			
			})
        </script>
    
    </head>
    <body>
    	<div id="description">
            <h1><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.findCoord"/></h1>
            
            <ul>
                <li><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.dragAndDrop"/></li>
                <li><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.zoomIn"/></li>
                <li><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.dragAndDropMarker"/></li>
            </ul>
            
            <h1><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.findCoordinates"/></h1>
            <p><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.submitTheFull"/></p>
        </div>
        
        <div id="adressDiv">
            <label id="adressLabel"><b><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.address"/></b></label>
            <input id="address" type="text" class="input_50c"/>
        </div>
        
        <div id="map_canvas"></div>
        
        <div id="coordinatesDiv">
            <form:form id="latlongForm" method="post" class="edit">
            	<div class="listForm">
                       
                         <div class="row">
                            <div class="col_l"><label for="latLabel" id="latLabel"><b><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.latitude"/></b></label></div>
                            <div class="col_l"><input type="hidden" id="lat" name="lat" value="" class="input_20c"></div>
                            <div class="col_l"><input type="text" id="lat_deg" name="lat" value="" class="input_20c"></div>
                        </div>
                        
                        <br/>    
           
                        <div class="row">
                            <div class="col_l"><label for="lngLabel" id="lngLabel"><b><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.longitude"/></b></label></div>
                            <div class="col_l"><input type="hidden" id="lng" name="lng" value="" class="input_20c"></div>
                            <div class="col_l"><input type="text" id="long_deg" name="lng" value="" class="input_20c"></div>
                        </div>
                        
                </div>
                
                <br/>
                
        		<p><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.assignCoordinates"/></p>
            
                <div id="geoTitle">
                	<div id="text">
	                    <h3>${place.placeName}</h3>
	                    <h4>${place.placeNameFull}</h4>
	                    <c:if test="${place.plSource == 'TGN' && place.geogKey >= 1000000}">
	            		<h5><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.tgnPlace"/></h5>
	        			</c:if>
	        			<c:if test="${place.geogKey >= 1000000  && place.plSource == 'MAPPLACE'}">
	        			<h5><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.tgnPlaceRecordUpdated"/></h5>
	        			</c:if>
	        			<c:if test="${place.plSource == 'MAPPLACE' && (place.geogKey >= 100000 && place.geogKey < 400000) }">
						<h5><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.mapPlaceRecord"/></h5>
						</c:if>
	        			<c:if test="${place.plSource == 'MAPSITE' || (place.geogKey >= 400000 && place.geogKey < 1000000) }">
						<h5><fmt:message key="geobase.showGoogleMapsGeoCoorPlace.mapSiteOrSubsite"/></h5>
						</c:if>
	                    <h7>${place.plType}</h7>
	                </div>             
                </div>
                
                <form:hidden path="degreeLatitude"/>
                <form:hidden path="minuteLatitude"/>
                <form:hidden path="secondLatitude"/>
                <form:hidden path="directionLatitude"/>
                <form:hidden path="degreeLongitude"/>
                <form:hidden path="minuteLongitude"/>
                <form:hidden path="secondLongitude"/>
                <form:hidden path="directionLongitude"/>
            
            	<input class="button_small" type="submit" value="Assign" title="Assign coordinates to this place" />
           </form:form>
     	</div>
   
    </body>
</html>