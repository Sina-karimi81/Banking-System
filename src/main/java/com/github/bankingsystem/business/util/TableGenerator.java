package com.github.bankingsystem.business.util;

import com.github.bankingsystem.database.entity.BankAccount;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;

import java.util.List;

public class TableGenerator {

    public static TableBuilder listToArrayTableModel(List<BankAccount> accounts) {
        ArrayTableModel model = new ArrayTableModel(accounts.stream()
                .map(v -> new String[]{v.getId(), v.getName(), String.valueOf(v.getAmount())})
                .toArray(String[][]::new));
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light_double_dash);
        return tableBuilder;
    }

}
