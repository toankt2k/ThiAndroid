package com.example.thuchanh2.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thuchanh2.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    private final SQLiteHelper helper;

    private TicketDao(Context context) {
        helper = new SQLiteHelper(context);
    }

    public void insert(Ticket tk) {
        String sql = "INSERT INTO ticket(name, depart, " +
                "date, has_package, price) VALUES(?,?,?,?,?)";
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] args = {
                tk.getName(), tk.getDepartStr(),
                tk.getDateStr(), tk.getHasPackageStr(),
                tk.getPriceStr()
        };
        db.execSQL(sql, args);
    }

    public void update(Ticket tk) {
        String sql = "UPDATE ticket SET name=?, depart=?," +
                "date=?, has_package=?, price=? where id=?";
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] args = {
                tk.getName(), tk.getDepartStr(),
                tk.getDateStr(), tk.getHasPackageStr(),
                tk.getPriceStr(), tk.getIdStr()
        };
        db.execSQL(sql, args);
    }

    public void delete(Ticket em) {
        String sql = "DELETE FROM ticket where id=?";
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] args = {em.getIdStr()};
        db.execSQL(sql, args);
    }

    public List<Ticket> getAll() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM ticket";
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] args = {};
        try (Cursor cursor = db.rawQuery(sql, args)) {
            while (cursor.moveToNext()) {
                Ticket ticket = new Ticket();
                ticket.setId(cursor.getInt(0));
                ticket.setName(cursor.getString(1));
                ticket.setDepart(cursor.getInt(2));
                ticket.setDate(cursor.getString(3));
                ticket.setHasPackage(cursor.getString(4));
                ticket.setPrice(cursor.getInt(5));
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    private static TicketDao dao;

    public static TicketDao getInstance(Context context) {
        if (dao == null) {
            dao = new TicketDao(context);
        }
        return dao;
    }
}
