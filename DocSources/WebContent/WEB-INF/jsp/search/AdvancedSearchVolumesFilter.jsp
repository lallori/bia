<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchURL" value="/src/AdvancedSearch.do"/>
	<c:url var="SaveUserSearchFilterURL" value="/src/SaveUserSearchFilter.do"/>
	<c:url var="AdvancedSearchCountURL" value="/src/AdvancedSearchCount.json" />

	<div id="yourSearchFilterDiv">
		<h1 class="advSearchTitle">Your search filter</h1>
		
		<form id="yourEasySearchFilterForm" action="${AdvancedSearchURL}" method="post">
			<p><u>Custom Search Filter</u></p>
			<br />
			<div id="volumeSearchDiv">
			<c:forEach items="${searchFilter.filterData.volumes}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Volume in <fmt:message key="search.documents.volumeType.${searchFilter.filterData.volumesTypes[iterator.index]}" />: </span>
					<c:if test="${searchFilter.filterData.volumesTypes[iterator.index] == 'Exactly'}"><span class="wordSearch">${searchFilter.filterData.volumes[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.volumesTypes[iterator.index] == 'Between'}"><span class="wordSearch">${searchFilter.filterData.volumes[iterator.index]}, ${searchFilter.filterData.volumesBetween[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<input type="hidden" value="${searchFilter.filterData.volumesTypes[iterator.index]}|${searchFilter.filterData.volumes[iterator.index]}|${searchFilter.filterData.volumesBetween[iterator.index]}" name="volume">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.datesTypes) &&((not empty searchFilter.filterData.volumes))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="dateSearchDiv">
			<c:forEach items="${searchFilter.filterData.datesTypes}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch"><fmt:message key="search.documents.dateType.${searchFilter.filterData.datesTypes[iterator.index]}" />: </span>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'From'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Before'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Between'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}, ${searchFilter.filterData.datesYearBetween[iterator.index]} ${months[searchFilter.filterData.datesMonthBetween[iterator.index]]} ${searchFilter.filterData.datesDayBetween[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<input type="hidden" value="${searchFilter.filterData.datesTypes[iterator.index]}|${searchFilter.filterData.datesYear[iterator.index]}|${months[searchFilter.filterData.datesMonth[iterator.index]]}|${searchFilter.filterData.datesDay[iterator.index]}|${searchFilter.filterData.datesYearBetween[iterator.index]}|${months[searchFilter.filterData.datesMonthBetween[iterator.index]]}|${searchFilter.filterData.datesDayBetween[iterator.index]}" name="date">					
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(searchFilter.filterData.digitized != null) &&((not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.datesTypes))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="digitizedSearchDiv">
			<c:if test="${(searchFilter.filterData.digitized != null)}">
				<div class="searchFilterDiv">
					<span class="categorySearch">Digitized: </span><span class="wordSearch"><c:if test="${searchFilter.filterData.digitized == true}">Yes</c:if><c:if test="${searchFilter.filterData.digitized == false}">No</c:if></span><a class="remove" href="#">(remove)</a>
					<c:if test="${searchFilter.filterData.digitized.toString() ==  'true'}">
						<input type="hidden" value="Yes" name="digitized">
					</c:if>
					<c:if test="${searchFilter.filterData.digitized.toString() == 'false' }">
						<input type="hidden" value="No" name="digitized">
					</c:if>
				</div>
			</c:if>
			</div>
			<c:if test="${(not empty searchFilter.filterData.languages) && ((not empty searchFilter.filterData.volumes))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="languagesSearchDiv">
			<c:forEach items="${searchFilter.filterData.languages}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Languages: </span><span class="wordSearch">${searchFilter.filterData.languages[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.languages[iterator.index]}" name="languages">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.otherLang) && ((not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.languages))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="otherLangSearchDiv">
			<c:forEach items="${searchFilter.filterData.otherLang}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Other Language: </span><span class="wordSearch">${searchFilter.filterData.otherLang[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.otherLang[iterator.index]}" name="otherLang">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(searchFilter.filterData.cipher.length() != 0) && ((not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.languages) || (not empty searchFilter.filterData.otherLang))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="cipherSearchDiv">
			<c:if test="${searchFilter.filterData.cipher.length() != 0}">
				<div class="searchFilterDiv">
					<span class="categorySearch">Cipher: </span><span class="wordSearch">${searchFilter.filterData.cipher}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.cipher)}" name="cipher">
				</div>
			</c:if>
			</div>
			<c:if test="${(searchFilter.filterData.index.length() != 0) && ((searchFilter.filterData.cipher.length() != 0) || (not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.languages) || (not empty searchFilter.filterData.otherLang))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="indexSearchDiv">
			<c:if test="${searchFilter.filterData.index.length() != 0}">
				<div class="searchFilterDiv">
					<span class="categorySearch">Index: </span><span class="wordSearch">${searchFilter.filterData.index}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.index)}" name="index">
				</div>
			</c:if>
			</div>
			<c:if test="${(not empty searchFilter.filterData.fromVolume) && ((searchFilter.filterData.index.length() != 0) || (searchFilter.filterData.cipher.length() != 0) || (not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.languages) || (not empty searchFilter.filterData.otherLang))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="fromVolumeSearchDiv">
			<c:forEach items="${searchFilter.filterData.fromVolume}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">From: </span><span class="wordSearch">${searchFilter.filterData.fromVolume[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.fromVolume[iterator.index])}" name="fromVolume">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.toVolume) && ((not empty searchFilter.filterData.fromVolume) || (searchFilter.filterData.index.length() != 0) || (searchFilter.filterData.cipher.length() != 0) || (not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.languages) || (not empty searchFilter.filterData.otherLang))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="toVolumeSearchDiv">
			<c:forEach items="${searchFilter.filterData.toVolume}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">To: </span><span class="wordSearch">${searchFilter.filterData.toVolume[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.toVolume[iterator.index])}" name="toVolume">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.context) && ((not empty searchFilter.filterData.toVolume) || (not empty searchFilter.filterData.fromVolume) || (searchFilter.filterData.index.length() != 0) || (searchFilter.filterData.cipher.length() != 0) || (not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.languages) || (not empty searchFilter.filterData.otherLang))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="contextSearchDiv">
			<c:forEach items="${searchFilter.filterData.context}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Context: </span><span class="wordSearch">${searchFilter.filterData.context[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.context[iterator.index])}" name="context">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.inventario) && ((not empty searchFilter.filterData.context) || (not empty searchFilter.filterData.toVolume) || (not empty searchFilter.filterData.fromVolume) || (searchFilter.filterData.index.length() != 0) || (searchFilter.filterData.cipher.length() != 0) || (not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.languages) || (not empty searchFilter.filterData.otherLang)) }"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="inventarioSearchDiv">
			<c:forEach items="${searchFilter.filterData.inventario}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Inventario: </span><span class="wordSearch">${searchFilter.filterData.inventario[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.inventario[iterator.index])}" name="inventario">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(searchFilter.filterData.logicalDelete != null && searchFilter.filterData.logicalDelete.toString() == 'true') && ((not empty searchFilter.filterData.context) || (not empty searchFilter.filterData.toVolume) || (not empty searchFilter.filterData.fromVolume) || (searchFilter.filterData.index.length() != 0) || (searchFilter.filterData.cipher.length() != 0) || (not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.languages) || (not empty searchFilter.filterData.otherLang) || (not empty searchFilter.filterData.inventario))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="logicalDeleteSearchDiv">
			<c:if test="${(searchFilter.filterData.logicalDelete != null)}">
				<div class="searchFilterDiv">
					<span class="categorySearch">Logical Delete:</span><span class="wordSearch">Yes</span><a class="remove" href="#">(remove)</a>
					<c:if test="${searchFilter.filterData.logicalDelete.toString() ==  'true'}">
						<input type="hidden" value="true" name="logicalDelete">
					</c:if>
					<c:if test="${searchFilter.filterData.logicalDelete.toString() == 'false' }">
						<input type="hidden" value="false" name="logicalDelete">
					</c:if>
				</div>
			</c:if>
			</div>
			<p class="yourSearchDiv">Records found:
			<span class="recordsNum"></span></p>
			
			<br>
			<br>
			<a class="saveButton" href="#">Save</a>
			<input type="submit" title="Search" value="Search" id="advsearch">

			<input type="hidden" name="idSearchFilter" value="${command.idSearchFilter}">
			<input type="hidden" name="searchUUID" value="${command.searchUUID}">
			<input type="hidden" name="searchType" value="${command.searchType}">
		</form>
	</div>
	<script type="text/javascript">
		$j(document).ready(function() {
			//MD: Inserted the partial count on windows opening, because if we refine a search we can view the count
			$j.ajax({ type:"POST", url:'${AdvancedSearchCountURL}', data:$j("#yourEasySearchFilterForm").serialize(), async:false, success:function(json) {
 				// At this point we have count of total result. Review output page and put the total...
 				console.log("Advanced search result " + json.totalResult);
 				if(json.totalResult != undefined)
 					$j(".recordsNum").text(json.totalResult);
 				else
 					$j(".recordsNum").text("0");
 				return false;
			}});
			
			$j(".remove").live('click', function(){
				$j.ajax({ type:"POST", url:'${AdvancedSearchCountURL}', data:$j("#yourEasySearchFilterForm").serialize(), async:false, success:function(json) {
	 				// At this point we have count of total result. Review output page and put the total...
	 				console.log("Advanced search result " + json.totalResult);
	 				if(json.totalResult != undefined)
	 					$j(".recordsNum").text(json.totalResult);
	 				else
	 					$j(".recordsNum").text("0");
				}});
				
				return false;
			});
			
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

			$j('.saveButton').click(function(){
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
					window.opener.$j('#tabs ul li').eq(index).data('loaded', false).find('a').attr('href', formSubmitURL);
					window.opener.$j("#tabs").tabs("option", "active", index);
					window.opener.$j("#tabs").tabs("load" , index);
					//MD: Fix problem with title of tab (if it already exist, it still display "Loading...")
					window.opener.$j('#tabs ul li').eq(index).find('a').empty();
					window.opener.$j('#tabs ul li').eq(index).find('a').append("<span>Volume Search</span>");
					window.close();
				} else {
					//otherwise it's in a new search so we add a new tab.
					window.opener.$j("#tabs").tabs("add", formSubmitURL, "Volume Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
					window.close();
				}
				return false;
			});
		});
	</script>
