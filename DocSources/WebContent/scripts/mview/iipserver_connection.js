/*
The ippserver by Ruven Pillay <ruven@users.sourceforge.net> configuration
*/
/*
DocSources V5 Manuscript viewer tool.
Window Edit_extract developed in Extjs

The Medici Archive Project Inc. IT team: 
Lorenzo Allori <lorenzo.allori@gmail.com>
Lorenzo Pasquinelli <lorenzo.pasquinelli@gmail.com>
Joana Amill <joana.amill@gmail.com>
*/

var server = '/fcgi-bin/iipsrv.fcgi';
var images ='/srv/www/htdocs/iipimage2/images/0250.tif';
var credit = '&copy; ASF? - to be understood later';
				
iip = new IIP( "targetframe", {
	image: images,
	server: server,
	credit: credit, 
	zoom: 2,
	render: 'random',
	showNavButtons: true
});