(function($) {
    $.extend($, { dialog: { defaults: {
        modal: true,
        width: 450,
        minHeight: 150,
        autoOpen: false,
        resizable: false,
        showTitle: false,
        show: 'fade',
        hide: 'explode',
        buttons: [
            {
                text: '확인',
                click: function() {
                    $(this).dialog('close');
                }
            }
        ]
    }}});
})(jQuery);

$(function() {
/*    $('.datepicker').datepicker({
        dateFormat: 'yy.mm.dd',
        buttonImageOnly: false,
        showAnim: 'fadeIn',
        duration: 500,
        showMonthAfterYear: true,
        showButtonPanel: true
    });*/


    
/*    $('<div class="contains" style="display: none;"><ul /></div>')
        .appendTo('body')
        .dialog($.dialog.defaults);*/

    $('a.delete').click(function() {
        return confirm('삭제하시겠습니까?');
    });

    $('a.registed').click(function() {
        return confirm('등록 상태로 변경하시겠습니까?');
    });
    
});

$.validator.setDefaults({onkeyup:false,onclick:false,onfocusout:false});