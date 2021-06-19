package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCampgroundDao implements CampgroundDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCampgroundDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Campground> getCampgroundsByParkId(int parkId) {

        List<Campground>campgrounds = new ArrayList<>();

        String sql = "SELECT campground.campground_id,campground.park_id, campground.name,campground.open_from_mm, " +
                "campground.open_to_mm, campground.daily_fee " +
                "FROM campground " +
                "JOIN park ON campground.park_id = park.park_id " +
                "WHERE park.park_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,parkId);
        while(results.next()){
            campgrounds.add(mapRowToCampground(results));
        }
        return campgrounds;
    }

    private Campground mapRowToCampground(SqlRowSet results) {
        Campground camp = new Campground();
        camp.setCampgroundId(results.getInt("campground_id"));
        camp.setParkId(results.getInt("park_id"));
        camp.setName(results.getString("name"));
        camp.setOpenFromMonth(results.getInt("open_from_mm"));
        camp.setOpenToMonth(results.getInt("open_to_mm"));
        camp.setDailyFee(results.getDouble("daily_fee"));
        return camp;
    }
}
