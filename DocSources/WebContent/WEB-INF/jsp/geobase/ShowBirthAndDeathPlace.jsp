<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowBirthPeoplePlaceURL" value="/de/geobase/ShowBirthPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowDeathPeoplePlaceURL" value="/de/geobase/ShowDeathPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowActiveStartPeoplePlaceURL" value="/de/geobase/ShowActiveStartPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowActiveEndPeoplePlaceURL" value="/de/geobase/ShowActiveEndPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<div class="background" id="EditBirthDeathPlaceDiv">
	<div class="title">
		<h5>BIRTH and DEATH PLACE<a class="helpIcon" title="Text">?</a></h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<div class="value"><c:if test="${birthPlace != 0}"><a id="linkSearch" class="birth" href="${ShowBirthPeoplePlaceURL}">${birthPlace} Births</a></c:if><c:if test="${birthPlace == 0}">0 Births</c:if>      <c:if test="${activeStartPlace != 0}"><a id="linkSearch" class="activeStart" href="${ShowActiveStartPeoplePlaceURL}">${activeStartPlace} Active Starts</a></c:if><c:if test="${activeStartPlace == 0}">0 Active Starts</c:if></div>
		</div>
		<div class="row">
			<div class="value"><c:if test="${deathPlace != 0}"><a id="linkSearch" class="death" href="${ShowDeathPeoplePlaceURL}">${deathPlace} Deaths</a></c:if><c:if test="${deathPlace == 0}">0 Deaths</c:if>      <c:if test="${activeEndPlace != 0}"><a id="linkSearch" class="activeStart" href="${ShowActiveEndPeoplePlaceURL}">${activeEndPlace} Active Ends</a></c:if><c:if test="${activeEndPlace == 0}">0 Active Ends</c:if></div>
		</div>
	</div>
</div>

<br />
<br />

<script type="text/javascript">
		$j(document).ready(function() {
			$j(".birth").click(function(){
				var tabName = "Birth ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".death").click(function(){
				var tabName = "Death ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".activeStart").click(function(){
				var tabName = "Active Start ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".activeEnd").click(function(){
				var tabName = "Active End ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
		});
</script>