CREATE DATABASE `docsources` /*!40100 DEFAULT CHARACTER SET latin1 */;

CREATE DATABASE `docsources_audit` ;
drop table docsources_audit.tblAltNames_AUD;
drop table docsources_audit.tblBiblioT_AUD;
drop table docsources_audit.tblBioRefLink_AUD;
drop table docsources_audit.tblCatalog_AUD;
drop table docsources_audit.tblCountries_AUD;
drop table docsources_audit.tblDocuments_AUD;
drop table docsources_audit.tblEPLTOLink_AUD;
drop table docsources_audit.tblEPLink_AUD;
drop table docsources_audit.tblFactChecks_AUD;
drop table docsources_audit.tblImages_AUD;
drop table docsources_audit.tblMarriages_AUD;
drop table docsources_audit.tblPOLink_AUD;
drop table docsources_audit.tblPeople_AUD;
drop table docsources_audit.tblPlaces_AUD;
drop table docsources_audit.tblPrcLink_AUD;
drop table docsources_audit.tblRoleCats_AUD;
drop table docsources_audit.tblSeriesList_AUD;
drop table docsources_audit.tblSynExtracts_AUD;
drop table docsources_audit.tblTitleOccsList_AUD;
drop table docsources_audit.tblTopicsList_AUD;
drop table docsources_audit.tblVolumes_AUD;

create table docsources_audit.tblAltNames_AUD (NAMEID integer not null, REV integer not null, REVTYPE tinyint, ALTNAME varchar(255), NAMEPREFIX varchar(50), NAMETYPE varchar(50), NOTES LONGTEXT, PERSONID integer, primary key (NAMEID, REV));
create table docsources_audit.tblBiblioT_AUD (BIBLIOID integer not null, REV integer not null, REVTYPE tinyint, AUTHOREDITOR varchar(255), DATES varchar(255), NOTES varchar(255), PERIODICAL varchar(255), PUBLISHER varchar(255), SHELFNUM varchar(255), TITLE varchar(255), TYPE varchar(255), primary key (BIBLIOID, REV));
create table docsources_audit.tblBioRefLink_AUD (REFID integer not null, REV integer not null, REVTYPE tinyint, NOTES varchar(255), BIBLIOID integer, PERSONID integer, primary key (REFID, REV));
create table docsources_audit.tblCatalog_AUD (ID integer not null, REV integer not null, REVTYPE tinyint, CARTE_BIANCHE varchar(50), CARTE_MANCANTI varchar(50), CARTULAZIONE varchar(50), COMPRESSIONE varchar(50), DATA_RIPRESA datetime, DATE_ESTREME varchar(50), DESCRIZIONE_CONTENUTO varchar(50), DIM_MEDIA_IMMAGINI bigint, DIM_TOTALE_IMMAGINI bigint, DIMENSIONI_ALTEZZA integer, DIMENSIONI_BASE varchar(50), FONDO varchar(50), FORMATO varchar(50), ISTITUTO varchar(50), LEGATURA varchar(50), N_UNITA integer, NOME_FILES varchar(50), NOTE_ALLA_CARTULAZIONE varchar(50), NUMERO_TOTALE_IMMAGINI integer, OEPRATORE varchar(50), PROFONDITA_COLORE varchar(50), RESPONSABILE_FOTORIPRODUZIONE varchar(50), RISOLUZIONE varchar(50), SERIE varchar(50), SISTEMA_DI_SCANSIONE varchar(50), SUPPORTO varchar(50), TITOLO varchar(50), primary key (ID, REV));
create table docsources_audit.tblCountries_AUD (CODE varchar(2) not null, REV integer not null, REVTYPE tinyint, NAME varchar(50), primary key (CODE, REV));
create table docsources_audit.tblDocuments_AUD (ENTRYID integer not null, REV integer not null, REVTYPE tinyint, CONTDISC tinyint, DATEAPPROX varchar(50), DATECREATED datetime, DATENOTES varchar(255), DATEUNS tinyint, DOCDAY integer, DOCMONTHNUM integer, DOCSTATBOX varchar(50), DOCTOBEVETTEDDATE datetime, DOCTOBEVETTED TINYINT default '-1', DOCVETBEGINS datetime, DOCVETID varchar(50), DOCVETTED TINYINT default '-1', DOCVETTEDDATE datetime, DOCYEAR integer, FOLIOMOD varchar(15), FOLIONUM integer, TRANSCRIBEFOLIONUM integer, TRANSCRIBEFOLIOMOD varchar(15), GRAPHIC tinyint, INSERTLET varchar(15), INSERTNUM varchar(50), LASTUPDATE datetime, NEWENTRY tinyint, RECIPNOTES varchar(250), RECIPUNS tinyint, RECIPLOCUNS tinyint, RECKONING tinyint, RESID varchar(50), SENDNOTES varchar(250), SENDUNS tinyint, SENDLOCUNS tinyint, SORTABLEDATE varchar(50), SUBVOL varchar(50), UNDATED tinyint, UNPAGED tinyint, YEARMODERN integer, RECIPID integer, RECIPLOCPLALL integer, SENDID integer, SENDLOCPLALL integer, SUMMARYID integer, primary key (ENTRYID, REV));
create table docsources_audit.tblEPLTOLink_AUD (EPLTOID integer not null, REV integer not null, REVTYPE tinyint, DATECREATED datetime, ENTRYID integer, PLACESALLID integer, TOPICID integer, primary key (EPLTOID, REV));
create table docsources_audit.tblEPLink_AUD (EPLINKID integer not null, REV integer not null, REVTYPE tinyint, ASSIGNUNSURE tinyint, DOCROLE varchar(50), DATECREATED datetime, PORTRAIT tinyint, ENTRYID integer, PERSONID integer, primary key (EPLINKID, REV));
create table docsources_audit.tblFactChecks_AUD (VETID integer not null, REV integer not null, REVTYPE tinyint, ADDLRES LONGTEXT, DATEINFO varchar(50), EDITCOMMENT LONGTEXT, PERSON varchar(50), PLACE varchar(50), ENTRYID integer, primary key (VETID, REV));
create table docsources_audit.tblImages_AUD (imageId integer not null, REV integer not null, REVTYPE tinyint, dateCreated datetime, imageName varchar(45), imageType varchar(1), storagePath integer, volLetExt varchar(1), volNum integer, primary key (imageId, REV));
create table docsources_audit.tblMarriages_AUD (MARRIAGEID integer not null, REV integer not null, REVTYPE tinyint, DATECREATED datetime, ENDDAY integer, ENDMONTH varchar(10), ENDMONTHNUM integer, ENDUNS tinyint, ENDYEAR integer, MARTERM varchar(50), NOTES LONGTEXT, REFID integer, STARTDAY integer, STARTMONTH varchar(10), STARTMONTHNUM integer, STARTUNS TINYINT, STARTYEAR integer, HUSBANDID integer, WIFEID integer, primary key (MARRIAGEID, REV));
create table docsources_audit.tblPOLink_AUD (PRLINKID integer not null, REV integer not null, REVTYPE tinyint, STARTUNS TINYINT, DATECREATED datetime, ENDAPPROX TINYINT, ENDDAY integer, ENDMONTH varchar(50), ENDMONTHNUM integer, ENDUNS TINYINT, ENDYEAR integer, PRLINKNOTES LONGTEXT, PRTAG integer, STARTAPPROX TINYINT, STARTDAY integer, STARTMONTH varchar(50), STARTMONTHNUM integer, STARTYEAR integer, PERSONID integer, TITLEOCCID integer, primary key (PRLINKID, REV));
create table docsources_audit.tblPeople_AUD (PERSONID integer not null, REV integer not null, REVTYPE tinyint, ACTIVEEND varchar(50), ACTIVESTART varchar(50), BAPPROX TINYINT, BDATEBC TINYINT, BDAY TINYINT, BMONTHNUM integer, BPLACE varchar(50), BPLACEID integer, BPLACEUNS TINYINT, BYEAR integer, BIONOTES LONGTEXT, DAPPROX TINYINT, DDAY TINYINT, DMONTHNUM integer, DPLACE varchar(50), DPLACEID integer, DPLACEUNS TINYINT, DYEAR integer, DYEARBC TINYINT, DATECREATED datetime, FIRST varchar(50), GENDER varchar(1), LAST varchar(50), LASTPREFIX varchar(50), LASTUPDATE datetime, MAPNameLF varchar(150), MIDPREFIX varchar(50), MIDDLE varchar(50), PORTRAIT TINYINT, POSTLAST varchar(50), POSTLASTPREFIX varchar(50), RESID varchar(255), STAFFNOTES LONGTEXT, STATUS varchar(15), SUCNUM varchar(6), FATHERID integer, MOTHERID integer, primary key (PERSONID, REV));
create table docsources_audit.tblPlaces_AUD (PLACEALLID integer not null, REV integer not null, REVTYPE tinyint, ADDLRES TINYINT, DATEENTERED datetime, GPARENT varchar(255), GEOGKEY integer, GEOGKEY_CHILDREN LONGTEXT, GGP varchar(255), GGPTYPE varchar(255), GP2 varchar(255), GP2TYPE varchar(255), GPTYPE varchar(255), LANGUAGE integer, OTHER_FLAGS varchar(50), PARENTTYPE varchar(255), PLNAMEFULL_PLTYPE varchar(255), PLPARENT varchar(255), PLPARENT_SUBJECT_ID integer, PLPARENT_TERM_ID integer, PLSOURCE varchar(50), PLTYPE varchar(255), PLACENAME varchar(255), PLACENAMEFULL varchar(255), PLACENAMEID integer, PLACESMEMO LONGTEXT, PREFFLAG varchar(5), RESID varchar(50), TERM_ACCENT varchar(50), PLPARENT_PLACEALLID integer, primary key (PLACEALLID, REV));
create table docsources_audit.tblPrcLink_AUD (PRCLINKID integer not null, REV integer not null, REVTYPE tinyint, DATECREATED datetime, PERSONID integer, ROLECATID integer, primary key (PRCLINKID, REV));
create table docsources_audit.tblRoleCats_AUD (ROLECATID integer not null, REV integer not null, REVTYPE tinyint, ROLECATMAJOR varchar(255), ROLECATMAJORID integer, ROLECATMINOR varchar(255), SORTGROUS integer, primary key (ROLECATID, REV));
create table docsources_audit.tblSeriesList_AUD (SERIESREFNUM integer not null, REV integer not null, REVTYPE tinyint, SUBTITLE1 varchar(100), SUBTITLE2 varchar(100), TITLE varchar(100), primary key (SERIESREFNUM, REV));
create table docsources_audit.tblSynExtracts_AUD (SynExtrID integer not null, REV integer not null, REVTYPE tinyint, DATECREATED datetime, DOCEXTRACT LONGTEXT, LASTUPDATE datetime, SYNOPSIS LONGTEXT, ENTRYID integer, primary key (SynExtrID, REV));
create table docsources_audit.tblTitleOccsList_AUD (TITLEOCCID integer not null, REV integer not null, REVTYPE tinyint, TITLEOCC varchar(255), TITLEVARIANTS LONGTEXT, ROLECATMINORID integer, primary key (TITLEOCCID, REV));
create table docsources_audit.tblTopicsList_AUD (TOPICID integer not null, REV integer not null, REVTYPE tinyint, DESCRIPTION LONGTEXT, TOPICTITLE varchar(50), primary key (TOPICID, REV));
create table docsources_audit.tblVolumes_AUD (SUMMARYID integer not null, REV integer not null, REVTYPE tinyint, BOUND TINYINT default '-1', CCONDITION LONGTEXT, CCONTEXT LONGTEXT, CIPHER TINYINT default '-1', CIPHERNOTES varchar(255), DATECREATED datetime, DATENOTES LONGTEXT, ENDDAY TINYINT, ENDMONTH varchar(50), ENDMONTHNUM integer, ENDYEAR integer, ENGLISH TINYINT default '-1', FOLIOCOUNT varchar(50), FOLSNUMBRD TINYINT default '-1', FRENCH TINYINT default '-1', GERMAN TINYINT default '-1', ITALIAN TINYINT default '-1', LATIN TINYINT default '-1', OLDALPHAINDEX TINYINT default '-1', ORGNOTES LONGTEXT, OTHERLANG varchar(50), PRINTEDDRAWINGS TINYINT default '-1', PRINTEDMATERIAL TINYINT default '-1', RECIPS LONGTEXT, RESID varchar(255), SENDERS LONGTEXT, SPANISH TINYINT default '-1', STAFFMEMO LONGTEXT, STARTDAY TINYINT, STARTMONTH varchar(50), STARTMONTHNUM integer, STARTYEAR integer, STATBOX varchar(50), VOLLETEXT varchar(1), VOLNUM integer, VOLTOBEVETTED TINYINT default '-1', VOLTOBEVETTEDDATE datetime, VOLVETBEGINS datetime, VOLVETID varchar(50), VOLVETTED TINYINT default '-1', VOLVETTEDDATE datetime, SERIESREFNUM integer, primary key (SUMMARYID, REV));

CREATE TABLE docsources.persistent_logins (
	`series` VARCHAR(255) NOT NULL,
	`last_used` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`token` VARCHAR(255) NOT NULL,
	`username` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`series`)
) ENGINE=InnoDB;

CREATE TABLE docsources.tblAccessLog (
	`idAccessLog` INT(11) NOT NULL AUTO_INCREMENT,
	`action` VARCHAR(1000) NOT NULL,
	`username` VARCHAR(50) NOT NULL,
	`authorities` VARCHAR(50) NULL DEFAULT NULL,
	`dateAndTime` DATETIME NOT NULL,
	`executionTime` BIGINT(20) NOT NULL,
	`httpMethod` VARCHAR(8) NOT NULL,
	`informations` VARCHAR(3000) NULL DEFAULT NULL,
	`ipAddress` VARCHAR(50) NOT NULL,
	`errors` VARCHAR(3000) NULL DEFAULT NULL,
	PRIMARY KEY (`idAccessLog`)
) ENGINE=InnoDB AUTO_INCREMENT=59052;

CREATE TABLE docsources.tblActivationUser (
	`Uuid` VARCHAR(50) NOT NULL,
	`account` VARCHAR(30) NOT NULL,
	`activationDate` DATETIME NULL DEFAULT NULL,
	`activationIPAddress` VARCHAR(50) NULL DEFAULT NULL,
	`active` BIT(1) NOT NULL,
	`mailSended` BIT(1) NOT NULL,
	`mailSendedDate` DATETIME NULL DEFAULT NULL,
	`requestDate` DATETIME NOT NULL,
	`requestIPAddress` VARCHAR(50) NOT NULL,
	`ACTIOVATIONIPADDRESS` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`Uuid`)
) ENGINE=InnoDB;

CREATE TABLE docsources.tblCountries (
	`CODE` VARCHAR(2) NOT NULL,
	`NAME` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`CODE`)
) ENGINE=InnoDB;

CREATE TABLE docsources.tblImages (
	`imageId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`volNum` INT(10) UNSIGNED NOT NULL,
	`volLetExt` VARCHAR(1) NULL DEFAULT NULL,
	`imageName` VARCHAR(45) NOT NULL,
	`imageType` VARCHAR(1) NOT NULL,
	`imageRectoVerso` VARCHAR(1) NOT NULL,
	`missedNumbering` VARCHAR(3) NULL DEFAULT NULL,
	`imageOrder` INT(5) NULL DEFAULT NULL,
	`imageProgTypeNum` INT(5) NOT NULL,
	`storagePath` INT(11) NOT NULL,
	`dateCreated` DATETIME NOT NULL,
	PRIMARY KEY (`imageId`)
) ENGINE=InnoDB AUTO_INCREMENT=58033;

CREATE TABLE `tblParents` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`parentId` INT(10) NOT NULL,
	`childId` INT(10) NOT NULL,
	`dateCreated` DATETIME NULL DEFAULT NULL,
	`lastUpdate` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK7A67536B501303B1` (`parentId`),
	INDEX `FK7A67536B36829BA3` (`childId`)
) ENGINE=InnoDB AUTO_INCREMENT=26022;

CREATE TABLE docsources.tblPlaceType (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`description` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=437;

ALTER TABLE `docsources`.`persistent_logins` ENGINE = InnoDB ;
ALTER TABLE `docsources`.`tblAccessLog` ENGINE = InnoDB ;
ALTER TABLE `docsources`.`tblPRCLink`RENAME TO `docsources`.`tblPrcLink`;

ALTER TABLE `tblEPLTOLink` CHANGE COLUMN `EPLTOID#` `EPLTOID` INT(10) NOT NULL AUTO_INCREMENT FIRST;
-- ALTER TABLE `docsources`.`tblEPLTOLink` DROP COLUMN `EPLTOID`;

-- MYSQL DATABASE STRUCTURE NORMALIZATION PATCH

-- DOCUMENTS
-- Doc Typology
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `DOCTYPOLOGY` VARCHAR(250) AFTER `GRAPHIC`;
-- Folio transcribes Num
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `TRANSCRIBEFOLIONUM` INT(10) AFTER `FOLIOMOD`;
-- Folio transcribes Mod
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `TRANSCRIBEFOLIOMOD` VARCHAR(15) AFTER `TRANSCRIBEFOLIONUM`;
-- Change doc date fields order
ALTER TABLE `docsources`.`tblDocuments` CHANGE COLUMN `DOCYEAR` `DOCYEAR` INT(10) NULL DEFAULT NULL  AFTER `UNPAGED` , CHANGE COLUMN `DOCMONTHNUM` `DOCMONTHNUM` INT(10) NULL DEFAULT NULL  AFTER `DOCYEAR` , CHANGE COLUMN `DOCDAY` `DOCDAY` INT(10) NULL DEFAULT NULL  AFTER `DOCMONTHNUM` ;
-- Doc Date for Lucene indexing 
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `DOCDATE` INT(11) AFTER `DOCDAY`;
-- Year Modern : type is int (4)
ALTER TABLE `docsources`.`tblDocuments` CHANGE COLUMN `YEARMODERN` `YEARMODERN` INT(4) NULL DEFAULT NULL  ;
-- Sortable date : type size decreased from 50 to 10
ALTER TABLE `docsources`.`tblDocuments` CHANGE COLUMN `SORTABLEDATE` `SORTABLEDATE` VARCHAR(10) NULL DEFAULT NULL  ;
-- Logical delete
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `LOGICALDELETE` TINYINT(4) NOT NULL DEFAULT '0' AFTER `DOCTYPOLOGY`;

-- PEOPLE
-- Change born date fields and death date fields order
ALTER TABLE `docsources`.`tblPeople` CHANGE COLUMN `BYEAR` `BYEAR` DOUBLE NULL DEFAULT NULL  AFTER `ACTIVEEND` , CHANGE COLUMN `DYEAR` `DYEAR` DOUBLE NULL DEFAULT NULL  AFTER `BPLACE` ;
-- Born Date for Lucene indexing 
ALTER TABLE `docsources`.`tblPeople` ADD COLUMN `BORNDATE` INT(11) AFTER `BDAY`;
-- Death Date for Lucene indexing 
ALTER TABLE `docsources`.`tblPeople` ADD COLUMN `DEATHDATE` INT(11) AFTER `DDAY`;
-- Logical delete
ALTER TABLE `docsources`.`tblPeople` ADD COLUMN `LOGICALDELETE` TINYINT(4) NOT NULL DEFAULT '0' AFTER `LastUpdate`;

-- PLACES
-- GEOGKEY_CHILDREN
ALTER TABLE `docsources`.`tblPlaces` ADD COLUMN `GEOGKEY_CHILDREN` LONGTEXT NULL  AFTER `OTHER_FLAGS`;
-- GEO_ID_ENCODING
ALTER TABLE `docsources`.`tblPlaces` ADD COLUMN `GEO_ID_ENCODING` VARCHAR(10) NULL DEFAULT NULL AFTER `GEOGKEY_CHILDREN`;
-- Logical delete
ALTER TABLE `docsources`.`tblPlaces` ADD COLUMN `LOGICALDELETE` TINYINT(4) NOT NULL DEFAULT '0' AFTER `GEO_ID_ENCODING`;

-- VOLUMES
-- Start Date for Lucene indexing 
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `STARTDATE` INT(11) AFTER `STARTDAY`;
-- End Date for Lucene indexing 
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `ENDDATE` INT(11) AFTER `ENDDAY`;
-- Context to CContext (context is a reserved key on Mysql)
ALTER TABLE `docsources`.`tblVolumes` CHANGE COLUMN `CONTEXT` `CCONTEXT` LONGTEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL, CHANGE COLUMN `CONDITION` `CCONDITION` LONGTEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
-- Printed Drawings
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `PRINTEDDRAWINGS` TINYINT(1) AFTER `STAFFMEMO`;
-- Printed Material
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `PRINTEDMATERIAL` TINYINT(1) AFTER `PRINTEDDRAWINGS`;
-- Digitized column.
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `DIGITIZED` TINYINT(4) NOT NULL DEFAULT '0' AFTER `PRINTEDMATERIAL`;
-- Logical delete.
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `LOGICALDELETE` TINYINT(4) NOT NULL DEFAULT '0' AFTER `DIGITIZED`;


-- MYSQL DATABASE DATA NORMALIZATION PATCH

-- DOCUMENTS :
-- docMonthNum 0 or 13 must be setted to null  (Foreign Keys Checks)
update docsources.tblDocuments set docMonthNum = null where docMonthNum = 0;
update docsources.tblDocuments set docMonthNum = null where docMonthNum = 13;
-- Sender people linked to invalid people (Foreign Keys Checks)
update docsources.tblDocuments set sendID = null where sendID not in (select personId from tblPeople);
-- Recipient people linked to invalid people (Foreign Keys Checks)
update docsources.tblDocuments set recipID = null where recipID not in (select personId from tblPeople);
-- Sender place linked to invalid place (Foreign Keys Checks)
update docsources.tblDocuments set sendLocplall = null where sendLocplall not in (select placeAllId from tblPlaces);
-- Recipient place linked to invalid place (Foreign Keys Checks)
update docsources.tblDocuments set recipLocplall = null where recipLocplall not in (select placeAllId from tblPlaces);
-- FolioMod must be setted to null instead of empty string
update docsources.tblDocuments set folioMod=null where folioMod='';

-- IMAGES
-- Folio type : this update sets the correct type by imageName field (example from filza n.7 : '0536_C_333_R.tif')
update docsources.tblImages set imageType = substr(imageName, 6,1);
-- Recto Verso : 
update docsources.tblImages set imageRectoVerso = substring(SUBSTRING_INDEX(imageName, '_', -1),1,1); 

-- PARENTS
-- Creating father records
insert into docsources.tblParents select null, fatherId, personId, dateCreated, now() from tblPeople where fatherId is not null;
-- Creating mother records
insert into docsources.tblParents select null, motherId, personId, now(), now() from tblPeople where motherId is not null;

-- PEOPLE
-- Gender types patch to allow correct use of numeration type Gender
update docsources.tblPeople set gender = 'M' where gender = 'm';
update docsources.tblPeople set gender = 'F' where gender = 'f';
update docsources.tblPeople set gender = 'X' where gender = 'x';
update docsources.tblPeople set gender = null where gender = '';
-- Born place linked to invalid place
update docsources.tblPeople set bPlaceId = null where (bPlaceId not in (select placeAllId from tblPlaces));
-- Death place linked to invalid place
update docsources.tblPeople set dPlaceId = null where (dPlaceId not in (select placeAllId from tblPlaces));

-- Marriages linked to invalid people
delete from docsources.tblMarriages where (husbandId not in (select personId from tblPeople) ) or ( wifeId not in (select personId from tblPeople));
-- PrcLink linked to invalid RoleCat
delete from docsources.tblPrcLink where RoleCatId not in (select roleCatID from tblRoleCats);
-- Birth month num 0 or 13 must be setted to null
update docsources.tblPeople set bMonthNum = null where bMonthNum = 0;
update docsources.tblPeople set bMonthNum = null where bMonthNum = 13;
-- Death month num 0 or 13 must be setted to null
update docsources.tblPeople set dMonthNum = null where dMonthNum = 0;
update docsources.tblPeople set dMonthNum = null where dMonthNum = 13;
-- lastUpdate cannot be null, we update this to dateCreated
update docsources.tblPeople set lastUpdate = dateCreated where lastUpdate is null;

-- linked alternative names is an enumeration with first letter in upper case
update docsources.tblAltNames set nameType = 'Appellative' where lower(nameType) = 'appellative';
update docsources.tblAltNames set nameType = 'Family' where lower(nameType) = 'family';
update docsources.tblAltNames set nameType = 'Given' where lower(nameType) = 'given';
update docsources.tblAltNames set nameType = 'Maiden' where lower(nameType) = 'maiden';
update docsources.tblAltNames set nameType = 'Married' where lower(nameType) = 'married';
update docsources.tblAltNames set nameType = 'Patronymic' where lower(nameType) = 'patronymic';
update docsources.tblAltNames set nameType = 'SearchName' where lower(nameType) = 'searchname';

-- Preferred Role must be a boolean column (tinyint)
update docsources.tblPOLink set prtag = 1 where prtag =-1;
-- MonthNum in linked TitleOrOccupation must be null if not present
update docsources.tblPOLink set startMonthNum = null where startMonthNum = 0;
update docsources.tblPOLink set endMonthNum = null where endMonthNum = 0;
-- Set startMonthNum from the startMonth field
update docsources.tblPOLink set startMonthNum = null where startMonth is null;
update docsources.tblPOLink set startMonthNum = 1 where startMonth like 'January';
update docsources.tblPOLink set startMonthNum = 2 where startMonth like 'February';
update docsources.tblPOLink set startMonthNum = 3 where startMonth like 'March';
update docsources.tblPOLink set startMonthNum = 4 where startMonth like 'April';
update docsources.tblPOLink set startMonthNum = 5 where startMonth like 'May';
update docsources.tblPOLink set startMonthNum = 6 where startMonth like 'June';
update docsources.tblPOLink set startMonthNum = 7 where startMonth like 'July';
update docsources.tblPOLink set startMonthNum = 8 where startMonth like 'August';
update docsources.tblPOLink set startMonthNum = 9 where startMonth like 'September';
update docsources.tblPOLink set startMonthNum = 10 where startMonth like 'October';
update docsources.tblPOLink set startMonthNum = 11 where startMonth like 'November';
update docsources.tblPOLink set startMonthNum = 12 where startMonth like 'December';
-- Set endMonthNum from the endMonth field
update docsources.tblPOLink set endMonthNum = null where endMonth is null;
update docsources.tblPOLink set endMonthNum = 1 where endMonth like 'January';
update docsources.tblPOLink set endMonthNum = 2 where endMonth like 'February';
update docsources.tblPOLink set endMonthNum = 3 where endMonth like 'March';
update docsources.tblPOLink set endMonthNum = 4 where endMonth like 'April';
update docsources.tblPOLink set endMonthNum = 5 where endMonth like 'May';
update docsources.tblPOLink set endMonthNum = 6 where endMonth like 'June';
update docsources.tblPOLink set endMonthNum = 7 where endMonth like 'July';
update docsources.tblPOLink set endMonthNum = 8 where endMonth like 'August';
update docsources.tblPOLink set endMonthNum = 9 where endMonth like 'September';
update docsources.tblPOLink set endMonthNum = 10 where endMonth like 'October';
update docsources.tblPOLink set endMonthNum = 11 where endMonth like 'November';
update docsources.tblPOLink set endMonthNum = 12 where endMonth like 'December';

-- PLACES
-- added new field to manage creation date
update docsources.tblPlaces set dateCreated = dateEntered where dateCreated is null;
-- added new field to manage last update
update docsources.tblPlaces set lastUpdate = dateEntered where lastUpdate is null;

-- VOLUMES 
-- start Document Month num 0 or 13 must be setted to null
update docsources.tblVolumes set startMonthNum = null where startMonthNum = 0;
update docsources.tblVolumes set startMonthNum = null where startMonthNum = 13;
-- end Document Month num 0 or 13 must be setted to null
update docsources.tblVolumes set endMonthNum = null where endMonthNum = 0;
update docsources.tblVolumes set endMonthNum = null where endMonthNum = 13;

-- PlaceType
delete from docsources.tblPlaceType;
insert into docsources.tblPlaceType (description) select distinct(plType) from docsources.tblPlaces order by 1 asc;

-- Lucene Indexing (START) : Integer Date Fields Population  
-- Document docDate
update docsources.tblDocuments set docDate=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where docYear is not null and docMonthNum is not null and docDay is not null;
update docsources.tblDocuments set docDate=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'01') where docYear is not null and docMonthNum is not null and docDay is null;
update docsources.tblDocuments set docDate=concat(docYear,'01',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where docYear is not null and docMonthNum is null and docDay is not null;
update docsources.tblDocuments set docDate=concat(docYear,'01','01') where docYear is not null and docMonthNum is null and docDay is null;
update docsources.tblDocuments set docDate=concat('01',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where docYear is null and docMonthNum is not null and docDay is not null;
update docsources.tblDocuments set docDate=concat('01',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'01') where docYear is null and docMonthNum is not null and docDay is null;
update docsources.tblDocuments set docDate=concat('01','01',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where docYear is null and docMonthNum is null and docDay is not null;
update docsources.tblDocuments set docDate=concat('01','01','01') where docYear is null and docMonthNum is null and docDay is null;

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

-- People bornDate
update docsources.tblPeople set bornDate=concat(bYear,SUBSTRING(concat('0',bMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',bDay) FROM -2 FOR 2)) where bYear is not null and bMonthNum is not null and bDay is not null;
update docsources.tblPeople set bornDate=concat(bYear,SUBSTRING(concat('0',bMonthNum) FROM -2 FOR 2),'01') where bYear is not null and bMonthNum is not null and bDay is null;
update docsources.tblPeople set bornDate=concat(bYear,'01',SUBSTRING(concat('0',bDay) FROM -2 FOR 2)) where bYear is not null and bMonthNum is null and bDay is not null;
update docsources.tblPeople set bornDate=concat(bYear,'01','01') where bYear is not null and bMonthNum is null and bDay is null;
update docsources.tblPeople set bornDate=concat('01',SUBSTRING(concat('0',bMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',bDay) FROM -2 FOR 2)) where bYear is null and bMonthNum is not null and bDay is not null;
update docsources.tblPeople set bornDate=concat('01',SUBSTRING(concat('0',bMonthNum) FROM -2 FOR 2),'01') where bYear is null and bMonthNum is not null and bDay is null;
update docsources.tblPeople set bornDate=concat('01','01',SUBSTRING(concat('0',bDay) FROM -2 FOR 2)) where bYear is null and bMonthNum is null and bDay is not null;
update docsources.tblPeople set bornDate=concat('01','01','01') where bYear is null and bMonthNum is null and bDay is null;

-- People deathDate
update docsources.tblPeople set deathDate=concat(dYear,SUBSTRING(concat('0',dMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',dDay) FROM -2 FOR 2)) where dYear is not null and dMonthNum is not null and dDay is not null;
update docsources.tblPeople set deathDate=concat(dYear,SUBSTRING(concat('0',dMonthNum) FROM -2 FOR 2),'01') where dYear is not null and dMonthNum is not null and dDay is null;
update docsources.tblPeople set deathDate=concat(dYear,'01',SUBSTRING(concat('0',dDay) FROM -2 FOR 2)) where dYear is not null and dMonthNum is null and dDay is not null;
update docsources.tblPeople set deathDate=concat(dYear,'01','01') where dYear is not null and dMonthNum is null and dDay is null;
update docsources.tblPeople set deathDate=concat('01',SUBSTRING(concat('0',dMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',dDay) FROM -2 FOR 2)) where dYear is null and dMonthNum is not null and dDay is not null;
update docsources.tblPeople set deathDate=concat('01',SUBSTRING(concat('0',dMonthNum) FROM -2 FOR 2),'01') where dYear is null and dMonthNum is not null and dDay is null;
update docsources.tblPeople set deathDate=concat('01','01',SUBSTRING(concat('0',dDay) FROM -2 FOR 2)) where dYear is null and dMonthNum is null and dDay is not null;
update docsources.tblPeople set deathDate=concat('01','01','01') where dYear is null and dMonthNum is null and dDay is null;

-- Volume startDate
update docsources.tblVolumes set startDate=concat(startYear,SUBSTRING(concat('0',startMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',startDay) FROM -2 FOR 2)) where startYear is not null and startMonthNum is not null and startDay is not null;
update docsources.tblVolumes set startDate=concat(startYear,SUBSTRING(concat('0',startMonthNum) FROM -2 FOR 2),'01') where startYear is not null and startMonthNum is not null and startDay is null;
update docsources.tblVolumes set startDate=concat(startYear,'01',SUBSTRING(concat('0',startDay) FROM -2 FOR 2)) where startYear is not null and startMonthNum is null and startDay is not null;
update docsources.tblVolumes set startDate=concat(startYear,'01','01') where startYear is not null and startMonthNum is null and startDay is null;
update docsources.tblVolumes set startDate=concat('01',SUBSTRING(concat('0',startMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',startDay) FROM -2 FOR 2)) where startYear is null and startMonthNum is not null and startDay is not null;
update docsources.tblVolumes set startDate=concat('01',SUBSTRING(concat('0',startMonthNum) FROM -2 FOR 2),'01') where startYear is null and startMonthNum is not null and startDay is null;
update docsources.tblVolumes set startDate=concat('01','01',SUBSTRING(concat('0',startDay) FROM -2 FOR 2)) where startYear is null and startMonthNum is null and startDay is not null;
update docsources.tblVolumes set startDate=concat('01','01','01') where startYear is null and startMonthNum is null and startDay is null;

-- Volume endDate
update docsources.tblVolumes set endDate=concat(endYear,SUBSTRING(concat('0',endMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',endDay) FROM -2 FOR 2)) where endYear is not null and endMonthNum is not null and endDay is not null;
update docsources.tblVolumes set endDate=concat(endYear,SUBSTRING(concat('0',endMonthNum) FROM -2 FOR 2),'01') where endYear is not null and endMonthNum is not null and endDay is null;
update docsources.tblVolumes set endDate=concat(endYear,'01',SUBSTRING(concat('0',endDay) FROM -2 FOR 2)) where endYear is not null and endMonthNum is null and endDay is not null;
update docsources.tblVolumes set endDate=concat(endYear,'01','01') where endYear is not null and endMonthNum is null and endDay is null;
update docsources.tblVolumes set endDate=concat('01',SUBSTRING(concat('0',endMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',endDay) FROM -2 FOR 2)) where endYear is null and endMonthNum is not null and endDay is not null;
update docsources.tblVolumes set endDate=concat('01',SUBSTRING(concat('0',endMonthNum) FROM -2 FOR 2),'01') where endYear is null and endMonthNum is not null and endDay is null;
update docsources.tblVolumes set endDate=concat('01','01',SUBSTRING(concat('0',endDay) FROM -2 FOR 2)) where endYear is null and endMonthNum is null and endDay is not null;
update docsources.tblVolumes set endDate=concat('01','01','01') where endYear is null and endMonthNum is null and endDay is null;
-- Lucene Indexing (END) : Integer Date Fields Population  

-- Logical delete (New boolean field to manage logical delete)
update docsources.tblDocuments set logicalDelete=0;
update docsources.tblPeople set logicalDelete=0;
update docsources.tblPlaces set logicalDelete=0;
update docsources.tblVolumes set logicalDelete=0;

-- Set Digitized information on volumes to false
update docsources.tblVolumes set digitized=0;
-- These 2 updates will set rights digitized. MySQL does not allow to UPDATE or DELETE a table's data if you're simultaneously reading that same data with a subquery, so we use a temporary c1 view 
update docsources.tblVolumes set digitized=true where summaryId in (select summaryId from (select distinct(summaryId) from tblImages a, tblVolumes b where a.volNum = b.volnum and a.volLetExt is null) as c1);
update docsources.tblVolumes set digitized=true where summaryId in (select summaryId from (select distinct(summaryId) from tblImages a, tblVolumes b where a.volNum = b.volnum and a.volLetExt = b.volLetExt) as c1);

-- Adjusting missedNumbering in table Images
update docsources.tblImages set missedNumbering = substr(imageName,12,3) where length(imageName)>16;

-- Adjusting active fields in People to permit use of projection with Hibernate-Search
update docsources.tblPeople set activeStart = null where activeStart = '';
update docsources.tblPeople set activeEnd = null where activeEnd = '';

-- COUNTRY DATA ENTRY (Table schema is based on ISO standard 3166 code lists http://www.iso.org/iso/list-en1-semic-3.txt)
delete from docsources.tblCountries;
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('AFGHANISTAN', 'AF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ALAND ISLANDS', 'AX');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ALBANIA', 'AL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ALGERIA', 'DZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('AMERICAN SAMOA', 'AS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ANDORRA', 'AD');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ANGOLA', 'AO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ANGUILLA', 'AI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ANTARCTICA', 'AQ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ANTIGUA AND BARBUDA', 'AG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ARGENTINA', 'AR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ARMENIA', 'AM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ARUBA', 'AW');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('AUSTRALIA', 'AU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('AUSTRIA', 'AT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('AZERBAIJAN', 'AZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BAHAMAS', 'BS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BAHRAIN', 'BH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BANGLADESH', 'BD');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BARBADOS', 'BB');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BELARUS', 'BY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BELGIUM', 'BE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BELIZE', 'BZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BENIN', 'BJ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BERMUDA', 'BM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BHUTAN', 'BT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BOLIVIA, PLURINATIONAL STATE OF', 'BO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BOSNIA AND HERZEGOVINA', 'BA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BOTSWANA', 'BW');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BOUVET ISLAND', 'BV');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BRAZIL', 'BR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BRITISH INDIAN OCEAN TERRITORY', 'IO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BRUNEI DARUSSALAM', 'BN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BULGARIA', 'BG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BURKINA FASO', 'BF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('BURUNDI', 'BI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CAMBODIA', 'KH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CAMEROON', 'CM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CANADA', 'CA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CAPE VERDE', 'CV');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CAYMAN ISLANDS', 'KY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CENTRAL AFRICAN REPUBLIC', 'CF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CHAD', 'TD');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CHILE', 'CL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CHINA', 'CN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CHRISTMAS ISLAND', 'CX');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('COCOS (KEELING) ISLANDS', 'CC');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('COLOMBIA', 'CO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('COMOROS', 'KM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CONGO', 'CG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CONGO, THE DEMOCRATIC REPUBLIC OF THE', 'CD');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('COOK ISLANDS', 'CK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('COSTA RICA', 'CR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('C�TE D''IVOIRE', 'CI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CROATIA', 'HR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CUBA', 'CU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CYPRUS', 'CY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('CZECH REPUBLIC', 'CZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('DENMARK', 'DK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('DJIBOUTI', 'DJ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('DOMINICA', 'DM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('DOMINICAN REPUBLIC', 'DO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ECUADOR', 'EC');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('EGYPT', 'EG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('EL SALVADOR', 'SV');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('EQUATORIAL GUINEA', 'GQ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ERITREA', 'ER');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ESTONIA', 'EE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ETHIOPIA', 'ET');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('FALKLAND ISLANDS (MALVINAS)', 'FK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('FAROE ISLANDS', 'FO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('FIJI', 'FJ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('FINLAND', 'FI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('FRANCE', 'FR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('FRENCH GUIANA', 'GF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('FRENCH POLYNESIA', 'PF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('FRENCH SOUTHERN TERRITORIES', 'TF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GABON', 'GA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GAMBIA', 'GM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GEORGIA', 'GE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GERMANY', 'DE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GHANA', 'GH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GIBRALTAR', 'GI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GREECE', 'GR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GREENLAND', 'GL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GRENADA', 'GD');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GUADELOUPE', 'GP');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GUAM', 'GU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GUATEMALA', 'GT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GUERNSEY', 'GG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GUINEA', 'GN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GUINEA-BISSAU', 'GW');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('GUYANA', 'GY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('HAITI', 'HT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('HEARD ISLAND AND MCDONALD ISLANDS', 'HM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('HOLY SEE (VATICAN CITY STATE)', 'VA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('HONDURAS', 'HN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('HONG KONG', 'HK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('HUNGARY', 'HU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ICELAND', 'IS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('INDIA', 'IN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('INDONESIA', 'ID');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('IRAN, ISLAMIC REPUBLIC OF', 'IR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('IRAQ', 'IQ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('IRELAND', 'IE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ISLE OF MAN', 'IM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ISRAEL', 'IL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ITALY', 'IT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('JAMAICA', 'JM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('JAPAN', 'JP');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('JERSEY', 'JE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('JORDAN', 'JO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('KAZAKHSTAN', 'KZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('KENYA', 'KE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('KIRIBATI', 'KI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('KOREA, DEMOCRATIC PEOPLE''S REPUBLIC OF', 'KP');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('KOREA, REPUBLIC OF', 'KR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('KUWAIT', 'KW');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('KYRGYZSTAN', 'KG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LAO PEOPLE''S DEMOCRATIC REPUBLIC', 'LA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LATVIA', 'LV');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LEBANON', 'LB');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LESOTHO', 'LS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LIBERIA', 'LR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LIBYAN ARAB JAMAHIRIYA', 'LY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LIECHTENSTEIN', 'LI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LITHUANIA', 'LT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('LUXEMBOURG', 'LU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MACAO', 'MO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF', 'MK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MADAGASCAR', 'MG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MALAWI', 'MW');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MALAYSIA', 'MY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MALDIVES', 'MV');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MALI', 'ML');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MALTA', 'MT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MARSHALL ISLANDS', 'MH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MARTINIQUE', 'MQ');

INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MAURITANIA', 'MR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MAURITIUS', 'MU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MAYOTTE', 'YT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MEXICO', 'MX');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MICRONESIA, FEDERATED STATES OF', 'FM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MOLDOVA, REPUBLIC OF', 'MD');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MONACO', 'MC');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MONGOLIA', 'MN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MONTENEGRO', 'ME');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MONTSERRAT', 'MS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MOROCCO', 'MA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MOZAMBIQUE', 'MZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('MYANMAR', 'MM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NAMIBIA', 'NA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NAURU', 'NR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NEPAL', 'NP');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NETHERLANDS', 'NL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NETHERLANDS ANTILLES', 'AN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NEW CALEDONIA', 'NC');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NEW ZEALAND', 'NZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NICARAGUA', 'NI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NIGER', 'NE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NIGERIA', 'NG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NIUE', 'NU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NORFOLK ISLAND', 'NF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NORTHERN MARIANA ISLANDS', 'MP');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('NORWAY', 'NO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('OMAN', 'OM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PAKISTAN', 'PK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PALAU', 'PW');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PALESTINIAN TERRITORY, OCCUPIED', 'PS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PANAMA', 'PA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PAPUA NEW GUINEA', 'PG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PARAGUAY', 'PY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PERU', 'PE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PHILIPPINES', 'PH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PITCAIRN', 'PN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('POLAND', 'PL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PORTUGAL', 'PT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('PUERTO RICO', 'PR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('QATAR', 'QA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('R�UNION', 'RE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ROMANIA', 'RO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('RUSSIAN FEDERATION', 'RU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('RWANDA', 'RW');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAINT BARTH�LEMY', 'BL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA', 'SH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAINT KITTS AND NEVIS', 'KN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAINT LUCIA', 'LC');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAINT MARTIN', 'MF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAINT PIERRE AND MIQUELON', 'PM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAINT VINCENT AND THE GRENADINES', 'VC');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAMOA', 'WS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAN MARINO', 'SM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAO TOME AND PRINCIPE', 'ST');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SAUDI ARABIA', 'SA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SENEGAL', 'SN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SERBIA', 'RS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SEYCHELLES', 'SC');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SIERRA LEONE', 'SL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SINGAPORE', 'SG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SLOVAKIA', 'SK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SLOVENIA', 'SI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SOLOMON ISLANDS', 'SB');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SOMALIA', 'SO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SOUTH AFRICA', 'ZA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS', 'GS');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SPAIN', 'ES');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SRI LANKA', 'LK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SUDAN', 'SD');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SURINAME', 'SR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SVALBARD AND JAN MAYEN', 'SJ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SWAZILAND', 'SZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SWEDEN', 'SE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SWITZERLAND', 'CH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('SYRIAN ARAB REPUBLIC', 'SY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TAIWAN, PROVINCE OF CHINA', 'TW');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TAJIKISTAN', 'TJ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TANZANIA, UNITED REPUBLIC OF', 'TZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('THAILAND', 'TH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TIMOR-LESTE', 'TL');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TOGO', 'TG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TOKELAU', 'TK');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TONGA', 'TO');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TRINIDAD AND TOBAGO', 'TT');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TUNISIA', 'TN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TURKEY', 'TR');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TURKMENISTAN', 'TM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TURKS AND CAICOS ISLANDS', 'TC');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('TUVALU', 'TV');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('UGANDA', 'UG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('UKRAINE', 'UA');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('UNITED ARAB EMIRATES', 'AE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('UNITED KINGDOM', 'GB');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('UNITED STATES', 'US');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('UNITED STATES MINOR OUTLYING ISLANDS', 'UM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('URUGUAY', 'UY');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('UZBEKISTAN', 'UZ');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('VANUATU', 'VU');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('VENEZUELA, BOLIVARIAN REPUBLIC OF', 'VE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('VIET NAM', 'VN');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('VIRGIN ISLANDS, BRITISH', 'VG');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('VIRGIN ISLANDS, U.S.', 'VI');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('WALLIS AND FUTUNA', 'WF');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('WESTERN SAHARA', 'EH');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('YEMEN', 'YE');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ZAMBIA', 'ZM');
INSERT INTO docsources.tblCountries (NAME, CODE) VALUES ('ZIMBABWE', 'ZW');


insert into docsources.tblAccessLog select * from docsources_2011.tblAccessLog;
insert into docsources.tblUserComment select * from docsources_2011.tblUserComment;
insert into docsources.tblUserHistory select * from docsources_2011.tblUserHistory;
insert into docsources.tblUserMessage select * from docsources_2011.tblUserMessage;

truncate table docsources.tblApplicationProperty 
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('iipimage.image.path', 'Remote server image path', '/data/tiled_mdp/');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.fcgi.path', 'IIPImage fcgi-bin path', '/fcgi-bin/iipsrv.fcgi');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.host', 'IIPImage host name', 'iipimage.medici.org');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.port', 'IIPImage listening port', '80');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.protocol', 'IIPImage supported protocl', 'http');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.version', 'Property must be filled with 0.9.8 or 0.9.9', '0.9.9');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('mail.activationUser.subject', '', 'Action Required to Activate Membership for DocSources');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('mail.activationUser.text', '', 'Dear {0},\r\n\r\nThank you for registering at the DocSources. Before we can activate your account one last step must be taken to complete your registration.\r\nPlease note - you must complete this last step to become a registered member. You will only need to visit this url once to activate your account.\r\nTo complete your registration, please visit this url:\r\n\r\n{3}://{4}/DocSources/user/ActivateUser.do?uuid={2}\r\n\r\nPlease be sure not to add extra spaces.\r\nIf you are still having problems signing up please contact a member of our support staff.\r\nYour Account is: {1}\r\nYour Activation uuid is:{2}\r\n\r\nAll the best,\r\nDocSources Support Service');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('mail.resetUserPassword.subject', '', 'Your login details for DocSources');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('mail.resetUserPassword.text', '', 'Dear {0},\r\n\r\nYou have requested to reset your password on DocSources because you have forgotten your password.\r\nTo reset your password, go to this page:\r\n\r\n{3}://{4}/DocSources/user/ResetUserPassword.do?uuid={2}\r\n\r\nPlease be sure not to add extra spaces.\r\n\r\nIf you are still having problems signing up please contact a member of our support staff.\r\nYour Username is: {1}\r\nYour Reset Password uuid is:{2}\r\n\r\nAll the best,\r\nDocSources Support Service\r\n');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('recaptcha.domainName', 'reCAPTCHA will only work on this domain and subdomains. If you have more than one domain (or a staging server), you can create a new set of keys.', 'localhost');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('recaptcha.privateKey', 'Use this when communicating between your server and our server. Be sure to keep it a secret.', '6LcA-LsSAAAAAInVIlSHKjxqKKre-40BOpb3Abcs ');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('recaptcha.publicKey', 'Use this in the JavaScript code that is served to your users', '	6LcA-LsSAAAAAKYVTuOi0KQWArgUQwPtMQFqwe_6 ');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('recaptcha.server', 'Google recaptcha remote server. http://api.recaptcha.net or https://api-secure.recaptcha.net', 'https://api-secure.recaptcha.net');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('recaptcha.siteId', 'Site identifier', '314849700');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('user.expiration.password.months', 'Number of months to calculate user password from last change password', '6');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('user.expiration.user.months', 'Number of months to calculate user expiration date from registration date', '6');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('user.maxBadLogin', 'How many bad login user can have before user lock', '5');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('website.domain', '', 'bia.medici.org');
INSERT INTO docsources.tblApplicationProperty (id, help, value) VALUES ('website.protocol', '', 'http');


INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (1, NULL, 'CATEGORY', 'Board Index', 'Board Index forum', '2012-03-02 08:46:47', '2012-06-02 08:47:00', 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (2, 1, 'CATEGORY', 'Scholarly discussion forums', 'Scholarly discussion forums', '2012-04-16 08:13:07', '2011-04-16 08:13:07', 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (3, 1, 'CATEGORY', 'Learning Forums', 'Learning Forums', '2012-04-16 08:12:07', '2012-04-16 11:13:07', 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (4, 1, 'CATEGORY', 'Technical Forums', 'Technical Forums', '2012-04-17 08:13:07', '2012-04-20 21:13:07', 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (5, 2, 'FORUM', 'Documents', 'Documents forum', '2012-04-22 18:41:29', '2012-04-22 18:41:29', 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (6, 2, 'FORUM', 'People', 'People forum', '2012-04-23 08:42:05', '2012-04-23 08:42:05', 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (7, 2, 'FORUM', 'Places', 'Places forum', '2012-04-23 18:27:05', '2012-04-23 18:27:05', 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (8, 2, 'FORUM', 'Volumes', 'Volumes forum', '2012-05-26 08:43:59', '2012-05-26 08:44:01', 0, 1, 0, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (9, 3, 'FORUM', 'General Questions', 'General questions forum', '2012-03-26 11:00:01', '2012-03-26 14:54:01', 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (10, 3, 'FORUM', 'Paleography', 'Paleography forum', '2012-03-26 10:12:01', '2012-03-26 13:11:32', 0, 1, 0, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (11, 4, 'FORUM', 'How to use BIA', 'How to use BIA forum', '2012-03-27 14:44:01', '2012-05-27 14:44:01', 0, 1, 0, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO docsources.tblForum (id, forumParent, type, title, description, dateCreated, lastUpdate, postsNumber, status, topicsNumber, lastPost, dispositionOrder, entryId, peopleId, placeAllId, summaryId) VALUES (12, 4, 'FORUM', 'Problems with BIA', 'Problems with BIA forum', '2012-04-10 22:44:01', '2012-05-10 12:44:01', 0, 1, 0, NULL, 1, NULL, NULL, NULL, NULL);
