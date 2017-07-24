package com.pronovich.hotelbooking.entity;

import com.pronovich.hotelbooking.entity.propertyenum.RoomType;

import java.io.Serializable;
import java.math.BigDecimal;

public class Room  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer roomNumber;

    private Integer size;

    private BigDecimal price;

    private RoomType roomType;

    public Room() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != null ? !id.equals(room.id) : room.id != null) return false;
        if (roomNumber != null ? !roomNumber.equals(room.roomNumber) : room.roomNumber != null) return false;
        if (size != null ? !size.equals(room.size) : room.size != null) return false;
        if (price != null ? !price.equals(room.price) : room.price != null) return false;
        return roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (roomType != null ? roomType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Room № " + roomNumber +
                ", type=" + roomType;
    }
}
