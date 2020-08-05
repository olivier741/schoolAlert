package com.tatsinktech.web.util;

import com.tatsinktech.web.model.register.Promotion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.tatsinktech.web.model.register.PromoTable;

public class CsvUtils {

//    public static void promotionTableToCsv(PrintWriter writer, List<PromoTable> listpromoTab) throws IOException {
//
//        try (
//                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("msisdn"));
//
//                ) {
//            for (PromoTable promoTab : listpromoTab) {
//                List<String> data = Arrays.asList(
//                        String.valueOf(promoTab.getId()),
//                        promoTab.getMsisdn(),
//                        promoTab.getPromotion().getPromotionName()
//                );
//
//                csvPrinter.printRecord(data);
//            }
//            csvPrinter.flush();
//        } catch (Exception e) {
//            System.out.println("Writing CSV error!");
//            e.printStackTrace();
//        }
//    }

    public static List<String> parseCsvFile(InputStream is) {
        BufferedReader fileReader = null;
        CSVParser csvParser = null;

        List<String> listMsisdn = new ArrayList<String>();

        try {
            fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                listMsisdn.add(csvRecord.get("msisdn"));
            }

        } catch (Exception e) {
            System.out.println("Reading CSV Error!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Closing fileReader/csvParser Error!");
                e.printStackTrace();
            }
        }

        return listMsisdn;
    }
}
