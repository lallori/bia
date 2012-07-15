<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDescriptionVolume" value="/de/volbase/EditDescriptionVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>

	<div id="EditDescriptionVolumeDiv" class="background">
		<div class="title">
			<h5>DESCRIPTION </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
			<c:if test="${volume.summaryId > 0}">
			<a id="EditDescriptionVolume" href="${EditDescriptionVolume}" class="editButton"></a><span id="loading"/>
			</c:if>
		</security:authorize>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item">Organizational Criteria</div>
				<div class="value">${volume.orgNotes}</div>
			</div>
			<c:if test="${schedone != null && schedone.noteCartulazione != null}">
				<div class="row">
					<div class="item">Note alla cartulazione</div>
					<div class="value">${schedone.noteCartulazione}<br />${schedone.noteCartulazioneEng}</div>
				</div>
			</c:if>
			<div class="row">
				<div class="item">Condition</div>
				<div class="value">${volume.ccondition}</div>
			</div>
			<div class="row">
				<div class="item">Bound</div>
				<div class="value">${volume.bound ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Folios Numbered</div>
				<div class="value">${volume.folsNumbrd ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Folios Count</div>
				<div class="value">${volume.folioCount}</div>
			</div>
			<div class="row">
				<div class="item">Missing folios</div>
				<div class="value">${fn2:toString(volumeSummary.missingFolios)}</div>
			</div>
			<div class="row">
				<div class="item">Index of Names</div>
				<div class="value">${volume.oldAlphaIndex ? 'Yes' : 'No'}</div>
			</div>
			<c:if test="${schedone != null && (schedone.dimensioniBase != null || schedone.dimensioniAltezza != null)}">
				<div class="row">
					<div class="item">Volume height</div>
					<c:if test="${schedone.dimensioniAltezza == null}">
						<div class="value">-</div>
					</c:if>
					<c:if test="${schedone.dimensioniAltezza != null}">
						<div class="value">${schedone.dimensioniAltezza} mm</div>
					</c:if>
				</div>
				<div class="row">
					<div class="item">Volume width</div>
					<c:if test="${schedone.dimensioniBase == null}">
						<div class="value">-</div>
					</c:if>
					<c:if test="${schedone.dimensioniBase != null}">
						<div class="value">${schedone.dimensioniBase} mm</div>
					</c:if>
				</div>
			</c:if>
			<div class="row">
				<div class="item">Printed material</div>
				<div class="value">${volume.printedMaterial ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Printed drawings</div>
				<div class="value">${volume.printedDrawings ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Languages</div>
				<div class="value"> ${volume.italian ? 'Italian;' : '' }
									${volume.spanish ? 'Spanish;' : ''}
									${volume.english ? 'English;' : ''}
									${volume.latin ? 'Latin;' : ''} 
									${volume.german ? 'German;' : ''}
									${volume.french ? 'French;' : ''}
									${volume.otherLang}
				</div>
			</div>
			<div class="row">
				<div class="item">Some Documents in Cipher</div>
				<div class="value">${volume.cipher ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item">Cipher Notes</div>
				<div class="value">${volume.cipherNotes}</div>
			</div>
		</div>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#EditContextVolume").css('visibility', 'visible');
		        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
		        $j("#EditDescriptionVolume").css('visibility', 'visible'); 
				$j("#EditDetailsVolume").css('visibility', 'visible'); 

				 $j("#EditDescriptionVolume").click(function(){
					$j(this).next().css('visibility', 'visible');
					$j("#EditDescriptionVolumeDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</security:authorize>