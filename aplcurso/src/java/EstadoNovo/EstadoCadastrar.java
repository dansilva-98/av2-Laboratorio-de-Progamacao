package br.com.aplcurso.controller.estado;

import br.com.aplcurso.dao.EstadoDAO;
import br.com.aplcurso.model.Estado;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EstadoCadastrar", urlPatterns = {"/EstadoCadastrar"})
public class EstadoCadastrar extends HttpServlet {

    private String valor(String parametro) {
        return parametro == null ? "" : parametro.trim();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=iso-8859-1");

        try {
            EstadoDAO dao = new EstadoDAO();

            int id = 0;

            try {
                id = Integer.parseInt(valor(request.getParameter("id")));
            } catch (Exception e) {
                id = 0;
            }

            String nomeEstado = valor(request.getParameter("nomeEstado")).toUpperCase();
            String siglaEstado = valor(request.getParameter("siglaEstado")).toUpperCase();

            if (nomeEstado.isEmpty() || siglaEstado.isEmpty()) {
                response.getWriter().write("5");
                return;
            }

            if (siglaEstado.length() != 2) {
                response.getWriter().write("7");
                return;
            }

            if (dao.siglaEstadoExiste(siglaEstado, id)) {
                response.getWriter().write("6");
                return;
            }

            Estado oEstado = new Estado();

            oEstado.setId(id);
            oEstado.setNomeEstado(nomeEstado);
            oEstado.setSiglaEstado(siglaEstado);

            if (dao.cadastrar(oEstado)) {
                response.getWriter().write("1");
            } else {
                response.getWriter().write("0");
            }

        } catch (Exception ex) {
            System.out.println("Problemas no Servlet ao cadastrar Estado! Erro: " + ex.getMessage());
            ex.printStackTrace();
            response.getWriter().write("0");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}