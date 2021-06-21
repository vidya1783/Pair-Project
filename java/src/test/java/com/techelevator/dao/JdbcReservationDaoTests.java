package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcReservationDaoTests extends BaseDaoTests {


    private ReservationDao dao;
    private Reservation testReservation;

    @Before
    public void setup() {
        dao = new JdbcReservationDao(dataSource);

    }

    @Test
    public void createReservation_Should_ReturnNewReservationId() {
        int createdReservation = dao.createReservation(9999,"TEST NAME",LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3));
          Assert.assertEquals(createdReservation,1);

    }

   @Test
    public void viewUpcomingReservations_Should_Return_Reservations(){
       List <Reservation> reservations = dao.viewUpcomingReservations(99);
       Assert.assertEquals(2, reservations.size());

   }
}