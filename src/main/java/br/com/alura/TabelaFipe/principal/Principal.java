package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Brand;
import br.com.alura.TabelaFipe.model.Model;
import br.com.alura.TabelaFipe.model.ModelData;
import br.com.alura.TabelaFipe.model.Year;
import br.com.alura.TabelaFipe.service.ConsumptionAPI;
import br.com.alura.TabelaFipe.service.ConvertsData;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Principal {

    private static final String ADDRESS = "https://parallelum.com.br/fipe/api/v1";

    private Scanner scanner = new Scanner(System.in);
    private ConsumptionAPI consume = new ConsumptionAPI();
    private ConvertsData convert = new ConvertsData();



    public void execute() {
        System.out.println(
                "=======================\n" +
                " Consulta TABELA FIPE\n" +
                "=======================\n\n" +
                "Selecione umas das opçoes abaixo:\n" +
                "1 - Carros\n" +
                "2 - Motos\n" +
                "3 - Caminhões\n"
        );

        String address = vehicleChoice();

        var json = consume.getDataAPI(address);

        System.out.println("\nLista completa de Códigos/Marcas\n");

        var list = convert.getList(json, Brand.class);
        list.stream()
                .sorted(Comparator.comparing(Brand::codigo))
                .forEach(System.out::println);

        var codeBrand = codeBrandChoice(list);

        address = address + "/" + codeBrand + "/modelos";
        json = consume.getDataAPI(address);

        System.out.println("\nLista completa de Códigos/Modelos\n");

        ModelData models = convert.getData(json, ModelData.class);
        models.models().stream()
                .sorted(Comparator.comparing(Model::codigo))
                .forEach(System.out::println);

        var codeModel = codeModelChoice(models);

        address = address + "/" + codeModel + "/anos";
        json = consume.getDataAPI(address);

        System.out.println("\nLista completa de Códigos/Anos: \n");

        var years = convert.getList(json, Year.class);
        years.stream()
                .sorted(Comparator.comparing(Year::codigo))
                .forEach(System.out::println);
        }

    public String vehicleChoice(){
        boolean check = false;
        String address = null;
        String choice = null;
        while(!check){
            choice = scanner.nextLine();
            if (Objects.equals(choice, "1")) {
                address = ADDRESS + "/carros/marcas";
                check = true;
            } else if (Objects.equals(choice, "2")) {
                address = ADDRESS + "/motos/marcas";
                check = true;
            } else if (Objects.equals(choice, "3")) {
                address = ADDRESS + "/caminhoes/marcas";
                check = true;
            } else {
                System.out.println("Entrada inválida, tente novamente!");
            }
        }
        return address;
    }

    public Integer codeBrandChoice(List<Brand> list){
        boolean check = false;
        int finalCode = -1;

        while(!check){

            try{
                System.out.println("\nDigite o código da marca: ");
                var code = Integer.parseInt(scanner.nextLine());

                check = list.stream()
                        .anyMatch(d -> d.codigo() == code);

                if(!check){
                    System.out.println("Código inválido, tente novamente!");
                } else {
                    finalCode = code;
                }
            } catch (NumberFormatException ex){
                System.out.println("Código inválido, tente novamente!");
            }
        }
        return finalCode;
    }

    public Integer codeModelChoice(ModelData models){
        boolean check = false;
        int finalCode = -1;

        while(!check){

            try{
                System.out.println("\nDigite o código do modelo: ");
                var code = Integer.parseInt(scanner.nextLine());

                check = models.models().stream()
                        .anyMatch(d -> d.codigo() == code);

                if(!check){
                    System.out.println("Código inválido, tente novamente!");
                } else {
                    finalCode = code;
                }
            } catch (NumberFormatException ex){
                System.out.println("Código inválido, tente novamente!");
            }
        }
        return finalCode;
    }
}
