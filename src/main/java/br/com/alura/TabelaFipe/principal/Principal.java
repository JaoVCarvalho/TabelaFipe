package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.CarBrandData;
import br.com.alura.TabelaFipe.service.ConsumptionAPI;
import br.com.alura.TabelaFipe.service.ConvertsData;

import java.util.Comparator;

public class Principal {

    private static final String ADDRESS = "https://parallelum.com.br/fipe/api/v1";
    private static final String BRAND = "/carros/marcas";
    private ConsumptionAPI consume = new ConsumptionAPI();
    private ConvertsData convert = new ConvertsData();



    public void execute(){

        var json = consume.getDataAPI(ADDRESS + BRAND);
        System.out.println(json);
    }
}
