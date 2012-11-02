<%@ tag import="org.medici.bia.common.util.GrantedAuthorityUtils" %>

<%@ attribute name="id" required="true" type="java.lang.String"%>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="cssClass" required="false" type="java.lang.String"%>
<%@ attribute name="value" required="true" type="org.medici.bia.domain.UserAuthority.Authority"%>
<%
	StringBuilder stringBuilder = new StringBuilder("<input type=\"checkbox\" ");

	if (id != null) {
		stringBuilder.append(" id=\"");
		stringBuilder.append(id);
		stringBuilder.append("\"");
	}

	if (name != null) {
		stringBuilder.append(" name=\"");
		stringBuilder.append(name);
		stringBuilder.append("\"");
	}
	if (cssClass != null) {
		stringBuilder.append(" class=\"");
		stringBuilder.append(cssClass);
		stringBuilder.append("\"");
	}

	if (value != null) {
		stringBuilder.append(" value=\"");
		stringBuilder.append(value.toString());
		stringBuilder.append("\"");
	}

	if (GrantedAuthorityUtils.isGranted(value)) {
		stringBuilder.append("checked");
	}

	stringBuilder.append(">");
%>

<%= stringBuilder.toString() %>