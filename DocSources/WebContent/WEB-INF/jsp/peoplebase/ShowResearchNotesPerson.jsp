<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditResearchNotesPersonDiv">
	<h5>RESEARCH NOTES <a id="EditResearchNotesPerson" href="/DocSources/de/peoplebase/EditResearchNotesPerson.html">edit</a></h5>
	<hr id="lineSeparator"/>

	<ul>
		<li>${person.bioNotes}</li>
	</ul>
</div>
