<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchURL" value="/src/AdvancedSearch.do"/>
	<c:url var="SaveUserSearchFilterURL" value="/src/SaveUserSearchFilter.do"/>

	<div id="yourSearchFilterDiv">
		<div class="yourSearchFilterTitle"></div>
		<div class="easySearchModeOn"></div>
		<form id="yourEasySearchFilterForm" action="${AdvancedSearchURL}" method="post">
			<p><u>Custom Search Filter</u></p>
			<br />
			<div id="namePartsSearchDiv">
<%-- 			<c:forEach items="${searchFilter.filterData.nameParts}" varStatus="iterator"> --%>
<!-- 				<div class="searchFilterDiv"> -->
<%-- 					<span class="categorySearch">Word Search in <fmt:message key="search.documents.wordType.${searchFilter.filterData.wordsTypes[iterator.index]}" />: </span><span class="wordSearch">${searchFilter.filterData.words[iterator.index]}</span><a class="remove" href="#">(remove)</a> --%>
<%-- 					<input type="hidden" value="${searchFilter.filterData.nameParts[iterator.index]}|${fn2:encode(searchFilter.filterData.nameParts[iterator.index])}" name="word"> --%>
<!-- 				</div> -->
<%-- 				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if> --%>
<%-- 			</c:forEach> --%>
			</div>
			<div id="wordSearchDiv">
<%-- 			<c:forEach items="${searchFilter.filterData.words}" varStatus="iterator"> --%>
<!-- 				<div class="searchFilterDiv"> -->
<%-- 					<span class="categorySearch">Word Search in <fmt:message key="search.documents.wordType.${searchFilter.filterData.wordsTypes[iterator.index]}" />: </span><span class="wordSearch">${searchFilter.filterData.words[iterator.index]}</span><a class="remove" href="#">(remove)</a> --%>
<%-- 					<input type="hidden" value="${searchFilter.filterData.words[iterator.index]}|${fn2:encode(searchFilter.filterData.nameParts[iterator.index])}" name="word"> --%>
<!-- 				</div> -->
<%-- 				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if> --%>
<%-- 			</c:forEach> --%>
			</div>
			<div id="dateSearchDiv"></div>
			<div id="roleCategorySearchDiv"></div>
			<div id="occupationSearchDiv"></div>
			<div id="placeSearchDiv"></div>
			<br />
			<br />
			<input type="submit" title="Search" value="Search" id="advsearch">
			<a class="saveButton" href="#">Save</a>
			<a class="saveAsButton" href="#">Save as</a>
			
			<input type="hidden" name="idSearchFilter" value="${command.idSearchFilter}">
			<input type="hidden" name="searchUUID" value="${command.searchUUID}">
			<input type="hidden" name="searchType" value="${command.searchType}">
		</form>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			var $dialogSaveAs = $j('<div id="DialogSaveAs"></div>').dialog({
				autoOpen: false,
				width: 250,
				height: 180,
				modal: true,
				zIndex: 3999,
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				position: ['center',250],
				open: function(event, ui) { 
            		$j.ajax({ type:"GET", url: '${SaveUserSearchFilterURL}', data: $j("#yourEasySearchFilterForm").serialize(), async:false, success:function(html) { 
						$j("#DialogSaveAs").focus();
						$j("#DialogSaveAs").html(html);
						} 
					});
           		},
				dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
				dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
			});

			$j('.saveAsButton').click(function(){
				if ($dialogSaveAs.dialog("isOpen")) {
					$dialogSaveAs.dialog("close");
					return false;
				} else {
					$dialogSaveAs.dialog("open");
					return false;
				}
				return false;
			});

			$j("#yourEasySearchFilterForm").submit(function() {
				// this is search url form 
				var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();

				// If we found refine button of this search, user is in refine.
				if (window.opener.$j('#tabs').find("#refine${command.searchUUID}").length==1) {
					// calculate tab position
					var index = window.opener.$j("#tabs ul li").index(window.opener.$j("li:has(a[href='#" + window.opener.$j("#tabs").find("#refine${command.searchUUID}").parent().attr("id") + "'])"));
					window.opener.$j("#tabs").tabs("url", index, formSubmitURL);
					window.opener.$j("#tabs").tabs("select", index);
					window.opener.$j("#tabs").tabs("load" , index);
				} else {
					//otherwise it's in a new search so we add a new tab.
					window.opener.$j("#tabs").tabs("add", formSubmitURL, "Advanced Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
				}
				return false;
			});
		});
	</script>
