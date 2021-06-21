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

        int createReservation = jdbcTemplate.update(sql, siteId, name, fromDate, toDate);

        return createReservation;

    }
    @Override
    public List<Reservation> viewUpcomingReservations(int parkId) {
        List<Reservation> futureReservations = new ArrayList<>();
        String sql = "SELECT reservation_id, r.site_id, r.name, from_date, to_date, create_date "
                + "FROM reservation r " + "JOIN site s ON s.site_id = r.site_id "
                + "JOIN campground c ON c.campground_id = s.campground_id "
                + "WHERE from_date  >= CURRENT_DATE and from_date <= (CURRENT_DATE + INTERVAL '30 days')AND park_id = ? "
                + "ORDER BY create_date DESC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
        while (results.next()){
            futureReservations.add(mapRowToReservation(results));
        }
        return futureReservations;
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
