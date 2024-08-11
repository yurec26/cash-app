package org.example.cash_app.logger;

import org.example.cash_app.model.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLogger {

    public enum LogType {
        PENDING,
        SUCCESS,
        FAILED,
        UNKNOWN
    }

    private final static String INCOME_TRANS = "Пришёл запрос на транзакцию, детали : ";
    private final static String COMP_TRANS = "Транзакция обработана : ";
    private final static String STAT_PENDING = "статус : в обратобтке.";
    private final static String STAT_SUCCESS = "статус : успепшно.";
    private final static String STAT_FAILED = "статус : отклонено.";
    private final static String CARD_UNKNOWN = "транзакция не обнаружена";
    //
    private final static String LOG_PATH = "src/main/resources/log.txt";
    private final static String LOG_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public static void logProcessedTrans(Transaction transaction, LogType logType) {
        StringBuilder logBuilder = new StringBuilder();
        if (logType.equals(LogType.PENDING)) {
            logBuilder.append(INCOME_TRANS);
        } else {
            logBuilder.append(COMP_TRANS);
        }
        //
        logBuilder.append(transaction.toString());
        //
        switch (logType) {
            case PENDING -> logBuilder.append(STAT_PENDING);
            case SUCCESS -> logBuilder.append(STAT_SUCCESS);
            case FAILED -> logBuilder.append(STAT_FAILED);
        }
        log(logBuilder.toString());
    }

    public static void logUnknown(LogType logType) {
        StringBuilder logBuilder = new StringBuilder();
        if (logType.equals(LogType.UNKNOWN)) {
            logBuilder.append(CARD_UNKNOWN).append(" ").append(STAT_FAILED);
        }
        log(logBuilder.toString());
    }

    public static void log(String msg) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOG_DATE_PATTERN);
        String timestamp = localDateTime.format(formatter);
        //
        String logMessage = String.format("[%s] %s%n", timestamp, msg);
        //
        Path path = Path.of(LOG_PATH);
        //
        try {
            // Проверяем, существует ли файл, и создаем его, если нет
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            // Записываем сообщение в файл в режиме добавления
            Files.write(path, logMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException exception) {
            System.out.println("wrong path");
        }
    }
}
