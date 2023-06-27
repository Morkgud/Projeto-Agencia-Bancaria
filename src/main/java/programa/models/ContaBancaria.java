package programa.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Table(name = "contas_bancarias")
public class ContaBancaria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	private BigDecimal saldo;

	public ContaBancaria() {
		this.saldo = BigDecimal.ZERO;
	}

	public ContaBancaria(Cliente cliente) {
		this.cliente = cliente;
		this.saldo = BigDecimal.ZERO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public void depositar(BigDecimal valor) {
		try {
			if (valor.compareTo(BigDecimal.ZERO) > 0) {
				this.saldo = this.saldo.add(valor);
			} else {
				throw new IllegalArgumentException("Valor inválido para depósito!");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao depositar: " + e.getMessage());
		}
	}

	public void sacar(BigDecimal valor) {
		try {
			if (valor.compareTo(BigDecimal.ZERO) > 0) {
				if (valor.compareTo(this.saldo) <= 0) {
					this.saldo = this.saldo.subtract(valor);
					System.out.println("Saque realizado com sucesso!");
				} else {
					throw new IllegalArgumentException("Saldo insuficiente para saque!");
				}
			} else {
				throw new IllegalArgumentException("Valor inválido para saque!");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao sacar: " + e.getMessage());
		}
	}

	public void transferencia(ContaBancaria contaDestino, BigDecimal valor) {
		try {
			if (valor.compareTo(BigDecimal.ZERO) > 0) {
				if (valor.compareTo(this.saldo) <= 0) {
					this.saldo = this.saldo.subtract(valor);
					contaDestino.depositar(valor);
					System.out.println("Transferência realizada com sucesso!");
				} else {
					throw new IllegalArgumentException("Saldo insuficiente para transferência!");
				}
			} else {
				throw new IllegalArgumentException("Valor inválido para transferência!");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao transferir: " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return 	"\n=============================="+
				"\nConta: " + id +
				"\nCliente: " + cliente.getNome() +
				"\nSaldo: R$" + saldo.toString() +
				"\n==============================";
	}
}
