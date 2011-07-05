<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamePersonURL" value="/de/peoplebase/EditNamePerson.do" />

		<c:url var="EditNamesPersonURL" value="/de/peoplebase/EditNamesPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>
	<br>
	<form:form id="EditNamePersonForm" action="${EditNamePersonURL}" method="post" cssClass="edit">
		<fieldset>
			<legend>
			<c:if test="${empty command.nameId}"> 
				<b>ADD NEW NAME</b>
			</c:if>
			<c:if test="${command.nameId > 0}">
				<b>Edit NAME</b>
			</c:if> 
			</legend>
			<div>
				<form:label id="altNameLabel" for="altName" path="altName" cssErrorClass="error">Name:</form:label>
				<form:input path="altName" class="input_30c" />
			</div>
			<div>
				<form:label id="nameTypeLabel" for="nameType" path="nameType" cssErrorClass="error">NameType:</form:label>
				<form:select path="nameType" cssClass="selectform_long" items="${nameTypes}"/>
			</div>
			<div>
				<input id="closePerson" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>
		</fieldset>	

		<form:hidden path="nameId"/>
		<form:hidden path="personId"/>
	</form:form>

	<script type="text/javascript"> 
	    $j(document).ready(function() { 
			$j('#closePerson').click(function(e) {
				$j('#EditNamePersonDiv').block({ message: $j('#question') }); 
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