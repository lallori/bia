<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="searchForumAllDiv">
					<form id="SearchForumAll" action="<c:url value="/community/SimpleSearchForumPost.do"/>" method="post">
                        <input id="searchForumAllText" name="searchForumAllText" type="text" value="Search all forums...">
                        <input id="search" class="button_small" type="submit" title="Search" value="Search" disabled="disabled"/>
					</form>
				</div>

                <a href="<c:url value="/community/ShowAdvancedSearchForumPost.do"/>" class="buttonMedium button_medium" id="advancedSearchButton"><fmt:message key="community.fragments.searchForum.advancedSearch"/></a>


				<script>
					$j(document).ready(function() {
						$j('#searchForumAllText').click(function(){
							$j(this).val('');
							$j("#search").removeAttr('disabled');
							return false;
						});
						
						$j('#SearchForumAll').submit(function (){
							$j("#main").load($j(this).attr("action") + '?' + $j(this).serialize() + "&sortResults=POST_TIME&order=asc");
							return false;
						});

						$j('#advancedSearchButton').click(function (){
							$j("#main").load($j(this).attr("href"));
							return false;
						});

					});
				</script>
