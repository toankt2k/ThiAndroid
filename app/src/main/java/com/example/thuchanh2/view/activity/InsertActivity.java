package com.example.thuchanh2.view.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thuchanh2.R;
import com.example.thuchanh2.db.TicketDao;
import com.example.thuchanh2.model.Ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsertActivity extends AppCompatActivity {

    private EditText edtName, edtDate, edtPrice;
    private RadioButton rd1, rd2, rd3, rd4, rd5, rdY, rdN;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener listener;
    private SimpleDateFormat format;
    private Calendar calendar;
    private TicketDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        edtName = findViewById(R.id.edt_name);
        edtDate = findViewById(R.id.edt_date);
        edtPrice = findViewById(R.id.edt_price);
        rdY = findViewById(R.id.radio_yes);
        rdN = findViewById(R.id.radio_no);
        rd1 = findViewById(R.id.radio_1);
        rd2 = findViewById(R.id.radio_2);
        rd3 = findViewById(R.id.radio_3);
        rd4 = findViewById(R.id.radio_4);
        rd5 = findViewById(R.id.radio_5);

        format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        listener = (datePicker, year, month, date) -> {
            calendar.set(year, month, date);
            edtDate.setText(format.format(calendar.getTime()));
        };
        dao = TicketDao.getInstance(this);
    }

    private Ticket getTicket() {
        Date date;
        int price = 0;
        try {
            date = format.parse(edtDate.getText().toString());
        } catch (ParseException e) {
            showMessage("Ngày không hợp lệ.");
            return null;
        }
        if (edtName.getText() == null || edtName.length() == 0) {
            showMessage("Chưa nhập tên.");
            return null;
        }
        try {
            price = Integer.parseInt(edtPrice.getText().toString());
        } catch (Exception e) {
            showMessage("Giá không hợp lệ.");
        }
        String name = edtName.getText().toString();
        int depart = 0;
        if (rd2.isChecked()) {
            depart = 1;
        }
        if (rd3.isChecked()) {
            depart = 2;
        }
        if (rd4.isChecked()) {
            depart = 3;
        }
        if (rd5.isChecked()) {
            depart = 4;
        }
        Ticket ticket = new Ticket();
        ticket.setHasPackage(rdY.isChecked());
        ticket.setDepart(depart);
        ticket.setName(name);
        ticket.setDate(date);
        ticket.setPrice(price);
        return ticket;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void pickDate(View view) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(
                    (Context) this, listener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE)
            );
        }
        datePickerDialog.show();
    }

    public void insertTicket(View view) {
        Ticket ticket = getTicket();
        if (ticket != null) {
            dao.insert(ticket);
            showMessage("Đặt vé thành công");
        }
    }
}
