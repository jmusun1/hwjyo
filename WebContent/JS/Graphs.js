
// Load the Visualization API and the piechart package.
var globalJSONData; //Allows for graphs to be resized easily
google.load('visualization', '1.0', {'packages':['corechart']});
function drawTOUChart(jsonData) {

	  globalJSONData = jsonData;
	  var obj = JSON.parse(jsonData);
	  var e1Total = obj.d[0].E1.total;
	  var e6Off = obj.d[0].E6.peaks.off;
	  var e6Part = obj.d[0].E6.peaks.part;
	  var e6Peak = obj.d[0].E6.peaks.peak;
	  var evOff = obj.d[0].EV.peaks.off;
	  var evPart = obj.d[0].EV.peaks.part;
	  var evPeak = obj.d[0].EV.peaks.peak;
	  
	  // Create the data table.
	  var data1 = google.visualization.arrayToDataTable([
	  ['Peaks', 'Off Peak', { type: 'string', role: 'tooltip' }, 
	   'Part Peak', { type: 'string', role: 'tooltip' }, 
	   'Peak', { type: 'string', role: 'tooltip' }, 
	   'No Tou', { type: 'string', role: 'tooltip' } ],
	  ['E1', 0, '', 0,'', 0,'', e1Total, 'E1 Total $' + e1Total],
	  ['E6', e6Off, 'E6 Off Peak $' + e6Off, e6Part,'E6 Part Peak $' + e6Part, e6Peak,'E6 Peak $' + e6Peak, 0, ''],
	  ['EV', evOff, 'EV Off Peak $' + evOff, evPart,'EV Part Peak $' + evPart, evPeak,'EV Peak $' + evPeak, 0, '']
	]);

	var options1 = {
	title: 'Average Monthly Bill (by TOU)',
	titleTextStyle: {fontSize: '16'},
	  //width: 600,
	  height: '400',
	  format: 'currency',
	  legend: { position: 'top', maxLines: 3 },
	  bar: { groupWidth: '75%' },
	  isStacked: true,
	  vAxis: {format: 'currency'},
	  colors: ['#8B88FF','#FF9C00','#7BB31A','#EEDB00']
	    };
	var chart = new google.visualization.ColumnChart(document.getElementById('chart_div_tou'));
    chart.draw(data1, options1);  
	
}
function drawTiersChart(jsonData) {

	  globalJSONData = jsonData;
	  var obj = JSON.parse(jsonData);
	  var e1T1 = obj.d[0].E1.tiers.t1;
	  var e1T2 = obj.d[0].E1.tiers.t2;
	  var e1T3 = obj.d[0].E1.tiers.t3;
	  var e1T4 = obj.d[0].E1.tiers.t4;
	  var e6T1 = obj.d[0].E6.tiers.t1;
	  var e6T2 = obj.d[0].E6.tiers.t2;
	  var e6T3 = obj.d[0].E6.tiers.t3;
	  var e6T4 = obj.d[0].E6.tiers.t4;
	  var evTotal = obj.d[0].EV.total;
	  // Create the data table.
	  var data = google.visualization.arrayToDataTable([
	  ['Tiers', 'Tier 1', { type: 'string', role: 'tooltip' },
	   'Tier 2', { type: 'string', role: 'tooltip' },
	   'Tier 3', { type: 'string', role: 'tooltip' },
	   'Tier 4', { type: 'string', role: 'tooltip' },
	   'No Tier',  { type: 'string', role: 'tooltip' } ],
	  ['E1', e1T1,'E1 Tier 1 $' + e1T1, e1T2,'E1 Tier 2 $' + e1T2, e1T3,'E1 Tier 3 $' + e1T3, e1T4,'E1 Tier 4 $' + e1T4, 0, ''],
	  ['E6', e6T1,'E6 Tier 1 $' + e6T1, e6T2,'E6 Tier 2 $' + e6T2, e6T3,'E6 Tier 3 $' + e6T3, e6T4,'E6 Tier 4 $' + e6T4, 0, ''],
	  ['EV', 0,'', 0,'', 0,'', 0,'', evTotal, 'EV Total $' + evTotal]
	]);

	var options = {
	title: 'Average Monthly Bill (by Tiers)',
	titleTextStyle: {fontSize: '16'},
	  //width: 600,
	  height: '400',
	  format: 'currency',
	  legend: { position: 'top', maxLines: 3 },
	  bar: { groupWidth: '75%' },
	      isStacked: true,
	      vAxis: {format: 'currency'},
	  colors: ['#99FFCC','#33CCFF','#0099FF','#0033CC','#ffa101']
	 };
	var chart = new google.visualization.ColumnChart(document.getElementById('chart_div_tiers'));
    chart.draw(data, options);  
}

$(window).resize(function(){
	  drawTiersChart(globalJSONData);
		drawTOUChart(globalJSONData);
	}
);
