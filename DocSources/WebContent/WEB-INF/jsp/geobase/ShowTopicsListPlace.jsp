<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowTopicsPlaceURL" value="/de/geobase/ShowTopicsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<div class="background" id="EditTopicsPlaceDiv">
	<div class="title">
		<h5>TOPICS LIST </h5>
	</div>
	<div class="list">	
		<div class="row">
			<c:if test="${place.eplToLinks.size() != 0}">
				<div class="value"><a id="linkSearch" class="topics" href="${ShowTopicsPlaceURL}">${docInTopics} Documents on ${place.eplToLinks.size()} Topics</a></div>
			</c:if>
			<c:if test="${place.eplToLinks.size() == 0}">
				<div class="value">0 Document on 0 Topic</div>
			</c:if>
		</div>
	</div>
</div>

<br />
<br />

<script type="text/javascript">
		$j(document).ready(function() {
			$j(".topics").click(function(){
				var tabName = "Topics ${place.placeName}";
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