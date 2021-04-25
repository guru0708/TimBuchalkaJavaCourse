package com.gurparshad.todolist.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class TodoData {
    private static TodoData instance = new TodoData();
    private static String filename = "TodoListItems.txt";

    private ObservableList<Todoitem> todoitems;
    private DateTimeFormatter formatter;

    public static TodoData getInstance() {
        return instance;
    }

    private TodoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<Todoitem> getTodoitems() {
        return todoitems;
    }

    public void addTodoItem(Todoitem item) {
        todoitems.add(item);
    }

//    public void setTodoitems(List<Todoitem> todoitems) {
//        this.todoitems = todoitems;
//    }

    public void loadTodoItems() throws IOException {
        todoitems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);

        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);

                Todoitem todoitem = new Todoitem(shortDescription, details, date);
                todoitems.add(todoitem);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeTodoItems() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {
            Iterator<Todoitem> iter = todoitems.iterator();

            while (iter.hasNext()) {
                Todoitem item = iter.next();
                bw.write(String.format("%s\t%s\t%s", item.getShortDescription(), item.getDetails(), item.getDeadline().format(formatter)));
                bw.newLine();
            }
        } finally {
            if(bw != null) {
                bw.close();
            }
        }
    }

    public void deleteTodoItem(Todoitem item) {
        todoitems.remove(item);
    }
}
