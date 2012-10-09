<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditPersonalNotesUserURL" value="/user/EditPersonalNotesUser.do" />
	
	<c:url var="DeletePersonalNotesUserURL" value="/user/DeletePersonalNotesUser.do" />
	
	<c:url var="ShowPersonalNotesUserURL" value="/user/ShowPersonalNotesUser.do" />
	
	<form:form id="EditPersonalNotesForm" method="post" cssClass="edit">
			
		<form:textarea path="personalNotes" cssClass="txtarea_personalNotes"/>

		<div id="closesavePersonalNotes">
	   		<a id="goBack" href="${ShowPersonalNotesUserURL}" title="Go Back to Personal Notes" >Go Back</a>
			<a id="close" href="#" title="Close this window">Close</a>
		</div>
	    <input id="savePersonalNotes" type="submit" title="Save your Personal Notes" value="Save"/>
		
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#goBack").click(function(){
				Modalbox.show($j(this).attr("href"), {title: "PERSONAL NOTES", width: 750, height: 415});
				return false;
			});
						
			$j("#EditPersonalNotesForm").submit(function(){
				if($j("#personalNotes") != ""){
					$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						$j("#EditPersonalNotes").html(html);
					}});
				}
				return false;
			});
		});
	</script>
