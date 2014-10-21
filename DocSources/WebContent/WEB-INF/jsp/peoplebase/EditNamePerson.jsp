<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditNamePersonURL" value="/de/peoplebase/EditNamePerson.do" />

		<c:url var="EditNamesPersonURL" value="/de/peoplebase/EditNamesPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>
	<br>
	
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditNamePersonForm" action="${EditNamePersonURL}" method="post" cssClass="edit">
		<fieldset>
			<legend>
			<c:if test="${empty command.nameId}"> 
				<b><fmt:message key="peoplebase.editNamePerson.addNewName"/></b>
			</c:if>
			<c:if test="${command.nameId > 0}">
				<b><fmt:message key="peoplebase.editNamePerson.editName"/></b>
			</c:if> 
			</legend>
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="<fmt:message key="peoplebase.editNamePerson.help.name"></fmt:message>">?</a>
						<form:label id="altNameLabel" for="altName" path="altName" cssErrorClass="error"><fmt:message key="peoplebase.editNamePerson.name"/></form:label>
					</div>
					<div class="col_l"><form:input path="altName" class="input_30c" /></div>
				</div>
				<div class="row">
					<div class="col_l"><form:label id="nameTypeLabel" for="nameType" path="nameType" cssErrorClass="error"><fmt:message key="peoplebase.editNamePerson.nameType"/></form:label></div>
					<div class="col_l"><form:select path="nameType" cssClass="selectform_Mlong" items="${nameTypes}"/></div>
				</div>
			</div>
			
			<div>
				<input id="closePerson" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
			</div>
			<input type="hidden" value="" id="modify" />
		</fieldset>	

		<form:hidden path="nameId"/>
		<form:hidden path="personId"/>
	</form:form>

	<script type="text/javascript"> 
	    $j(document).ready(function() {
	    	
	    	$j("#EditNamePersonForm :input").change(function(){
					$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
					return false;
			});
	    	
	    	
	    	$j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });
	    	
	    	
			$j('#closePerson').click(function(e) {
// FAR FUNZIONARE ANCHE QUI				
				if($j("#modify").val() == 1){
	        		// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
					$j('#EditNamePersonDiv').block({ message: $j('#question'),
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
		       		$j.ajax({ url: '${EditNamesPersonURL}', cache: false, success:function(html) { 
						$j("#EditNamesPersonDiv").html(html);
					}});
				}
				return false;
	            
			});
			
			
    				
    			

			$j("#EditNamePersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditNamesPersonDiv").load('${EditNamesPersonURL}');
				}})
				return false;
			});
		});					  
	</script>

<div id="question" style="display:none; cursor: default"> 
	<h1><fmt:message key="peoplebase.editNamePerson.discardChangesQuestion"/></h1> 
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
			$j("#EditNamePersonDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditNamesPersonURL}', cache: false, success:function(html) { 
				$j("#EditNamesPersonDiv").html(html);
			}});

			return false; 
		}); 
     
	});
</script>