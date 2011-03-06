<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDescriptionVolume" value="/de/volbase/EditDescriptionVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>

	<div id="EditDescriptionVolumeDiv">
		<h5>Description </h5><security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDescriptionVolume" href="${EditDescriptionVolume}">edit</a><span id="loading"/></security:authorize>
		<hr id="lineSeparator"/>
		
		<ul>
			<li><b>Organizational Criteria: </b>${volume.orgNotes}</li>
			<li><b>Condition: </b> ${volume.ccondition}</li>
			<li><b>Bound: </b>${volume.bound ? 'Yes' : 'No'}</li>
			<li><b>Folios Numbered: </b>${volume.folsNumbrd ? 'Yes' : 'No'}</li>
			<li><b>Folios Count: </b>${volume.folioCount}</li>
			<li><b>Alphabetical Index: </b>${volume.oldAlphaIndex ? 'Yes' : 'No'}</li>
			<li><b>Printed material: </b>${volume.printedMaterial ? 'Yes' : 'No'}</li>
			<li><b>Printed drawings: </b>${volume.printedDrawings ? 'Yes' : 'No'}</li>
			<li><b>Languages: </b>  ${volume.italian ? 'Italian' : '' } 
									${volume.spanish ? 'Spanish' : ''}
									${volume.english ? 'English' : ''}
									${volume.latin ? 'Latin' : ''}
									${volume.german ? 'German' : ''}
									${volume.french ? 'French' : ''}
									${volume.otherLang}
			</li>
			<li><b>Some Documents in Cipher: </b>${volume.cipher ? 'Yes' : 'No'}</li>
			<li><b>Cipher Notes: </b>${volume.cipherNotes}</li>
		</ul>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#EditContextVolume").css('visibility', 'visible');
		        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
				$j("#EditDetailsVolume").css('visibility', 'visible');

				 $j("#EditDescriptionVolume").click(function(){
					$j(this).next().css('visibility', 'visible');
					$j("#EditDescriptionVolumeDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</security:authorize>