<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamesOrNamesVariantsPlaceURL" value="/de/geobase/EditNamesOrNameVariantsPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
			<c:param name="geogKey" value="${command.geogKey}" />
		</c:url>
	</security:authorize>
	
	<br />
	<form:form id="EditNamePlaceForm" method="post" class="edit">
	<fieldset>
		<legend>
		<c:if test="${command.currentPlaceAllId == 0}"> 
				<b>ADD NEW NAME</b>
			</c:if>
			<c:if test="${command.currentPlaceAllId > 0}">
				<b>Edit NAME</b>
			</c:if> 
		</legend>
		<div>
			<form:label for="namePlace" id="namePlaceLabel" path="plName">Name</form:label>
			<form:input id="namePlace" path="plName" cssClass="input_30c" type="text" />
		</div>
		
		<div>
			<form:label for="nameType" id="nameTypeLabel" path="plType">Place Type</form:label>
			<form:input id="nameType" path="plType" cssClass="input_35c_disabled" type="text" disabled="disabled" />
		</div>
		
		<div>
			<input id="closeName" type="submit" value="Close" title="Do not save changes" />
			<input id="save" type="submit" value="Save" />
		</div>
		
	</fieldset>	
	</form:form>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'hidden');
			$j("#EditGeoCoorPlace").css('visibility', 'hidden'); 
			$j("#EditExtLinkPlace").css('visibility', 'hidden');

			$j('#nameType').attr('disabled', 'disabled');
			
			$j('#closeName').click(function(e) {
				$j('#EditNameVariantPlaceDiv').block({ message: $j('#question') }); 
	            return false;
			});
			
			$j("#EditNamePlaceForm").submit(function (){
				if($j("#namePlace").val() == ""){
					return false;
				}
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditNamePlaceDiv").load('${EditNamesOrNamesVariantsPlaceURL}');
				}})
				return false;
			});
		});
	</script>
</security:authorize>

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
			$j("#EditNamePlaceDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditNamesOrNamesVariantsPlaceURL}', cache: false, success:function(html) { 
				$j("#EditNamePlaceDiv").html(html);
			}});

			return false; 
		}); 
     
	});
</script>