<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div class="welcome_list">
    <h3>Welcome ${user.firstName}!</h3>
    <p>Please use the <span class="simpleSearch">Simple Search</span> tool to perform your search or the <span class="advancedSearch">Advanced Search</span> tool if you are an experienced user.</p>
    <p>To access the Scholarly Discussions just click on the <span class="communityForums">Community Forums</span> button.</p>
</div>

<script type="text/javascript">
	$j(document).ready(function(){
		$j(".simpleSearch").mouseover(function(){
            $j('#searchFormPointer').fadeIn("slow");
		});
		
		$j(".simpleSearch").mouseleave(function(){
            $j('#searchFormPointer').fadeOut("slow");
 		});
		
		$j(".advancedSearch").mouseover(function(){
       	    $j('#advancedSearchPointer').fadeIn("slow");
 		});
		
		$j(".advancedSearch").mouseleave(function(){
        	$j('#advancedSearchPointer').fadeOut("slow");
		});

		$j(".communityForums").mouseover(function(){
          	$j('#communityForumsPointer').fadeIn("slow");
 		});

		$j(".communityForums").mouseleave(function(){
          	$j('#communityForumsPointer').fadeOut("slow");
 		});
	})
</script>

