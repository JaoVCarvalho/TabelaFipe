package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.*;
import br.com.alura.TabelaFipe.service.ConsumptionAPI;
import br.com.alura.TabelaFipe.service.ConvertsData;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private static final String ADDRESS = "https://parallelum.com.br/fipe/api/v1";
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumptionAPI consume = new ConsumptionAPI();
    private final ConvertsData convert = new ConvertsData();


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

        var codeBrand = brandChoice(address);

        address = address + "/" + codeBrand + "/modelos";

        var codeModel = modelChoice(address);

        address = address + "/" + codeModel + "/anos";
        vehicleYears(address);

        System.out.println("\nAplicação encerrada com sucesso!");
    }

    public String vehicleChoice() {
        boolean check = false;
        String address = null;
        String choice = null;
        while (!check) {
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

    public Integer brandChoice(String address) {

        var json = consume.getDataAPI(address);

        System.out.println("\nLista completa de Códigos/Marcas\n");

        var list = convert.getList(json, Brand.class);
        list.stream()
                .sorted(Comparator.comparing(Brand::codigo))
                .forEach(System.out::println);

        list = filterBrand(list);
        list.stream()
                .sorted(Comparator.comparing(Brand::codigo))
                .forEach(System.out::println);

        return codeBrandChoice(list);
    }

    public Integer codeBrandChoice(List<Brand> list) {
        boolean check = false;
        int finalCode = -1;

        while (!check) {

            try {
                System.out.println("\nDigite o código da marca: ");
                var code = Integer.parseInt(scanner.nextLine());

                check = list.stream()
                        .anyMatch(d -> d.codigo() == code);

                if (!check) {
                    System.out.println("Código inválido, tente novamente!");
                } else {
                    finalCode = code;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Código inválido, tente novamente!");
            }
        }
        return finalCode;
    }

    public Integer modelChoice(String address){

        var json = consume.getDataAPI(address);

        System.out.println("\nLista completa de Códigos/Modelos\n");

        var models = convert.getData(json, ModelData.class);
        models.models().stream()
                .sorted(Comparator.comparing(Model::codigo))
                .forEach(System.out::println);

        var newModels = filterModel(models);
        newModels.stream()
                .sorted(Comparator.comparing(Model::codigo))
                .forEach(System.out::println);

        return codeModelChoice(newModels);
    }

    public Integer codeModelChoice(List<Model> models) {
        boolean check = false;
        int finalCode = -1;

        while (!check) {

            try {
                System.out.println("\nDigite o código do modelo: ");
                var code = Integer.parseInt(scanner.nextLine());

                check = models.stream()
                        .anyMatch(d -> d.codigo() == code);

                if (!check) {
                    System.out.println("Código inválido, tente novamente!");
                } else {
                    finalCode = code;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Código inválido, tente novamente!");
            }
        }
        return finalCode;
    }

    public void vehicleYears(String address){
        var json = consume.getDataAPI(address);
        var years = convert.getList(json, Year.class);

        List<Vehicle> vehicles = new ArrayList<>();

        for (Year year : years) {
            String newAddress = address + "/" + year.codigo();
            json = consume.getDataAPI(newAddress);
            var vehicle = convert.getData(json, Vehicle.class);
            vehicles.add(vehicle);
        }

        System.out.println("\nLista completa com todos os anos desse modelo: \n");
        vehicles.stream()
                .sorted(Comparator.comparing(Vehicle::anoModelo))
                .forEach(System.out::println);
    }

    public List<Brand> filterBrand(List<Brand> list) {
        System.out.println("""
                                
                Muitas informações?.... Deseja filtar?
                S. Sim
                N. Não
                """);
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("s") || choice.equals("sim")) {

            boolean check = false;
            List<Brand> newList = null;

            while (!check) {
                System.out.println("\nDigite a Letra/Marca que deseja filtrar: ");
                String brand = scanner.nextLine();
                newList = list.stream()
                        .filter(l -> l.nome().toLowerCase().contains(brand.toLowerCase()))
                        .collect(Collectors.toList());

                if (newList.isEmpty()) {
                    System.out.println("\nMarca não encontrada, tente novamente!");
                } else {
                    check = true;
                }
            }
            return newList;
        } else {
            System.out.println("\nNão?... OK");
            return list;
        }
    }

    public List<Model> filterModel(ModelData models){
        System.out.println("""
                                
                Muitas informações?.... Deseja filtar?
                S. Sim
                N. Não
                """);
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("s") || choice.equals("sim")) {

            boolean check = false;
            List<Model> newList = null;

            while (!check) {
                System.out.println("\nDigite a Letra/Modelo que deseja filtrar: ");
                String brand = scanner.nextLine();
                newList = models.models().stream()
                        .filter(l -> l.nome().toLowerCase().contains(brand.toLowerCase()))
                        .collect(Collectors.toList());

                if (newList.isEmpty()) {
                    System.out.println("\nMarca não encontrada, tente novamente!");
                } else {
                    check = true;
                }
            }
            return newList;
        } else {
            System.out.println("\nNão?... OK");
            return new ArrayList<>(models.models());

        }
    }
}
