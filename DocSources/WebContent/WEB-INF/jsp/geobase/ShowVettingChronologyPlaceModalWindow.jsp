<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="vettingHistoryTableDiv">
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="vettingHistoryTable">
        <thead>
            <tr>
                <th>Date</th>
                <th>Action</th>
                <th>Who</th>
            </tr>
        </thead>
        <tbody>
            <tr>                                                                                              
                <td colspan="3" class="dataTables_empty">Loading data from server</td>                        
            </tr> 
        </tbody>
    </table>
</div>

<div id="vettingHistoryButtons">
	<a id="close" href="#" title="Close Vetting History window">Close</a>
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