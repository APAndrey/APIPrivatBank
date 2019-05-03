package com.company.andreypetrus;

import com.google.gson.Gson;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter date dd.mm.yyyy: ");
        Scanner scanner = new Scanner(System.in);
        String inputDate = scanner.nextLine();

        long timeNow = System.currentTimeMillis();
        String response = HttpUtil.sendRequest("https://api.privatbank.ua/p24api/exchange_rates?json=true&date=" + inputDate, null, null);
        timeNow = System.currentTimeMillis() - timeNow;
        System.out.println(response);
        System.out.println("Request timeNow: " + timeNow / 1000.0 + "s");

        Gson gson = new Gson();
        Bank bankAtDate = gson.fromJson(response, Bank.class);

        for (Currency currency : bankAtDate.getExchangeRate()) {
            if (currency.getCurrency() != null && currency.getCurrency().equals("USD")) {
                System.out.println("Курс покупки: " + currency.getPurchaseRate());
                System.out.println("Курс продажи: " + currency.getSaleRate());
            }
        }

    }
}

