<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		
	</security:authorize>
	<br>
	<form:form id="CreateNewTitlesOccupationsForm" method="post" cssClass="edit">
	<!--- Loading div when saving the form -->
	<div id="loadingDiv"></div>
		<fieldset>
			<div>
				<form:label id="titleOccupationNameLabel" for="titleOcc" path="titleOcc" cssErrorClass="error">Title/Occupation Name</form:label>
				<form:input id="titleOccupationName" path="titleOcc" cssClass="input_29c" type="text" value=""/>
			</div>
			<div>
				<b>Role Categories</b><br />
				<form:label id="ordByMajorLabel" for="roleCatId" path="roleCatId">Ordered by Role Category Mayor</form:label>
				<form:select id="ordByMajor" cssClass="selectform_XLong" path="roleCatId">
					<form:option value="-" label="-Please Select" />
					<c:forEach var="roleCatMinor" items="${roleCat}">
						<form:option value="${roleCatMinor.roleCatId}">${roleCatMinor.roleCatMajor} / ${roleCatMinor.roleCatMinor}</form:option>
					</c:forEach>
<%-- 					<form:options items="${roleCat}" itemValue="roleCatId" itemLabel="roleCatMinor" /> --%>
				</form:select>
			</div>
						
			<div>
				<input id="closeDialog" type="submit" value="Close" title="do not save changes" class="button" />
				<input type="submit" value="Save" id="save">
			</div>
			
		</fieldset>	
	</form:form>
	
	
	<script type="text/javascript">
		$j(document).ready(function() {
			
				$j("#save").click(function(){
	        		$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       			$j("#loadingDiv").css('visibility', 'visible');
	        	});
			
				$j('#closeDialog').click(function() {
					$j("#DialogNewTitleOccupation").block({ message: $j("#question"),
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

			$j("#CreateNewTitlesOccupationsForm").submit(function (){
				if($j("#ordByMajor").val() == '-'){
					$j("#DialogNewTitleOccupation").block({ message: $j("#questionError"),
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
						});
					return false;
				}else{
					$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
						$j("#DialogNewTitleOccupation").dialog('close');
					}})
					return false;
				}
			});	
			
		});
	</script>
	
<div id="question" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<div id="questionError" style="display:none; cursor:default">
	<h1>Please select the Role Category</h1>
	<input type="button" id="ok" value="Ok" />
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#DialogNewTitleOccupation").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditTitlesOrOccupationsPersonURL}', cache: false, success:function(html) { 
				$j("#EditTitlesOrOccupationsPersonDiv").html(html);
			}});
			$j("#DialogNewTitleOccupation").dialog('close');
				
			return false; 
		}); 
		
		$j('#ok').click(function(){
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#questionError").hide();
			$j("#DialogNewTitleOccupation").append($j("#questionError"));
			$j(".blockUI").remove();
			return false;
		});
     
	});
</script>