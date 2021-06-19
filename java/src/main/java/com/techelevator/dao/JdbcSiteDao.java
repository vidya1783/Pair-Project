package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcSiteDao implements SiteDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSiteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> getSitesThatAllowRVs(int parkId) {
        List<Site> sites= new ArrayList<>();

        String sql = "SELECT site.site_id, site.campground_id, site.site_number,site.max_occupancy," +
                "site.accessible,site.max_rv_length, site.utilities " +
                "FROM site " +
                "JOIN campground ON site.campground_id = campground.campground_id " +
                "WHERE campground.park_id = ? AND site.max_rv_length != 0;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,parkId);
        while(results.next()){
            sites.add(mapRowToSite(results));
        }
        return sites;
    }

   /* The application needs the ability to search for currently available
    sites in a given park for a customer who didn't make a reservation.
    A site is unavailable if there's a current reservation for the site.*/


    /*public List<Site> searchAvailableSites (int parkId){
        List<Site> sites = new ArrayList<>();
        String sql = "SELECT site.site_id, site.campground_id,site.site_number,site.max_occupancy "

    }*/

    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteId(results.getInt("site_id"));
        site.setCampgroundId(results.getInt("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        /*if (!(results.getInt("max_rv_length") =0)){
            site.setMaxRvLength(results.getInt("max_rv_length");
        };*/
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }
}
