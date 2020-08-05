/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $('#startTime').datetimepicker(
            {
                format: 'YYYY-MM-DD HH:mm',
                extraFormats: ['DD-MM-YYYY HH:mm'],
                useCurrent: false,
                sideBySide: false,
                showTodayButton: true,
                showClear: true,
                keepInvalid: true,
                allowInputToggle: true,
            }
    );
    $('#endTime').datetimepicker(
            {
                format: 'YYYY-MM-DD HH:mm',
                extraFormats: ['DD-MM-YYYY HH:mm'],
                useCurrent: false,
                sideBySide: false,
                showTodayButton: true,
                showClear: true,
                keepInvalid: true,
                allowInputToggle: true,
            }
    );


});

$.fn.datetimepicker.Constructor.Default = $.extend({},
        $.fn.datetimepicker.Constructor.Default,
        {icons:
                    {time: 'fas fa-clock',
                        date: 'fas fa-calendar',
                        up: 'fas fa-arrow-up',
                        down: 'fas fa-arrow-down',
                        previous: 'fas fa-arrow-circle-left',
                        next: 'fas fa-arrow-circle-right',
                        today: 'far fa-calendar-check-o',
                        clear: 'fas fa-trash',
                        close: 'far fa-times'
                    }
        }
);
