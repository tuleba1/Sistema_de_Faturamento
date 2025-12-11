package br.com.sistema.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static void salvar(String caminho, List<String> linhas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        }
    }

    public static List<String> ler(String caminho) throws IOException {
        List<String> linhas = new ArrayList<>();

        File file = new File(caminho);
        if (!file.exists()) return linhas;

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        }
        return linhas;
    }
}
