package programa.controllers;

import programa.dao.*;
import programa.models.*;
import programa.utilitario.Utils;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static programa.views.Agencia.operacoes;

public class Funcionalidades {
    private static Scanner input = new Scanner(System.in);

    public static void criarConta() {
        try {
            System.out.println("Informe o nome do cliente:");
            String nome = input.nextLine();

            System.out.println("Informe o CPF do cliente:");
            String cpf = input.nextLine();

            System.out.println("Informe o email do cliente:");
            String email = input.nextLine();

            Cliente cliente = new Cliente(nome, email, cpf);
            ClienteDAO.salvar(cliente);

            ContaBancaria conta = new ContaBancaria(cliente);
            ContaBancariaDAO.salvar(conta);

            System.out.println("Conta criada com sucesso!");
            operacoes();
        } catch (Exception e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
            operacoes();
        }
    }

    public static void depositar() {
        try {
            System.out.println("Informe o número da conta:");
            int numeroConta = input.nextInt();
            input.nextLine();

            ContaBancaria conta = ContaBancariaDAO.buscarPorNumeroConta(numeroConta);
            if (conta != null) {
                System.out.println("Informe o valor a ser depositado:");
                BigDecimal valor = input.nextBigDecimal();
                input.nextLine();

                if (valor.compareTo(BigDecimal.ZERO) > 0) {
                    conta.depositar(valor);
                    ContaBancariaDAO.salvar(conta);
                    System.out.println("Depósito realizado com sucesso! Novo saldo: " + Utils.formatarValorMonetario(conta.getSaldo()));
                } else {
                    throw new IllegalArgumentException("Valor inválido para depósito!");
                }
            } else {
                throw new IllegalArgumentException("A conta informada não foi encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar depósito: " + e.getMessage());
        }
        operacoes();
    }

    public static void sacar() {
        try {
            System.out.println("Informe o número da conta:");
            int numeroConta = input.nextInt();
            input.nextLine();

            ContaBancaria conta = ContaBancariaDAO.buscarPorNumeroConta(numeroConta);
            if (conta != null) {
                System.out.println("Informe o valor a ser sacado:");
                BigDecimal valor = input.nextBigDecimal();
                input.nextLine();

                if (valor.compareTo(BigDecimal.ZERO) > 0) {
                    if (valor.compareTo(conta.getSaldo()) <= 0) {
                        conta.sacar(valor);
                        ContaBancariaDAO.salvar(conta);
                        System.out.println("Saque realizado com sucesso! Novo saldo: " + Utils.formatarValorMonetario(conta.getSaldo()));
                    } else {
                        throw new IllegalArgumentException("Saldo insuficiente para saque!");
                    }
                } else {
                    throw new IllegalArgumentException("Valor inválido para saque!");
                }
            } else {
                throw new IllegalArgumentException("A conta informada não foi encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar saque: " + e.getMessage());
        }
        operacoes();
    }

    public static void transferir() {
        try {
            System.out.println("Informe o número da conta de origem:");
            int numeroContaOrigem = input.nextInt();
            input.nextLine();
            System.out.println("Informe o número da conta de destino:");
            int numeroContaDestino = input.nextInt();
            input.nextLine();

            ContaBancaria contaOrigem = ContaBancariaDAO.buscarPorNumeroConta(numeroContaOrigem);
            ContaBancaria contaDestino = ContaBancariaDAO.buscarPorNumeroConta(numeroContaDestino);

            if (contaOrigem != null && contaDestino != null) {
                System.out.println("Informe o valor a ser transferido:");
                BigDecimal valor = input.nextBigDecimal();
                input.nextLine();

                if (valor.compareTo(BigDecimal.ZERO) > 0) {
                    if (valor.compareTo(contaOrigem.getSaldo()) <= 0) {
                        contaOrigem.transferencia(contaDestino, valor);
                        ContaBancariaDAO.salvar(contaOrigem);
                        ContaBancariaDAO.salvar(contaDestino);
                        System.out.println("Transferência realizada com sucesso!");
                        System.out.println("Novo saldo da conta de origem: " + Utils.formatarValorMonetario(contaOrigem.getSaldo()));
                        System.out.println("Novo saldo da conta de destino: " + Utils.formatarValorMonetario(contaDestino.getSaldo()));
                    } else {
                        throw new IllegalArgumentException("Saldo insuficiente para transferência!");
                    }
                } else {
                    throw new IllegalArgumentException("Valor inválido para transferência!");
                }
            } else {
                throw new IllegalArgumentException("Uma ou ambas as contas informadas não foram encontradas.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar transferência: " + e.getMessage());
        }
        operacoes();
    }

    public static void listarContas() {
        try {
            List<ContaBancaria> contas = ContaBancariaDAO.listar();
            if (!contas.isEmpty()) {
                System.out.println("Listagem de contas:");
                for (ContaBancaria conta : contas) {
                    System.out.println(conta);
                }
            } else {
                throw new IllegalStateException("Nenhuma conta encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar contas: " + e.getMessage());
        }
        operacoes();
    }
    public static void deletarConta() {
        try {
            System.out.println("Informe o número da conta a ser deletada:");
            int numeroConta = input.nextInt();
            input.nextLine();

            ContaBancaria conta = ContaBancariaDAO.buscarPorNumeroConta(numeroConta);
            if (conta != null) {
                ContaBancariaDAO.deletar(conta);
                System.out.println("Conta deletada com sucesso!");
            } else {
                throw new IllegalArgumentException("A conta informada não foi encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar conta: " + e.getMessage());
        }
        operacoes();
    }
    public static void listarConta() {
        try {
            System.out.println("Informe o número da conta:");
            int numeroConta = input.nextInt();
            input.nextLine();

            ContaBancaria conta = ContaBancariaDAO.buscarPorNumeroConta(numeroConta);
            if (conta != null) {
                System.out.println("Conta encontrada:");
                System.out.println(conta);
            } else {
                throw new IllegalArgumentException("A conta informada não foi encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Número da conta inválido. Tente novamente.");
            listarConta();
        } catch (Exception e) {
            System.out.println("Erro ao listar conta: " + e.getMessage());
        }
        operacoes();
    }



}