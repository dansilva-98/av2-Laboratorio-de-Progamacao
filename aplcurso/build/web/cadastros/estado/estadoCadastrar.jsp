<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<div class="container-fluid">

    <h1 class="h3 mb-2 text-gray-800">Estados</h1>
    <p class="mb-4">Formulário de Cadastro</p>

    <a class="btn btn-secondary mb-4" href="${pageContext.request.contextPath}/EstadoListar">
        <i class="fas fa-undo-alt"></i>
        <strong>Voltar</strong>
    </a>

    <div class="row">
        <div class="col-lg-9">
            <div class="card shadow mb-4">
                <div class="card-body">

                    <div class="form-group">
                        <label>Id</label>
                        <input class="form-control" type="text" name="id" id="id"
                               value="${estado.id}" readonly="readonly"/>
                    </div>

                    <div class="form-group">
                        <label>Nome Estado</label>
                        <input class="form-control" type="text" name="nomeEstado" id="nomeEstado"
                               value="${estado.nomeEstado}" size="100" maxlength="100"/>
                    </div>

                    <div class="form-group">
                        <label>Sigla Estado</label>
                        <input class="form-control" type="text" name="siglaEstado" id="siglaEstado"
                               value="${estado.siglaEstado}" size="2" maxlength="2"/>
                    </div>

                    <div class="form-group">
                        <button class="btn btn-success" type="button" id="submit" onclick="validarCampos()">
                            Salvar Documento
                        </button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#nomeEstado').focus();

        $('#siglaEstado').on('input', function () {
            $(this).val($(this).val().toUpperCase());
        });

        $('#siglaEstado').blur(function () {
            verificarSiglaEstado();
        });
    });

    function verificarSiglaEstado() {
        var siglaEstado = $('#siglaEstado').val().trim().toUpperCase();

        if (siglaEstado === '') {
            return;
        }

        if (siglaEstado.length !== 2) {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Sigla inválida!',
                text: 'A sigla deve conter exatamente 2 caracteres. Exemplo: SP',
                showConfirmButton: true,
                timer: 4000
            }).then(function () {
                $('#siglaEstado').focus();
            });
            return;
        }

        $.ajax({
            type: 'get',
            url: '${pageContext.request.contextPath}/EstadoVerificarSigla',
            data: {
                id: $('#id').val(),
                siglaEstado: siglaEstado
            },
            success: function (response) {
                if (response == '1') {
                    Swal.fire({
                        position: 'center',
                        icon: 'warning',
                        title: 'Sigla já cadastrada!',
                        text: 'Informe outra sigla.',
                        showConfirmButton: true,
                        timer: 4000
                    }).then(function () {
                        $('#siglaEstado').val('');
                        $('#siglaEstado').focus();
                    });
                }
            },
            error: function () {
                console.log("Erro ao verificar sigla no servidor.");
            }
        });
    }

    function validarCampos() {
        var nomeEstado = $('#nomeEstado').val().trim();
        var siglaEstado = $('#siglaEstado').val().trim().toUpperCase();

        if (nomeEstado === '') {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique o nome do estado!',
                showConfirmButton: false,
                timer: 1500
            });
            $('#nomeEstado').focus();

        } else if (siglaEstado === '') {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Verifique a sigla do estado!',
                showConfirmButton: false,
                timer: 1500
            });
            $('#siglaEstado').focus();

        } else if (siglaEstado.length !== 2) {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'Sigla inválida!',
                text: 'A sigla deve conter exatamente 2 caracteres. Exemplo: SP',
                showConfirmButton: true,
                timer: 4000
            });
            $('#siglaEstado').focus();

        } else {
            gravarDados();
        }
    }

    function gravarDados() {
        $.ajax({
            type: 'post',
            url: '${pageContext.request.contextPath}/EstadoCadastrar',
            data: {
                id: $('#id').val(),
                nomeEstado: $('#nomeEstado').val().trim().toUpperCase(),
                siglaEstado: $('#siglaEstado').val().trim().toUpperCase()
            },
            success: function (data) {
                console.log("resposta servlet Estado ->");
                console.log(data);

                if (data == 1) {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Sucesso',
                        text: 'Estado gravado com sucesso!',
                        showConfirmButton: true,
                        timer: 3000
                    }).then(function () {
                        window.location.href = '${pageContext.request.contextPath}/EstadoNovo';
                    });

                } else if (data == 5) {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Dados em branco ou não informados, verifique!',
                        showConfirmButton: true,
                        timer: 5000
                    }).then(function () {
                        $('#nomeEstado').focus();
                    });

                } else if (data == 6) {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Sigla já cadastrada!',
                        text: 'Informe outra sigla.',
                        showConfirmButton: true,
                        timer: 5000
                    }).then(function () {
                        $('#siglaEstado').focus();
                    });

                } else if (data == 7) {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Sigla inválida!',
                        text: 'A sigla deve conter exatamente 2 caracteres. Exemplo: SP',
                        showConfirmButton: true,
                        timer: 5000
                    }).then(function () {
                        $('#siglaEstado').focus();
                    });

                } else {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Não foi possível gravar o estado!',
                        showConfirmButton: true,
                        timer: 5000
                    }).then(function () {
                        $('#nomeEstado').focus();
                    });
                }
            },
            error: function () {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Erro de comunicação',
                    text: 'Falha na comunicação com o servidor.',
                    showConfirmButton: true,
                    timer: 5000
                }).then(function () {
                    $('#nomeEstado').focus();
                });
            }
        });
    }
</script>

<jsp:include page="/footer.jsp"/>
