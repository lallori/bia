<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditTitleOrOccupationURL" value="/de/peoplebase/EditTitlesOrOccupationsPerson.do">
			<c:param name="titleOccId"   value="${command.titleOccId}" />
		</c:url>
		
		<c:url var="ShowTitleOrOccupationURL" value="/src/peoplebase/ShowTitleOrOccupation.do">
			<c:param name="titleOccId" value="${command.titleOccId}" />
		</c:url>
		
	</security:authorize>
	
	<form:form id="EditTitleOrOccupationForm" method="post" cssClass="edit">
		<!--- Loading div when saving the form -->
		<div id="loadingDiv"></div>
            <fieldset>
            	<legend><b><fmt:message key="peoplebase.editTitleOrOccupation.editTitlesOccupations”/></b></legend>
                <div class="listForm">
                    <div class="row">
                        <div class="col_l">
                            <form:label for="titleOcc" id="titleOccupationNameLabel" path="titleOcc"><fmt:message key="peoplebase.editTitleOrOccupation.titleOccupationName”/></form:label>
                        </div>
                        <div class="col_r">
                            <form:input id="titleOccupationName" name="titleOcc" path="titleOcc" class="input_33c" type="text" value="" />
                        </div>
                   </div>
                	<div class="row">
                    	<div class="col_l"><fmt:message key="peoplebase.editTitleOrOccupation.roleCategories”/></div>
                    	<div class="col_r">
							<form:select id="ordByMajor" cssClass="selectform_XXLlong" path="roleCatId">
								<form:option value="" label="-Please Select" />
								<c:forEach var="roleCatMinor" items="${roleCat}">
								<form:option value="${roleCatMinor.roleCatId}">${roleCatMinor.roleCatMajor} / ${roleCatMinor.roleCatMinor}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>
                            
                <div>
                    <input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
                    <input type="submit" value="Save" id="save" class="button_small fr" />
                </div>
                <input type="hidden" value="" id="modify" />
            </fieldset>	
		</form:form>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			
			$j("#EditTitleOrOccupationForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
			
			$j("#close").click(function(){
				if($j("#modify").val() == 1){
					$j("#EditTitleOccupationDiv").block({ message: $j("#question"),
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
					$j.ajax({ url: '${ShowTitleOrOccupationURL}', cache: false, success:function(html) { 
						$j("#EditTitleOccupationDiv").html(html);
					}});
						
					return false; 
				}
			});

			$j("#EditTitleOrOccupationForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditTitleOccupationDiv").html(html)
				}})
				return false;
			});
		});
	</script>
	
<div id="question" style="display:none; cursor: default"> 
	<h1><fmt:message key="peoplebase.editTitleOrOccupation.discardChangesQuestion”/></h1> 
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
			$j("#EditTitleOccupationDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowTitleOrOccupationURL}', cache: false, success:function(html) { 
				$j("#EditTitleOccupationDiv").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>
