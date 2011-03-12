<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="editPersonalNotesForm" value="/src/mview/EditPersonalNotesDialog.do?"/>

	<form:form id="EditPersonalNotesForm" action="${editPersonalNotesForm}" method="post" cssClass="edit">
		<form:textarea id="personalNotes" path="personalNotes" rows="20" style="width: 96%; height: 96%;"/>
		<input type="image" alt="Save Notes" src="/DocSources/images/mview/button_saveNotes.png" id="saveNotes">
		<input type="image" alt="Clean Notes" src="/DocSources/images/mview/button_cleanNotes.png" id="cleanNotes">
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#saveNotes").click(function (){
				$j.ajax({ type:"POST", url:$j("#EditPersonalNotesForm").attr("action"), data:$j("#EditPersonalNotesForm").serialize(), async:false, success:function(html) { 
						$j("#DialogPersonalNotes").html(html);
						personalNotesChanged=false;
					} 
				});
				return false;
			});
			
			$j("#cleanNotes").click(function (){
				$j("#personalNotes").val('');
				$j.ajax({ type:"POST", url:$j("#EditPersonalNotesForm").attr("action"), data:$j("#EditPersonalNotesForm").serialize(), async:false, success:function(html) { 
						$j("#DialogPersonalNotes").html(html);
						personalNotesChanged=false;
					} 
				});
				return false;
			});

			$j("#personalNotes").change(function(){
				personalNotesChanged=true;
			});
		});
	</script>

