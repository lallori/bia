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
			<c:forEach items="${searchFilter.filterData.names}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Name Parts in <fmt:message key="${searchFilter.filterData.namesTypes[iterator.index]}" />: </span><span class="wordSearch">${searchFilter.filterData.names[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.namesTypes[iterator.index]}|${fn2:encode(searchFilter.filterData.names[iterator.index])}" name="nameParts">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.words) && ((not empty searchFilter.filterData.names))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="wordSearchDiv">
			<c:forEach items="${searchFilter.filterData.words}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Word Search: </span><span class="wordSearch">${searchFilter.filterData.words[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.words[iterator.index])}" name="word">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.datesTypes) && ((not empty searchFilter.filterData.names) || (not empty searchFilter.filterData.words))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="dateSearchDiv">
			<c:forEach items="${searchFilter.filterData.datesTypes}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">${searchFilter.filterData.datesTypes[iterator.index]}: </span>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Any'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Born after'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Died by'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Lived between'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}, ${searchFilter.filterData.datesYearBetween[iterator.index]} ${months[searchFilter.filterData.datesMonthBetween[iterator.index]]} ${searchFilter.filterData.datesDayBetween[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Born/Died on'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<input type="hidden" value="${searchFilter.filterData.datesTypes[iterator.index]}|${searchFilter.filterData.datesYear[iterator.index]}|${months[searchFilter.filterData.datesMonth[iterator.index]]}|${searchFilter.filterData.datesDay[iterator.index]}|${searchFilter.filterData.datesYearBetween[iterator.index]}|${months[searchFilter.filterData.datesMonthBetween[iterator.index]]}|${searchFilter.filterData.datesDayBetween[iterator.index]}" name="date">					
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.roleCategories) && ((not empty searchFilter.filterData.names) || (not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.datesTypes))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="roleCategorySearchDiv">
			<c:forEach items="${searchFilter.filterData.roleCategories}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Role Category: </span><span class="wordSearch">${searchFilter.filterData.roleCategories[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.roleCategories[iterator.index])}" name="role Category"/>
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.titlesOcc) && ((not empty searchFilter.filterData.names) || (not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.datesTypes) || (not empty searchFilter.filterData.roleCategories)) }"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="occupationSearchDiv">
			<c:forEach items="${searchFilter.filterData.titlesOcc}" varStatus="iterator">	
				<div class="searchFilterDiv">
					<span class="categorySearch">Occupation: </span><span class="wordSearch">${searchFilter.filterData.titlesOcc[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.titlesOccId[iterator.index]}|${fn2:encode(searchFilter.filterData.titlesOcc[iterator.index])}" name="occupation"/>
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.place) && ((not empty searchFilter.filterData.names) || (not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.datesTypes) || (not empty searchFilter.filterData.roleCategories) || (not empty searchFilter.filterData.titlesOcc)) }"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="placeSearchDiv">
			<c:forEach items="${searchFilter.filterData.place}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Place: </span><span class="wordSearch">${searchFilter.filterData.place[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.placeId[iterator.index]}|${fn2:encode(searchFilter.filterData.place[iterator.index])}" name="place"/>
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
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
					window.close();
				} else {
					//otherwise it's in a new search so we add a new tab.
					window.opener.$j("#tabs").tabs("add", formSubmitURL, "Person Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
					window.close();
				}
				return false;
			});
		});
	</script>
