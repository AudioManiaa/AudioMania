package com.audiomania.service;

import com.audiomania.model.Usuario;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UsuarioService {
    private static final Map<String, Usuario> usuarios = new HashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    static {
        //Usuários pré-cadastrados para teste
        cadastrarUsuario("admin", "admin123", 3);
        cadastrarUsuario("operador", "op123", 1);
    }

    /**
     * Verificar as credenciais
     * @param login Nome de usuário
     * @param senha Senha do usuário
     * @return O objeto Usuario se autenticado, null caso contrário
     */
    public static Usuario autenticar(String login, String senha) {
        Usuario usuario = usuarios.get(login);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    /**
     * Cadastra um novo usuário
     * @param login Nome de usuário
     * @param senha Senha do usuário
     * @param nivelAcesso Nível de permissão (1: básico, 2: intermediário, 3: administrador)
     * @return true se cadastrado com sucesso, false se o login já existir
     */
    public static boolean cadastrarUsuario(String login, String senha, int nivelAcesso) {
        if (usuarios.containsKey(login)) {
            return false;
        }

        int id = idGenerator.getAndIncrement();
        usuarios.put(login, new Usuario(id, login, senha, nivelAcesso));
        return true;
    }

    /**
     * Lista todos os usuários cadastrados (apenas para fins administrativos)
     * @return Um mapa contendo todos os usuários
     */
    public static Map<String, Usuario> listarUsuarios() {
        return new HashMap<>(usuarios);
    }
}