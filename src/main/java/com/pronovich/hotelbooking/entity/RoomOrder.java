package com.pronovich.hotelbooking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RoomOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private BigDecimal amount;

    private Room room;

    private User user;

    private OrderStatus orderStatus;

    private boolean isPaid;

    public RoomOrder() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomOrder roomOrder = (RoomOrder) o;

        if (isPaid != roomOrder.isPaid) return false;
        if (id != null ? !id.equals(roomOrder.id) : roomOrder.id != null) return false;
        if (checkInDate != null ? !checkInDate.equals(roomOrder.checkInDate) : roomOrder.checkInDate != null)
            return false;
        if (checkOutDate != null ? !checkOutDate.equals(roomOrder.checkOutDate) : roomOrder.checkOutDate != null)
            return false;
        if (amount != null ? !amount.equals(roomOrder.amount) : roomOrder.amount != null) return false;
        if (room != null ? !room.equals(roomOrder.room) : roomOrder.room != null) return false;
        if (user != null ? !user.equals(roomOrder.user) : roomOrder.user != null) return false;
        return orderStatus == roomOrder.orderStatus;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (checkInDate != null ? checkInDate.hashCode() : 0);
        result = 31 * result + (checkOutDate != null ? checkOutDate.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (isPaid ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return  "check-in " + checkInDate +
                ", check-out " + checkOutDate +
                ", amount " + amount +
                ", room № " + room +
                ", user " + user +
                ", status " + orderStatus +
                ", order is paid " + isPaid;
    }
}
