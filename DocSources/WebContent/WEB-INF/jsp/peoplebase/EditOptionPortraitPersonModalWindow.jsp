<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

	<c:url var="EditOptionPortraitPersonURL" value="/de/peoplebase/EditOptionPortraitPerson.do">
		<c:param name="personId" value="${person.personId }" />
	</c:url>
	
	<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	
	<form:form id="editOptionPortraitPersonForm" action="${EditOptionPortraitPersonURL}" method="post" class="edit" enctype="multipart/form-data">
		<div class="listForm">
			<div class="row">
				<div class="col_l">
					<form:label for="portraitAuthor" id="portraitAuthorLable" path="portraitAuthor"><fmt:message key="peoplebase.people.editOptionPortraitPersonModalWindow.author"/>
</form:label>
				</div>
				<div class="col_l">
					<form:input id="portraitAuthor" name="portraitAuthor" path="portraitAuthor" class="input_40c" type="text" />
				</div>
			</div>
			<div class="row">
				<div class="col_l">
					<form:label for="portraitSubject" id="portraitSubjectLabel" path="portraitSubject"><fmt:message key="peoplebase.people.editOptionPortraitPersonModalWindow.subject"/>
</form:label>
				</div>
				<div class="col_l">
					<form:input id="portraitSubject" name="portraitSubject" path="portraitSubject" class="input_28c" type="text" value="" size="30" />
				</div>
			</div>
		</div>
		<br>
		<div>
			<input type="hidden" value="${person.personId}" />
			<input id="save" type="submit" class="savePortrait button_small" value="Save" />
		</div>
	
	</form:form>
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#editOptionPortraitPersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#body_left").load('${ShowPersonURL}');
					$j(".optionPortraitWindow").dialog("close");
					$j(".optionPortraitWindow").remove();
				}});
				return false;
			});
		});
		</script>