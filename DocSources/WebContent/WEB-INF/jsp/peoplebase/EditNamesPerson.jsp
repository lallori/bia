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
		<c:forEach items="${command.altNames}" var="currentName">
			<c:url var="EditNamePersonURL" value="/de/peoplebase/EditNamePerson.do">
				<c:param name="personId" value="${command.personId}" />
				<c:param name="nameId" value="${currentName.nameId}" />
			</c:url>

			<c:url var="DeleteNamePersonURL" value="/de/peoplebase/DeleteNamePerson.do" >
				<c:param name="personId" value="${command.personId}" />
				<c:param name="nameId" value="${currentName.nameId}" />
			</c:url>

			<div>
      			<input id="name_${currentName.nameId}" name="name_${currentName.nameId}" class="input_28c_disabled" type="text" value="${currentName}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteNamePersonURL}"></a>
				<a class="editValue" class="editValue" href="${EditNamePersonURL}">edit value</a>
			</div>
		</c:forEach>
			
			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<a id="AddNewValue" title="Add new Name" href="${AddNamePersonURL}"></a>
			</div>
			
		</fieldset>	
		<div id="EditNamePersonDiv"></div>
	
		<script type="text/javascript">
			$j(document).ready(function() {
		        $j("#EditDetailsPerson").css('visibility', 'hidden'); 
		        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'hidden'); 
		        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden');
		        $j("#EditDocumentInManuscriptViewer").css('visibility', 'hidden');
		        $j("#EditDocumentInModal").css('visibility', 'hidden');
		        $j("#EditFactCheckDocument").css('visibility', 'hidden');
		        
		        $j('#close').click(function() {
					$j('#EditTopicsDocumentDiv').block({ message: $j('#question') }); 
					return false;
				});

		        $j(".deleteValue").click(function() {
					$j.get(this.href, function(data) {
						if(data.match(/KO/g)){
				            var resp = $j('<div></div>').append(data); // wrap response
						} else {
							$j("#EditNamesPersonDiv").load('${EditNamesPersonURL}');
						}
			        });
					return false;
				});

				$j(".editValue").click(function() {
					$j("#EditNamePersonDiv").load($j(this).attr("href"));
					return false;
				});

				$j("#topicDescription").click(function() {
					Modalbox.show(this.href, {title: this.title, width: 750});
					return false;
				});

				$j("#AddNewValue").click(function(){
					$j("#EditNamePersonDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</form:form>

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
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>