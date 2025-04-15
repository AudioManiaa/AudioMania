
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente {

    private String nome;
    private String cpf;
    private String telefone;
    private static String endereco;

    public Cliente(String nome, String cpf, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    // Setters, se quiser editar depois
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void exibirCliente() {
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
        System.out.println("Endereco: " + endereco);
        System.out.println("------------------------");
    }

    public static class GerenciadorClientes {
        private List<Cliente> clientes = new ArrayList<>();
        private Scanner sc = new Scanner(System.in);

        public void adicionarCliente() {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();

            System.out.println("Endereco: ");
            String endereco = sc.nextLine();

            Cliente novoCliente = new Cliente(nome, cpf, telefone, endereco);
            clientes.add(novoCliente);

            System.out.println("Cliente adicionado com sucesso!");
        }

        public void listarClientes() {
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
                return;
            }

            System.out.println("\n--- Lista de Clientes ---");
            for (Cliente cliente : clientes) {
                cliente.exibirCliente();
            }
        }
    }
}
