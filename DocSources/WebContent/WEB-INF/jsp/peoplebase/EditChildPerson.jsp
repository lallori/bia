<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditChildPersonURL" value="/de/peoplebase/EditChildPerson.do">
			<c:param name="personId"   value="${command.parentId}" />
		</c:url>
		<c:url var="EditChildrenPersonURL" value="/de/peoplebase/EditChildrenPerson.do">
			<c:param name="personId"   value="${command.parentId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.parentId}" />
		</c:url>
	</security:authorize>
	<br>
	<form:form id="EditChildPersonForm" action="${EditChildPersonURL}" method="post" cssClass="edit">
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>		
		<fieldset>
			<legend>
			<c:if test="${empty command.childId}"> 
				<b>ADD NEW CHILD</b>
			</c:if>
			<c:if test="${command.childId > 0}">
				<b>Edit CHILD</b>
			</c:if>
			</legend>
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title="<fmt:message key="peoplebase.addchild.edit.childname"></fmt:message>">?</a>
					<div class="col_r"><form:label id="childDescriptionLabel" for="childDescription" path="childDescription" cssErrorClass="error">Name</form:label></div>
					<div class="col_l"><form:input id="childDescriptionAutoCompleter" path="childDescription" class="input_30c" /></div>
				</div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_r"><form:label id="bornYearLabel" for="bornYear" path="bornYear" cssErrorClass="error">Born</form:label></div>
					<div class="col_l"><form:input path="bornYear" cssClass="input_4c_disabled" maxlength="4"/></div>
					<div class="col_r"><form:label id="deathYearLabel" for="deathYear" path="deathYear" cssErrorClass="error">Died</form:label></div>
					<div class="col_l"><form:input path="deathYear" cssClass="input_4c_disabled" maxlength="4"/></div>
					<div class="col_r"><form:label id="ageAtDeathLabel" for="ageAtDeath" path="ageAtDeath" cssErrorClass="error">Age at death</form:label></div>
					<div class="col_l"><form:input path="ageAtDeath" cssClass="input_2c_disabled" maxlength="2"/></div>
				</div>
			</div>
			
			<form:errors path="childId" cssClass="inputerrors" htmlEscape="false"/>
			
			<div>
				<input id="closeChild" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>
		</fieldset>	

		<form:hidden path="id"/>
		<form:hidden path="parentId"/>
		<form:hidden path="childId"/>
	</form:form>
	
	<c:url var="SearchChildLinkableToPersonURL" value="/de/peoplebase/SearchChildLinkableToPerson.json">
		<c:param name="personId" value="${command.parentId}" />
	</c:url>

	<c:url var="ShowChildDetailsURL" value="/de/peoplebase/ShowChildDetails.json" />

	<script type="text/javascript"> 
	    $j(document).ready(function() { 
	    	$j('#bornYear, #deathYear, #ageAtDeath').attr('disabled', 'disabled');
	    	
	    	$j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });
	    	
			var childDescription = $j('#childDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${SearchChildLinkableToPersonURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#childId').val(data);
					$j.get("${ShowChildDetailsURL}", { personId: "" + data }, function(data) {
						$j("#bornYear").val(data.bornYear);
						$j("#deathYear").val(data.deathYear);
						$j("#ageAtDeath").val(data.ageAtDeath);
					});
				}
			  });

			$j('#closeChild').click(function(e) {
				$j('.autocomplete').remove();
				$j('#EditChildPersonDiv').block({ message: $j('#question'),
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

			$j("#EditChildPersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					if($j(html).find(".inputerrors").length > 0){
						$j("#EditChildPersonDiv").html(html);
					}else{					
						$j("#EditChildrenPersonDiv").load('${EditChildrenPersonURL}');
					}
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
		
		$j('.helpIcon').tooltip({ 
			track: true, 
			fade: 350 
		});
		
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditChildPersonDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditChildrenPersonURL}', cache: false, success:function(html) { 
				$j("#EditChildrenPersonDiv").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>