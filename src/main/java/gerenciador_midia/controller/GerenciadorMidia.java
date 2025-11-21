package gerenciador_midia.controller;

import gerenciador_midia.model.Midia;

import java.util.ArrayList;


public class GerenciadorMidia {

    ArrayList<Midia> midias;

    public void incluirMidia(Midia midia){
        midias.add(midia);
    }
    public void EditarMidia(Midia midia){
        midias.remove(midia);
        midias.add(midia);
    }
    public void excluirMidia(Midia midia){
        midias.remove(midia);
    }
    public void moverMidia(Midia midia){

    }

    }

