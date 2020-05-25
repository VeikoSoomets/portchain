$('#dataModal').on('show.bs.modal', function (event) {
    var tr = $(event.relatedTarget);
    var title = tr.data('title');
    var content = tr.data('content');
    var modal = $(this);
    modal.find('.modal-title').text(title);
    modal.find('.modal-body').text(content);
});