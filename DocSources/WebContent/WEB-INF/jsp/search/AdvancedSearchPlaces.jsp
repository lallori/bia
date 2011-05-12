<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<b>ADVANCED SEARCH</b><a href="#" id="helpLink">?</a>
<p><u>Search Places</u></p>
<br />
<div>
	<form id="placeNameSearchForm" method="post" class="edit">
		<label for="placeNameSearch" id="placeNameSearchLabel">Place Name: <a href="#" class="helpLink">?</a></label>
		<input id="placeNameSearch" name="placeNameSearch" class="input_20c" type="text" value=""/>
		<label for="stressSense" id="stressSenseLabel">Stress sense</label>
		<input type="checkbox" name="stressSense" class="checkbox4"/>
		<a href="#" id="addPlaceNameSearch">Add</a>
	</form>
</div>

<div>
	<form id="placeTypeSearchForm" method="post" class="edit">
		<label for="placeTypeSearch" id="placeTypeSearchLabel">Place Type: <a href="helpLink">?</a></label>
		<select id="placeTypeSearch" name="placeTypeSearch" class="selectform_Mlong">
			<option value="Select a Place Type" selected="selected">Select a Place Type</option>
		</select>
		<a href="#" id="addPlaceTypeSearch">Add</a>
	</form>
</div>

<div>
	<form id="linkedToTopicsSearchForm" method="post" class="edit">
		<label for="linkedToTopicSearch" id="linkedToTopicSearchLabel">Linked to Topics: <a href="#" id="helpLink">?</a></label>
		<select id="linkedToTopicSearch" name="linkedToTopicSearch" class="selectform_Mlong">
			<option value="Select a Topic" selected="selected">Select a Topic</option>
		</select>
		<a href="#" id="addLinkedToTopicSearch">Add</a>
	</form>
</div>

<div>
	<form id="linkedToPeopleSearchForm" method="post" class="edit">
		<label for="linkedToPeopleSearch" id="linkedToPeopleSearchLabel">Linked to People: <a href="#" id="helpLink">?</a></label>
		<select id="linkedToPeopleSearch" name="linkedToPeopleSearch" class="selectform_Mlong">
			<option value="Select a Topic" selected="selected">Select a Topic</option>
			<option value="Sender Location">Sender Location</option>
			<option value="Recipient Location">Recipient Location</option>
			<option value="Birt Place">Birt Place</option>
			<option value="Death Place">Death Place</option>
		</select>
		<a href="#" id="addLinkedToPeopleSearch">Add</a>
	</form>
</div>
