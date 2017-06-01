$(document).ready(function()
{
	
	 function getDoc(frame) {
	     var doc = null;
	     
	     // IE8 cascading access check
	     try {
	         if (frame.contentWindow) {
	             doc = frame.contentWindow.document;
	         }
	     } catch(err) {
	     }
	
	     if (doc) { // successful getting content
	         return doc;
	     }
	
	     try { // simply checking may throw in ie8 under ssl or mismatched protocol
	         doc = frame.contentDocument ? frame.contentDocument : frame.document;
	     } catch(err) {
	         // last attempt
	         doc = frame.document;
	     }
	     return doc;
	 }

	$("#myform").submit(function(e) {
		var formObj = $(this);
		var formURL = formObj.attr("action");
	
		if(window.FormData !== undefined) { // for HTML5 browsers
			var formData = new FormData(this);
			$.ajax({
	        	url: formURL,
		        type: 'POST',
				data:  formData,
				mimeType:"multipart/form-data",
				contentType: false,
	    	    cache: false,
	        	processData:false,
				success: function(data, textStatus, jqXHR)
			    {
						drawTOUChart(data);//in Graphs.js file
						drawTiersChart(data);//in Graphs.js file
						printBills(data);
						$("#form_alert").attr("class", "alert alert-warning");
						$("#form_alert").text("Done");
			    },
			  	error: function(jqXHR, textStatus, errorThrown) 
		    	{
			  		$("#form_alert").attr("class", "alert alert-danger");
					$("#form_alert").text("One or more inputs caused the program to fail. Please make sure file types are correctly formatted and try again.");
					//$("#multi-msg").html('<pre><code class="prettyprint">AJAX Request Failed<br/> textStatus='+textStatus+', errorThrown='+errorThrown+'</code></pre>');
		    	} 	        
		   });
	        e.preventDefault();
	        e.unbind();
	   }
	   else { //for olden browsers
			//generate a random id
			var  iframeId = 'unique' + (new Date().getTime());
	
			//create an empty iframe
			var iframe = $('<iframe src="javascript:false;" name="'+iframeId+'" />');
	
			//hide it
			iframe.hide();
	
			//set form target to iframe
			formObj.attr('target',iframeId);
	
			//Add iframe to body
			iframe.appendTo('body');
			iframe.load(function(e) {
				var doc = getDoc(iframe[0]);
				var docRoot = doc.body ? doc.body : doc.documentElement;
				var data = docRoot.innerHTML;
				$("#multi-msg").html('<pre><code>'+data+'</code></pre>');
			});
		}
	});


	$("#form-post").click(function() {
		if(validateForm()){
			//$("#form_alert").css("background-color", "white");
			$("#form_alert").attr("class", "alert alert-info");
			$("#form_alert").text("Loading...");
			$(".temp-pics").hide();
			$("#myform").submit();
		}	
	});
});//on document ready ends here

function printBills(jsonData) {
	var obj = JSON.parse(jsonData);
	var e1Total = obj.d[0].E1.total;
	var e6Total = obj.d[0].E6.total;
	var evTotal = obj.d[0].EV.total;
	
	$("#e1_bill").attr("class", "");//Fixes Color if user doesn't refresh page and runs again
	$("#e6_bill").attr("class", "");
	$("#ev_bill").attr("class", "");
	
	$("#e1_bill").html("Total E1 Bill: $" + e1Total.toFixed(2) + "<br>");
	$("#e6_bill").html("Total E6 Bill: $" + e6Total.toFixed(2) + "<br>");
	$("#ev_bill").html("Total EV Bill: $" + evTotal.toFixed(2) + "<br>");
	
	//Changes color of smallest bill to green
	if(e1Total < e6Total && e1Total < evTotal) {
		$("#e1_bill").attr("class", "text-success");
	}
	else if (e6Total < e1Total && e6Total < evTotal) {
		$("#e6_bill").attr("class", "text-success");
	}
	else {
		$("#ev_bill").attr("class", "text-success");
	}
}

function validateForm() {
	if(document.myform.heatSource.value == "") {
		$("#form_alert").text("Please select Heat Source");
		document.myform.heatSource.focus();
		return false;
	}
	if(document.myform.weekdayDistance.value == "" || isNaN(document.myform.weekdayDistance.value) || document.myform.weekdayDistance.value < 0) {
		$("#form_alert").text("Please enter valid distance for Weekday");
		document.myform.weekdayDistance.focus();
		return false;
	}
	if(document.myform.weekdayDistance.value > 1000) {
		$("#form_alert").text("Please enter a smaller weekday distance. That distance is not possible to drive in one day");
		document.myform.weekdayDistance.focus();
		return false;
	}
	if(document.myform.weekendDistance.value == "" || isNaN(document.myform.weekendDistance.value) || document.myform.weekdayDistance.value < 0) {
		$("#form_alert").text("Please enter valid distance for Weekend");
		document.myform.weekendDistance.focus();
		return false;
	}
	if(document.myform.weekendDistance.value > 1000) {
		$("#form_alert").text("Please enter a smaller weekend distance. That distance is not possible to drive in one day");
		document.myform.weekendDistance.focus();
		return false;
	}
	if(document.myform.chargeStartTime.value == "" || isNaN(document.myform.chargeStartTime.value)) {
		$("#form_alert").text("Please enter valid charging start time");
		document.myform.chargeStartTime.focus();
		return false;
	}
	if(document.myform.intervalUsage.value == "") {
		$("#form_alert").text("Please select Interval Usage File");
		document.myform.intervalUsage.focus();
		return false;
	}
	var ext = $("[name='intervalUsage']").val().split('.').pop().toLowerCase();
	if($.inArray(ext, ['xml']) == -1) {
		$("#form_alert").text("Please input an XML file with extension .xml");
		document.myform.intervalUsage.focus();
		return false;
	}
	if(document.myform.billingPeriods.value == "") {
		$("#form_alert").text("Please select Billings Periods File");
		document.myform.billingPeriods.focus();
		return false;
	}
	var ext1 = $("[name='billingPeriods']").val().split('.').pop().toLowerCase();
	if($.inArray(ext1, ['csv']) == -1) {
		$("#form_alert").text("Please input a CSV file with extension .csv");
		document.myform.billingPeriods.focus();
		return false;
	}
	return true;
}


//Creates all tooltips on page
$(document).ready(function(){
    $('#disclaimer').tooltip({title: "The rate comparisons are provided for illustrative purposes only " +
    		"and do not constitute a representation or recommendation by PG&E .  This information only depicts " +
    		"volumetric usage charges based on available interval data.  It does not include other fees such as " +
    		"local utility user taxes and other taxes, surcharges, fees, or medical baseline adjustments. As a result " +
    		"the cost comparison displayed in the chart will not reflect all of the charges on your bill." +
    		"PG&E cannot guarantee the accuracy, completeness, or usefulness of rate information or the " +
    		"estimated cost information displayed.  PG&E expressly disclaims any and all liability for any " +
    		"damages of any nature (including direct, indirect, incidental, and consequential) arising in " +
    		"connection with the use of the rate comparisons and arising in connection with the use of the " +
    		"estimated bill comparison."
    	}); 
    $("h1").tooltip({title: "This tool calculates your average monthly bill for the E1, E6, and EV rates based " +
    		"on how many miles you expect to drive and the usage data that you supply. "
    	});
   
    $("[for='baselineTerritory']").popover({title: "Please look on your bill for baseline Territory and Heat Source",
    	content: "<img src='../images/pgebill.jpg' width=100%/>",
    	html: true,
    	//Width is defined in css
    });
    
    $("[for='chargingLevel']").tooltip({title: "Level 1: charging from a standard wall outlet"
	});
    $("[for='weekdayDistance']").tooltip({title: "Please estimate how many miles you expect to drive each weekday. The average American drives 37 miles every day"
	});
    $("[for='weekendDistance']").tooltip({title: "Please estimate how many miles you expect to drive each day of the weekend."
	});
    $("[for='chargeStartTime']").tooltip({title: "The optimal time to charge your car is between 11 PM and 7 AM"
	});
    $("[for='intervalUsage']").tooltip({title: "Please refer to instructions for downloading Green Button data. " +
    		"Please be sure to choose the XML File"
	});
    $("[for='billingPeriods']").tooltip({title: "Please refer to instructions for downloading Green Button data. " +
    		"Please be sure to choose the CSV File"
	});
    

    $('body').on('change', "[name='chargingLevel']", function() {
    	if($("[name='chargingLevel']").val() == "1.6") {
        	$("[for='chargingLevel']").attr('data-original-title', 'Level 1: charging from a standard wall outlet');
        }
        else if($("[name='chargingLevel']").val() == "3.3") {
        	$("[for='chargingLevel']").attr('data-original-title', 'Level 2 (3.3 kW): typically a wall-mounted charging station at 240V power');
        }
        else if($("[name='chargingLevel']").val() == "6.6") {
        	$("[for='chargingLevel']").attr('data-original-title', 'Level 2 (6.6 kW): typically a wall-mounted charging station at 240V power');
        }
        else if($("[name='chargingLevel']").val() == "8.8") {
        	$("[for='chargingLevel']").attr('data-original-title', 'Tesla Single charger');
        }
        else {
        	$("[for='chargingLevel']").attr('data-original-title', 'Upgraded Tesla charger');
        }
    });
    
});


