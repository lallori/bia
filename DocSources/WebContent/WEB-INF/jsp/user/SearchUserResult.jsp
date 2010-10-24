<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="searchUserPaginationUrl" value="/user/ajax/pagination.json"/>
<c:url var="searchUserPaginationUrlExample" value="/examples/user/ajax/pagination.txt"/>

		<script type="text/javascript" language="javascript" src='<c:url value="/scripts/jquery-1.4.2.js"/>'></script>
		<script type="text/javascript" language="javascript" src="<c:url value="/scripts/jquery.dataTables.js"/>"></script>
		<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$('#result').dataTable( {
					"bProcessing": true,
					"bServerSide": true,
					"iDisplayLength": 10,
					"iDisplayStart": 0,
					"oSearch": {"sSearch": "${command.alias}"},
					"sAjaxSource": "${searchUserPaginationUrl}"
				} );
			} );
		</script>

<table cellpadding="0" cellspacing="0" border="0" class="display"  id="result">
	<thead>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Account</th>
			<th>Email</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="5" class="dataTables_empty">Loading data from server</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Account</th>
			<th>Email</th>
		</tr>
	</tfoot>
</table>