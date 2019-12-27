(function() {
	var tips = $( ".validateTips" );
	
	jQuery.updateTips = function ( t ) {
		tips.text( t ).addClass( "ui-state-highlight" );
        setTimeout(function() {
        	tips.removeClass( "ui-state-highlight", 1500 );
        }, 500 );
    };
    
    jQuery.fn.checkRegexp = function ( regexp, n ) {
        if ( !( regexp.test( this.val() ) ) ) {
        	this.addClass( "ui-state-error" );
            jQuery.updateTips( n );
            return false;
        } else {
            return true;
        }
    };
})();