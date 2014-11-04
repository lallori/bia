<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditExternalLinksPlaceURL" value="/de/geobase/EditExternalLinksPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
		
		<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
		
		<c:url var="AddExternalLink" value="/de/geobase/EditExternalLinkPlace.do">
				<c:param name="placeAllId" value="${place.placeAllId}" />
				<c:param name="placeExternalLinksId" value="0" />
		</c:url>
	</security:authorize>
	
	<form:form id="EditExternalLinksPlaceForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b><fmt:message key="geobase.editExternalLinksPlace.externalLinks"/></b></legend>
		<c:forEach items="${place.placeExternalLinks}" var="currentExternalLink">
			<c:url var="EditExternalLink" value="/de/geobase/EditExternalLinkPlace.do">
				<c:param name="placeAllId" value="${place.placeAllId}" />
				<c:param name="placeExternalLinksId" value="${currentExternalLink.placeExternalLinksId}" />
			</c:url>
			
			<c:url var="DeleteExternalLink" value="/de/geobase/DeleteExternalLinkPlace.do">
				<c:param name="placeAllId" value="${place.placeAllId}" />
				<c:param name="placeExternalLinksId" value="${currentExternalLink.placeExternalLinksId}" />
			</c:url>
		
		<div class="listForm">
			<div class="row">
				<div class="col_l"><input id="externalLink_${currentExternalLink.placeExternalLinksId}" name="externalLink_${currentExternalLink.placeExternalLinksId}" class="input_40c_disabled" type="text" value="${currentExternalLink.description}" disabled="disabled" /></div>
				<div class="col_r"><a href="${DeleteExternalLink}" class="deleteIcon" title="Delete this entry"></a></div>
				<div class="col_r"><a id="editValue" class="editValue" href="${EditExternalLink}" title="Edit this entry"></a></div>
			</div>
		</div>
		</c:forEach>
		
		<div>
			<input id="close" class="button_small fl" type="submit" value="Close" title="Do not save changes"/>
			<input id="AddNewValue" class="button_small fr" value="Add" title="Add new Name" type="submit"/>
		</div>
		
		</fieldset>
	</form:form>
	
	<div id="EditExternalLinkDiv"></div>
	

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'hidden');
			$j("#EditGeoCoorPlace").css('visibility', 'hidden'); 
			$j("#EditNamesOrNameVariantsPlace").css('visibility', 'hidden');
			
			$j(".deleteIcon").click(function() {
					$j.get(this.href, function(data) {
						if(data.match(/KO/g)){
				            var resp = $j('<div></div>').append(data); // wrap response
						} else {
							$j("#EditExtLinkPlaceDiv").load('${EditExternalLinksPlaceURL}');
						}
			        });
					return false;
			});
			
			$j(".editValue").click(function() {
				$j("#EditExternalLinkDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j("#AddNewValue").click(function(){
				$j("#EditExternalLinkDiv").load("${AddExternalLink}");
				return false;
			});

			$j('#close').click(function(e) {
				$j.ajax({ url: '${ShowPlaceURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});

				return false;
			});
			
		});
	</script>
