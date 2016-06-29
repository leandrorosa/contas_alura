package br.com.caelum.contas.modelo;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Conta {

	private Long id;

	@NotNull
	@Size(min = 3, message = "{conta.description.min}")
	private String descricao;

	private boolean paga;

	private double valor;

	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Calendar dataPagamento;

	private TipoDaConta tipo;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public boolean isPaga() {
		return paga;
	}

	public void setPaga(final boolean paga) {
		this.paga = paga;
	}

	public Calendar getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(final Calendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public TipoDaConta getTipo() {
		return tipo;
	}

	public void setTipo(final TipoDaConta tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(final double valor) {
		this.valor = valor;
	}

}
