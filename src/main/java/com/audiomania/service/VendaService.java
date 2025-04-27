package com.audiomania.service;

import com.audiomania.entities.VendaEntity;
import com.audiomania.repository.VendaRepositoryrr;
import java.util.List;

public class VendaService {

    private VendaRepositoryrr vendaRepository = new VendaRepositoryrr();

    public void salvarVenda(VendaEntity venda) {
        vendaRepository.salvar(venda);
    }

    public List<VendaEntity> listarVendas() {
        return vendaRepository.listarTodas();
    }
}
