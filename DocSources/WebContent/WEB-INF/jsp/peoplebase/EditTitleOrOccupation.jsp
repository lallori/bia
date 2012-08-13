<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTitleOrOccupationURL" value="/de/peoplebase/EditTitlesOrOccupationsPerson.do">
			<c:param name="titleOccId"   value="${command.titleOccId}" />
		</c:url>
		
	</security:authorize>
	<br>
	<form:form id="EditTitleOrOccupationForm" method="post" cssClass="edit">
		<!--- Loading div when saving the form -->
		<div id="loadingDiv"></div>
            <fieldset>
                <div class="listForm">
                    <div class="row">
                        <div class="col_l">
                            <label for="titleOcc" id="titleOccupationNameLabel">Title/Occupation Name</label>
                        </div>
                        <div class="col_r">
                            <input id="titleOccupationName" name="titleOcc" class="input_28c" type="text" value="">
                        </div>
                   </div>
                </div>
                <div>
                    <p><b>Role Categories</b></p>
                    <label for="roleCatId" id="ordByMajorLabel">Ordered by Role Category Mayor</label>
						<form:select id="ordByMajor" cssClass="selectform_XLong" path="roleCatId">
							<form:option value="" label="-Please Select" />
							<c:forEach var="roleCatMinor" items="${roleCat}">
							<form:option value="${roleCatMinor.roleCatId}">${roleCatMinor.roleCatMajor} / ${roleCatMinor.roleCatMinor}</form:option>
							</c:forEach>
						</form:select>
				</div>
                            
                <div>
                    <input id="closeDialog" type="submit" value="Close" title="do not save changes" class="button">
                    <input type="submit" value="Save" id="save">
                </div>
                
            </fieldset>	
		</form:form>
	
	<script type="text/javascript">
		$j(document).ready(function() {

			$j("#EditTitleOrOccupationForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditTitleOrOccupationDiv").load('${EditTitleOrOccupationURL}');
				}})
				return false;
			});
		});
	</script>
	
<div id="question" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditTitleOrOccupationDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditTitleOrOccupationURL}', cache: false, success:function(html) { 
				$j("#EditTitleOrOccupationDiv").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>
