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
	
	<form id="yourEasySearchFilterForm" method="post" action="${AdvancedSearchURL}">
		<p><u>Custom Search Filter</u></p>
		<br />
		<div id="placeNameSearchDiv">
		<c:forEach items="${searchFilter.filterData.placesName}" varStatus="iterator">
			<div class="searchFilterDiv">
				<span class="categorySearch">Place Name: </span><span class="wordSearch">${searchFilter.filterData.placesName[iterator.index]}</span><a class="remove" href="#">(remove)</a>
				<input type="hidden" value="${fn2:encode(searchFilter.filterData.placesName[iterator.index])}" name="placeName" />
			</div>
			<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
		</c:forEach>
		</div>
		<c:if test="${(not empty searchFilter.filterData.placeType) && ((not empty searchFilter.filterData.placesName)) }"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
		<div id="placeTypeSearchDiv">
		<c:forEach items="${searchFilter.filterData.placeType}" varStatus="iterator">
			<div class="searchFilterDiv">
				<span class="categorySearch">Place Type: </span><span class="wordSearch">${searchFilter.filterData.placeType[iterator.index]}</span><a class="remove" href="#">(remove)</a>
				<input type="hidden" value="${searchFilter.filterData.placeType[iterator.index]}" name="placeType" />
			</div>
			<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
		</c:forEach>
		</div>
		<c:if test="${(not empty searchFilter.filterData.linkedToPeople) && ((not empty searchFilter.filterData.placeType) || (not empty searchFilter.filterData.placesName))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
		<div id="linkedToPeopleSearchDiv">
		<c:forEach items="${searchFilter.filterData.linkedToPeople}" varStatus="iterator">
			<div class="searchFilterDiv">
				<span class="categorySearch">Linked To People: </span><span class="wordSearch">${searchFilter.filterData.linkedToPeople[iterator.index]}</span><a class="remove" href="#">(remove)</a>
				<input type="hidden" value="${searchFilter.filterData.linkedToPeople[iterator.index]}" name="linkedToPeople" />
			</div>
			<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
		</c:forEach>
		</div>
		<c:if test="${(searchFilter.filterData.logicalDelete != null && searchFilter.filterData.logicalDelete.toString() == 'true') && ((not empty searchFilter.filterData.placeType) || (not empty searchFilter.filterData.placesName) || (not empty searchFilter.filterData.linkedToPeople))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
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
		
		<br />
		<br />
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
					window.opener.$j('#tabs ul li').eq(index).find('a').append("<span>Place Search</span>");
					window.close();
				} else {
					//otherwise it's in a new search so we add a new tab.
					window.opener.$j("#tabs").tabs("add", formSubmitURL, "Place Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
					window.close();
				}
				return false;
			});
		});
	</script>