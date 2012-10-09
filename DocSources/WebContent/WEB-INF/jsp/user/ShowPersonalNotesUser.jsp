<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditPersonalNotesUserURL" value="/user/EditPersonalNotesUser.do" />
	
	<c:url var="DeletePersonalNotesUserURL" value="/user/DeletePersonalNotesUser.do" />
	
	<c:url var="ShowPersonalNotesUserURL" value="/user/ShowPersonalNotesUser.do" />
	
	<c:url var="ShowUserProfileURL" value="/user/ShowUserProfile.do"/>

	<div id="modalBox">
		<div id="EditPersonalNotes">
			<div class="personalNotesText">
				${userPersonalNotes.personalNotes}
			</div>
	
		<div id="editCleanClose">
			<a id="edit" href="${EditPersonalNotesUserURL}" title="Edit your Personal Notes">Edit</a>
			<a id="cleanAll" href="${DeletePersonalNotesUserURL}" title="Delete all your Personal Notes">Delete</a>
			<a id="close" href="#" title="Close this window">Close</a>
			<a id="goBack" href="${ShowUserProfileURL}" title="Go Back to My Profile">Go back</a>
		</div>
		
		</div>	
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#edit").click(function(){
				$j("#EditPersonalNotes").load($j(this).attr("href"));
				return false;
			});
			$j("#cleanAll").click(function(){
				//$j("#EditPersonalNotes").load($j(this).attr("href"));
				return false;
			});
			$j("#goBack").click(function(){
				Modalbox.show($j(this).attr("href"), {title: "USER PREFERENCES", width: 760, height: 470});																
				return false;
			});
			$j("#close").click(function(){
				Modalbox.hide(); 
				return false;
			});
			
			$j(".personalNotesText").html($j(".personalNotesText").text().replace(/\n\r?/g, '<br />'));
		});
	</script>
