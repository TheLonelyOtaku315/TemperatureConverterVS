/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Cell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 *
 * @author Tonny
 * @param <S>
 * @param <T>
 */
public class TooltippedTableCell<S, T> extends TableCell<S, T> {

    public static <S> Callback<TableColumn<S, String>, TableCell<S, String>> forTableColumn() {
        return forTableColumn(new DefaultStringConverter());
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(final StringConverter<T> converter) {
        return list -> new TooltippedTableCell<>(converter);
    }

    private static <T> String getItemText(Cell<T> cell, StringConverter<T> converter) {
        return converter == null ? cell.getItem() == null ? "" : cell.getItem()
                .toString() : converter.toString(cell.getItem());
    }

    private void updateItem(final Cell<T> cell, final StringConverter<T> converter) {

        if (cell.isEmpty()) {
            cell.setText(null);
            cell.setTooltip(null);

        } else {
            cell.setText(getItemText(cell, converter));

            //Add text as tooltip so that user can read text without editing it.
            Tooltip tooltip = new Tooltip(getItemText(cell, converter));
            tooltip.setWrapText(true);
            tooltip.prefWidthProperty().bind(cell.widthProperty());
            cell.setTooltip(tooltip);

        }
    }

    private ObjectProperty<StringConverter<T>> converter;

    public TooltippedTableCell() {
        this(null);
        this.converter = new SimpleObjectProperty<>(this, "converter");
    }

    public TooltippedTableCell(StringConverter<T> converter) {
        this.converter = new SimpleObjectProperty<>(this, "converter");
        this.getStyleClass().add("tooltipped-table-cell");
        setConverter(converter);
    }

    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }

    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        updateItem(this, getConverter());
    }
}
