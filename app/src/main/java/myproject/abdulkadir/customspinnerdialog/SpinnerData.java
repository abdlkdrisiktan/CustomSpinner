package myproject.abdulkadir.customspinnerdialog;

import myproject.abdulkadir.customspinnerdialog.spinner.listeners.Searchable;

public class SpinnerData implements Searchable {

    private String data;

    public SpinnerData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public SpinnerData setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String getString() {
        return data;
    }
}
