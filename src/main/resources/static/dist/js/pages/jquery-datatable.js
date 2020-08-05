$(function () {
    $('.js-basic-example').DataTable({
        responsive: true
    });

    //Exportable table
    $('.js-exportable').DataTable({
        dom: 'Bfrtip',
        paging: false,
        info: false,
        ordering: false,
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ]
    });
});