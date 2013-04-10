<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	

<c:url var="ShowUserProfileURL" value="/user/ShowUserProfile.do"/>		
<c:url var="ShowMyMarkedListURL" value="/user/ShowMyMarkedList.do"/>

<c:url var="ShowPersonalAnnotationsUserURL" value="/user/ShowPersonalAnnotationsUser.do" />

<c:url var="ShowPersonalNotesUserURL" value="/user/ShowPersonalNotesUser.do" />

<div id="personalDirectoryModalDiv">
	<div id="userProfileDiv">
        <a id="personalUserProfile" href="${ShowUserProfileURL}">User Preferences</a>
	</div>
    
	<div id="personalAnnotationsDiv">
        <a id="personalAnnotations" href="${ShowPersonalAnnotationsUserURL}">Personal Annotations</a>
	</div>
    
	<div id="personalNotesDiv">
        <a id="personalNotes" href="${ShowPersonalNotesUserURL}">Personal Notes</a>
	</div>

	<input id="close" type="submit" title="Close Digitization Module window" value="Close"/>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#markedList").click(function() {															
			Modalbox.show($j(this).attr("href"), {title: "MY MARKED LIST", width: 750, height: 415});
			return false;
		});	
		
		$j("#personalUserProfile").click(function() {
			Modalbox.show($j(this).attr("href"), {title: "USER PREFERENCES", width: 760, height: 470});
			return false;
		});	
		
		$j("#personalNotes").click(function(){
			Modalbox.show($j(this).attr("href"), {title: "PERSONAL NOTES", width: 750, height: 415});
			return false;
		});
		
		$j("#personalAnnotations").click(function(){
			Modalbox.show($j(this).attr("href"), {title: "PERSONAL ANNOTATIONS", width: 750, height: 415});
			return false;
		});
		
		$j("#close").click(function(){
			Modalbox.hide();
			return false;
		});
	});
</script>
	