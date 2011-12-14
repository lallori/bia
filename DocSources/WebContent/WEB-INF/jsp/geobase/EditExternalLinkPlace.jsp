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
	
	<form:form id="EditExternalLinkForm" method="post" cssClass="edit">
		<fieldset>
		<c:if test="${command.placeExternalLinksId == 0}">
			<legend><b>ADD EXTERNAL LINK</b></legend>
		</c:if>
		<c:if test="${command.placeExternalLinksId > 0 }">
			<legend><b>EXTERNAL LINKS</b></legend>
		</c:if>
				
		<div>
			<form:label for="name" id="nameLabel" path="description">Name</form:label>
			<form:input id="name" path="description" cssClass="input_30c" type="text" />
		</div>
		
		<div>
			<form:label for="link" id="linkLabel" path="externalLink">Link</form:label>
			<form:input id="link" path="externalLink" class="input_39c" type="text" />
		</div>
		
		<div>
			<input id="closeExternalLink" type="submit" value="Close" title="Do not save changes"/>
			<input id="save" type="submit" value="Save" class="button" />
		</div>
		
		</fieldset>
			
		<form:hidden path="placeExternalLinksId" />
		<form:hidden path="placeAllId" />
	
	</form:form>
		
<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'hidden');
			$j("#EditGeoCoorPlace").css('visibility', 'hidden'); 
			$j("#EditNamesOrNameVariantsPlace").css('visibility', 'hidden');
			
			$j('#closeExternalLink').click(function() { 
	            $j('#EditExternalLinkDiv').block({ message: $j('#question') }); 
	            return false;
			});

			$j("#EditExternalLinkForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditExtLinkPlaceDiv").load('${EditExternalLinksPlaceURL}');
				}})
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
				$j("#EditExtLinkPlaceDiv").append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${EditExternalLinksPlaceURL}', cache: false, success:function(html) { 
					$j("#EditExtLinkPlaceDiv").html(html);
				}});
	
				return false; 
			}); 
	     
		});
	</script>
