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
	</security:authorize>
	
	<div id="loadingDiv"></div>
	<form:form id="EditExternalLinkForm" method="post" cssClass="edit">
		<fieldset>
		<c:if test="${command.placeExternalLinksId == 0}">
			<legend><b><fmt:message key="geobase.editExternalLinkPlace.addExternalLink"/></b></legend>
		</c:if>
		<c:if test="${command.placeExternalLinksId > 0 }">
			<legend><b><fmt:message key="geobase.editExternalLinkPlace.externalLinks"/></b></legend>
		</c:if>
		
		<div class="listForm">
			<div class="row">
				<div class="col_l">
					<a class="helpIcon" title="Text goes here">?</a>
					<form:label for="name" id="nameLabel" path="description"><fmt:message key="geobase.editExternalLinkPlace.name"/></form:label>
				</div>
				<div class="col_l"><form:input id="name" path="description" cssClass="input_30c" type="text" /></div>
			</div>
			<div class="row">
				<div class="col_l">
					<a class="helpIcon" title="Text goes here">?</a>
					<form:label for="link" id="linkLabel" path="externalLink"><fmt:message key="geobase.editExternalLinkPlace.link"/></form:label>
				</div>
				<div class="col_l"><form:input id="link" path="externalLink" class="input_39c" type="text" /></div>
			</div>
		</div>		
		
		<div>
			<input id="closeExternalLink" class="button_small fl" type="submit" value="Close" title="Do not save changes"/>
			<input id="save" class="button_small fr" type="submit" value="Save"  />
		</div>
		
		</fieldset>
			
		<form:hidden path="placeExternalLinksId" />
		<form:hidden path="placeAllId" />
	
	</form:form>
		
<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditExternalLinkForm");
			
			$j("#EditDetailsPlace").css('visibility', 'hidden');
			$j("#EditGeoCoorPlace").css('visibility', 'hidden'); 
			$j("#EditNamesOrNameVariantsPlace").css('visibility', 'hidden');
			
			$j('#closeExternalLink').click(function() { 
	            $j('#EditExternalLinkDiv').block({ message: $j('#question'),
	            	css: { 
						border: 'none', 
						padding: '5px',
						boxShadow: '1px 1px 10px #666',
						'-webkit-box-shadow': '1px 1px 10px #666'
						} ,
						overlayCSS: { backgroundColor: '#999' }	
	            }); 
	            return false;
			});

			$j("#EditExternalLinkForm").submit(function (){
				$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditExtLinkPlaceDiv").load('${EditExternalLinksPlaceURL}');
				}})
				return false;
			});
			
		});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1><fmt:message key="geobase.editExternalLinkPlace.discardChangesQuestion"/></h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			
			$j('.helpIcon').tooltip({ 
				track: true, 
				fade: 350 
			});
			
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
