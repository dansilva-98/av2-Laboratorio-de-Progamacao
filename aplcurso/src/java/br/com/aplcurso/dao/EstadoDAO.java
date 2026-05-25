package br.com.aplcurso.dao;

import br.com.aplcurso.model.Estado;
import br.com.aplcurso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO implements GenericDAO {

    private Connection conexao;

    public EstadoDAO() throws Exception {
        conexao = SingleConnection.getConnection();
    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Estado oEstado = (Estado) objeto;
        Boolean retorno = false;

        if (oEstado.getId() == 0) {
            retorno = this.inserir(oEstado);
        } else {
            retorno = this.alterar(oEstado);
        }

        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Estado oEstado = (Estado) objeto;

        String sql = "insert into estado (nomeEstado, siglaEstado) values (?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, oEstado.getNomeEstado());
            stmt.setString(2, oEstado.getSiglaEstado());

            stmt.execute();
            conexao.commit();

            return true;

        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao cadastrar Estado! Erro: " + ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }

            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Estado oEstado = (Estado) objeto;

        String sql = "update estado set nomeEstado = ?, siglaEstado = ? where id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, oEstado.getNomeEstado());
            stmt.setString(2, oEstado.getSiglaEstado());
            stmt.setInt(3, oEstado.getId());

            stmt.execute();
            conexao.commit();

            return true;

        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao alterar Estado! Erro: " + ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }

            return false;
        }
    }

    @Override
    public Boolean excluir(int numero) {
        String sql = "delete from estado where id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, numero);
            stmt.execute();
            conexao.commit();

            return true;

        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao excluir Estado! Erro: " + ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }

            return false;
        }
    }

    @Override
    public Object carregar(int numero) {
        String sql = "select * from estado where id = ?";
        Estado oEstado = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, numero);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    oEstado = new Estado();
                    oEstado.setId(rs.getInt("id"));
                    oEstado.setNomeEstado(rs.getString("nomeestado"));
                    oEstado.setSiglaEstado(rs.getString("siglaestado"));
                }
            }

            return oEstado;

        } catch (SQLException ex) {
            System.out.println("Erro ao carregar Estado: " + ex.getMessage());
            ex.printStackTrace();
        }

        return oEstado;
    }

    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        String sql = "select * from estado order by id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estado oEstado = new Estado();
                oEstado.setId(rs.getInt("id"));
                oEstado.setNomeEstado(rs.getString("nomeestado"));
                oEstado.setSiglaEstado(rs.getString("siglaestado"));
                resultado.add(oEstado);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar Estados: " + ex.getMessage());
            ex.printStackTrace();
        }

        return resultado;
    }

    public boolean siglaEstadoExiste(String siglaEstado, int idEstado) {

        if (siglaEstado == null || siglaEstado.trim().isEmpty()) {
            return false;
        }

        String sql = "select count(*) as quantidade "
                   + "from estado "
                   + "where upper(siglaEstado) = upper(?) "
                   + "and id <> ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, siglaEstado.trim());
            stmt.setInt(2, idEstado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade") > 0;
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao verificar sigla do estado: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
