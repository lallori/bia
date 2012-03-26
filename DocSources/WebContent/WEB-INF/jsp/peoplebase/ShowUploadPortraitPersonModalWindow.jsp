<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form id="EditPortraitPersonForm" action="/DocSources/de/peoplebase/EditDetailsPerson.do?summaryId=0&amp;volNum=0&amp;volLeText=" method="post" class="edit">
	       <div>
	       	<label for="browseLabel" id="browseLabel">Browse</label>	
	           <input id="browse" name="browse" type="file" value="Browse"/>
	       </div>
	       <div>
	           <label for="link" id="linkLabel">Link</label>
	           <input id="link" name="link" class="input_43c" type="text" value="http://" />
	       </div>
	       <div>
	           <input id="save" type="submit" value="Save" />
	       </div>
	   </form>
