<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchCountURL" value="/src/AdvancedSearchCount.json">
	</c:url>
	
	<c:url var="searchPlaceURL" value="/src/SearchPlace.json"/>

<body class="search">
	<div id="advancedSearch">
		<div id="advancedSearch_top"><fmt:message key="search.advancedSearchPlaces.title"/></div>
		<div id="body_left">
			<div id="customSearchFilterDiv">
				<h1 class="advSearchTitle"><fmt:message key="search.advancedSearchPlaces.createCustomSearchFilter"/></h1>			
				<div id="accordion">
					<h1 id="placeNameH1"><a><fmt:message key="search.advancedSearchPlaces.placeName.title"/></a></h1>
					<div>
						<div class="listAdvSearch">
							<div class="row">
								<div class="col_l"><fmt:message key="search.advancedSearchPlaces.placeName.wordSearch"/></div>
							</div>
							<form id="placeNameSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPlaces.help.placename"></fmt:message>">?</a>
										<input id="placeName" name="placeName" class="input_20c" type="text" value=""/>
							<!-- 		<label for="stressSense" id="placeNameType">Stress sense</label> -->
							<!-- 		<input type="checkbox" name="stressSense" class="checkbox4"/> -->
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchPlaces.addToYourSearchFilter.alt"/>">
										<input type="hidden" id="category" value="Place Name">
					               	</div>
					            </div>
				            </form>
				            <br />
				            <div class="row">
				            	<div class="col_l"><fmt:message key="search.advancedSearchPlaces.placeName.matchTheExactName"/></div>
				            </div>
				            <form id="placeSearchForm" method="post" class="edit">
			            	<div class="row">
				        		<div class="col_l">
				        			<a class="helpIcon" title="<fmt:message key="search.advancedSearchPlaces.help.place"></fmt:message>">?</a>
									<input id="place" name="place" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
				        		</div>
				        		<div class="col_r">
				        			<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="placeAdd button_small" disabled="disabled">
									<input type="hidden" id="category" value="place">
									<input type="hidden" id="placeId" value="">
				        		</div>
				        	</div>
			            </form>
				        </div>
				    </div>
					
					<h1 id="placeTypeH1"><a><fmt:message key="search.advancedSearchPlaces.placeName.placeType"/></a></h1>
					<div>
						<div class="listAdvSearch">
							<form id="placeTypeSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPlaces.help.placetype.selectaplacetype"></fmt:message>">?</a>
										<select id="placeTypeSelect" name="placeTypeSelect" class="selectform_XXXlong">
											<option value="Select a Place Type" selected="selected"><fmt:message key="search.advancedSearchPlaces.placeName.selectPlaceType"/></option>
											<c:forEach items="${placeTypes}" var="placeType">
												<option value="${placeType}">${placeType}</option>
											</c:forEach>
										</select>
					               	</div>
					               	<div class="col_r">
					               		<input type="hidden" id="placeType" name="placeType" type="text" value=""/>
										<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this to your search filter">
										<input type="hidden" id="category" value="Place Type">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
				
					<h1 id="linkedPeopleH1"><a><fmt:message key="search.advancedSearchPlaces.linkedToPeople.title"/></a></h1>
					<div>
						<div class="listAdvSearch">
							<form id="linkedToPeopleSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPlaces.help.linkedtopeople.selectalinktype.senderlocation"></fmt:message>">?</a>
										<select id="linkedToPeopleSelect" name="linkedToPeopleSelect" class="selectform_MXlong">
											<option value="Select a Link Type" selected="selected"><fmt:message key="search.advancedSearchPlaces.linkedToPeople.selectLinkType"/></option>
											<option value="Sender Location"><fmt:message key="search.advancedSearchPlaces.linkedToPeople.senderLocation"/></option>
											<option value="Recipient Location"><fmt:message key="search.advancedSearchPlaces.linkedToPeople.recipientLocation"/></option>
											<option value="Birth Place"><fmt:message key="search.advancedSearchPlaces.linkedToPeople.birthPlace"/></option>
											<option value="Death Place"><fmt:message key="search.advancedSearchPlaces.linkedToPeople.deathPlace"/></option>
										</select>
					               	</div>
					               	<div class="col_r">
					               		<input type="hidden" id="linkedToPeople" name="linkedToPeople" type="text" value=""/>
										<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this to your search filter">
										<input type="hidden" id="category" value="Linked to People">
					               	</div>
					            </div>
				            </form>
				        </div> 
					</div>
					
					<h1 id="placeIdSearch"><a><fmt:message key="search.advancedSearchPlaces.placeId.title"/></a></h1>
					<div>
						<div class="listAdvSearch">
							<form id="placeIdSearchForm" method="post" class="edit">
								<div class="row">
									<div class="col_l">
										<a class="helpIcon" title="<fmt:message key="search.advancedSearchPlaces.help.placeid"></fmt:message>">?</a>
										<input id="placeId" name="placeId" class="input_7c" type="text" maxlength="5" />
									</div>
									<div class="col_r">
										<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this word search to your search filter">
										<input type="hidden" id="category" value="Place Id">
									</div>
								</div>
							</form>
						</div>
					</div>
					
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					<h1 id="logicalDeleteSearch"><a><fmt:message key="search.advancedSearchPlaces.logicalDelete.title"/></a></h1>
					<div>
						<div class="listAdvSearch">
							<form id="logicalDeleteSearchForm" method="post" class="edit">
								<div class="row">
									<div class="col_l">
										<a class="helpIcon" title="<fmt:message key="search.advancedSearchPlaces.help.logicaldelete"></fmt:message>">?</a>
										<label for="logicalDelete" id="logicalDeleteLabel"><fmt:message key="search.advancedSearchPlaces.logicalDelete.deleted"/></label>
										<input type="checkbox" name="logicalDelete" value="true"/>
									</div>
									<div class="col_r">
										<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this word search to your search filter">
										<input type="hidden" id="category" value="Logical Delete">
									</div>
								</div>
							</form>
						</div>
					</div>
					</security:authorize>
					
				</div>
			</div>

<script type="text/javascript">
	$j(document).ready(function() {								
		$j('#placeNameSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j('#placeSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j('#placeTypeSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j('#linkedToTopicsSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j('#linkedToPeopleSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j("#placeIdSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j('#logicalDeleteSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		
		$j('#accordion').accordion({
			active: false, 
			autoHeight: false,
			collapsible:true
			});		
		$j("#placeTypeSelect").change(function(){
			 if($j(this).val() != "Select a Place Type"){
			 	$j("#placeType").val($j(this).val());
			 }
			 else{
				 $j("#placeType").val("");
			 }
			 return false;
		 });
		
		$j("#linkedToPeopleSelect").change(function(){
			 if($j(this).val() != "Select a Link Type")
			 	$j("#linkedToPeople").val($j(this).val());
			 else
				 $j("#linkedToPeople").val("");
			 return false;
		 });
		$j('#placeNameH1').click(function(){
			$j.scrollTo({top:'0px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "0px"}, "slow");
			return false;
		});
		$j('#placeTypeH1').click(function(){
			$j.scrollTo({top:'113px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "70px"}, "slow");
			return false;
		});
		$j('#linkedPeopleH1').click(function(){
			$j.scrollTo({top:'168px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "125px"}, "slow");
			return false;
		});
		$j('#placeIdSearch').click(function(){
			$j.scrollTo({top:'195px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "150px"}, "slow");
			return false;
		});
		
		var $placeValue = '';
		var $placeAutocomplete = $j("#place").autocompletePlace({
			serviceUrl: '${searchPlaceURL}',
		    loadingImageUrl:'${LoadingImageURL}',
			minChars: 3,
			delimiter: null,
			maxHeight: 400,
			width: 450,
			zIndex: 9999,
			deferRequestBy: 0,
			noCache: true,
			onSelect: function(value, data){
				$j(".placeAdd").die();
				$j(".placeAdd").removeAttr("disabled");
				$j('#placeId').val(data);
				$j(".placeAdd").attr("disabled");
				$j(".placeAdd").prop("disabled", false);
				$placeValue = $j("#place").val();
				$j("#place").live('keyup', function(){
					if($j("#place").val() != $placeValue){
						$j(".placeAdd").attr("disabled","disabled");
						$j("#placeId").val("");
					}
					return false;
				});
				$j("#place").live('keypress', function(e){
					if(e.keyCode == 13 && $j("#place").val() != $placeValue){
						e.stopPropagation();
						return false;
					}
				});
			}
		});	
		 
		$j("#placeSearchForm").submit(function(){
			$j("#placeId").val("");
			$j(".placeAdd").attr("disabled","disabled");
		});
	});
</script>
