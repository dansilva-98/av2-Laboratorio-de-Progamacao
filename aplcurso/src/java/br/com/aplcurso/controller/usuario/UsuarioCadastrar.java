package br.com.aplcurso.controller.usuario;

import br.com.aplcurso.dao.UsuarioDAO;
import br.com.aplcurso.model.Usuario;
import br.com.aplcurso.utils.DocumentoValidador;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

@WebServlet(name = "UsuarioCadastrar", urlPatterns = {"/UsuarioCadastrar"})
public class UsuarioCadastrar extends HttpServlet {

    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private String valor(String parametro) {
        return parametro == null ? "" : parametro.trim();
    }

    private boolean emailValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private Date converterData(String dataTexto) throws Exception {
        dataTexto = valor(dataTexto);

        if (dataTexto.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return java.sql.Date.valueOf(dataTexto);
        }

        SimpleDateFormat formatoBR = new SimpleDateFormat("dd/MM/yyyy");
        formatoBR.setLenient(false);
        return formatoBR.parse(dataTexto);
    }

    private double converterSalario(String salarioTexto) throws Exception {
        salarioTexto = valor(salarioTexto);

        // Remove R$, espaços normais, espaços invisíveis e qualquer símbolo
        salarioTexto = salarioTexto.replaceAll("[^0-9,.-]", "");

        if (salarioTexto.contains(",") && salarioTexto.contains(".")) {
            salarioTexto = salarioTexto.replace(".", "").replace(",", ".");
        } else if (salarioTexto.contains(",")) {
            salarioTexto = salarioTexto.replace(",", ".");
        }

        return Double.parseDouble(salarioTexto);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=iso-8859-1");

        try {

            UsuarioDAO dao = new UsuarioDAO();

            int id = 0;
            try {
                id = Integer.parseInt(valor(request.getParameter("id")));
            } catch (Exception e) {
                id = 0;
            }

            String nome = valor(request.getParameter("nome"));
            String dataNascimentoStr = valor(request.getParameter("datanascimento"));
            String cpf = valor(request.getParameter("cpf")).replaceAll("[^\\d]", "");
            String email = valor(request.getParameter("email")).toLowerCase();
            String senha = valor(request.getParameter("senha"));
            String salarioStr = valor(request.getParameter("salario"));

            if (nome.isEmpty()
                    || cpf.isEmpty()
                    || dataNascimentoStr.isEmpty()
                    || salarioStr.isEmpty()
                    || email.isEmpty()
                    || senha.isEmpty()) {

                response.getWriter().write("5");
                return;
            }

            if (!emailValido(email)) {
                response.getWriter().write("7");
                return;
            }

            Date dataNascimento;
            try {
                dataNascimento = converterData(dataNascimentoStr);
            } catch (Exception e) {
                System.out.println("Data inválida recebida: [" + dataNascimentoStr + "]");
                response.getWriter().write("5");
                return;
            }

            double salario;
            try {
                salario = converterSalario(salarioStr);
            } catch (Exception e) {
                System.out.println("Salário inválido recebido: [" + salarioStr + "]");
                response.getWriter().write("5");
                return;
            }

            if (salario <= 0) {
                response.getWriter().write("5");
                return;
            }

            if (!DocumentoValidador.isDocumentoValido(cpf)) {
                response.getWriter().write("3");
                return;
            }

            if (dao.cpfExiste(cpf) && id == 0) {
                response.getWriter().write("4");
                return;
            }

            if (dao.emailExiste(email) && id == 0) {
                response.getWriter().write("6");
                return;
            }

            Usuario oUsuario = new Usuario();

            oUsuario.setId(id);
            oUsuario.setNome(nome);
            oUsuario.setCpf(cpf);
            oUsuario.setDataNascimento(dataNascimento);
            oUsuario.setEmail(email);
            oUsuario.setSenha(senha);
            oUsuario.setSalario(salario);

            if (dao.cadastrar(oUsuario)) {
                response.getWriter().write("1");
            } else {
                response.getWriter().write("0");
            }

        } catch (Exception ex) {
            System.out.println("Problemas no Servlet ao cadastrar Usuario! Erro: " + ex.getMessage());
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

    @Override
    public String getServletInfo() {
        return "Servlet para cadastrar usuario";
    }
}
