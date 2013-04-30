<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
		<c:url var="EditDescriptionVolume" value="/de/volbase/EditDescriptionVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>

	<div id="EditDescriptionVolumeDiv" class="background">
		<div class="title">
			<h5><fmt:message key="volbase.showDescriptionVolume.title"/></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
			<c:if test="${volume.summaryId > 0}">
			<a id="EditDescriptionVolume" href="${EditDescriptionVolume}" class="editButton"></a><span id="loading"/>
			</c:if>
		</security:authorize>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.organizationalCriteria"/></div>
				<div class="value">${volume.orgNotes}</div>
			</div>
			<c:if test="${schedone != null && schedone.noteCartulazione != null}">
				<div class="row">
					<div class="item"><fmt:message key="volbase.showDescriptionVolume.numerationNotes"/></div>
					<div class="value">${schedone.noteCartulazione}<br />${schedone.noteCartulazioneEng}</div>
				</div>
			</c:if>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.condition"/></div>
				<div class="value">${volume.ccondition}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.bound"/></div>
				<div class="value">${volume.bound ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.foliosNumbered"/></div>
				<div class="value">${volume.folsNumbrd ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.folioCount"/></div>
				<div class="value">${volume.folioCount}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.missingFolios"/></div>
				<div class="value">${fn2:toString(volumeSummary.missingFolios)}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.indexOfNames"/></div>
				<div class="value">${volume.oldAlphaIndex ? 'Yes' : 'No'}</div>
			</div>
			<c:if test="${schedone != null && (schedone.dimensioniBase != null || schedone.dimensioniAltezza != null)}">
				<div class="row">
					<div class="item"><fmt:message key="volbase.showDescriptionVolume.volumeHeight"/></div>
					<c:if test="${schedone.dimensioniAltezza == null}">
						<div class="value">-</div>
					</c:if>
					<c:if test="${schedone.dimensioniAltezza != null}">
						<div class="value">${schedone.dimensioniAltezza}<fmt:message key="volbase.showDescriptionVolume.volumeHeight.dimension"/></div>
					</c:if>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="volbase.showDescriptionVolume.volumeWidth"/></div>
					<c:if test="${schedone.dimensioniBase == null}">
						<div class="value">-</div>
					</c:if>
					<c:if test="${schedone.dimensioniBase != null}">
						<div class="value">${schedone.dimensioniBase} <fmt:message key="volbase.showDescriptionVolume.volumeWidth.dimension"/></div>
					</c:if>
				</div>
			</c:if>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.printedMaterial"/></div>
				<div class="value">${volume.printedMaterial ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.printedDrawings"/></div>
				<div class="value">${volume.printedDrawings ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.languages"/></div>
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
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.cipher"/></div>
				<div class="value">${volume.cipher ? 'Yes' : 'No'}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="volbase.showDescriptionVolume.cipherNotes"/></div>
				<div class="value">${volume.cipherNotes}</div>
			</div>
		</div>
	</div>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
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