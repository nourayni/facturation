package com.nourayni.facturation.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class GenerateNumFacture {
    public static String generateInvoiceNumber() {
        // Partie date/heure (AAAA-MM-JJ-HH-mm-ss)
        String dateTimePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // Partie UUID (Unique ID tronqué)
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

        // Combine la date et la partie UUID pour assurer l'unicité, limité à 6 caractères
        String invoiceNumber = dateTimePart.substring(dateTimePart.length() - 4) + uuidPart;

        return invoiceNumber;
    }

}
