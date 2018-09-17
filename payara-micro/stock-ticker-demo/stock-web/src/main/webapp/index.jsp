<!DOCTYPE HTML>
<!--
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) [2017] Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.

This code creates a new HTML5 based chart using the HighCharts library.

It also connects a websocket to the JavaEE WebSocket Endpoint. 

Whenever a message is received on the websocket it updates the data in the HighChart graph
to display the new data in real time.

-->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Payara Micro Stock Ticker</title>
                <link rel="stylesheet" href="css/ws-demo.css"/>
		<script type="text/javascript" src="jquery-1.8.1.js"></script>
		<script type="text/javascript">
$(document).ready(function() {
	Highcharts.setOptions({
		global: {
			useUTC: false
		}
	});

	var chart;
	document.chart = new Highcharts.Chart({
		chart: {
			renderTo: 'container',
			defaultSeriesType: 'spline',
			marginRight: 10,
                        plotBackgroundColor: '#FFFFFF',
                        plotShadow: true,
                        animation: {
                            duration: 200
                        }
		},
		title: {
			text: 'Payara Stock Price'
		},
		xAxis: {
			type: 'datetime',
			tickPixelInterval: 150
		},
		yAxis: {
			title: {
				text: 'Price ($)'
			},
			plotLines: [{
				value: 0,
				width: 2,
				color: '#808080'
			}]
		},
		tooltip: {
			formatter: function() {
					return '<b>'+ this.series.name +'</b><br/>'+
					Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>$'+
					Highcharts.numberFormat(this.y, 2);
			}
		},
		legend: {
			enabled: true
		},
		exporting: {
			enabled: false
		},
		series: [{
			name: 'PAYARA',
			data: (function() {
				// generate an array of random data
				var data = [],
					time = (new Date()).getTime(),
					i;

				for (i = -19; i <= 0; i++) {
					data.push({
						x: time + i * 1000,
						y: 0
					});
				}
				return data;
			})()
		}]
	});
});


 
		</script>
	</head>
	<body>
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/modules/exporting.js"></script>
<img src="images/payara-logo-blue.png" alt="Payara Logo" height="20%" width="20%"/>
<div>This demo shows a real time updating graph using data received over the Payara Clustered CDI Event Bus and pushed to the browser using WebSockets.</div>

<div id="container" style="width: 80%; height: 80%; margin: 0 auto;"></div>
<div>The code for this is available online in the <a href="https://github.com/payara/Payara-Examples">Payara Examples Repository.</a></div>
<script type="text/javascript">
 
var wsUri = "ws://" + location.host + "${pageContext.request.contextPath}/graph";
console.log("CONNECT CALLED");
websocket = new WebSocket(wsUri); 
websocket.onopen = function(event) { onOpen(event) }; 
websocket.onclose = function(event) { onClose(event) }; 
websocket.onerror = function(event) { onError(event) };   

websocket.onmessage = function(event) { 
   var object = JSON.parse(event.data);
   var x = (new Date()).getTime();
   var y = object.price;
    document.chart.series[0].addPoint([x,y],true,true,false);   
  }

        function onOpen(event) 
        { 
       
        }  
        
        function onError(event) 
        { 
       
        }  

        function onClose(event) 
        { 
             
        } 
</script>

	</body>
</html>
