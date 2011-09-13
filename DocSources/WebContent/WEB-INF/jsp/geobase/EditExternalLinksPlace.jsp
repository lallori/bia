<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditExternalLinksPlaceURL" value="/de/geobase/EditExternalLinksPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
		
		<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<form:form id="EditExternalLinksPlaceForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>EXTERNAL LINKS</b></legend>
		<div>
			<input id="firstLink" name="firstLink" class="input_35c_disabled" type="text" value="Wikipedia - Florence" disabled="disabled" />
			<a href="#" class="deleteIcon" title="Delete this entry"></a>
			<a id="editValue" href="/DocSources/de/geobase/EditExtLinkTgnPlace.html">edit value</a>
		</div>
		
		<div>
			<input id="secondLink" name="secondLink" class="input_35c_disabled" type="text" value="Google Maps - Florence" disabled="disabled" />
			<a href="#" class="deleteIcon" title="Delete this entry"></a>
			<a id="editValue" href="/DocSources/de/geobase/EditExtLinkTgnPlace.html">edit value</a>
		</div>
		
		<div>
			<input id="close" type="submit" value="Close" title="Do not save changes"/>
			<a href="#" id="AddNewValue" title="Add new Name">Add</a>
		</div>
		
		</fieldset>
	</form:form>
	

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'hidden');
			$j("#EditGeoCoorPlace").css('visibility', 'hidden'); 
			$j("#EditNamesOrNameVariantsPlace").css('visibility', 'hidden');

			$j("#EditExtLinkPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditExtLinkPlaceDiv").load($j(this).attr("href"));
				return false;
			});

			$j('#close').click(function(e) {
				$j('#EditExternalLinksPlaceForm').block({ message: $j('#question') }); 
	            return false;
			});
			
		});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1>discard changes?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#question").hide();
				$j("#EditExternalLinksPlaceForm").append($j("#question"));
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
