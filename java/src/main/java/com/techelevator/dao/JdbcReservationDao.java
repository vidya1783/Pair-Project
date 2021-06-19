package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JdbcReservationDao implements ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) {

        String sql = "INSERT INTO reservation (site_id, name, from_date, to_date) " +
                "VALUES (?, ?, ?, ?);";

        int newId = jdbcTemplate.update(sql, siteId, name, fromDate, toDate, LocalDate.now());

        return newId;

    }
   /* The application needs the ability to view a list of all upcoming
    reservations within the next 30 days for a selected park.
    A reservation includes a reservation ID, site ID, name, start date, end date, and date created.
    Find the correct classes where you'll need to write this method and test.
    The test data returns two reservations for test parkId 99.*/

    @Override
    public List<Reservation> upcomingReservation(int parkId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.reservation_id,  r.site_id, r.name, r.from_date, r.to_date, r.create_date" +
                "FROM reservation r" +
                "JOIN site ON r.site_id = s.site_id" +
                "JOIN campground ON site.campground_id = campground.campground_id" +
                "JOIN park ON campground.park_id = park.park_id" +
                "WHERE park_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
        while(results.next()){
            reservations.add(mapRowToReservation(results));
        }
        return reservations;
    }

    private Reservation mapRowToReservation(SqlRowSet results) {
        Reservation r = new Reservation();
        r.setReservationId(results.getInt("reservation_id"));
        r.setSiteId(results.getInt("site_id"));
        r.setName(results.getString("name"));
        r.setFromDate(results.getDate("from_date").toLocalDate());
        r.setToDate(results.getDate("to_date").toLocalDate());
        r.setCreateDate(results.getDate("create_date").toLocalDate());
        return r;
    }


}
