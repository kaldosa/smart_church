/* Japanese initialisation for the jQuery time picker plugin. */
/* Written by Bernd Plagge (bplagge@choicenet.ne.jp). */
jQuery(function($){
    $.timepicker.regional['ko'] = {
                hourText: '시간',
                minuteText: '분',
                amPmText: ['오전', '오후'] }
    $.timepicker.setDefaults($.timepicker.regional['ko']);
});
