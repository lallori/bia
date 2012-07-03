-- PLACES
-- added new field to manage creation date
update docsources.tblPlaces set dateCreated = dateEntered where dateCreated is null;
-- added new field to manage last update
update docsources.tblPlaces set lastUpdate = dateEntered where lastUpdate is null;


-- Document sortableDateInt: this is not for lucene indexing. This field is important for order documents by date.
update docsources.tblDocuments set sortableDateInt=concat(yearModern,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is not null and docMonthNum is not null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat(yearModern,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is not null and docMonthNum is not null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat(yearModern,'00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is not null and docMonthNum is null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat(yearModern,'00','00') where yearModern is not null and docMonthNum is null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is not null and docMonthNum is not null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is null and docYear is not null and docMonthNum is not null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat(docYear,'00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is not null and docMonthNum is null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat(docYear,'00','00') where yearModern is null and docYear is not null and docMonthNum is null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat('00',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is null and docMonthNum is not null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat('00',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is null and docYear is null and docMonthNum is not null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat('00','00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is null and docMonthNum is null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat('00','00','00') where yearModern is null and docYear is null and docMonthNum is null and docDay is null;

