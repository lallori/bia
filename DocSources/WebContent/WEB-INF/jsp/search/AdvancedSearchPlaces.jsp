<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="multiOpenAccordion">
	<h1><a>Place Name</a></h1>
	<div>
		<form id="placeNameSearchForm" method="post" class="edit">
			<a class="helpIcon" title="You can search here for place names in many languages (without accents).">?</a>
			<input id="placeName" name="placeName" class="input_20c" type="text" value=""/>
			<label for="stressSense" id="stressSenseLabel">Stress sense</label>
			<input type="checkbox" name="stressSense" class="checkbox4"/>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Place Name">
		</form>
	</div>
	
	<h1><a>Place Type</a></h1>
	<div>
		<form id="placeTypeSearchForm" method="post" class="edit">
			<a class="helpIcon" title="These Place Types are assigned to geographical place names in the Getty Thesaurus of Geographic Names. Most places in the Documentary Sources database are of the type 'Inhabited Place' (that is to say, cities, towns and villages.)">?</a>
			<select id="placeType" name="placeType" class="selectform_Mlong">
				<option value="Select a Place Type" selected="selected">Select a Place Type</option>
			</select>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Place Type">
		</form>
	</div>
	
	<h1><a>Linked to Topics</a></h1>
	<div>
		<form id="linkedToTopicsSearchForm" method="post" class="edit">
			<a class="helpIcon" title="A set of 42 Topic Categories related to the arts and humanities defines the scope of this database. Most documents are indexed to one or more Topic Categories, and each Topic is indexed to the related Places. By selecting a Topic, you will create a list here of the places that are associated with that topic in the indexed documents. For example, selecting the Topic 'Music and Musical Instruments' will produce a list of places that are linked to documents mentioning Music and Musical Instruments. This allows you to select those relevant to your research.">?</a>
			<select id="linkedToTopic" name="linkedToTopic" class="selectform_Mlong">
				<option value="Select a Topic" selected="selected">Select a Topic</option>
			</select>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Linked to Topics">
		</form>
	</div>

	<h1><a>Linked to People</a></h1>
	<div>
		<form id="linkedToPeopleSearchForm" method="post" class="edit">
			<a class="helpIcon" title="You can refine your Places search by specifying 'Sender Location', 'Recipient Location', 'Birth Place', or 'Death Place'. The resulting hit list will include a link to the biographical records for the people who meet your criteria.">?</a>
			<select id="linkedToPeopleType" name="linkedToPeopleType" class="selectform_Mlong">
				<option value="Select a Topic" selected="selected">Select a Topic</option>
				<option value="Sender Location">Sender Location</option>
				<option value="Recipient Location">Recipient Location</option>
				<option value="Birt Place">Birt Place</option>
				<option value="Death Place">Death Place</option>
			</select>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Linked to People">
		</form>
	</div>
</div>

<script type="text/javascript">
	$j(document).ready(function() {								
		$j('#placeNameSearchForm').advancedSearchForm();
		$j('#placeTypeSearchForm').advancedSearchForm();
		$j('#linkedToTopicsSearchForm').advancedSearchForm();
		$j('#linkedToPeopleSearchForm').advancedSearchForm(); 

		$j('#multiOpenAccordion').multiAccordion({active: [0]});
		return false;	
	});
</script>
