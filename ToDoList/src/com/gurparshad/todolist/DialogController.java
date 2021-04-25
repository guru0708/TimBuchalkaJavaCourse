package com.gurparshad.todolist;

import com.gurparshad.todolist.datamodel.TodoData;
import com.gurparshad.todolist.datamodel.Todoitem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField shortDescriptionText;
    @FXML
    private TextArea detailsText;
    @FXML
    private DatePicker deadlineDatePicker;

    public Todoitem processResults() {
        String shortDescription = shortDescriptionText.getText().trim();
        String details = detailsText.getText().trim();
        LocalDate deadlineValue = deadlineDatePicker.getValue();

        Todoitem newItem = new Todoitem(shortDescription, details, deadlineValue);
        TodoData.getInstance().addTodoItem(newItem);
        return newItem;
    }
}
