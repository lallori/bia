<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="searchForm">
					<form id="SearchForm" action="<c:url value="/community/SimpleSearchForumPost.do"/>" method="post">
                        <input id="searchText" name="searchText" type="text" value="Search...">
                        <input id="search" type="submit" title="Search" value="Search"/>
					</form>
				</div>

                <a href="<c:url value="/community/AdvancedSearchForumPost.do"/>" class="buttonMedium" id="advancedSearchButton">Advanced Search</a>


				<script>
					$j(document).ready(function() {
						$j('#SearchForm').submit(function (){
							$j("#mainContent").load($j(this).attr("href"));
							return false;
						});

						$j('#advancedSearchButton').submit(function (){
							$j("#mainContent").load($j(this).attr("href"));
							return false;
						});

					});
				</script>
