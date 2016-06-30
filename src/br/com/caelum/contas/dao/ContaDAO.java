package br.com.caelum.contas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.contas.modelo.Conta;
import br.com.caelum.contas.modelo.TipoDaConta;
@Repository
public class ContaDAO {


    private final Connection connection;

    @Autowired
    public ContaDAO(final DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adiciona(final Conta conta) {
        final String sql = "insert into contas (descricao, paga, valor, tipo) values (?,?,?,?)";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, conta.getDescricao());
            stmt.setBoolean(2, conta.isPaga());
            stmt.setDouble(3, conta.getValor());
            stmt.setString(4, conta.getTipo().name());
            stmt.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void remove(final Conta conta) {

        if (conta.getId() == null) {
            throw new IllegalStateException("Id da conta naoo deve ser nula.");
        }

        final String sql = "delete from contas where id = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, conta.getId());
            stmt.execute();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void altera(final Conta conta) {
        final String sql = "update contas set descricao = ?, paga = ?, dataPagamento = ?, tipo = ?, valor = ? where id = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, conta.getDescricao());
            stmt.setBoolean(2, conta.isPaga());
            stmt.setDate(3, conta.getDataPagamento() != null ? new Date(conta
                .getDataPagamento().getTimeInMillis()) : null);
            stmt.setString(4, conta.getTipo().name());
            stmt.setDouble(5, conta.getValor());
            stmt.setLong(6, conta.getId());
            stmt.execute();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Conta> lista() {
        try {
            final List<Conta> contas = new ArrayList<Conta>();
            final PreparedStatement stmt = this.connection
                .prepareStatement("select * from contas");

            final ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // adiciona a conta na lista
                contas.add(populaConta(rs));
            }

            rs.close();
            stmt.close();

            return contas;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Conta buscaPorId(final Long id) {


        if (id == null) {
            throw new IllegalStateException("Id da conta nao deve ser nula.");
        }

        try {
            final PreparedStatement stmt = this.connection
                .prepareStatement("select * from contas where id = ?");
            stmt.setLong(1, id);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return populaConta(rs);
            }

            rs.close();
            stmt.close();

            return null;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void paga(final Long id) {

        if (id == null) {
            throw new IllegalStateException("Id da conta nao deve ser nula.");
        }

        final String sql = "update contas set paga = ?, dataPagamento = ? where id = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
            stmt.setLong(3, id);
            stmt.execute();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Conta populaConta(final ResultSet rs) throws SQLException {
        final Conta conta = new Conta();

        conta.setId(rs.getLong("id"));
        conta.setDescricao(rs.getString("descricao"));
        conta.setPaga(rs.getBoolean("paga"));
        conta.setValor(rs.getDouble("valor"));

        final Date data = rs.getDate("dataPagamento");
        if (data != null) {
            final Calendar dataPagamento = Calendar.getInstance();
            dataPagamento.setTime(data);
            conta.setDataPagamento(dataPagamento);
        }

        conta.setTipo(Enum.valueOf(TipoDaConta.class, rs.getString("tipo")));

        return conta;
    }
}
