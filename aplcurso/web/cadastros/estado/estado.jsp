<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<div class="container-fluid">

    <h1 class="h3 mb-2 text-gray-800">Estados</h1>
    <p class="mb-4">Cadastro de Estados</p>

    <a class="btn btn-success mb-4" href="${pageContext.request.contextPath}/EstadoNovo">
        <i class="fas fa-sticky-note"></i>
        <strong>Novo</strong>
    </a>

    <div class="card shadow">
        <div class="card-body">
            <table id="datatable" class="display">
                <thead>
                    <tr>
                        <th align="right">Id</th>
                        <th align="left">Nome Estado</th>
                        <th align="left">Sigla</th>
                        <th align="center">Excluir</th>
                        <th align="center">Alterar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="estado" items="${estados}">
                        <tr>
                            <td align="right">${estado.id}</td>
                            <td align="left">${estado.nomeEstado}</td>
                            <td align="left">${estado.siglaEstado}</td>
                            <td align="center">
                                <a href="#" title="Excluir" onclick="deletar(${estado.id})">
                                    <button>Excluir</button>
                                </a>
                            </td>
                            <td align="center">
                                <a href="${pageContext.request.contextPath}/EstadoCarregar?id=${estado.id}">
                                    <button>Alterar</button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#datatable').DataTable({
            "oLanguage": {
                "sProcessing": "Processando...",
                "sLengthMenu": "Mostrar _MENU_ registros",
                "sZeroRecords": "Nenhum registro encontrado.",
                "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
                "sInfoFiltered": "",
                "sSearch": "Buscar:",
                "oPaginate": {
                    "sFirst": "Primeiro",
                    "sPrevious": "Anterior",
                    "sNext": "Seguinte",
                    "sLast": "Último"
                }
            }
        });
    });

    function deletar(codigo) {
        var id = codigo;

        Swal.fire({
            title: 'Você tem certeza?',
            text: "Você não poderá recuperar depois!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sim, apague o estado!',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: 'post',
                    url: '${pageContext.request.contextPath}/EstadoExcluir',
                    data: {
                        id: id
                    },
                    success: function (data) {
                        if (data == 1) {
                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Sucesso',
                                text: 'Estado excluído com sucesso!',
                                showConfirmButton: false,
                                timer: 2000
                            });
                        } else {
                            Swal.fire({
                                position: 'top-end',
                                icon: 'error',
                                title: 'Erro',
                                text: 'Não foi possível excluir o estado!',
                                showConfirmButton: false,
                                timer: 2000
                            });
                        }

                        window.location.href = "${pageContext.request.contextPath}/EstadoListar";
                    },
                    error: function () {
                        window.location.href = "${pageContext.request.contextPath}/EstadoListar";
                    }
                });
            }
        });
    }
</script>

<jsp:include page="/footer.jsp"/>