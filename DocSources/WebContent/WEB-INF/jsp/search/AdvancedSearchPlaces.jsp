<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="customSearchFilterDiv">
	<h1 class="advSearchTitle">Create your custom search filter</h1>

<div id="accordion">
	<h1 id="placeNameH1"><a>Place Name</a></h1>
	<div>
		<div class="listAdvSearch">
			<form id="placeNameSearchForm" method="post" class="edit">
	           	<div class="row">
	               	<div class="col_l">
	               		<a class="helpIcon" title="You can search here for place names using either whole or partial words (without accents).">?</a>
						<input id="placeName" name="placeName" class="input_20c" type="text" value=""/>
			<!-- 		<label for="stressSense" id="placeNameType">Stress sense</label> -->
			<!-- 		<input type="checkbox" name="stressSense" class="checkbox4"/> -->
	               	</div>
	               	<div class="col_r">
	               		<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter">
						<input type="hidden" id="category" value="Place Name">
	               	</div>
	            </div>
            </form>
        </div>
    </div>
	
	<h1 id="placeTypeH1"><a>Place Type</a></h1>
	<div>
		<div class="listAdvSearch">
			<form id="placeTypeSearchForm" method="post" class="edit">
	           	<div class="row">
	               	<div class="col_l">
	               		<a class="helpIcon" title="These Place Types are assigned to geographical place names in the Getty Thesaurus of Geographic Names. Most places in the Documentary Sources database are of the type 'Inhabited Place' (that is to say, cities, towns and villages.)">?</a>
						<select id="placeTypeSelect" name="placeTypeSelect" class="selectform_XXXlong">
							<option value="Select a Place Type" selected="selected">Select a Place Type</option>
							<c:forEach items="${placeTypes}" var="placeType">
								<option value="${placeType}">${placeType}</option>
							</c:forEach>
						</select>
	               	</div>
	               	<div class="col_r">
	               		<input type="hidden" id="placeType" name="placeType" type="text" value=""/>
						<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter">
						<input type="hidden" id="category" value="Place Type">
	               	</div>
	            </div>
            </form>
        </div>
	</div>

	<h1 id="linkedPeopleH1"><a>Linked to People</a></h1>
	<div>
		<div class="listAdvSearch">
			<form id="linkedToPeopleSearchForm" method="post" class="edit">
	           	<div class="row">
	               	<div class="col_l">
	               		<a class="helpIcon" title="You can refine your Places search by specifying 'Sender Location', 'Recipient Location', 'Birth Place', or 'Death Place'. The resulting hit list will include a link to the biographical records for the people who meet your criteria.">?</a>
						<select id="linkedToPeopleSelect" name="linkedToPeopleSelect" class="selectform_MXlong">
							<option value="Select a Link Type" selected="selected">Select a Link Type</option>
							<option value="Sender Location">Sender Location</option>
							<option value="Recipient Location">Recipient Location</option>
							<option value="Birth Place">Birth Place</option>
							<option value="Death Place">Death Place</option>
						</select>
	               	</div>
	               	<div class="col_r">
	               		<input type="hidden" id="linkedToPeople" name="linkedToPeople" type="text" value=""/>
						<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter">
						<input type="hidden" id="category" value="Linked to People">
	               	</div>
	            </div>
            </form>
        </div> 
	</div>
</div>
</div>

<script type="text/javascript">
	$j(document).ready(function() {								
		$j('#placeNameSearchForm').advancedSearchForm();
		$j('#placeTypeSearchForm').advancedSearchForm();
		$j('#linkedToTopicsSearchForm').advancedSearchForm();
		$j('#linkedToPeopleSearchForm').advancedSearchForm(); 
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
	});
</script>
