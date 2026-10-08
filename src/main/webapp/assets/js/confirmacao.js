function confirmDelete(id) {
    const url = 'excluirProjeto?id=' + id;  // url que será afetada pela ação
    const action = "excluir";  // Define a ação

    // Mostra o texto que irá aparecer no modal de confirmação
    document.getElementById('confirmDeleteButton').textContent = `Confirmar`;

    // Link de ação para excluir o projeto
    const confirmBtn = document.getElementById('confirmDeleteButton');
    confirmBtn.setAttribute('onclick', 'window.location.href = "' + url + '"');

    // Exibe o modal
    const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    modal.show();
}

function confirmDeletePostagem(id) {
    const form = document.getElementById('formExcluirPostagem' + id);
    const confirmBtn = document.getElementById('confirmDeletePostagemButton');

    confirmBtn.onclick = function () {
        form.submit();
    };

    const modal = new bootstrap.Modal(document.getElementById('confirmDeletePostagemModal'));
    modal.show();
}