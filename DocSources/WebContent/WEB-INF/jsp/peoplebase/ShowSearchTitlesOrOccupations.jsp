<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
            <div id="browseContent">
                <div class="listForm">
					<form:form id="searchTitles" method="post" class="edit">
                        <div class="row">
                            <div class="col_l">
                                <label for="containing" id="containing">Containing</label>
                            </div>
                            <div class="col_l"><input id="textSearch" name="textSearch" class="input_23c" type="text" value=""/></div>
                            <div class="col_r"><input type="submit" id="search" class="searchSubmit" value="Search" title="Search"></div>
                        </div>
                        <!-- se usi uno si disattiva l'altro -->
                        <div class="row">
                            <div class="col_l">
                                <label for="roleCategories" id="roleCategoriesLabel">Role Categories</label>
                            </div>
                            <div class="col_l">
								<form:select id="ordByMajor" cssClass="selectform_XLong" path="roleCatId">
									<form:option value="" label="-Please Select" />
									<c:forEach var="roleCatMinor" items="${roleCat}">
										<form:option value="${roleCatMinor.roleCatId}">${roleCatMinor.roleCatMajor} / ${roleCatMinor.roleCatMinor}</form:option>
									</c:forEach>
                                </form:select>
                            </div>
                            <div class="col_r"><input type="submit" id="search" class="searchSubmit" value="Search" title="Search"></div>
                        </div>
                        
                    </form:form>		
                </div>
            </div>
		<script type="text/javascript" charset="utf-8">
			$j(document).ready(function() {
				$j("#searchTitles").submit(function(){
					// this is search url form 
					var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
					
					var tabName = "Titles/Occ";
					var numTab = 0;
					
					//Check if already exist a tab with this person
					var tabExist = false;
					window.opener.$j("#tabs ul li a").each(function(){
						if(!tabExist){
							if(this.text != ""){
								numTab++;
							}
						}
						if(this.text == tabName){
							tabExist = true;
						}
					});
					
					if(!tabExist){
						window.opener.$j("#tabs").tabs( "add" , formSubmitURL, tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
						window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
						//window.close();
					} else{
						window.opener.$j("#tabs").tabs("select", numTab);
						window.close();
					}

					return false;
				});
			});
		</script>
	</security:authorize>
