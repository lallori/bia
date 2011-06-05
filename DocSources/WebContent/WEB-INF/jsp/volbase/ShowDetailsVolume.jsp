<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShareVolumeURL" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>

	<c:url var="checkVolumeDigitizedURL" value="/src/volbase/CheckVolumeDigitized.json">
		<c:param name="volNum"   value="${volume.volNum}" />
		<c:param name="volLetExt"   value="${volume.volLetExt}" />
	</c:url>

	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
		<c:param name="flashVersion" value="false" />
	</c:url>

	<c:url var="EditDetailsVolumeURL" value="/de/volbase/EditDetailsVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
		
	
	<div id="EditDetailsVolumeDiv" class="background">
		<div class="title">
			<h5>VOLUME DETAILS </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a id="EditDetailsVolume" href="${EditDetailsVolumeURL}" class="editButton"></a><span id="loading"/>
		</security:authorize>
		</div>

		<c:if test="${not empty image}">
		<div id="SpineVolumeDigitDiv">
			<img src="<c:url value="/mview/ReverseProxyIIPImageThumbnail.do?imageName=${image}"/>">
			<b>Volume Spine</b><br>
			<a id="ShowVolumeExplorer" href="${ShowExplorerVolumeURL}">Show in Volume Explorer</a>
		</div>
		</c:if>
		<c:if test="${empty image}">
			<div id="SpineVolumeNotDigitDiv">
				<img src="<c:url value="/images/1024/img_volumespinedefault.png"/>">
				<b>Volume Spine</b>
			</div>
		</c:if>
		<!-- <div id="SpineVolumeDiv">
			<img src="<c:url value="/images/image_volume.png"/>" alt="default image" />
			<p><b>Costola</b> <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditPortraitPerson" href="/DocSources/de/peoplebase/EditPortraitPerson.html">edit</a></security:authorize></p>
		</div> -->
		
		<h3>${volume.serieList}</h3>
		<div class="listDetails">
			<div class="row">
				<div class="item">Volume/Filza (MDP)</div>
				<div class="value">${volume.volNum}${volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item">Start Date</div>
				<div class="value">${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay}</div>
			</div>
			<div class="row">
				<div class="item">End Date</div>
				<div class="value">${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay}</div>
			</div>
			<div class="row">	
				<div class="item">Date Notes</div>
				<div class="value">${volume.dateNotes}</div>
			</div>
		</div>
	</div>
	<br />
	<br />
<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditContextVolume").css('visibility', 'visible');
	        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
	        $j("#EditDescriptionVolume").css('visibility', 'visible'); 
			$j("#EditDetailsVolume").css('visibility', 'visible'); 

			$j("#EditDetailsVolume").volumeExplorer( {
				volNum					: "${volume.volNum}",
				volLetExt				: "${volume.volLetExt}",
				checkVolumeDigitizedURL	: "${checkVolumeDigitizedURL}",
				showExplorerVolumeURL	: "${ShowExplorerVolumeURL}",
				target 					: $j("#body_right") 
			});  

			$j("#buttonShareLink").click(function() {
				window.open('${ShareVolumeURL}','ADD NEW PERSON','width=490,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
			});

			$j("#buttonShareLink").hover(function(){
				var iconName = $j(this).find("img").attr("src");
				var origen =  $j(this).find("img").attr("src");
				$j(this).find("img").attr("src");
				$j(this).find("span").attr({"style": 'display:inline'});
				$j(this).find("span").animate({opacity: 1, top: "-60"}, {queue:false, duration:400});
			}, function(){
				var iconName = $j(this).find("img").attr("src");
				var origen =  $j(this).find("img").attr("src");
				$j(this).find("img").attr("src");
				$j(this).find("span").animate({opacity: 0, top: "-50"}, {queue:false, duration:400, complete: function(){
					$j(this).attr({"style": 'display:none'});
				}});
			});

			$j("#EditDetailsVolume").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsVolumeDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j("#ShowVolumeExplorer").click(function(){
				var tabName = "Volume Explorer ${volume.volNum} ${volume.volLetExt} </span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab";
            	$j("#tabs").tabs("add", "" + $j(this).attr("href"), tabName);
            	$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
            	return false;
			});
		});
	</script>
</security:authorize>