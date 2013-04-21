<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamesPersonURL" value="/de/peoplebase/EditNamesPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="AddNamePersonURL" value="/de/peoplebase/EditNamePerson.do">
			<c:param name="personId"   value="${command.personId}" />
			<c:param name="nameId"  value="0" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditNamesPersonForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>NAMES</b></legend>
		<c:forEach items="${person.altName}" var="currentName">
			<c:url var="EditNamePersonURL" value="/de/peoplebase/EditNamePerson.do">
				<c:param name="personId" value="${command.personId}" />
				<c:param name="nameId" value="${currentName.nameId}" />
			</c:url>

			<c:url var="DeleteNamePersonURL" value="/de/peoplebase/DeleteNamePerson.do" >
				<c:param name="personId" value="${command.personId}" />
				<c:param name="nameId" value="${currentName.nameId}" />
			</c:url>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<select disabled="disabled" class="selectform_long_disabled" name="nameType" id="nameType">
							<option value="${currentName.nameType}" selected="selected">${currentName.nameType}</option>
						</select>
					</div>
					<div class="col_l"><input id="name_${currentName.nameId}" name="name_${currentName.nameId}" class="input_20c_disabled" type="text" value="${currentName.namePrefix} ${currentName.altName}" disabled="disabled" /></div>
					<div class="col_r"><a class="deleteIcon" title="Delete this entry" href="${DeleteNamePersonURL}"></a></div>
					<div class="col_r"><a class="editValue" class="editValue" href="${EditNamePersonURL}" title="Edit this entry"></a></div>
				</div>
			</div>
		</c:forEach>
			
			<div>
				<input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="AddNewValue" class="button_small fr" type="submit" value="Add" title="Add new Name" />
			</div>
			
		</fieldset>	
		<div id="EditNamePersonDiv"></div>
	</form:form>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditNamesPersonForm");
			
			$j("#EditDetailsPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditParentsPerson").css('visibility', 'hidden');
			$j("#EditChildrenPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
	        
	        $j('#close').click(function() {
				$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
					
				return false;
			});

	        $j(".deleteIcon").click(function() {
	        	var toDelete = $j(this);
	        	$j("#EditNamesPersonDiv").block({ message: $j(".question"),
	        		css: { 
						border: 'none', 
						padding: '5px',
						boxShadow: '1px 1px 10px #666',
						'-webkit-box-shadow': '1px 1px 10px #666'
						} ,
						overlayCSS: { backgroundColor: '#999' }	
	        	});
	        	
	        	$j('.no').click(function(){
	        		$j.unblockUI();
	        		$j(".blockUI").fadeOut("slow");
					$j(".question").hide();
					$j("#EditNamesPersonDiv").append($j(".question"));
					$j(".blockUI").remove();
					$j("#EditNamesPersonDiv").load('${EditNamesPersonURL}');
					return false; 
	        	});
	        	
	        	$j(".yes").click(function(){
	        		$j.get(toDelete.attr("href"), function(data) {
						if(data.match(/KO/g)){
			            	var resp = $j('<div></div>').append(data); // wrap response
						} else {
							$j("#EditNamesPersonDiv").load('${EditNamesPersonURL}');
							return false;
						}
						return false;
		        	});
					
	        	});
	        	return false;
			});

			$j(".editValue").click(function() {
				$j("#EditNamePersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j("#AddNewValue").click(function(){
				$j("#EditNamePersonDiv").load("${AddNamePersonURL}");
				return false;
			});
		});
	</script>
	
	<div class="question" style="display:none; cursor: default"> 
		<h1>Are you sure you want to delete this name variant?</h1> 
		<input type="button" class="yes" value="Yes" /> 
		<input type="button" class="no" value="No" /> 
	</div>
