import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Funcionario {
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;

    public Funcionario(String nome, String cpf, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }
    
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
    
    public void exibirFuncionario() {
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
        System.out.println("Endereco: " + endereco);
        System.out.println("------------------------");
    }
    
    public void adicionarFuncionario() {
        List<Funcionario> funcionarios = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Cadastro de Funcionario");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();    
        System.out.println("Endereco: ");
        String endereco = sc.nextLine();
        
        Funcionario novFuncionario = new Funcionario(nome, cpf, telefone, endereco);
        funcionarios.add(novFuncionario);
        
        System.out.println("Funcionario cadastrado com sucesso!");
    }
    
    public void listarFuncionarios() {
        System.out.println("\n--- Lista de Funcionarios ---");
        exibirFuncionario();
    }
}
