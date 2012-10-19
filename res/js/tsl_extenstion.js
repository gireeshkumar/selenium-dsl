	
$.extend($.expr[':'], {
	anyTagContains: function (a, t, m) {
		return $(a).children().length == 0 && $(a).get(0).tagName != "SCRIPT" && $(a).text().indexOf(m[3]) != -1;
	},
	regex: function(elem, index, match) {
	    var matchParams = match[3].split(',');
	        validLabels = /^(data|css):/;
	        attr = {
	            method: matchParams[0].match(validLabels) ? 
	                        matchParams[0].split(':')[0] : 'attr',
	            property: matchParams.shift().replace(validLabels,'')
	        };
	        regexFlags = 'ig';
	        regex = new RegExp(matchParams.join('').replace(/^\s+|\s+$/g,''), regexFlags);
	    return regex.test(jQuery(elem)[attr.method](attr.property));
	},
	regexVal: function(elem, index, match) {
		if(elem.firstChild != null && elem.firstChild.nodeType == 3){
			var matchParams = match[3];
	        regexFlags = 'ig';
	        regex = new RegExp(matchParams, regexFlags);
	        var val = jQuery(elem).text();
		    rslt = regex.test(val);
		    return rslt;
		}else{
			return false;	
		}    
	}
});


	/*
	param "a"  should be
	
	for button - "button,input[type=submit]"
	for text  - input[type=text]
	*/
window.TSLFINDERS = {
	inDocument: null,
	findByLinkButtonAny:function(txt){
		var obj = this.findLink(txt);
		if(obj == null){
			obj = this.buttonContains(txt);
		}
		if(obj == null){
			var lst = $(":anyTagContains('"+txt+"')");
			if(lst != null && lst.length > 0){
				obj = lst[0];
			}
		}
		return obj;
	},
	findLink:function(txt){
	   var found = null;
	  $( document ).find("a").each(function() {     
			 if($(this).text() == txt){
				found = this;
				return false;
			}
		});
	 	return found;
	},
	firstVisibleElement: function (selector) {
		rslt = $(document).find(selector).filter(":visible:first");
		return rslt;
	},	buttonContains: function (txt) {
		fnd = $(document).find("button:contains('"+txt+"'), input[type=button][value*='"+txt+"'],input[type=submit][value*='"+txt+"'],input[type=reset][value*='"+txt+"']").filter(":visible:first");		
		return fnd;
	},
	toDOMElement: function(jqrslt){
		var arr = new Array();
		$(jqrslt).each(function(){
			arr[arr.length] = $(this)[0];
		});
		return arr;
	}
};
