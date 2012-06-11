<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<c:url var="UploadPortraitPersonURL" value="/de/peoplebase/UploadPortraitPerson.do" >
		<c:param name="personId" value="${person.personId }" />
	</c:url>
	
	<form id="EditPortraitPersonForm" action="${UploadPortraitPersonURL}" method="post" class="edit" enctype="multipart/form-data">
	`	   <div class="listForm">
              <div class="row">
                          <div class="col_l"><label for="browse" id="browseLabel">Browse</label></div>
                              <div class="col_l"><input id="browse" name="browse" class="input_28c" type="file" value="" size="30"/></div>
                      </div>
                      <div class="row">
                          <div class="col_l"><label for="link" id="linkLabel">Link</label></div>
                             <div class="col_l"><input id="link" name="link" class="input_40c" type="text" value="http://" /></div>
                     </div>
              </div>    
              <div>
                     <input id="save" type="submit" value="Save" />
             </div>
	       
	   </form>
