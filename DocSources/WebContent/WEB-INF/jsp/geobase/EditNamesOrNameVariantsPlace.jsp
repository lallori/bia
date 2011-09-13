<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamesOrNameVariantsPlaceURL" value="/de/geobase/EditNamesOrNameVariantsPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
		
		<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<form:form id="EditNamesOrNameVariantsPlaceForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>NAME or NAMES VARIANTS</b></legend>
		<div>
			<input id="firstName" name="firstName" class="input_35c_disabled" type="text" value="Firenze" disabled="disabled" />
			<a href="#" class="deleteIcon" title="Delete this entry"></a>
			<a class="editValue" href="/DocSources/de/geobase/EditNameTgnPlace.html">edit value</a>
		</div>
		
		<div>
			<input id="secondLink" name="secondLink" class="input_35c_disabled" type="text" value="Fiorenza" disabled="disabled" />
			<a href="#" class="deleteIcon" title="Delete this entry"></a>
			<a class="editValue" href="/DocSources/de/geobase/EditNameTgnPlace.html">edit value</a>
		</div>
		
		<div>
			<input id="thirdLink" name="thirdLink" class="input_35c_disabled" type="text" value="Florencia" disabled="disabled" />
			<a href="#" class="deleteIcon" title="Delete this entry"></a>
			<a class="editValue" href="/DocSources/de/geobase/EditNameTgnPlace.html">edit value</a>
		</div>
		
		<div>
			<input id="fourthLink" name="fourthLink" class="input_35c_disabled" type="text" value="Fiorentia" disabled="disabled" />
			<a href="#" class="deleteIcon" title="Delete this entry"></a>
			<a class="editValue" href="/DocSources/de/geobase/EditNameTgnPlace.html">edit value</a>
		</div>
		
		<div>
			<input id="fifthLink" name="fifthLink" class="input_35c_disabled" type="text" value="Fiorentine" disabled="disabled" />
			<a href="#" class="deleteIcon" title="Delete this entry"></a>
			<a class="editValue" href="/DocSources/de/geobase/EditNameTgnPlace.html">edit value</a>
		</div>
		
		<div>
			<input id="sixthLink" name="sixthLink" class="input_35c_disabled" type="text" value="Florenz" disabled="disabled" />
			<a href="#" class="deleteIcon" title="Delete this entry"></a>
			<a class="editValue" href="/DocSources/de/geobase/EditNameTgnPlace.html">edit value</a>
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
			$j("#EditExtLinkPlace").css('visibility', 'hidden');

			$j("#EditNamesOrNameVariantsPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditNamesOrNameVariantsPlaceDiv").load($j(this).attr("href"));
				return false;
			});

			$j('#close').click(function(e) {
				$j('#EditNamesOrNameVariantsPlaceForm').block({ message: $j('#question') }); 
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
				$j("#EditNamesOrNameVariantsPlaceForm").append($j("#question"));
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
