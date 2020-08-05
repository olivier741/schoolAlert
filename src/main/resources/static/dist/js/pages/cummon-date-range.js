/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var start_reg = moment().subtract(29, 'days');
var end_reg = moment();

var start_renew = moment().subtract(29, 'days');
var end_renew = moment();

var start_exp = moment().subtract(29, 'days');
var end_exp = moment();

var start_unreg = moment().subtract(29, 'days');
var end_unreg = moment();

var start_cancel = moment().subtract(29, 'days');
var end_cancel = moment();

function cb_regtime(start, end) {
    $('#reg_daterange span').html(start.format('YYYY-MM-DD HH:mm') + ' --- ' + end.format('YYYY-MM-DD HH:mm'));
    $('input[name="reg_dateFrom"]').val(start.format('YYYY-MM-DD HH:mm'));
    $('input[name="reg_dateTo"]').val(end.format('YYYY-MM-DD HH:mm'));
}

function cb_renewtime(start, end) {
    $('#renew_daterange span').html(start.format('YYYY-MM-DD HH:mm') + ' --- ' + end.format('YYYY-MM-DD HH:mm'));
    $('input[name="renew_dateFrom"]').val(start.format('YYYY-MM-DD HH:mm'));
    $('input[name="renew_dateTo"]').val(end.format('YYYY-MM-DD HH:mm'));
}

function cb_exptime(start, end) {
    $('#exp_daterange span').html(start.format('YYYY-MM-DD HH:mm') + ' --- ' + end.format('YYYY-MM-DD HH:mm'));
    $('input[name="exp_dateFrom"]').val(start.format('YYYY-MM-DD HH:mm'));
    $('input[name="exp_dateTo"]').val(end.format('YYYY-MM-DD HH:mm'));
}

function cb_unregtime(start, end) {
    $('#unreg_daterange span').html(start.format('YYYY-MM-DD HH:mm') + ' --- ' + end.format('YYYY-MM-DD HH:mm'));
    $('input[name="unreg_dateFrom"]').val(start.format('YYYY-MM-DD HH:mm'));
    $('input[name="unreg_dateTo"]').val(end.format('YYYY-MM-DD HH:mm'));
}

function cb_canceltime(start, end) {
    $('#cancel_daterange span').html(start.format('YYYY-MM-DD HH:mm') + ' --- ' + end.format('YYYY-MM-DD HH:mm'));
    $('input[name="cancel_dateFrom"]').val(start.format('YYYY-MM-DD HH:mm'));
    $('input[name="cancel_dateTo"]').val(end.format('YYYY-MM-DD HH:mm'));
}


$('#reg_daterange').daterangepicker({
    timePicker: true,
    startDate: start_reg,
    endDate: end_reg,
    ranges: {
        'Today': [moment(), moment()],
        'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
        'Last 30 Days': [moment().subtract(29, 'days'), moment()],
        'This Month': [moment().startOf('month'), moment().endOf('month')],
        'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    }
}, cb_regtime);

cb_regtime(start_reg, end_reg);

$('#renew_daterange').daterangepicker({
    timePicker: true,
    startDate: start_renew,
    endDate: end_renew,
    ranges: {
        'Today': [moment(), moment()],
        'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
        'Last 30 Days': [moment().subtract(29, 'days'), moment()],
        'This Month': [moment().startOf('month'), moment().endOf('month')],
        'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    }
}, cb_renewtime);

cb_renewtime(start_renew, end_renew);



$('#exp_daterange').daterangepicker({
    timePicker: true,
    startDate: start_exp,
    endDate: end_exp,
    ranges: {
        'Today': [moment(), moment()],
        'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
        'Last 30 Days': [moment().subtract(29, 'days'), moment()],
        'This Month': [moment().startOf('month'), moment().endOf('month')],
        'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    }
}, cb_exptime);

cb_exptime(start_exp, end_exp);



$('#unreg_daterange').daterangepicker({
    timePicker: true,
    startDate: start_unreg,
    endDate: end_unreg,
    ranges: {
        'Today': [moment(), moment()],
        'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
        'Last 30 Days': [moment().subtract(29, 'days'), moment()],
        'This Month': [moment().startOf('month'), moment().endOf('month')],
        'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    }
}, cb_unregtime);

cb_unregtime(start_exp, end_exp);


$('#cancel_daterange').daterangepicker({
    timePicker: true,
    startDate: start_cancel,
    endDate: end_cancel,
    ranges: {
        'Today': [moment(), moment()],
        'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
        'Last 30 Days': [moment().subtract(29, 'days'), moment()],
        'This Month': [moment().startOf('month'), moment().endOf('month')],
        'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    }
}, cb_canceltime);

cb_canceltime(start_exp, end_exp);
