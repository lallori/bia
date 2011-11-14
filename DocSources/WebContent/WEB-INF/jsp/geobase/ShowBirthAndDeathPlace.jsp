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

<div class="background" id="EditBirthDeathPlaceDiv">
	<div class="title">
		<h5>BIRTH and DEATH PLACE </h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<c:if test="${place.bornedPeople.size() != 0}">
				<div class="value"><a id="linkSearch" class="birth" href="${ShowBirthPeoplePlaceURL}">${place.bornedPeople.size()} Birth</a></div>
			</c:if>
			<c:if test="${place.bornedPeople.size() == 0}">
				<div class="value">0 Birth</div>
			</c:if>
		</div>
		<div class="row">
			<c:if test="${place.deathPeople.size() != 0}">
				<div class="value"><a id="linkSearch" class="death" href="${ShowDeathPeoplePlaceURL}">${place.deathPeople.size()} Death</a></div>
			</c:if>
			<c:if test="${place.deathPeople.size() == 0}">
				<div class="value">0 Death</div>
			</c:if>
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
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".death").click(function(){
				var tabName = "Death ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
		});
</script>