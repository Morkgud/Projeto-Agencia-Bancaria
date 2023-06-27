package programa.views;
import java.util.Scanner;
import static programa.controllers.Funcionalidades.*;
import static programa.utilitario.ExibirMenu.exibirMenu;
public class Agencia {
    private static Scanner input = new Scanner(System.in);

    public static void operacoes() {
        exibirMenu();
        int opcao = input.nextInt();
        input.nextLine();

        switch (opcao) {
            case 0 -> encerrarPrograma();
            case 1 -> criarConta();
            case 2 -> depositar();
            case 3 -> sacar();
            case 4 -> transferir();
            case 5 -> listarContas();
            case 6 -> deletarConta();
            case 7 -> listarConta();
            default -> {
                System.out.println("Opção inválida. Tente novamente.");
                operacoes();
            }
        }
    }
    public static void encerrarPrograma() {
        System.out.println("Programa encerrado.");
        System.exit(0);
    }
}
