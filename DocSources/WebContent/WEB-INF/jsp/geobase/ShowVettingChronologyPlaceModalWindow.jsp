<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="VettingChronologyDiv">
	
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="result">
			<thead>
				<tr>
					<th class="sortingWhen">WHEN <a href="#" class="helpLink">?</a></th>
					<th class="sortingWho">WHO <a href="#" class="helpLink">?</a></th>
					<th class="sortingAction">ACTION <a href="#" class="helpLink">?</a></th>
					<th class="sortingWhat">WHAT <a href="#" class="helpLink">?</a></th>
				</tr>
			</thead>
			<tbody>
				<tr class="odd">
					<td>
						<a class="whenResult" href="#">01/01/2011</a>
					</td>
					<td>
						<a class="whoResult" href="#">Lisa Kaborycha</a>
					</td>
					<td>
						<a class="actionResult" href="#">Created Record</a>
					</td>
					<td>
						<a class="whatResult" href="#"></a>
					</td>
				</tr>
				<tr>
					<td>
						<a class="whenResult" href="#">01/07/2011</a>
					</td>
					<td>
						<a class="whoResult" href="#">Alessio Assonitis</a>
					</td>
					<td>
						<a class="actionResult" href="#">Edit Record</a>
					</td>
					<td>
						<a class="whatResult" href="#">Changed Sender</a>
					</td>
				</tr>
				<tr class="odd">
					<td>
						<a class="whenResult" href="#">01/13/2011</a>
					</td>
					<td>
						<a class="whoResult" href="#">Roberta Piccinelli</a>
					</td>
					<td>
						<a class="actionResult" href="#">Edit Record</a>
					</td>
					<td>
						<a class="whatResult" href="#">Added new Topic</a>
					</td>
				</tr>
				<tr>
					<td>
						<a class="whenResult" href="#">01/14/2011</a>
					</td>
					<td>
						<a class="whoResult" href="#">Maurizio Arfaioli</a>
					</td>
					<td>
						<a class="actionResult" href="#">Edit Record</a>
					</td>
					<td>
						<a class="whatResult" href="#">Edit Extract</a>
					</td>
				</tr>
				<tr class="odd">
					<td>
						<a class="whenResult" href="#">01/20/2011</a>
					</td>
					<td>
						<a class="whoResult" href="#">Julia Vicioso</a>
					</td>
					<td>
						<a class="actionResult" href="#">Edit Record</a>
					</td>
					<td>
						<a class="whatResult" href="#">Added new Correspondents/People</a>
					</td>
				</tr>
				<tr>
					<td>
						<a class="whenResult" href="#">01/23/2011</a>
					</td>
					<td>
						<a class="whoResult" href="#">Maurizio Arfaioli</a>
					</td>
					<td>
						<a class="actionResult" href="#">Edit Record</a>
					</td>
					<td>
						<a class="whatResult" href="#">Edit Extract</a>
					</td>
				</tr>
				<tr class="odd">
					<td>
						<a class="whenResult" href="#">01/25/2011</a>
					</td>
					<td>
						<a class="whoResult" href="#">Alessio Assonitis</a>
					</td>
					<td>
						<a class="actionResult" href="#">Edit Record</a>
					</td>
					<td>
						<a class="whatResult" href="#">Changed Sender</a>
					</td>
				</tr>
			</tbody>
		</table>
		
		<input id="close" type="submit" title="Close Saved Search Filters window" value=""/>
		<a id="goBack" title="Go Back to Advanced Search"></a>
	
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(
				function(){
					Modalbox.hide();return false;}
					);
			$j("#goBack").click(
				function(){
						Modalbox.show("/DocSources/src/advancedSearchModal.html", {title: "ENTRY MENU", width: 750, height: 380});return false;}
						);
		});
	</script>