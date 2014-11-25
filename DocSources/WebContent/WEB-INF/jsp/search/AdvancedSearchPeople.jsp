<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchCountURL" value="/src/AdvancedSearchCount.json">
	</c:url>

	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

<body class="search">
	<div id="advancedSearch">
		<div id="advancedSearch_top"><fmt:message key="search.advancedSearchPeople.searchForPeople.title"/></div>
		<div id="body_left">
			<div id="customSearchFilterDiv">
				<h1 class="advSearchTitle"><fmt:message key="search.advancedSearchPeople.searchForPeople.customSearchFilter.title"/></h1>
			<div id="accordion">
				<h1 id="nameParts"><a><fmt:message key="search.advancedSearchPeople.nameParts"/></a></h1>
				<div class="people">
					<div class="listAdvSearch">
						<div class="row">
							<div class="col_l"><fmt:message key="search.advancedSearchPeople.nameParts.wordSearch"/></div>
						</div>
						<form id="namePartsSearchForm" method="post" class="edit">
				           	<div class="row">
				               	<div class="col_l">
				               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.nameparts"></fmt:message>">?</a>
									<input id="nameParts" name="nameParts" class="input_20c" type="text" value=""/>
				               	</div>
				               	<div class="col_l"><fmt:message key="search.advancedSearchPeople.nameParts.in"/></div>
				               	<div class="col_l">
				               		<select id="namePartsType" name="namePartsType" class="selectform_SXlong">
										<option value="All Name Types" selected="selected"><fmt:message key="search.advancedSearchPeople.nameParts.allNameTypes"/></option>
										<option value="Appellative"><fmt:message key="search.advancedSearchPeople.nameParts.appellative"/></option>
										<option value="Family"><fmt:message key="search.advancedSearchPeople.nameParts.family"/></option>
										<option value="Given"><fmt:message key="search.advancedSearchPeople.nameParts.given"/></option>
										<option value="Maiden"><fmt:message key="search.advancedSearchPeople.nameParts.maiden"/></option>
										<option value="Married"><fmt:message key="search.advancedSearchPeople.nameParts.married"/></option>
										<option value="Patronymic"><fmt:message key="search.advancedSearchPeople.nameParts.patronymic"/></option> 
									</select>
				               	</div>
				               	<div class="col_r">
				               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>">
									<input type="hidden" id="category" value="Name Parts">
				               	</div>
				            </div>
			            </form>	
			            <br />
			            <div class="row">
			            	<div class="col_l"><fmt:message key="search.advancedSearchPeople.matchTheExactName"/></div>
			            </div>
			            <form id="personSearchForm" method="post" class="edit">
			            	<div class="row">
				        		<div class="col_l">
				        			<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.person"></fmt:message>">?</a>
									<input id="person" name="person" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
				        		</div>
				        		<div class="col_l"></div>
				        		<div class="col_l"></div>
				        		<div class="col_r">
				        			<input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>" class="personAdd button_small" disabled="disabled">
									<input type="hidden" id="category" value="person">
									<input type="hidden" id="personId" value="">
				        		</div>
				        	</div>
			            </form>
			        </div>
				</div>
				
				<h1 id="titleOccupation"><a><fmt:message key="search.advancedSearchPeople.titleOccupation.title"/></a></h1>
				<div class="people">
			    	<div class="listAdvSearch">
			    		<form id="roleCategorySearchForm" method="post" class="edit">
				           	<div class="row">
				               	<div class="col_l">
				               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.titleoccupation.selectarolecategory"></fmt:message>">?</a>
									<select id="roleCategorySelect" name="roleCategorySelect" class="selectform_XXXlong">
						                    <option value="Select a Role Category" selected="selected"><fmt:message key="search.advancedSearchPeople.selectRoleCategory"/></option>
						                        <optgroup label="ARTISTS and ARTISANS">
						                        	<option value="ARTISTS and ARTISANS"><fmt:message key="search.advancedSearchPeople.allAr"/></option>
						                        	<option value="Actors/Dancers"><fmt:message key="search.advancedSearchPeople.actD"/></option>
						                        	<option value="Architects/Engineers"><fmt:message key="search.advancedSearchPeople.archite"/></option>
						                        	<option value="Armorers/Weapon Makers"><fmt:message key="search.advancedSearchPeople.armor"/></option>
						                       		<option value="Cloth Weavers/Embroiderers"><fmt:message key="search.advancedSearchPeople.clothW"/></option>
						                        	<option value="Clothing Makers"><fmt:message key="search.advancedSearchPeople.clothing"/></option>
						                        	<option value="Gold/Silver Workers"><fmt:message key="search.advancedSearchPeople.goldSil"/></option>
						                        	<option value="Jewelers/Hard Stone Workers"><fmt:message key="search.advancedSearchPeople.jewelers"/></option>
						                        	<option value="Musicians/Singers/Instrument Makers"><fmt:message key="search.advancedSearchPeople.musicians"/></option>
						                        	<option value="Painters"><fmt:message key="search.advancedSearchPeople.painters"/></option>
						                        	<option value="Printmakers"><fmt:message key="search.advancedSearchPeople.printM"/></option>
						                        	<option value="Sculptors"><fmt:message key="search.advancedSearchPeople.sculp"/></option>
						                        	<option value="Tapestry Weavers"><fmt:message key="search.advancedSearchPeople.tapest"/></option>
						                        	<option value="Woodworkers"><fmt:message key="search.advancedSearchPeople.woodwor"/></option>
						                        	<option value="Artists and Artisans Other"><fmt:message key="search.advancedSearchPeople.artistsAnd"/></option>
						                        </optgroup>
						                        <optgroup label="CORPORATE BODIES">
						                        	<option value="CORPORATE BODIES"><fmt:message key="search.advancedSearchPeople.allCorp"/></option>
						                        	<option value="Religious"><fmt:message key="search.advancedSearchPeople.religi"/></option>
						                        	<option value="Secular"><fmt:message key="search.advancedSearchPeople.secul"/></option>
						                        	<option value="Corporate Bodies Other"><fmt:message key="search.advancedSearchPeople.corpor"/></option>
						                        </optgroup>
						                        <optgroup label="ECCLESIASTICS">
						                        	<option value="ECCLESIASTICS"><fmt:message key="search.advancedSearchPeople.allEccl"/></option>
						                        	<option value="Beatified/Saints"><fmt:message key="search.advancedSearchPeople.beat"/></option>
						                        	<option value="Bishops/Archbishops"><fmt:message key="search.advancedSearchPeople.bisho"/></option>
						                        	<option value="Cardinals"><fmt:message key="search.advancedSearchPeople.cardi"/></option>
						                        	<option value="Members of Religious Orders"><fmt:message key="search.advancedSearchPeople.membersOfR"/></option>
						                        	<option value="Popes"><fmt:message key="search.advancedSearchPeople.popes"/></option>
						                        	<option value="Ecclesiastics Other"><fmt:message key="search.advancedSearchPeople.ecclesi"/></option>
						                        </optgroup>
						                        <optgroup label="HEADS of STATE">
						                        	<option value="HEADS of STATE"><fmt:message key="search.advancedSearchPeople.allHeads"/></option>
						                        	<option value="Emperors-Empresses/Kings-Queens"><fmt:message key="search.advancedSearchPeople.emper"/></option>
						                        	<option value="Sovereign Dukes-Duchesses/Grand Dukes-Duchesses"><fmt:message key="search.advancedSearchPeople.sovereign"/></option>
						                        	<option value="Viceroys-Vicereines/Governors"><fmt:message key="search.advancedSearchPeople.viceroy"/></option>
						                        	<option value="Heads of State Other"><fmt:message key="search.advancedSearchPeople.headsOf"/></option>
						                        </optgroup>
						                        <optgroup label="MILITARY and NAVAL PERSONNEL">
						                        	<option value="MILITARY and NAVAL PERSONNEL"><fmt:message key="search.advancedSearchPeople.allMilit"/></option>
						                        	<option value="Captains"><fmt:message key="search.advancedSearchPeople.captains"/></option>
						                        	<option value="Colonels"><fmt:message key="search.advancedSearchPeople.colonels"/></option>
						                        	<option value="Generals/Admirals"><fmt:message key="search.advancedSearchPeople.generals"/></option>
						                        	<option value="Lieutenants/Ensigns"><fmt:message key="search.advancedSearchPeople.lieuten"/></option>
						                        	<option value="Military and Naval Personnel Other"><fmt:message key="search.advancedSearchPeople.military"/></option>
						                        </optgroup>
						                        <optgroup label="NOBLES">
						                        	<option value="NOBLES"><fmt:message key="search.advancedSearchPeople.allNobles"/></option>
						                        	<option value="Barons-Baronesses"><fmt:message key="search.advancedSearchPeople.baron"/></option>
						                        	<option value="Counts-Countesses"><fmt:message key="search.advancedSearchPeople.counts"/></option>
						                        	<option value="Dukes-Duchesses/Archdukes-Archduchesses"><fmt:message key="search.advancedSearchPeople.dukes"/></option>
						                        	<option value="Marquises-Marchionesses"><fmt:message key="search.advancedSearchPeople.marquises"/></option>
						                        	<option value="Members Chivalric Orders"><fmt:message key="search.advancedSearchPeople.members"/></option>
						                        	<option value="Princes-Princesses"><fmt:message key="search.advancedSearchPeople.princes"/></option>
						                        	<option value="Nobles OTHER"><fmt:message key="search.advancedSearchPeople.nobles"/></option>
						                        </optgroup>
						                        <optgroup label="PROFESSIONS">
						                        	<option value="PROFESSIONS"><fmt:message key="search.advancedSearchPeople.allProf"/></option>
						                        	<option value="Bankers/Merchants"><fmt:message key="search.advancedSearchPeople.bankers"/></option>
						                        	<option value="Lawyers/Notaries"><fmt:message key="search.advancedSearchPeople.lawyers"/></option>
						                        	<option value="Medical Practitioners"><fmt:message key="search.advancedSearchPeople.medical"/></option>
						                        	<option value="Professions OTHER"><fmt:message key="search.advancedSearchPeople.professions"/></option>
						                        </optgroup>
						                        <optgroup label="SCHOLARLY and LITERARY">
						                        	<option value="SCHOLARLY and LITERARY"><fmt:message key="search.advancedSearchPeople.allSchol"/></option>
						                        	<option value="Poets/Writers"><fmt:message key="search.advancedSearchPeople.poets"/></option>
						                        	<option value="Printers/Booksellers"><fmt:message key="search.advancedSearchPeople.printers"/></option>
						                        	<option value="Scholarly/Learned"><fmt:message key="search.advancedSearchPeople.schol"/></option>
						                        	<option value="Scholarly and Literary Other"><fmt:message key="search.advancedSearchPeople.scholarlyOther"/></option>
						                        </optgroup>
						                        <optgroup label="STATE and COURT PERSONNEL">
						                        	<option value="STATE and COURT PERSONNEL"><fmt:message key="search.advancedSearchPeople.allState"/></option>
						                        	<option value="Civic/Local/Regional Administrators"><fmt:message key="search.advancedSearchPeople.civic"/></option>
						                        	<option value="Courtiers"><fmt:message key="search.advancedSearchPeople.courtiers"/></option>
						                        	<option value="Diplomats"><fmt:message key="search.advancedSearchPeople.diplomats"/></option>
						                        	<option value="Judges/Magistrates"><fmt:message key="search.advancedSearchPeople.judges"/></option>
						                        	<option value="Secretaries/Ministers"><fmt:message key="search.advancedSearchPeople.secretaries"/></option>
						                        	<option value="State and Court Personnel Other"><fmt:message key="search.advancedSearchPeople.stateAnd"/></option>
						                        </optgroup>
						                        <optgroup label="UNASSIGNED">
						                        	<option value="Unassigned"><fmt:message key="search.advancedSearchPeople.unassigned"/></option>
						                        </optgroup>
						            </select>
				               	</div>
				               	<div class="col_r">
				               		<input type="hidden" id="roleCategory" name="roleCategory" type="text" value=""/>
						            <input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>">
									<input type="hidden" id="category" value="Role Category">
				               	</div>
				            </div>
			            </form>
			        
					<br />
					
						<div class="row">
			               	<div class="col_l"><fmt:message key="search.advancedSearchPeople.wordSearchInTitleOccupation.title"/></div>
			            </div>
			        
			        <form id="occupationWordSearchForm" method="post" class="edit">
			            <div class="row">
			               	<div class="col_l">
			               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.titleoccupation.wordsearchintitleoccupation"></fmt:message>">?</a>
								<input id="occupationWord" name="occupationWord" class="input_20c" type="text" value=""/>
			               	</div>
			               	<div class="col_r">
			               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this to your search filter">
								<input type="hidden" id="category" value="OccupationWord">
			               	</div>
			            </div>
			        </form>
			        
			        <br />
			        
				        <div class="row">
				             <div class="col_l"><fmt:message key="search.advancedSearchPeople.matchTheExactTitleOccupation.title"/></div>
				        </div>
			        
			        <form id="occupationSearchForm" method="post" class="edit">
				        <div class="row">
				        	<div class="col_l">
				        		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.titleoccupation.matchthexatcttitleoccupation"></fmt:message>">?</a>
								<input id="occupation" name="occupation" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE -->
				        	</div>
				        	<div class="col_r">
				        		<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="occupationAdd button_small" disabled="disabled">
								<input type="hidden" id="category" value="Occupation">
								<input type="hidden" id="occupationId" value="">
				        	</div>
				        </div>
			        </form>
				</div> 
				</div>
				
				<h1 id="placeSearch"><a><fmt:message key="search.advancedSearchPeople.personDetails.title"/></a></h1>
				<div class="people">
					<div class="listAdvSearch">
						<form id="genderSearchForm" method="post" class="edit">
				           	<div class="row">
				               	<div class="col_l">
				               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.persondetailsandvitalstatistics.gender"></fmt:message>">?</a>
									<label for="gender" id="genderLabel"><fmt:message key="search.advancedSearchPeople.personDetails.gender"/></label>
				               	</div>
				               	<div class="col_l">
				               		<select id="genderSelect" name="genderSelect" class="selectform_short">
										<option value="M" selected="selected"><fmt:message key="search.advancedSearchPeople.personDetails.gender.male"/></option>
						                <option value="F"><fmt:message key="search.advancedSearchPeople.personDetails.gender.female"/></option>
						                <option value="X"><fmt:message key="search.advancedSearchPeople.personDetails.gender.notApplicable"/></option>
									</select>
				               	</div>
				               	<div class="col_r">
				               		<input type="hidden" id="gender" name="gender" type="text" value=""/>
									<input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>" class="genderAdd button_small">
									<input type="hidden" id="category" value="Gender">
				               	</div>
				            </div>
			            </form>
			       
			           	<form id="placeSearchForm" method="post" class="edit">
				           	<div class="row">
				               	<div class="col_l">
				               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.persondetailsandvitalstatistics.birthdeathplace"></fmt:message>">?</a>
									<select id="placeType" name="placeType" class="selectform_MXlong">
										<option value="Birth/Death Place" selected="selected"><fmt:message key="search.advancedSearchPeople.personDetails.birthDeathPlace"/></option>
										<option value="Birth Place"><fmt:message key="search.advancedSearchPeople.personDetails.birthPlace"/></option>
										<option value="Death Place"><fmt:message key="search.advancedSearchPeople.personDetails.deathPlace"/></option>
									</select>
				               	</div>
				               	<div class="col_l"><input id="place" name="place" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE --></div>
				               	<div class="col_r">
				               		<input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>" class="placeAdd button_small" disabled="disabled">
									<input type="hidden" id="category" value="Place">
									<input type="hidden" id="placeId" value="">
				               	</div>
				            </div>
			            </form>
			        </div>
			            
			           <div class="listAdvSearch">
			           	<form id="dateSearchForm" method="post" class="edit">
				           	<div class="row">
				               	<div class="col_l">
				               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.persondetailsandvitalstatistics.bornanddeathdate"></fmt:message>">?</a>
									<select id="dateType" name="dateType" class="selectform_Llong">
										<option value="Born after" selected="selected" ><fmt:message key="search.advancedSearchPeople.personDetails.bornAfter"/></option>
										<option value="Dead by"><fmt:message key="search.advancedSearchPeople.personDetails.deadBy"/></option>
										<option value="Lived between"><fmt:message key="search.advancedSearchPeople.personDetails.livedBetween"/></option>
										<option value="Born/Died on"><fmt:message key="search.advancedSearchPeople.personDetails.bornDiedOn"/></option>
									</select>
				               	</div>
				               	<div class="col_l"><input id="dateYear" name="dateYear" class="input_4c" type="text" value="yyyy" maxlength="4"/></div>
				               	<div class="col_l">
				               		<select id="dateMonth" name="dateMonth" class="selectform">
									<c:forEach items="${months}" var="month">
										<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
									</c:forEach>
									</select>
				               	</div>
				               	<div class="col_l"><input id="dateDay" name="dateDay" class="input_2c" type="text" value="dd" maxlength="2"/></div>
				               	<div class="col_r"><input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>" class="visible button_small"></div>
				            </div>
				            
				            <div class="row">
				               	<div class="col_l">
				               		<input type="hidden" id="category" value="Date">
									<p class="invisible"><fmt:message key="search.advancedSearchPeople.personDetails.livedBetween.and"/></p>
				               	</div>
				               	<div class="col_l"><input id="dateYearBetween" name="dateYearBetween" class="input_4c" type="text" value="yyyy" maxlength="4" /></div>
				               	<div class="col_l">
				               		<select id="dateMonthBetween" name="dateMonthBetween" class="selectform">
						            <c:forEach items="${months}" var="month">
										<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
									</c:forEach>
						            </select>
				               	</div>
				               	<div class="col_l"><input id="dateDayBetween" name="dateDayBetween" class="input_2c" type="text" value="dd" maxlength="2"/></div>
				               	<div class="col_r"><input type="submit" id="addSearchFilter" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>" class="invisible button_small"></div>
				            </div>
			            </form>
			        </div>
			    </div>
				
				<h1 id="researchNotes"><a><i><fmt:message key="search.advancedSearchPeople.inResearchNotes.title"/> </i><fmt:message key="search.advancedSearchPeople.ResearchNotes.title"/></a></h1>
				<div class="people">
					<div class="listAdvSearch">
						<form id="researchNotesSearchForm" method="post" class="edit">
				           	<div class="row">
				               	<div class="col_l">
				               		<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.researchnotes"></fmt:message>">?</a>
									<input id="researchNotes" name="researchNotes" class="input_20c" type="text" value=""/>
				               	</div>
				               	<div class="col_r">
				               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>">
									<input type="hidden" id="category" value="Research Notes">
				               	</div>
				            </div>
			            </form>
			        </div>
				</div>
				
				<h1 id="personIdSearch"><a><fmt:message key="search.advancedSearchPeople.personId.title"/></a></h1>
				<div class="people">
					<div class="listAdvSearch">
						<form id="personIdSearchForm" method="post" class="edit">
							<div class="row">
								<div class="col_l">
									<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.personid"></fmt:message>">?</a>
									<input id="personId" name="personId" class="input_7c" type="text" maxlength="5" />
								</div>
								<div class="col_r">
									<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>">
									<input type="hidden" id="category" value="Person Id">
								</div>
							</div>
						</form>
					</div>
				</div>
				
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
				<h1 id="logicalDeleteSearch"><a><fmt:message key="search.advancedSearchPeople.logicalDelete.title"/></a></h1>
				<div class="people">
					<div class="listAdvSearch">
						<form id="logicalDeleteSearchForm" method="post" class="edit">
							<div class="row">
								<div class="col_l">
									<a class="helpIcon" title="<fmt:message key="search.advancedSearchPeople.help.logicaldelete"></fmt:message>">?</a>
									<label for="logicalDelete" id="logicalDeleteLabel"><fmt:message key="search.advancedSearchPeople.logicalDelete.deleted"/></label>
									<input type="checkbox" name="logicalDelete" value="true"/>
								</div>
								<div class="col_r">
									<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="<fmt:message key="search.advancedSearchPeople.addToYourSearchFilter.alt"/>">
									<input type="hidden" id="category" value="Logical Delete">
								</div>
							</div>
						</form>
					</div>
				</div>				
				</security:authorize>
					
			</div>
			</div>

<c:url var="searchPlaceURL" value="/src/SearchPlace.json"/>
<c:url var="searchPersonURL" value="/src/SearchPerson.json"/>
<c:url var="searchTitleOrOccupationURL" value="/src/SearchTitleOrOccupation.json"/>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#dateMonth option:eq(0)").text("mm");
		$j("#dateMonth option:eq(0)").attr('selected', 'selected');
		$j("#dateMonthBetween option:eq(0)").text("mm");
		$j("#dateMonthBetween option:eq(0)").attr('selected', 'selected');

		$j('#namePartsSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		
		$j('#personSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});

		$j('#wordSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});

		$j('#dateSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});

		$j('#roleCategorySearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});

		$j('#occupationWordSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		$j('#occupationSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});

		$j('#genderSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});

		$j('#placeSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});	

		$j('#researchNotesSearchForm').advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});

		$j("#personIdSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});

		$j("#logicalDeleteSearchForm").advancedSearchForm({
			AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
			consoleLog : false
		});
		
		$j('#accordion').accordion({
			active: false, 
			autoHeight: false,
			collapsible:true
			});
		$j("#dateType").change(function(){
			if(this.options[2].selected) { 
				$j('#dateYearBetween').css('visibility','visible');
				$j('#dateMonthBetween').css('visibility','visible');
				$j('#dateDayBetween').css('visibility','visible');
				$j('.invisible').css('visibility','visible');
				$j('.visible').css('visibility','hidden');
		   } else { 
				$j('#dateYearBetween').css('visibility','hidden');
				$j('#dateMonthBetween').css('visibility','hidden');
				$j('#dateDayBetween').css('visibility','hidden');
				$j('.invisible').css('visibility','hidden');
				$j('.visible').css('visibility','visible');
			}
		});
		 $j('#dateYear').focus(function(){
				  if(this.value=='yyyy')
					  {
					  this.value=''
					  }
				 });
		 $j('#dateYearBetween').focus(function(){
				  if(this.value=='yyyy')
					  {
					  this.value=''
					  }
				 });
		 $j('#dateDay').focus(function(){
				  if(this.value=='dd')
					  {
					  this.value=''
					  }
				 });
		 $j('#dateDayBetween').focus(function(){
				  if(this.value=='dd')
					  {
					  this.value=''
					  }
				 });
		 
		 $j("#dateSearchForm :input").keyup(function(){
			if($j("#dateType option:selected").val() == 'Lived between'){
				if($j("#dateYear").val() == 'yyyy' || $j("#dateYear").val() == '' || $j("#dateYearBetween").val() == 'yyyy' || $j("#dateYearBetween").val() == '')
					$j(".addDateRange").attr("disabled", "disabled");
				else{
					$j(".addDateRange").attr("disabled");
					$j(".addDateRange").removeAttr("disabled");
					$j(".addDateRange").prop("disabled", false);
				}
			}else{
				$j(".addDateRange").attr("disabled");
				$j(".addDateRange").removeAttr("disabled");
				$j(".addDateRange").prop("disabled", false);
			} 
		 });
		 
		 $j("#dateType").change(function(){
			if($j("#dateType option:selected").val() == 'Lived between'){
				if($j("#dateYear").val() == 'yyyy' || $j("#dateYear").val() == '' || $j("#dateYearBetween").val() == 'yyyy' || $j("#dateYearBetween").val() == '')
					$j(".addDateRange").attr("disabled", "disabled");
				else{
					$j(".addDateRange").attr("disabled");
					$j(".addDateRange").removeAttr("disabled");
					$j(".addDateRange").prop("disabled", false);
				}
					
			}else{
				$j(".addDateRange").attr("disabled");
				$j(".addDateRange").removeAttr("disabled");
				$j(".addDateRange").prop("disabled", false);
			}
		 });
		 
		 $j("#dateSearchForm").submit(function(){
				$j('#dateType option[value="Born after"]').attr('selected', 'selected');
				$j('#dateYearBetween').css('visibility','hidden');
				$j('#dateMonthBetween').css('visibility','hidden');
				$j('#dateDayBetween').css('visibility','hidden');
				$j('.invisible').css('visibility','hidden');
				$j('.visible').css('visibility','visible');
			});
			
		 
		 $j("#roleCategorySelect").change(function(){
			 if($j(this).val() != "Select a Role Category")
			 	$j("#roleCategory").val($j(this).val());
			 else
				 $j("#roleCategory").val("");
			 return false;
		 });
		 
		 //This is for set the field when the page is loaded
		 $j("#gender").val($j("#genderSelect").find("option:selected").val());
		 
		 $j("#genderSelect").change(function(){
			 $j("#gender").val($j(this).val());
			 return false;
		 });
		 
		//MD: To fix bug in Chrome
		$j("h1").click(function(){
			$placeAutocomplete.killSuggestions();
			$occupationAutocomplete.killSuggestions();
			return false;
		});
		 
		var $personValue = '';
		var $personAutocomplete = $j("#person").autocompletePerson({
			serviceUrl: '${searchPersonURL}',
		    loadingImageUrl:'${LoadingImageURL}',
			minChars: 3,
			delimiter: null,
			maxHeight: 400,
			width: 450,
			zIndex: 9999,
			deferRequestBy: 0,
			noCache: true,
			onSelect: function(value, data){
				$j(".personAdd").die();
				$j(".personAdd").removeAttr("disabled");
				$j('#personId').val(data);
				$j(".personAdd").attr("disabled");
				$j(".personAdd").prop("disabled", false);
				$personValue = $j("#person").val();
				$j("#person").live('keyup', function(){
					if($j("#person").val() != $personValue){
						$j(".personAdd").attr("disabled","disabled");
						$j("#personId").val("");
					}
					return false;
				});
				$j("#person").live('keypress', function(e){
					if(e.keyCode == 13 && $j("#person").val() != $personValue){
						e.stopPropagation();
						return false;
					}
				});
			}
		});	
		 
		$j("#personSearchForm").submit(function(){
			$j("#personId").val("");
			$j(".personAdd").attr("disabled","disabled");
		});
		 
		var $placeValue = '';
		var $placeAutocomplete = $j("#place").autocompletePlace({
			serviceUrl: '${searchPlaceURL}',
		    loadingImageUrl:'${LoadingImageURL}',
			minChars: 3,
			delimiter: null,
			maxHeight: 400,
			width: 450,
			zIndex: 9999,
			deferRequestBy: 0,
			noCache: true,
			onSelect: function(value, data){
				$j(".placeAdd").die();
				$j(".placeAdd").removeAttr("disabled");
				$j('#placeId').val(data);
				$j(".placeAdd").attr("disabled");
				$j(".placeAdd").prop("disabled", false);
				$placeValue = $j("#place").val();
				$j("#place").live('keyup', function(){
					if($j("#place").val() != $placeValue){
						$j(".placeAdd").attr("disabled","disabled");
						$j("#placeId").val("");
					}
					return false;
				});
				$j("#place").live('keypress', function(e){
					if(e.keyCode == 13 && $j("#place").val() != $placeValue){
						e.stopPropagation();
						return false;
					}
				});
			}
		});	
		 
		$j("#placeSearchForm").submit(function(){
			$j("#placeId").val("");
			$j(".placeAdd").attr("disabled","disabled");
		});
		 
		var $occupationValue = '';
		var $occupationAutocomplete = $j("#occupation").AutocompleteTitle({
			 	serviceUrl:'${searchTitleOrOccupationURL}',
			    loadingImageUrl:'${LoadingImageURL}',
			    minChars:3, 
			    delimiter: null, // /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j(".occupationAdd").die();
			    	$j(".occupationAdd").removeAttr("disabled");
			    	$j('#occupationId').val(data);
			    	$j(".occupationAdd").attr("disabled");
			    	$j(".occupationAdd").prop("disabled", false);
			    	$occupationValue = $j("#occupation").val();
					$j("#occupation").live('keyup', function(){
						if($j("#occupation").val() != $occupationValue){
							$j(".occupationAdd").attr("disabled","disabled");
							$j("#occupationId").val("");
						}
						return false;
					});
					$j("#occupation").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#occupation").val() != $occupationValue){
							e.stopPropagation();
							return false;
						}
					});
			    }
		});
		
		$j("#occupationSearchForm").submit(function(){
			$j("#occupationId").val("");
			$j(".occupationAdd").attr("disabled","disabled");
		});
		 
		$j('#nameParts').click(function(){
			 $j.scrollTo({top:'0px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "0px"}, "high");
			 return false;
		});
		$j('#dateRange').click(function(){
			 $j.scrollTo({top:'113px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "70px"}, "high");
			 return false;
		});
		$j('#titleOccupation').click(function(){
			 $j.scrollTo({top:'140px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "100px"}, "high");
			 return false;
		});
		$j('#placeSearch').click(function(){
			 $j.scrollTo({top:'168px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "125px"}, "high");
			 return false;
		});
		$j('#researchNotes').click(function(){
			 $j.scrollTo({top:'195px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "150px"}, "high");
			 return false;
		});
		$j('#personIdSearch').click(function(){
			$j.scrollTo({top:'222px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "180px"}, "slow");
			return false;
		});
	});
</script>