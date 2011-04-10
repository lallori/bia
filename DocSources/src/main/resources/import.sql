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


-- MYSQL DATABASE STRUCTURE NORMALIZATION PATCH

-- DOCUMENTS
-- Doc Typology
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `DOCTYPOLOGY` VARCHAR(250) AFTER `GRAPHIC`;
-- Folio transcribes Num
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `TRANSCRIBEFOLIONUM` INT(10) AFTER `FOLIOMOD`;
-- Folio transcribes Mod
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `TRANSCRIBEFOLIOMOD` VARCHAR(15) AFTER `TRANSCRIBEFOLIONUM`;

-- IMAGES
-- ImageType
ALTER TABLE `docsources`.`tblImages` ADD COLUMN `imageType` VARCHAR(1) NOT NULL  AFTER `imageName` ;
-- ImageOrder
ALTER TABLE `docsources`.`tblImages` ADD COLUMN `imageOrder` INT(5) NOT NULL  AFTER `imageType` ;
-- ImageProgTypeNum
ALTER TABLE `docsources`.`tblImages` ADD COLUMN `imageProgTypeNum` INT(5) NOT NULL  AFTER `imageOrder` ;

-- VOLUMES
-- Context to CContext (context is a reserved key on Mysql)
ALTER TABLE `docsources`.`tblVolumes` CHANGE COLUMN `CONTEXT` `CCONTEXT` LONGTEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL, CHANGE COLUMN `CONDITION` `CCONDITION` LONGTEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
-- Printed Drawings
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN PRINTEDDRAWINGS TINYINT(1) AFTER `STAFFMEMO`;
-- Printed Material
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN PRINTEDMATERIAL TINYINT(1) AFTER `PRINTEDDRAWINGS`;


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


-- IMAGES

-- Folio type : this update sets the correct type by imageName field (example from filza n.7 : '0536_C_333_R.tif')
update docsources.tblImages set imageType = substr(imageName, 6,1);
-- Recto Verso : 
update docsources.tblImages set imageRectoVerso = substring(SUBSTRING_INDEX(imageName, '_', -1),1,1); 


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
-- linked alternative names is an enumeration with first letter in upper case
update docsources.tblAltNames set nameType = 'Appellative' where lower(nameType) = 'appellative';
update docsources.tblAltNames set nameType = 'Family' where lower(nameType) = 'family';
update docsources.tblAltNames set nameType = 'Given' where lower(nameType) = 'given';
update docsources.tblAltNames set nameType = 'Maiden' where lower(nameType) = 'maiden';
update docsources.tblAltNames set nameType = 'Married' where lower(nameType) = 'married';
update docsources.tblAltNames set nameType = 'Patronymic' where lower(nameType) = 'patronymic';
update docsources.tblAltNames set nameType = 'SearchName' where lower(nameType) = 'searchname';


-- VOLUMES 

-- start Document Month num 0 or 13 must be setted to null
update docsources.tblVolumes set startMonthNum = null where startMonthNum = 0;
update docsources.tblVolumes set startMonthNum = null where startMonthNum = 13;
-- end Document Month num 0 or 13 must be setted to null
update docsources.tblVolumes set endMonthNum = null where endMonthNum = 0;
update docsources.tblVolumes set endMonthNum = null where endMonthNum = 13;


-- COUNTRY DATA ENTRY (Table schema is based on ISO standard 3166 code lists http://www.iso.org/iso/list-en1-semic-3.txt)
INSERT INTO tblCountries (NAME, CODE) VALUES ('AFGHANISTAN', 'AF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ÅLAND ISLANDS', 'AX');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ALBANIA', 'AL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ALGERIA', 'DZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('AMERICAN SAMOA', 'AS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ANDORRA', 'AD');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ANGOLA', 'AO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ANGUILLA', 'AI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ANTARCTICA', 'AQ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ANTIGUA AND BARBUDA', 'AG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ARGENTINA', 'AR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ARMENIA', 'AM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ARUBA', 'AW');
INSERT INTO tblCountries (NAME, CODE) VALUES ('AUSTRALIA', 'AU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('AUSTRIA', 'AT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('AZERBAIJAN', 'AZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BAHAMAS', 'BS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BAHRAIN', 'BH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BANGLADESH', 'BD');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BARBADOS', 'BB');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BELARUS', 'BY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BELGIUM', 'BE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BELIZE', 'BZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BENIN', 'BJ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BERMUDA', 'BM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BHUTAN', 'BT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BOLIVIA, PLURINATIONAL STATE OF', 'BO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BOSNIA AND HERZEGOVINA', 'BA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BOTSWANA', 'BW');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BOUVET ISLAND', 'BV');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BRAZIL', 'BR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BRITISH INDIAN OCEAN TERRITORY', 'IO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BRUNEI DARUSSALAM', 'BN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BULGARIA', 'BG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BURKINA FASO', 'BF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('BURUNDI', 'BI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CAMBODIA', 'KH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CAMEROON', 'CM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CANADA', 'CA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CAPE VERDE', 'CV');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CAYMAN ISLANDS', 'KY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CENTRAL AFRICAN REPUBLIC', 'CF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CHAD', 'TD');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CHILE', 'CL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CHINA', 'CN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CHRISTMAS ISLAND', 'CX');
INSERT INTO tblCountries (NAME, CODE) VALUES ('COCOS (KEELING) ISLANDS', 'CC');
INSERT INTO tblCountries (NAME, CODE) VALUES ('COLOMBIA', 'CO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('COMOROS', 'KM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CONGO', 'CG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CONGO, THE DEMOCRATIC REPUBLIC OF THE', 'CD');
INSERT INTO tblCountries (NAME, CODE) VALUES ('COOK ISLANDS', 'CK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('COSTA RICA', 'CR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CÔTE D''IVOIRE', 'CI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CROATIA', 'HR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CUBA', 'CU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CYPRUS', 'CY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('CZECH REPUBLIC', 'CZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('DENMARK', 'DK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('DJIBOUTI', 'DJ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('DOMINICA', 'DM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('DOMINICAN REPUBLIC', 'DO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ECUADOR', 'EC');
INSERT INTO tblCountries (NAME, CODE) VALUES ('EGYPT', 'EG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('EL SALVADOR', 'SV');
INSERT INTO tblCountries (NAME, CODE) VALUES ('EQUATORIAL GUINEA', 'GQ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ERITREA', 'ER');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ESTONIA', 'EE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ETHIOPIA', 'ET');
INSERT INTO tblCountries (NAME, CODE) VALUES ('FALKLAND ISLANDS (MALVINAS)', 'FK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('FAROE ISLANDS', 'FO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('FIJI', 'FJ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('FINLAND', 'FI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('FRANCE', 'FR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('FRENCH GUIANA', 'GF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('FRENCH POLYNESIA', 'PF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('FRENCH SOUTHERN TERRITORIES', 'TF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GABON', 'GA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GAMBIA', 'GM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GEORGIA', 'GE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GERMANY', 'DE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GHANA', 'GH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GIBRALTAR', 'GI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GREECE', 'GR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GREENLAND', 'GL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GRENADA', 'GD');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GUADELOUPE', 'GP');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GUAM', 'GU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GUATEMALA', 'GT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GUERNSEY', 'GG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GUINEA', 'GN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GUINEA-BISSAU', 'GW');
INSERT INTO tblCountries (NAME, CODE) VALUES ('GUYANA', 'GY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('HAITI', 'HT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('HEARD ISLAND AND MCDONALD ISLANDS', 'HM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('HOLY SEE (VATICAN CITY STATE)', 'VA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('HONDURAS', 'HN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('HONG KONG', 'HK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('HUNGARY', 'HU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ICELAND', 'IS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('INDIA', 'IN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('INDONESIA', 'ID');
INSERT INTO tblCountries (NAME, CODE) VALUES ('IRAN, ISLAMIC REPUBLIC OF', 'IR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('IRAQ', 'IQ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('IRELAND', 'IE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ISLE OF MAN', 'IM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ISRAEL', 'IL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ITALY', 'IT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('JAMAICA', 'JM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('JAPAN', 'JP');
INSERT INTO tblCountries (NAME, CODE) VALUES ('JERSEY', 'JE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('JORDAN', 'JO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('KAZAKHSTAN', 'KZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('KENYA', 'KE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('KIRIBATI', 'KI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('KOREA, DEMOCRATIC PEOPLE''S REPUBLIC OF', 'KP');
INSERT INTO tblCountries (NAME, CODE) VALUES ('KOREA, REPUBLIC OF', 'KR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('KUWAIT', 'KW');
INSERT INTO tblCountries (NAME, CODE) VALUES ('KYRGYZSTAN', 'KG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LAO PEOPLE''S DEMOCRATIC REPUBLIC', 'LA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LATVIA', 'LV');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LEBANON', 'LB');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LESOTHO', 'LS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LIBERIA', 'LR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LIBYAN ARAB JAMAHIRIYA', 'LY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LIECHTENSTEIN', 'LI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LITHUANIA', 'LT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('LUXEMBOURG', 'LU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MACAO', 'MO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF', 'MK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MADAGASCAR', 'MG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MALAWI', 'MW');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MALAYSIA', 'MY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MALDIVES', 'MV');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MALI', 'ML');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MALTA', 'MT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MARSHALL ISLANDS', 'MH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MARTINIQUE', 'MQ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MAURITANIA', 'MR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MAURITIUS', 'MU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MAYOTTE', 'YT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MEXICO', 'MX');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MICRONESIA, FEDERATED STATES OF', 'FM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MOLDOVA, REPUBLIC OF', 'MD');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MONACO', 'MC');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MONGOLIA', 'MN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MONTENEGRO', 'ME');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MONTSERRAT', 'MS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MOROCCO', 'MA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MOZAMBIQUE', 'MZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('MYANMAR', 'MM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NAMIBIA', 'NA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NAURU', 'NR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NEPAL', 'NP');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NETHERLANDS', 'NL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NETHERLANDS ANTILLES', 'AN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NEW CALEDONIA', 'NC');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NEW ZEALAND', 'NZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NICARAGUA', 'NI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NIGER', 'NE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NIGERIA', 'NG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NIUE', 'NU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NORFOLK ISLAND', 'NF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NORTHERN MARIANA ISLANDS', 'MP');
INSERT INTO tblCountries (NAME, CODE) VALUES ('NORWAY', 'NO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('OMAN', 'OM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PAKISTAN', 'PK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PALAU', 'PW');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PALESTINIAN TERRITORY, OCCUPIED', 'PS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PANAMA', 'PA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PAPUA NEW GUINEA', 'PG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PARAGUAY', 'PY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PERU', 'PE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PHILIPPINES', 'PH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PITCAIRN', 'PN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('POLAND', 'PL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PORTUGAL', 'PT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('PUERTO RICO', 'PR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('QATAR', 'QA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('RÉUNION', 'RE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ROMANIA', 'RO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('RUSSIAN FEDERATION', 'RU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('RWANDA', 'RW');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAINT BARTHÉLEMY', 'BL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA', 'SH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAINT KITTS AND NEVIS', 'KN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAINT LUCIA', 'LC');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAINT MARTIN', 'MF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAINT PIERRE AND MIQUELON', 'PM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAINT VINCENT AND THE GRENADINES', 'VC');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAMOA', 'WS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAN MARINO', 'SM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAO TOME AND PRINCIPE', 'ST');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SAUDI ARABIA', 'SA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SENEGAL', 'SN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SERBIA', 'RS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SEYCHELLES', 'SC');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SIERRA LEONE', 'SL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SINGAPORE', 'SG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SLOVAKIA', 'SK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SLOVENIA', 'SI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SOLOMON ISLANDS', 'SB');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SOMALIA', 'SO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SOUTH AFRICA', 'ZA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS', 'GS');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SPAIN', 'ES');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SRI LANKA', 'LK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SUDAN', 'SD');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SURINAME', 'SR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SVALBARD AND JAN MAYEN', 'SJ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SWAZILAND', 'SZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SWEDEN', 'SE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SWITZERLAND', 'CH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('SYRIAN ARAB REPUBLIC', 'SY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TAIWAN, PROVINCE OF CHINA', 'TW');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TAJIKISTAN', 'TJ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TANZANIA, UNITED REPUBLIC OF', 'TZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('THAILAND', 'TH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TIMOR-LESTE', 'TL');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TOGO', 'TG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TOKELAU', 'TK');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TONGA', 'TO');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TRINIDAD AND TOBAGO', 'TT');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TUNISIA', 'TN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TURKEY', 'TR');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TURKMENISTAN', 'TM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TURKS AND CAICOS ISLANDS', 'TC');
INSERT INTO tblCountries (NAME, CODE) VALUES ('TUVALU', 'TV');
INSERT INTO tblCountries (NAME, CODE) VALUES ('UGANDA', 'UG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('UKRAINE', 'UA');
INSERT INTO tblCountries (NAME, CODE) VALUES ('UNITED ARAB EMIRATES', 'AE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('UNITED KINGDOM', 'GB');
INSERT INTO tblCountries (NAME, CODE) VALUES ('UNITED STATES', 'US');
INSERT INTO tblCountries (NAME, CODE) VALUES ('UNITED STATES MINOR OUTLYING ISLANDS', 'UM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('URUGUAY', 'UY');
INSERT INTO tblCountries (NAME, CODE) VALUES ('UZBEKISTAN', 'UZ');
INSERT INTO tblCountries (NAME, CODE) VALUES ('VANUATU', 'VU');
INSERT INTO tblCountries (NAME, CODE) VALUES ('VENEZUELA, BOLIVARIAN REPUBLIC OF', 'VE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('VIET NAM', 'VN');
INSERT INTO tblCountries (NAME, CODE) VALUES ('VIRGIN ISLANDS, BRITISH', 'VG');
INSERT INTO tblCountries (NAME, CODE) VALUES ('VIRGIN ISLANDS, U.S.', 'VI');
INSERT INTO tblCountries (NAME, CODE) VALUES ('WALLIS AND FUTUNA', 'WF');
INSERT INTO tblCountries (NAME, CODE) VALUES ('WESTERN SAHARA', 'EH');
INSERT INTO tblCountries (NAME, CODE) VALUES ('YEMEN', 'YE');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ZAMBIA', 'ZM');
INSERT INTO tblCountries (NAME, CODE) VALUES ('ZIMBABWE', 'ZW');
