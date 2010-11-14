<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="searchForm">
					<form id="SearchForm" action="<c:url value="/SearchData.do"/>" method="post">
						Search in <select name="searchType" class="select" style="margin-left:8px">
										<option value="documents" selected>Documents</option>
										<option value="volumes">Volumes</option>
										<option value="people">People</option>
										<option value="places">Places</option>
								</select>
						<br/>
							for <input id="text" name="text" type="text" value="" class="input_search" style="margin-top:5px"/>
						<br/><input id="create" type="image" src="<c:url value="/images/button_search.png"/>" alt="submit" title="submit form" style="margin-left:125px"/>
					</form>
				</div>
				<script type="text/javascript">
					$(document).ready(function() {
						$("#SearchForm").submit(function() {
							$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) {
									$("#body_left").html(html);
								}
							});
						return false;});
					});
				</script>