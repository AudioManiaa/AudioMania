package com.audiomania;

import com.audiomania.view.SistemaView;
import com.audiomania.model.Usuario;

public class Main {
    public static void main(String[] args) {
        SistemaView view = new SistemaView();
        Usuario usuarioLogado = view.iniciarLogin();

        if (usuarioLogado != null) {
            //INICIOLOGIN
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("Usuário logado: " + usuarioLogado.getLogin());
            System.out.println("Nível de acesso: " + usuarioLogado.getNivelAcesso());
            System.out.println("\nAqui você pode implementar o Menu Principal com as funcionalidades do sistema");
            //FIMLOGIN

            //INICIOFUNMENU

            //FIMFUNMENU
        }

        view.fechar();
    }
}
