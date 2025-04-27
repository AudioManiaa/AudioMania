package com.audiomania.service;

import com.audiomania.entities.VendaEntity;
import com.audiomania.repository.VendaRepository;
import java.util.List;

public class VendaService {

    private VendaRepository vendaRepository = new VendaRepository();

    public void salvarVenda(VendaEntity venda) {
        vendaRepository.salvar(venda);
    }

    public List<VendaEntity> listarVendas() {
        return vendaRepository.listarTodas();
    }
}
