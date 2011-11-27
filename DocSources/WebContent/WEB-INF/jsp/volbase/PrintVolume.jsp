<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

  
     <h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Volume Report</h4>

     
     <h3 class="first">Minute di Lettere e Registri / Minute: Cosimo I / Segretario: Concino</h3>
     <table>
         <tr> 
             <td width="100">Volume/Filza (MDP)</td>
             <td width="285" class="value">${volume.volNum}${volume.volLetExt}</td>
         </tr>
         <tr>
             <td width="100">Start Date</td>

             <td width="285" class="value">>${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay}</td>
         </tr>
         <tr>
             <td width="100">End Date</td>
             <td width="285" class="value">${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay}</td>
         </tr>
         <tr>

             <td width="100">Date Notes</td>
             <td width="285" class="value">${volume.dateNotes}</td>
         </tr>
     </table> 
     
     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
     
     <h5>Description</h5>
     <table>
         <tr> 
             <td width="90">Organizational Criteria</td>
             <td width="330" class="value" colspan="3">${volume.orgNotes}</td>
         </tr>
         <tr>
             <td width="70">Condition</td>
             <td width="330" class="value" colspan="3">${volume.ccondition}</td>
         </tr>
         <tr> 
             <td width="70">Bound</td>
             <td width="130" class="value">${volume.bound ? 'Yes' : 'No'}</td>
             <td width="150">Folios Numbered</td>
             <td width="30" class="valueRight">${volume.folsNumbrd ? 'Yes' : 'No'}</td>
         </tr>
         <tr> 
             <td width="70">Folio Count</td>
             <td width="150" class="value">${volume.folioCount}</td>

             <td width="100">Index of Names</td>
             <td width="50" class="valueRight">Yes${volume.oldAlphaIndex ? 'Yes' : 'No'}td>
         </tr>
         <tr> 
             <td width="70">Printed material</td>
             <td width="150" class="value">${volume.printedMaterial ? 'Yes' : 'No'}</td>
             <td width="100">Printed drawings</td>
             <td width="50" class="valueRight">${volume.printedDrawings ? 'Yes' : 'No'}</td>
         </tr>
         <tr> 
             <td width="70">Languages</td>
             <td width="150" class="valueLeft" colspan="3">${volume.italian ? 'Italian' : '' } 
									${volume.spanish ? 'Spanish' : ''}
									${volume.english ? 'English' : ''}
									${volume.latin ? 'Latin' : ''}
									${volume.german ? 'German' : ''}
									${volume.french ? 'French' : ''}
									${volume.otherLang}</td>
         </tr>
         <tr>
             <td width="130">Some Documents in Cipher</td>
             <td width="50" class="value">${volume.cipher ? 'Yes' : 'No'}</td>
         </tr>
         <tr> 
             <td width="70">Cipher Notes</td>
             <td width="350" class="value" colspan="3">${volume.cipherNotes}</td>
         </tr>
     </table> 
     
     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>

     
     <h5>Correspondents</h5>
     <table>
         <tr> 
           <td width="70">From</td>
           <td class="value">${volume.senders}</td>
         </tr>
         <tr> 
           <td width="70">To</td>

           <td class="value">${volume.recips}</td>
         </tr>
     </table>
     
     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
     
     <h5>Context</h5>
     <table>
         <tr> 
           <td width="70">Context</td>

           <td class="value">${volume.ccontext}</td>
         </tr>
         <tr> 
           <td width="70">Inventario Sommario Description</td>
           <td class="value">${volume.inventarioSommarioDescription}</td>
         </tr>
     </table> 
     
     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
