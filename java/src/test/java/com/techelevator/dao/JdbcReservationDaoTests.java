package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcReservationDaoTests extends BaseDaoTests {


    private ReservationDao dao;
    private Reservation testReservation;

    @Before
    public void setup() {
        dao = new JdbcReservationDao(dataSource);
      /*  testReservation = new Reservation(9999,"Test Testerson",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));*/
    }

    @Test
    public void createReservation_Should_ReturnNewReservationId() {
        int currentSiteReservation = dao.getReservationsByParkId(99).size();
        int reservationCreated = dao.createReservation(9999,
                "TEST NAME",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3));
        int postInsertReservation = dao.getReservationsByParkId(99).size();
        assertEquals(currentSiteReservation+1, postInsertReservation);
        dao.
    }

    @Test
    public void upcomingReservation_Should_ReturnListOfReservations(){
        List <Reservation> reservations = dao.upcomingReservation(99).size();

    }
}